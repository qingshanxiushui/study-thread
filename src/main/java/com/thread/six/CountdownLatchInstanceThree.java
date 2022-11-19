package com.thread.six;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Thread.sleep;

public class CountdownLatchInstanceThree {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(10);
        ExecutorService service = Executors.newFixedThreadPool(10);
        Random r = new Random();
        String[] all = new String[10];
        for (int j = 0; j<10 ;j++){
            int k =j;
            service.submit(()->{
                for (int i=0;i<=100;i++){
                    try {
                        Thread.sleep(r.nextInt(1000));
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    all[k] = i + "%";
                    System.out.println("\r" + Thread.currentThread().getName() + " : " +Arrays.toString(all));
                }
                latch.countDown();
            });
        }
        latch.await();
        System.out.println("\n 游戏开始");
        service.shutdown();

    }
}
