package com.thread.two;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockOne {
//可重入是指同一个线程如果首次获得了这把锁，那么因为它是这把锁的拥有者，因此有权利再次获取这把锁
//如果是不可重入锁，那么第二次获得锁时，自己也会被锁挡住
    static ReentrantLock lock = new ReentrantLock();
    public static void main(String[] args){
        method1();
    }

    public static void method1(){
        lock.lock();
        try {
            System.out.println("execute method1");
            method2();
        }finally {
            lock.unlock();
        }
    }
    public static void method2(){
        lock.lock();
        try {
            System.out.println("execute method2");
            method3();
        }finally {
            lock.unlock();
        }
    }
    public static void method3(){
        lock.lock();
        try {
            System.out.println("execute method3");
        }finally {
            lock.unlock();
        }
    }
}
