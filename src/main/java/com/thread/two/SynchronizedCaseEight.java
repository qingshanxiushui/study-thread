package com.thread.two;

import static java.lang.Thread.sleep;

public class SynchronizedCaseEight {

    //1s 后12， 或 2 1s后 1 用的同一个Number.class对象 互斥
    public static void main( String[] args ){
        NumberEight n1 = new NumberEight();
        NumberEight n2 = new NumberEight();
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
            n2.b();
        }).start();
    }
};
class NumberEight{
    public static synchronized void a() throws InterruptedException {
        sleep(1000);
        System.out.println("1");
    }
    public static synchronized void b(){
        System.out.println("2");
    }
};
