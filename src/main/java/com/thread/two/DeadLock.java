package com.thread.two;

import static java.lang.Thread.sleep;

public class DeadLock {
    public static void main(String[] args){
        Object A = new Object();
        Object B = new Object();
        Thread t1 = new Thread(()->{
            synchronized (A){
                System.out.println("t1 lock A");
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized (B){
                    System.out.println("t1 lock B");
                    System.out.println("t1 操作");
                }

            }
        },"t1");

        Thread t2 = new Thread(()->{
            synchronized (B){
                System.out.println("t2 lock B");
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized (A){
                    System.out.println("t2 lock A");
                    System.out.println("t2 操作");
                }

            }
        },"t2");

        t1.start();
        t2.start();

    }
}
