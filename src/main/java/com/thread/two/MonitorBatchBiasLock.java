package com.thread.two;

import org.openjdk.jol.info.ClassLayout;

import java.util.Vector;

public class MonitorBatchBiasLock {
    public static void main(String[] args){
        Vector<Cat> list = new Vector<>();
        Thread t1 = new Thread(()->{
            for (int i=0;i<30;i++){
                Cat d = new Cat();
                list.add(d);
                synchronized (d){
                    System.out.println(i + "\t" + ClassLayout.parseInstance(d).toPrintable());
                }
            }
            synchronized (list){
                list.notify();
            }
        },"t1");
        t1.start();

        Thread t2 = new Thread(()->{
            synchronized (list){
                try {
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("============================>");
            for(int i=0;i<30;i++){
                Cat d = list.get(i);
                System.out.println(i + "\t" + ClassLayout.parseInstance(d).toPrintable());
                synchronized (d){
                    System.out.println(i + "\t" + ClassLayout.parseInstance(d).toPrintable());
                }
                System.out.println(i + "\t" + ClassLayout.parseInstance(d).toPrintable());
            }
        },"t2");
        t2.start();
    }
}
class Cat{}
