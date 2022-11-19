package com.thread.two;

import org.openjdk.jol.info.ClassLayout;

public class MonitorUndoBiasLock {
    public static void main(String[] args){
        Dog d = new Dog();
        new Thread(()->{
            System.out.println(ClassLayout.parseInstance(d).toPrintable());
            synchronized (d){
                System.out.println(ClassLayout.parseInstance(d).toPrintable());
            }
            System.out.println(ClassLayout.parseInstance(d).toPrintable());
            synchronized (MonitorUndoBiasLock.class){
                MonitorUndoBiasLock.class.notify();
            }
        },"t1").start();

        new Thread(()->{
            synchronized (MonitorUndoBiasLock.class){
                try {
                    MonitorUndoBiasLock.class.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + ClassLayout.parseInstance(d).toPrintable());
            synchronized (d){
                System.out.println(Thread.currentThread().getName() + ClassLayout.parseInstance(d).toPrintable());
            }
            System.out.println(Thread.currentThread().getName() + ClassLayout.parseInstance(d).toPrintable());
        },"t2").start();

    }
}
class Dog{ }