package com.thread.two;

import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.sleep;

public class ReentrantLockTwo {
    private  static ReentrantLock lock = new ReentrantLock();
    public static void main(String[] args) throws InterruptedException {
         Thread t1 = new Thread(()->{
             try{
                 //如果没有竞争那么此方法就会获取lock对象锁
                 //如果有竞争就进入阻塞队列,可以被其它线程用interrupt方法打断
                 System.out.println("尝试获取锁");
                 lock.lockInterruptibly();
                 /*System.out.println("尝试获取锁");
                 lock.lock();*/
             }catch (Exception e) {
                 e.printStackTrace();
                 System.out.println("没有获得锁,返回");
                 return;
             }
             try{
                 System.out.println("获取到锁");
             }finally {
                 lock.unlock();
             }
         },"t1");
         lock.lock();
         t1.start();
         sleep(1000);
         System.out.println("打断 t1");
         t1.interrupt();
    }
}
