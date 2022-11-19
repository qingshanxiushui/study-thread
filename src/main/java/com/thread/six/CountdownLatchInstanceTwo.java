package com.thread.six;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Thread.sleep;

public class CountdownLatchInstanceTwo {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(3);
        ExecutorService service = Executors.newFixedThreadPool(4);
        service.submit(()->{
            System.out.println(Thread.currentThread().getName() + ": begin...");
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            latch.countDown();
            System.out.println(Thread.currentThread().getName() + ":end..." + latch.getCount());
        });

        service.submit(()->{
            System.out.println(Thread.currentThread().getName() + ":begin...");
            try {
                sleep(1500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            latch.countDown();
            System.out.println(Thread.currentThread().getName() + ":end..." + latch.getCount());
        });

        service.submit(()->{
            System.out.println(Thread.currentThread().getName() + ":begin...");
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            latch.countDown();
            System.out.println(Thread.currentThread().getName() + ":end..." + latch.getCount());
        });

        service.submit(()->{
            try {
                System.out.println(Thread.currentThread().getName() + ":wait...");
                latch.await();
                System.out.println(Thread.currentThread().getName() + ":end...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

    }
}
