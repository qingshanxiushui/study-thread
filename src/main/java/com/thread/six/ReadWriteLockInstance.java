package com.thread.six;

import java.util.concurrent.locks.ReentrantReadWriteLock;

import static java.lang.Thread.sleep;

public class ReadWriteLockInstance {
    private Object data;
    private ReentrantReadWriteLock rw = new ReentrantReadWriteLock();
    private ReentrantReadWriteLock.ReadLock r = rw.readLock();
    private ReentrantReadWriteLock.WriteLock w = rw.writeLock();

    public Object read(){
        System.out.println("获取读锁。。。");
        r.lock();
        try {
            System.out.println("读取");
            sleep(1000);
            return data;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            System.out.println("释放读锁。。。。");
            r.unlock();
        }
    }

    public Object write(){
        System.out.println("获取写锁。。。");
        w.lock();
        try {
            System.out.println("写入");
            sleep(1000);
            return data;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            System.out.println("释放写锁。。。。");
            w.unlock();
        }
    }

    public static void main(String[] args){
        //读读
        //readReadMethod();
        //读写:等到读锁释放掉锁才能写入
        //readWriteMethod();
        //写写:等到另一个写锁释放掉锁才能写入。
        writeWriteMethod();
    }
    private static void writeWriteMethod() {
        ReadWriteLockInstance readWriteLockInstance = new ReadWriteLockInstance();
        new Thread(()->{
            readWriteLockInstance.write();
        },"t1").start();
        new Thread(()->{
            readWriteLockInstance.write();
        },"t2").start();
    }

    private static void readWriteMethod() {
        ReadWriteLockInstance readWriteLockInstance = new ReadWriteLockInstance();
        new Thread(()->{
            readWriteLockInstance.read();
        },"t1").start();
        new Thread(()->{
            readWriteLockInstance.write();
        },"t2").start();
    }

    private static void readReadMethod() {
        ReadWriteLockInstance readWriteLockInstance = new ReadWriteLockInstance();
        new Thread(()->{
            readWriteLockInstance.read();
        },"t1").start();
        new Thread(()->{
            readWriteLockInstance.read();
        },"t2").start();
    }
}
