package com.thread.two;

import org.openjdk.jol.info.ClassLayout;

public class MonitorUndoBiasLockTwo {
    public static void main(String[] args){
        DogTwo d = new DogTwo();
        new Thread(()->{
            System.out.println(ClassLayout.parseInstance(d).toPrintable());
            synchronized (d){
                System.out.println(ClassLayout.parseInstance(d).toPrintable());
                try {
                    d.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(ClassLayout.parseInstance(d).toPrintable());
            }
        },"t1").start();

        new Thread(()->{
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (d){
                System.out.println("notity");
                d.notify();
            }
        },"t2").start();

    }
}
class DogTwo{ }