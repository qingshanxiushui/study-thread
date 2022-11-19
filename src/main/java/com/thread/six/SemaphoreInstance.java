package com.thread.six;

import java.util.concurrent.Semaphore;

import static java.lang.Thread.sleep;

public class SemaphoreInstance {
    public static void main(String[] args){
        //1. 创建semaphore对象
        Semaphore semaphore = new Semaphore(3);
        //2. 10个线程同时运行
        for (int i=0;i<10;i++){
            new Thread(()->{
                try {
                    semaphore.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    System.out.println(Thread.currentThread().getName() + "running");
                    sleep(2000);
                    System.out.println(Thread.currentThread().getName() + "end...");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    semaphore.release();
                }
            }).start();
        }
    }
}
