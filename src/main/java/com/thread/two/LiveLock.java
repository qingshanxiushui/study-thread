package com.thread.two;

import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.sleep;

public class LiveLock {

    //死锁：两个线程都互相持有对方想要的锁，导致谁都无法继续向下运行，阻塞住了。
    //活锁：没有阻塞 都在不断使用cpu 但是改变了对方结束条件 导致无法结束。
    //解决活锁：时间错开 增加随机睡眠。
    static volatile int count = 10;
    //Volatile关键字的作用主要有如下两个：
    //1.线程的可见性：当一个线程修改一个共享变量时，另外一个线程能读到这个修改的值。
    //2. 顺序一致性：禁止指令重排序。
    public static void main(String[] args){
        new Thread(()->{
            while (count>0){
                try {
                    sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                count--;
                System.out.println("t1:count"+count);
            }
        },"t1").start();
        new Thread(()->{
            while (count<20){
                try {
                    sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                count++;
                System.out.println("t2:count"+count);
            }
        },"t2").start();


    }
}
