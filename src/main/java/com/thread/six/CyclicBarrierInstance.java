package com.thread.six;

import java.util.concurrent.*;

import static java.lang.Thread.sleep;

public class CyclicBarrierInstance {
    public static void main(String[] args){
        ExecutorService service = Executors.newFixedThreadPool(2);
        CyclicBarrier barrier = new CyclicBarrier(2,()->{
            System.out.println("task1,task2 finish...");
        });
        for(int i=0;i<3;i++){
            service.submit(()->{
               System.out.println(Thread.currentThread().getName() + ":taks1 begin..");
                try {
                    sleep(1000);
                    barrier.await(); //2-1=1
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (BrokenBarrierException e) {
                    throw new RuntimeException(e);
                }

            });

            service.submit(()->{
                System.out.println(Thread.currentThread().getName() + ":taks2 begin..");
                try {
                    sleep(2000);
                    barrier.await(); //1-1=0
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (BrokenBarrierException e) {
                    throw new RuntimeException(e);
                }

            });
        }
        service.shutdown();
    }

}
