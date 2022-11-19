package com.thread.two;

import static java.lang.Thread.sleep;

public class WaitNotifyTwo {
    final static Object lock = new Object();
    public static void main(String[] args) throws InterruptedException {
        //sleepMethod();
        waitMethod();
    }

    private static void waitMethod() throws InterruptedException {
        new Thread(()->{
            synchronized (lock){
                System.out.println("t1:获得锁");
                try{
                    lock.wait(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("t1:其它代码。。。。。");
            }
        },"t1").start();

        //主线程两秒后执行
        sleep(1000);
        synchronized (lock){
            System.out.println("主：获得锁");
        }
    }

    private static void sleepMethod() throws InterruptedException {
        new Thread(()->{
            synchronized (lock){
                System.out.println("t1:获得锁");
                try{
                   Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("t1:其它代码。。。。。");
            }
        },"t1").start();

        //主线程两秒后执行
        sleep(1000);
        synchronized (lock){
            System.out.println("主：获得锁");
        }
    }
}
