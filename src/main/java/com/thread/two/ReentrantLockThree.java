package com.thread.two;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.sleep;

public class ReentrantLockThree {
    private  static ReentrantLock lock = new ReentrantLock();
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(()->{
            System.out.println("t1 尝试获取锁");
            try {
                if(!lock.tryLock(5, TimeUnit.SECONDS)){
                    System.out.println("t1 获取不到锁");
                    return;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("t1 获取不到锁");
                return;
            }
            try {
                System.out.println("t1 获得到锁");
            }finally {
                lock.unlock();
            }
        },"t1");

        lock.lock();
        System.out.println("main 获得到锁");
        t1.start();
        sleep(3000);
        lock.unlock();
        System.out.println("main 释放了锁");
    }
}
