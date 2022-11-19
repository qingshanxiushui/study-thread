package com.thread.six;

import java.util.concurrent.locks.StampedLock;

import static java.lang.Thread.sleep;

public class StampedLockInstance {
    private int data;
    private final StampedLock lock = new StampedLock();
    public StampedLockInstance(int data){
        this.data = data;
    }
    public int read(int readTime) throws InterruptedException {
        long stamp = lock.tryOptimisticRead();
        System.out.println("optimistic read locking ...."+stamp);
        sleep(readTime);
        if(lock.validate(stamp)){
            System.out.println("read finish....."+stamp);
            return data;
        }
        System.out.println("updating to read lock...."+stamp);
        try{
            stamp = lock.readLock();
            System.out.println("read lock "+stamp);
            sleep(readTime);
            System.out.println("read finish ..."+stamp);
            return data;
        }finally {
            System.out.println("read unlock "+ stamp);
            lock.unlockRead(stamp);
        }
    }
    public void write(int newData){
        long stamp = lock.writeLock();
        System.out.println("write lock "+stamp);
        try{
            sleep(2000);
            this.data = data;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }finally {
            System.out.println("write unlock "+stamp);
            lock.unlockWrite(stamp);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        //readReadMethod();
        //readWrite();
    }

    private static void readWrite() throws InterruptedException {
        StampedLockInstance stampedLockInstance = new StampedLockInstance(1);
        new Thread(()->{
            try {
                stampedLockInstance.read(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },"t1").start();
        sleep(500);
        new Thread(()->{
                stampedLockInstance.write(1000);
        },"t2").start();
    }

    private static void readReadMethod() throws InterruptedException {
        StampedLockInstance stampedLockInstance = new StampedLockInstance(1);
        new Thread(()->{
            try {
                stampedLockInstance.read(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },"t1").start();
        sleep(500);
        new Thread(()->{
            try {
                stampedLockInstance.read(0);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },"t2").start();
    }

}
