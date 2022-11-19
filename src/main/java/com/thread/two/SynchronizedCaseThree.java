package com.thread.two;

import static java.lang.Thread.sleep;

public class SynchronizedCaseThree {

    public static void main( String[] args ){
        NumberThree n1 = new NumberThree();

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

        new Thread( () -> {
            System.out.println("beginThree");
            n1.c();
        }).start();
    }
};
class NumberThree{
    public synchronized void a() throws InterruptedException {
        sleep(1000);
        System.out.println("1");
    }
    public synchronized void b(){
        System.out.println("2");
    }
    public void c(){
        System.out.println("3");
    }
};
