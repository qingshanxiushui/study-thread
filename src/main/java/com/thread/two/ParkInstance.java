package com.thread.two;

import java.util.concurrent.locks.LockSupport;

import static java.lang.Thread.sleep;

public class ParkInstance {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(()->{
            System.out.println("t1 start...");
            try {
                //sleep(1000);
                sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("t1 park...");
            LockSupport.park();
            System.out.println("t1 resume...");
        },"t1");
        t1.start();
        sleep(5000);
        System.out.println("main unpark...");
        LockSupport.unpark(t1);
    }
};
