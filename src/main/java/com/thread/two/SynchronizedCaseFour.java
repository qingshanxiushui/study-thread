package com.thread.two;

import static java.lang.Thread.sleep;

public class SynchronizedCaseFour {

    public static void main( String[] args ){
        NumberFour n1 = new NumberFour();
        NumberFour n2 = new NumberFour();
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
class NumberFour{
    public synchronized void a() throws InterruptedException {
        sleep(1000);
        System.out.println("1");
    }
    public synchronized void b(){
        System.out.println("2");
    }
};
