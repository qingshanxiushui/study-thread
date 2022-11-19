package com.thread.one;

import static java.lang.Thread.sleep;

public class CriticalPath {

    public static void main( String[] args )
    {
        Thread t1 = new Thread(()->{
            try {
                System.out.println("洗水壶");
                sleep(1000);
                System.out.println("烧开水");
                sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },"老王");

        Thread t2 = new Thread(()->{
            try {
                System.out.println("洗茶壶");
                sleep(1000);
                System.out.println("洗茶杯");
                sleep(2000);
                System.out.println("洗茶叶");
                sleep(1000);
                t1.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("泡茶");
        },"小王");

        t1.start();
        t2.start();

    }

}
