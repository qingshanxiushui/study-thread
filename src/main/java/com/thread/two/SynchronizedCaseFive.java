package com.thread.two;

import static java.lang.Thread.sleep;

public class SynchronizedCaseFive {

    //2 1s 后 1 锁的不是一个对象
    public static void main( String[] args ){
        NumberFive n1 = new NumberFive();
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
class NumberFive{
    public static synchronized void a() throws InterruptedException {
        sleep(1000);
        System.out.println("1");
    }
    public synchronized void b(){
        System.out.println("2");
    }
};
