package com.thread.one;


public class StartAndRun {
    public static void main( String[] args ){
        //runMethod();
        startMethod();
        /*
        直接调用 run 是在主线程中执行了 run，没有启动新的线程
        使用 start 是启动新的线程，通过新的线程间接执行 run 中的代码
        */
    }

    private static void startMethod() {
        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        };
        t1.start();
        System.out.println("do other things ...");
    }

    private static void runMethod() {
        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        };
        t1.run();
        System.out.println("do other things ...");
    }
}
