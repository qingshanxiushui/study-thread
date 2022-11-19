package com.thread.two;

import static java.lang.Thread.sleep;

public class SynchronizedCaseTwo {

    public static void main( String[] args ){
        NumberTwo n1 = new NumberTwo();

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
class NumberTwo{
    public synchronized void a() throws InterruptedException {
        sleep(1000);
        System.out.println("1");
    }
    public synchronized void b(){
        System.out.println("2");
    }
};
