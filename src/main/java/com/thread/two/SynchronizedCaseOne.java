package com.thread.two;

public class SynchronizedCaseOne {

    public static void main( String[] args ){
        NumberOne n1 = new NumberOne();
        new Thread( () -> {
            System.out.println("beginOne");
            n1.a();
        }).start();
        new Thread( () -> {
            System.out.println("beginTwo");
            n1.b();
        }).start();
    }
};
class NumberOne{
    public synchronized void a(){
        System.out.println("1");
    }
    public synchronized void b(){
        System.out.println("2");
    }
};
