package com.thread.two;

import org.openjdk.jol.info.ClassLayout;

import java.util.Vector;
import java.util.concurrent.locks.LockSupport;

public class MonitorBatchUndoBiasLock {
    static Thread t1,t2,t3;
    public static void main(String[] args) throws InterruptedException {
        Vector<CatTwo> list = new Vector<>();
        int loopNumber = 39;
        t1 = new Thread(()->{
            for (int i=0;i<loopNumber;i++){
                CatTwo d = new CatTwo();
                list.add(d);
                synchronized (d){
                    System.out.println(i + "\t" + ClassLayout.parseInstance(d).toPrintable());
                }
            }
            LockSupport.unpark(t2);
        },"t1");
        t1.start();

        t2 = new Thread(()->{
            LockSupport.park();
            System.out.println("============================>");
            for(int i=0;i<loopNumber;i++){
                CatTwo d = list.get(i);
                System.out.println(i + "\t" + ClassLayout.parseInstance(d).toPrintable());
                synchronized (d){
                    System.out.println(i + "\t" + ClassLayout.parseInstance(d).toPrintable());
                }
                System.out.println(i + "\t" + ClassLayout.parseInstance(d).toPrintable());
            }
            LockSupport.park(t3);
        },"t2");
        t2.start();

        t3 = new Thread(()->{
            LockSupport.park();
            System.out.println("============================>");
            for(int i=0;i<loopNumber;i++){
                CatTwo d = list.get(i);
                System.out.println(i + "\t" + ClassLayout.parseInstance(d).toPrintable());
                synchronized (d){
                    System.out.println(i + "\t" + ClassLayout.parseInstance(d).toPrintable());
                }
                System.out.println(i + "\t" + ClassLayout.parseInstance(d).toPrintable());
            }
            LockSupport.park(t3);
        },"t3");
        t3.start();
        t3.join();
        System.out.println(ClassLayout.parseInstance(new CatTwo()).toPrintable());
    }
}
class CatTwo{}
