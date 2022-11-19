package com.thread.two;

import java.util.Hashtable;

public class ThreadSafeClass {
    public static void main( String[] args ){

        //String
        //Integer
        //StringBuﬀer
        //Random
        //Vector
        //Hashtable
        //java.util.concurrent 包下的类
        //它们是线程安全的是指，多个线程调用它们同一个实例的某个方法时，是线程安全的
        Hashtable table = new Hashtable();
        new Thread(()->{table.put("key","value");}).start();
        new Thread(()->{table.put("key","value2");}).start();
    }
}
