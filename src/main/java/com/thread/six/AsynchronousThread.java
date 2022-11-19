package com.thread.six;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class AsynchronousThread {
    static final List<String> MENU = Arrays.asList("第三鲜","宫保鸡丁","辣子鸡丁","烤鸡腿");
    static Random RANDOM = new Random();
    static String cooking(){return MENU.get(RANDOM.nextInt(MENU.size()));}
    public static void main(String[] args){
        //一个线程池处理
        methodOne();
        //两个线程池分工处理
        //methodTwo();
    }

    private static void methodTwo() {
        ExecutorService waiterPool = Executors.newFixedThreadPool(1);
        ExecutorService cookPool = Executors.newFixedThreadPool(1);

        waiterPool.execute(()->{
            System.out.println("处理点餐 1。。。");
            Future<String> f = cookPool.submit(()->{
                System.out.println("做菜 1");
                return cooking();
            });
            try {
                System.out.println("上菜 1："+f.get());
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        });
        waiterPool.execute(()->{
            System.out.println("处理点餐 2。。。");
            Future<String> f = cookPool.submit(()->{
                System.out.println("做菜 2");
                return cooking();
            });
            try {
                System.out.println("上菜 2："+f.get());
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    private static void methodOne() {
        ExecutorService pool = Executors.newFixedThreadPool(2);

        pool.execute(()->{
            System.out.println("处理点餐 1。。。");
            Future<String> f = pool.submit(()->{
                System.out.println("做菜 1");
                return cooking();
            });
            try {
                System.out.println("上菜 1："+f.get());
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        });
        pool.execute(()->{
            System.out.println("处理点餐 2。。。");
            Future<String> f = pool.submit(()->{
                System.out.println("做菜 2");
                return cooking();
            });
            try {
                System.out.println("上菜 2："+f.get());
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
