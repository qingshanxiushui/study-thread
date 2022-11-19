package com.thread.six;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class ThreadPoolInstanceFour {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //提交一个任务
        //methodOne();
        //改为lambda格式
        //methodTwo();
        //批量提交
        //methodThree();
        //任何一个完成即可，其它任务取消
        //methodFour();
        //改为线程数1
        methodFive();
    }

    private static void methodFive() throws InterruptedException, ExecutionException {
        ExecutorService pool = Executors.newFixedThreadPool(1);
        String result = pool.invokeAny(Arrays.asList(
                ()->{
                    System.out.println(Thread.currentThread().getName()+"begin");
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread().getName()+"end");
                    return "1";
                },
                ()->{
                    System.out.println(Thread.currentThread().getName()+"begin");
                    Thread.sleep(500);
                    System.out.println(Thread.currentThread().getName()+"end");
                    return "2";
                },
                ()->{
                    System.out.println(Thread.currentThread().getName()+"begin");
                    Thread.sleep(2000);
                    System.out.println(Thread.currentThread().getName()+"end");
                    return "3";
                }
        ));
        System.out.println(Thread.currentThread().getName()+result);
    }

    private static void methodFour() throws InterruptedException, ExecutionException {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        String result = pool.invokeAny(Arrays.asList(
                ()->{
                    System.out.println(Thread.currentThread().getName()+"begin");
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread().getName()+"end");
                    return "1";
                },
                ()->{
                    System.out.println(Thread.currentThread().getName()+"begin");
                    Thread.sleep(500);
                    System.out.println(Thread.currentThread().getName()+"end");
                    return "2";
                },
                ()->{
                    System.out.println(Thread.currentThread().getName()+"begin");
                    Thread.sleep(2000);
                    System.out.println(Thread.currentThread().getName()+"end");
                    return "3";
                }
        ));
        System.out.println(Thread.currentThread().getName()+result);
    }

    private static void methodThree() throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        List<Future<String>> futures = pool.invokeAll(Arrays.asList(
                ()->{
                    System.out.println(Thread.currentThread().getName()+"begin");
                    Thread.sleep(1000);
                    return "1";
                },
                ()->{
                    System.out.println(Thread.currentThread().getName()+"begin");
                    Thread.sleep(500);
                    return "2";
                },
                ()->{
                    System.out.println(Thread.currentThread().getName()+"begin");
                    Thread.sleep(2000);
                    return "3";
                }
        ));
        futures.forEach(f->{
            try {
                System.out.println(Thread.currentThread().getName()+f.get());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }

        });
    }

    private static void methodTwo() throws InterruptedException, ExecutionException {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        Future<String> future = pool.submit(()->{
                System.out.println("running");
                Thread.sleep(1000);
                return "ok";
        });
        System.out.println(future.get());
    }

    private static void methodOne() throws InterruptedException, ExecutionException {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        Future<String> future = pool.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("running");
                Thread.sleep(1000);
                return "ok";
            }
        });
        System.out.println(future.get());
    }
}
