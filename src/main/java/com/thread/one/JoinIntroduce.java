package com.thread.one;

import static java.lang.Thread.sleep;

public class JoinIntroduce {

    static int r = 0;
    static int r1 = 0;
    static int r2 = 0;
    public static void main( String[] args ) throws InterruptedException {
        //test1();
        //用 join，加在 t1.start() 之后即可 放在主线程 表示主线程直到t1运行结束之后才可以继续运行

        //test2();
        /*第一个 join：等待 t1 时, t2 并没有停止, 而在运行
        第二个 join：1s 后, 执行到此, t2 也运行了 1s, 因此也只需再等待 1s*/

        //test3();
        //如果实际等待时间没有那么长 也会提前结束等待
    }

    private static void test3() throws InterruptedException {
        Thread t1 = new Thread(()->{
            try {
                sleep(4);
                r1=10;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        long start = System.currentTimeMillis();
        t1.start();
        System.out.println("join begin");
        t1.join(2);
        System.out.println("t1 join end");
        long end = System.currentTimeMillis();
        System.out.println("r1="+r1+";cost="+(end-start));
    }

    private static void test2() throws InterruptedException {
        Thread t1 = new Thread(()->{
            try {
                sleep(4);
                r1=10;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        Thread t2 = new Thread(()->{
            try {
                sleep(5);
                r2=20;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        t1.start();
        t2.start();
        long start = System.currentTimeMillis();
        System.out.println("join begin");
        t1.join();
        System.out.println("t1 join end");
        t2.join();
        System.out.println("t2 join end");
        long end = System.currentTimeMillis();
        System.out.println("r1="+r1+";r2="+r2+";cost="+(end-start));

    }

    static void test1() throws InterruptedException {
        System.out.println("开始");
        Thread t1 = new Thread(() -> {
           try {
                System.out.println("开始");
                sleep(1);
                System.out.println("结束");
                r = 10;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        t1.start();
        t1.join();
        System.out.println("结果:"+r);
        System.out.println("结束");
    }
}
