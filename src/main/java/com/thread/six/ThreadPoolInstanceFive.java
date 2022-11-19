package com.thread.six;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ThreadPoolInstanceFive {
    public static void main(String[] args) throws InterruptedException {
        //shutdown
        //methodOne();
        //shutdownNow
        methodTwo();
    }

    private static void methodTwo() {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        Future<Integer> result1 = pool.submit(()->{
            System.out.println("task 1 running ....");
            Thread.sleep(1000);
            System.out.println("task 1 finish ....");
            return 1;
        });
        Future<Integer> result2 = pool.submit(()->{
            System.out.println("task 2 running ....");
            Thread.sleep(1000);
            System.out.println("task 2 finish ....");
            return 2;
        });
        Future<Integer> result3 = pool.submit(()->{
            System.out.println("task 3 running ....");
            Thread.sleep(1000);
            System.out.println("task 3 finish ....");
            return 3;
        });
        System.out.println("shutdown");
        List<Runnable> runnables = pool.shutdownNow();
        System.out.println("other ..."+runnables);
    }

    private static void methodOne() throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        Future<Integer> result1 = pool.submit(()->{
            System.out.println("task 1 running ....");
            Thread.sleep(1000);
            System.out.println("task 1 finish ....");
            return 1;
        });
        Future<Integer> result2 = pool.submit(()->{
            System.out.println("task 2 running ....");
            Thread.sleep(1000);
            System.out.println("task 2 finish ....");
            return 2;
        });
        Future<Integer> result3 = pool.submit(()->{
            System.out.println("task 3 running ....");
            Thread.sleep(1000);
            System.out.println("task 3 finish ....");
            return 3;
        });
        System.out.println("shutdown");
        pool.shutdown();
        pool.awaitTermination(3, TimeUnit.SECONDS);
        System.out.println("other ...");
        Future<Integer> result4 = pool.submit(()->{
            System.out.println("task 4 running ....");
            Thread.sleep(1000);
            System.out.println("task 4 finish ....");
            return 4;
        });
    }
}
