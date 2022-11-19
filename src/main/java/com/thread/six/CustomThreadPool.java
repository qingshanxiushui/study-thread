package com.thread.six;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class CustomThreadPool {
    public static void main(String[] args){
        ThreadPool threadPool = new ThreadPool(
                1,1000,TimeUnit.MILLISECONDS,1
                , (queue,task)->{
                    //1 死等
                    //queue.put(task);
                    //2 带超时等待
                    //queue.offer(task,500,TimeUnit.MILLISECONDS);
                    //3 让调用者抛出异常
                    //System.out.println("放弃"+task);
                    //4 让调用者抛出异常
                    //throw new RuntimeException("任务执行失败"+task);
                    //5 让调用者自己执行任务
                    //task.run();
                }
        );
        for(int i=0;i<3;i++){
            int j=i;
            threadPool.execute(()->{
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(j);
            });
        }
    }
}
class BlockingQueue<T>{
    //1 任务队列
    private Deque<T> queue = new ArrayDeque<>();
    //2 锁
    private ReentrantLock lock = new ReentrantLock();
    //3 生产者条件变量
    private Condition fullWaitSet = lock.newCondition();
    //4 消费者条件变量
    private  Condition emptyWaitSet = lock.newCondition();
    //5 容量
    private int capcity;
    public BlockingQueue(int queueCapcity) {
        this.queue = new ArrayDeque(queueCapcity);
        this.capcity = queueCapcity;
    }

    //阻塞获取
    public T take(){
        lock.lock();
        try {
            while (queue.isEmpty()){
                try {
                    emptyWaitSet.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            T t = queue.removeFirst();
            fullWaitSet.signal();
            return t;
        }finally {
            lock.unlock();
        }
    }
    //阻塞添加
    public void put(T element){
        lock.lock();
        try {
            while (queue.size() == capcity){
                try {
                    System.out.println("等待加入队列。。。"+element);
                    fullWaitSet.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("加入任务队列"+element);
            queue.addLast(element);
            emptyWaitSet.signal();
        }finally {
            lock.unlock();
        }
    }
    //获取大小
    public int size(){
        lock.lock();
        try {
            return queue.size();
        }finally {
            lock.unlock();
        }
    }
    //带超时的阻塞获取
    public T poll(long timeout, TimeUnit unit){
        lock.lock();
        try {
            //将timeout统一转换为纳秒
            long nanos = unit.toNanos(timeout);
            while (queue.isEmpty()){
                try{
                    //返回的是剩余时间
                    if(nanos <= 0){
                        return null;
                    }
                    nanos = emptyWaitSet.awaitNanos(nanos);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            T t = queue.removeFirst();
            fullWaitSet.signal();
            return t;
        }finally {
            lock.unlock();
        }
    }
    //帶超時時間阻塞添加
    public boolean offer(T task,long timeout, TimeUnit timeUnit){
        lock.lock();
        try {
            long nanos = timeUnit.toNanos(timeout);
            while (queue.size() == capcity){
                try {
                    System.out.println("等待加入任务队列。。"+task);
                    if (nanos <=0){
                        return false;
                    }
                    nanos = fullWaitSet.awaitNanos(nanos);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("加入任务队列："+task);
            queue.addLast(task);
            emptyWaitSet.signal();
            return true;
        }finally {
            lock.unlock();
        }
    }
    public void tryPut(RejectPolicy<T> rejectPolicy, T task){
        lock.lock();
        try {
            //判断队列是否满
            if (queue.size() == capcity){
                rejectPolicy.reject(this,task);
            }else {//有空闲
                System.out.println("加入任务队列"+task);
                queue.addLast(task);
                emptyWaitSet.signal();
            }
        }finally {
            lock.unlock();
        }
    }

}
class ThreadPool{
    //任务队列
    private BlockingQueue<Runnable> taskQueue;
    //线程集合
    private HashSet<Worker> workers = new HashSet<>();
    //核心线程数
    private int coreSize;
    //获取任务时的超时时间
    private long timeout;
    private TimeUnit timeUnit;
    private RejectPolicy<Runnable> rejectPolicy;
    public ThreadPool(int coreSize,long timeout,TimeUnit timeUnit,int queueCapcity,RejectPolicy<Runnable> rejectPolicy){
        this.coreSize = coreSize;
        this.timeout = timeout;
        this.timeUnit = timeUnit;
        this.taskQueue = new BlockingQueue<>(queueCapcity);
        this.rejectPolicy = rejectPolicy;
    }
    //执行任务
    public void execute(Runnable task){
        //当任务数没有超过coreSize时,直接交给worker对象执行
        //如果任务数超过coreSize时,加入任务队列暂存
        synchronized (workers){
            if (workers.size() < coreSize){
                Worker worker = new Worker(task);
                System.out.println("新增worker:"+worker+task);
                workers.add(worker);
                worker.start();
            }else{
                System.out.println("加入任务队列："+task);
                //taskQueue.put(task);
                //1 死等
                //2 带超时等待
                //3 让调用者放弃任务执行
                //4 让调用者抛出异常
                //5 让调用者自己执行任务
                //使用设计模式中的策略模式 具体实现抽象成接口 到时候让调用者传递进来
                taskQueue.tryPut(rejectPolicy,task);
            }
        }
    }

    class Worker extends Thread{
        private Runnable task;
        public Worker(Runnable task){
            this.task = task;
        }
        public void run(){
            //执行任务
            //1 当task不为空，执行任务
            //2 当task执行完毕，再接着从任务队列获取任务并执行
            //while (task!=null || (task = taskQueue.take())!=null){
            while (task!=null || (task = taskQueue.poll(timeout,timeUnit))!=null){ //超时时间
                try {
                    System.out.println("正在执行"+this);
                    task.run();
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    task = null;
                }
            }
            synchronized (workers){
                System.out.println("worker被移除"+this);
                workers.remove(this);
            }
        }
    }
}
@FunctionalInterface //拒接策略
interface RejectPolicy<T>{
    void reject(BlockingQueue<T> queue, T Task);
}