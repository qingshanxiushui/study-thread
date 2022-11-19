package com.thread.one;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class CreateThreadThree {
    public static void main( String[] args ) throws ExecutionException, InterruptedException {

        /*
        FutureTask 能够接收 Callable 类型的参数，用来处理有返回结果的情况
        FutureTask 实现 RunnableFuture接口
        RunnableFuture extends Runnable, Future
        Future 接口返回任务执行结果
        Callable可以抛出异常
        */
        createMethodThree();

    }

    private static void createMethodThree() throws InterruptedException, ExecutionException {
        // 创建任务对象
        FutureTask<Integer> task3 = new FutureTask<>(new Callable<Integer>() {

            @Override
            public Integer call() throws Exception {
                System.out.println("hello");
                Thread.sleep(2000);
                return 100;
            }
        });
        //FutureTask<Integer> task3 = new FutureTask<>(() -> {
        //log.debug("hello");
        //return 100;
        //});
        // 参数1 是任务对象; 参数2 是线程名字，推荐
        new Thread(task3, "t3").start();
        // 主线程阻塞，同步等待 task 执行完毕的结果
        Integer result = task3.get();
        System.out.println("结果是:" + result);
    }


}
