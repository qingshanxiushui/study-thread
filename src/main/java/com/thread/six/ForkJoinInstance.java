package com.thread.six;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoinInstance {
    public static void main(String[] args){
        ForkJoinPool pool = new ForkJoinPool(4);

        //一个等另一个结果
        //new MyTask(5) 5 + new MyTask(4) 4 + new MyTask(3) 3 + new MyTask(2) 2 + new MyTask(1) 1
        //System.out.println(pool.invoke(new MyTask(5)));

        //分开算
        System.out.println(pool.invoke(new MyTaskTwo(1,5)));
    }
}
class MyTask extends RecursiveTask<Integer>{
    private int n;
    public MyTask(int n){
        this.n = n;
    }
    public String toString(){
        return "{"+n+"}";
    }
    @Override
    protected Integer compute() {
        //终止条件
        if(n==1){
            System.out.println(Thread.currentThread().getName()+" join:"+n);
            return 1;
        }
        //将任务进行拆分fork
        MyTask t1 = new MyTask(n -1);
        t1.fork();//让一个线程去执行此任务
        System.out.println(Thread.currentThread().getName()+" fork:"+n+"+"+t1);
        //合并(join)结果
        int result = n + t1.join();
        System.out.println(Thread.currentThread().getName()+" fork:"+n+"+"+t1+"="+result);
        return result;
    }
}
class MyTaskTwo extends RecursiveTask<Integer>{
    int begin;
    int end;
    public MyTaskTwo(int begin,int end){
        this.begin = begin;
        this.end = end;
    }
    public String toString(){
        return "{"+begin+","+end+"}";
    }
    @Override
    protected Integer compute() {
        //5,5
        if (begin == end){
            System.out.println(Thread.currentThread().getName() + "join:" + begin);
            return begin;
        }
        //4,5
        if(end - begin == 1){
            System.out.println(Thread.currentThread().getName() + "join:" + begin+"+"+end+"="+(begin+end));
            return (begin + end);
        }
        //1,5
        int mid = (end + begin)/2; //3

        MyTaskTwo t1 = new MyTaskTwo(begin,mid);//1,3
        t1.fork();
        MyTaskTwo t2 = new MyTaskTwo(mid+1,end);//4,5
        t2.fork();
        System.out.println(Thread.currentThread().getName() + "join:" + begin+"+"+end);

        int result = t1.join() + t2.join();
        System.out.println(Thread.currentThread().getName() + "join:" + t1+"+"+t2+"="+result);
        return result;
    }
}