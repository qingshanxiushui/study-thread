package com.thread.two;

import static java.lang.Thread.sleep;

public class SynchronizedCaseSeven {

    //2 1s 后 1 线程1锁的类对象 线程2锁的是n2对象 不是一个对象
    public static void main( String[] args ){
        NumberSeven n1 = new NumberSeven();
        NumberSeven n2 = new NumberSeven();
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
class NumberSeven{
    public static synchronized void a() throws InterruptedException {
        sleep(1000);
        System.out.println("1");
    }
    public synchronized void b(){
        System.out.println("2");
    }
};
