package com.thread.two;

import static java.lang.Thread.sleep;

public class SynchronizedCaseSix {

    //1s 后12， 或 2 1s后 1 锁的同一个对象 类对象
    public static void main( String[] args ){
        NumberSix n1 = new NumberSix();
        new Thread( () -> {
            try {
                System.out.println("beginOne");
                n1.a();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        new Thread( () -> {
            System.out.println("beginTwo");
            n1.b();
        }).start();
    }
};
class NumberSix{
    public static synchronized void a() throws InterruptedException {
        sleep(1000);
        System.out.println("1");
    }
    public static synchronized void b(){
        System.out.println("2");
    }
};
