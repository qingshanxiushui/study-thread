package com.thread.six;

import java.util.concurrent.SynchronousQueue;

import static java.lang.Thread.sleep;

public class ThreadPoolInstanceTwo {
    public static void main(String[] args) throws InterruptedException {
        SynchronousQueue<Integer> integers = new SynchronousQueue<>();
        new Thread(()->{
            try {
                System.out.println("putting:"+ 1);
                integers.put(1);
                System.out.println("putted:"+1);

                System.out.println("putting:"+ 2);
                integers.put(2);
                System.out.println("putted:"+2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },"t1").start();

        sleep(1000);

        new Thread(()->{
            try {
                System.out.println("taking:"+ 1);
                integers.take();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },"t2").start();

        sleep(1000);

        new Thread(()->{
            try {
                System.out.println("taking:"+ 2);
                integers.take();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },"t3").start();
    }
}

