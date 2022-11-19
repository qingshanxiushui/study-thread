package com.thread.two;

import static java.lang.Thread.sleep;

public class WaitNotifyThree {
    static final Object room = new Object();
    static boolean hasCigarette = false;
    static boolean hasTakeout = false;
    public static void main(String[] args) throws InterruptedException {
        //sleepMethod();

        //解决了其它干活的线程阻塞的问题,但如果有其它线程也在等待条件,无法解决。
        //waitMethod();

        //waitMethodTwo();

        //synchronized(lock){while(条件不成立){lock.wait()} 干活} 。
        //另一个线程synchronized(lock){lock.notifyAll()}
        waitMethodThree();

    }

    private static void waitMethodThree() throws InterruptedException {
        new Thread(()->{
            synchronized (room){
                System.out.println("有烟没："+hasCigarette);
                while(!hasCigarette){
                    System.out.println("没烟");
                    try {
                        room.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.println("有烟没2："+hasCigarette);
                if(hasCigarette){
                    System.out.println("小南 开始干活了");
                }else{
                    System.out.println("小南 没干成活");
                }

            }
        },"小南").start();

        new Thread(()->{
            synchronized (room){
                System.out.println("外卖送到没："+hasTakeout);
                if(!hasTakeout){
                    System.out.println("没外卖,先歇会");
                    try {
                        room.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.println("外卖送到没2："+hasTakeout);
                if(hasTakeout){
                    System.out.println("小女 开始干活了");
                }else{
                    System.out.println("小女 没干成活");
                }

            }
        },"小女").start();

        //主线程两秒后执行
        sleep(1000);
        new Thread(()->{
            synchronized (room){
                hasTakeout = true;
                System.out.println("外卖到了");
                room.notifyAll();
            }
        },"送外卖").start();
    }

    private static void waitMethodTwo() throws InterruptedException {
        new Thread(()->{
            synchronized (room){
                System.out.println("有烟没："+hasCigarette);
                if(!hasCigarette){
                    System.out.println("没烟");
                    try {
                        room.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.println("有烟没2："+hasCigarette);
                if(hasCigarette){
                    System.out.println("小南 开始干活了");
                }else{
                    System.out.println("小南 没干成活");
                }

            }
        },"小南").start();

        new Thread(()->{
            synchronized (room){
                System.out.println("外卖送到没："+hasTakeout);
                if(!hasTakeout){
                    System.out.println("没外卖,先歇会");
                    try {
                        room.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.println("外卖送到没2："+hasTakeout);
                if(hasTakeout){
                    System.out.println("小女 开始干活了");
                }else{
                    System.out.println("小女 没干成活");
                }

            }
        },"小女").start();

        //主线程两秒后执行
        sleep(1000);
        new Thread(()->{
            synchronized (room){
                hasTakeout = true;
                System.out.println("外卖到了");
                //notify 只能随机唤醒一个 WaitSet 中的线程，这时如果有其它线程也在等待，那么就可能唤醒不了正确的线 程，称之为【虚假唤醒】
                //room.notify();
                //notifyAll 仅解决某个线程的唤醒问题，但使用 if + wait 判断仅有一次机会，一旦条件不成立，就没有重新 判断的机会了。用 while + wait，当条件不成立，再次 wait。
                room.notifyAll();
            }
        },"送外卖").start();
    }

    private static void waitMethod() throws InterruptedException {
        new Thread(()->{
            synchronized (room){
                System.out.println("有烟没"+hasCigarette);
                if(!hasCigarette){
                    System.out.println("没烟");
                    try {
                        room.wait(4000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                if(hasCigarette){
                    System.out.println("小南 开始干活了");
                }

            }
        },"小南").start();

        for(int i=0; i<5; i++){
            new Thread(()->{
                synchronized (room){
                    System.out.println("其他人 开始干活了");
                }
            },"其他人").start();
        }

        //主线程两秒后执行
        sleep(1000);
        new Thread(()->{
            synchronized (room){
                hasCigarette = true;
                System.out.println("烟到了");
                room.notify();
            }
        },"t3").start();
    }

    private static void sleepMethod() throws InterruptedException {
        new Thread(()->{
            synchronized (room){
                System.out.println("有烟没"+hasCigarette);
                if(!hasCigarette){
                    System.out.println("没烟");
                    try {
                        sleep(4000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                if(hasCigarette){
                    System.out.println("小南 开始干活了");
                }

            }
        },"小南").start();

        for(int i=0; i<5; i++){
            new Thread(()->{
                synchronized (room){
                    System.out.println("其他人 开始干活了");
                }
            },"其他人").start();
        }

        //主线程两秒后执行
        sleep(1000);
        new Thread(()->{
            //这里不能加synchronized。加了 synchronized (room) 后，就好比小南在里面反锁了门睡觉，烟根本没法送进门，main 没加 synchronized 就好像 main 线程是翻窗户进来的
            hasCigarette = true;
            System.out.println("烟到了");
        },"t3").start();
    }

}
