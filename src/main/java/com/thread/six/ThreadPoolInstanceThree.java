package com.thread.six;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolInstanceThree {
    public static void main(String[] args){
        ExecutorService pool = Executors.newSingleThreadExecutor();
        pool.execute(()->{
            System.out.println("1");
            int i=1/0;
        });
        pool.execute(()->{
            System.out.println("2");
        });
        pool.execute(()->{
            System.out.println("3");
        });
    }
}
