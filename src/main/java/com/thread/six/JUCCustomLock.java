package com.thread.six;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import static java.lang.Thread.sleep;

//自定义锁,(不可重入锁)
public class JUCCustomLock implements Lock {
    //独占锁,同步锁
    class MySync extends AbstractQueuedSynchronizer{
        @Override
        protected boolean tryAcquire(int arg){
            if (compareAndSetState(0,1)){
                //加上了锁，并设置owner，为当前线程
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }
        @Override
        protected boolean tryRelease(int arg){
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }
        @Override //是否持有独占锁
        protected boolean isHeldExclusively(){
            return getState() == 1;
        }
        public Condition newCondition(){
            return new ConditionObject();
        }
    }

    private MySync sync = new MySync();

    @Override //加锁(不成功会进入等待队列)
    public void lock() {
        sync.acquire(1);
    }

    @Override //加锁，可打断
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override //尝试加锁(一次)
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    @Override //尝试加锁,带超时
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1,unit.toNanos(time));
    }

    @Override //解锁
    public void unlock() {
        sync.release(1);
    }

    @Override //创建条件变量
    public Condition newCondition() {
        return sync.newCondition();
    }

    public static void main(String[] args){
        JUCCustomLock lock = new JUCCustomLock();
        new Thread(()->{
            lock.lock();
            System.out.println("t1 locking....1");
            lock.lock();
            System.out.println("t1 locking....2");
            try {
                System.out.println("t1 locking....");
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }finally {
                System.out.println("t1 unlocking ....");
                lock.unlock();
            }
        },"t1").start();
        new Thread(()->{
            lock.lock();
            try {
                System.out.println("t2 locking....");
            }finally {
                System.out.println("t2 unlocking ....");
                lock.unlock();
            }
        },"t2").start();
    }


}
