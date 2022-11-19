package com.thread.six;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPoolInstance {
    public static void main(String[] args){
        //线程池中的线程都是非守护线程 不会随着主线程的结束而结束
        //methodOne();
        //自己实现线程工厂
        methodTwo();
    }

    private static void methodTwo() {
        ExecutorService pool = Executors.newFixedThreadPool(2, new ThreadFactory() {
            private AtomicInteger t = new AtomicInteger(1);
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r,"mypool_t"+t.getAndIncrement());
            }
        });
        pool.execute(()->{
            System.out.println(Thread.currentThread().getName()+"1");
        });
        pool.execute(()->{
            System.out.println(Thread.currentThread().getName()+"2");
        });
        pool.execute(()->{
            System.out.println(Thread.currentThread().getName()+"3");
        });
    }

    private static void methodOne() {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        pool.execute(()->{
            System.out.println(Thread.currentThread().getName()+"1");
        });
        pool.execute(()->{
            System.out.println(Thread.currentThread().getName()+"2");
        });
        pool.execute(()->{
            System.out.println(Thread.currentThread().getName()+"3");
        });
    }
}
