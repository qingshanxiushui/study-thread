package com.thread.six;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class TaskSchedulingThread {
    public static void main(String[] args){
        //Timer实现定时功能。任务2由于任务一被推迟了
        //methodOne();
        //任务一异常,导致timer结束，任务2不在执行
        //methodTwo();
        //用任务调度线程池,不会受异常影响newScheduledThreadPool
        //methodThree();
        //定时执行任务,比如每隔一秒执行
        //methodFour();
        //用固定间隔scheduleWithFixedDelay
        //methodFive();
    }

    private static void methodFive() {
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
        System.out.println("start...");
        pool.scheduleWithFixedDelay(()->{
            System.out.println("running...");
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },1,1,TimeUnit.SECONDS);
    }

    private static void methodFour() {
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
        System.out.println("start...");
        pool.scheduleAtFixedRate(()->{
            System.out.println("running...");
        },1,1,TimeUnit.SECONDS);
    }

    private static void methodThree() {
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(2);
        pool.schedule(()->{
            System.out.println("task1");
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },1, TimeUnit.SECONDS);
        pool.schedule(()->{
            System.out.println("task2");
        },1, TimeUnit.SECONDS);
    }

    private static void methodTwo() {
        Timer timer = new Timer();
        TimerTask task1 = new TimerTask() {
            @Override
            public void run() {
                System.out.println("task 1");
                int i=1/0;
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        TimerTask task2 = new TimerTask() {
            @Override
            public void run() {
                System.out.println("task 2");
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        System.out.println("start ...");
        timer.schedule(task1,1000);
        timer.schedule(task2,1000);
    }

    private static void methodOne() {
        Timer timer = new Timer();
        TimerTask task1 = new TimerTask() {
            @Override
            public void run() {
                System.out.println("task 1");
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        TimerTask task2 = new TimerTask() {
            @Override
            public void run() {
                System.out.println("task 2");
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        System.out.println("start ...");
        timer.schedule(task1,1000);
        timer.schedule(task2,1000);
    }
}
