package com.thread.two;

import java.util.concurrent.locks.LockSupport;

public class SequentialControlTwo {
    public static void main(String[] args){
        Thread t1 = new Thread(()->{
            LockSupport.park();
            System.out.println("t1 1");
        },"t1");
        t1.start();

        new Thread(()->{
            System.out.println("t2 2");
            LockSupport.unpark(t1);
        }).start();
    }
}
