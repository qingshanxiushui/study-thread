package com.thread.one;

import static java.lang.Thread.sleep;

public class DaemonThread {
    public static void main( String[] args ) throws InterruptedException {
        Thread t1 = new Thread(()->{
            while(true){
                if(Thread.currentThread().isInterrupted()){
                    break;
                }
            }
        },"t1");
        //其他非守护线程结束了 设置为daemon的线程（守护线程）也会强制结束 即使他之前一直在while（true）运行
        t1.setDaemon(true);
        t1.start();
        sleep(1000);
        System.out.println("结束");

    }
}
