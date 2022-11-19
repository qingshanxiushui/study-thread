package com.thread.two;

import static java.lang.Thread.sleep;

public class WaitNotifyOne {
    final static Object obj = new Object();
    public static void main(String[] args) throws InterruptedException {
        new Thread(()->{
            synchronized (obj){
                System.out.println("t1:执行。。。。。");
                try{
                    obj.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("t1:其它代码。。。。。");
            }
        },"t1").start();
        new Thread(()->{
            synchronized (obj){
                System.out.println("t2:执行。。。。。");
                try{
                    obj.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("t2:其它代码。。。。。");
            }
        },"t2").start();
        //主线程两秒后执行
        sleep(2000);
        System.out.println("唤醒obj上其它线程");
        synchronized (obj){
            //obj.notify(); //唤醒obj上一个线程
            obj.notifyAll();//唤醒obj上所有等待线程
        }
    }
}
