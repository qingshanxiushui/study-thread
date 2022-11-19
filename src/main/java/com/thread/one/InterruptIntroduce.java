package com.thread.one;


import static java.lang.Thread.sleep;

public class InterruptIntroduce {
    public static void main( String[] args ) throws InterruptedException {
        //test1();

        //test2();
        //调用interrupt 无法打断这个线程 到底要不要打断 要这个线程来决定 相当于告诉这个线程有人想打断你 你怎么办？

        test3();
    }

    private static void test3() throws InterruptedException {
        Thread t1 = new Thread(()->{
            while(true){
                boolean interrupted = Thread.currentThread().isInterrupted();
                if(interrupted){
                    System.out.println("被打断退出循环");
                    break;
                }
            }
        },"t1");
        t1.start();
        sleep(10);
        t1.interrupt();
        System.out.println("打断状态="+t1.isInterrupted());
    }

    private static void test2() throws InterruptedException {
        Thread t1 = new Thread(()->{
            while(true){

            }
        },"t1");
        t1.start();
        sleep(10);
        t1.interrupt();
        System.out.println("打断状态="+t1.isInterrupted());
    }

    private static void test1() throws InterruptedException {
        Thread t1 = new Thread(()->{
            try {
                sleep(4);
                System.out.println("没有打断");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },"t1");
        t1.start();
        sleep(2);
        t1.interrupt();
        System.out.println("打断状态="+t1.isInterrupted());
    }
}
