package com.thread.two;

public class SequentialControlOne {

    static final  Object lock = new Object();
    //表示t2是否运行过
    static boolean t2runned = false;

    public static void main(String[] args){
        Thread t1 = new Thread(()->{
            synchronized (lock){
                while (!t2runned){
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("t1 1");
            }
        },"t1");

        Thread t2 = new Thread(()->{
            synchronized (lock){
                System.out.println("t2 2");
                t2runned = true;
                lock.notify();
            }
        });

        t1.start();
        t2.start();
    }
}
