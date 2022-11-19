package com.thread.six;

import java.util.concurrent.CountDownLatch;

import static java.lang.Thread.sleep;

public class CountdownLatchInstance {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(3);
        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"begin...");
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            latch.countDown();
            System.out.println(Thread.currentThread().getName()+"end...");
        }).start();

        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"begin...");
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            latch.countDown();
            System.out.println(Thread.currentThread().getName()+"end...");
        }).start();

        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"begin...");
            try {
                sleep(1500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            latch.countDown();
            System.out.println(Thread.currentThread().getName()+"end...");
        }).start();

        System.out.println("waiting...");
        latch.await();
        System.out.println("waiting end...");
    }
}
