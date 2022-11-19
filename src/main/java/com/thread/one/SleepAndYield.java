package com.thread.one;

public class SleepAndYield {
    public static void main( String[] args ) throws InterruptedException {
        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                System.out.println("enter sleep...");
                try{
                    Thread.sleep(2000);
                }catch(InterruptedException e){
                    System.out.println("wake up ...");
                    e.printStackTrace();
                }
            }
        };
        t1.start();
        Thread.sleep(1000);
        System.out.println("do other things ...");
    }
}
