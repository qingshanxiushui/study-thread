package com.thread.six;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TaskExceptionHandling {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //主动捉异常
        //methodOne();
        //使用future。future做得很好，如果代码没有异常 get返回的是结果 有异常 返回的是异常信息。
        methodTwo();
    }

    private static void methodTwo() throws InterruptedException, ExecutionException {
        ExecutorService pool = Executors.newFixedThreadPool(1);
        Future<Boolean>  f = pool.submit(()->{
            System.out.println("task1");
            int i = 1/0;
            return true;
        });
        System.out.println("result:"+f.get());
    }

    private static void methodOne() {
        ExecutorService pool = Executors.newFixedThreadPool(1);
        pool.submit(()->{
            try {
                System.out.println("task1");
                int i = 1/0;
            }catch (Exception e){
                System.out.println("error:"+e);
            }
        });
    }
}
