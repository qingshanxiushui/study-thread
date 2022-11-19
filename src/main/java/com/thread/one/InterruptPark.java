package com.thread.one;

import java.util.concurrent.locks.LockSupport;

import static java.lang.Thread.sleep;

public class InterruptPark {
    public static void main( String[] args ) throws InterruptedException {
        //test1();
        //打断 park 线程, 不会清空打断状态

        //test2();
        //当打断标记为true的时候 即使再调用LockSupport.park()，park失效

        test3();
        //使用Thread.interrupted() 清除打断状态
    }

    private static void test3() throws InterruptedException {
        Thread t1 = new Thread(()->{
            System.out.println("park...");
            LockSupport.park();//暂停当前线程
            System.out.println("unpark...");
            System.out.println("打断状态："+Thread.interrupted());
            LockSupport.park();//暂停当前线程
            System.out.println("unpark...2");
        },"t1");
        t1.start();
        sleep(10);
        t1.interrupt();
    }

    private static void test2() throws InterruptedException {
        Thread t1 = new Thread(()->{
            for(int i = 0;i<5;i++){
                System.out.println("park..."+i);
                LockSupport.park();//暂停当前线程
                System.out.println("打断状态："+i+Thread.currentThread().isInterrupted());
            }
        },"t1");
        t1.start();
        sleep(10);
        t1.interrupt();
    }

    private static void test1() throws InterruptedException {
        Thread t1 = new Thread(()->{
            System.out.println("park...");
            LockSupport.park();//暂停当前线程
            System.out.println("unpark...");
            System.out.println("打断状态："+Thread.currentThread().isInterrupted());
        },"t1");
        t1.start();
        sleep(10);
        t1.interrupt();
    }
}
