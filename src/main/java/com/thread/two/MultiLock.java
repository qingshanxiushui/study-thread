package com.thread.two;

public class MultiLock {
    public static void main(String[] args){
        BigRoom bigRoom = new BigRoom();
        new Thread(()->{
            try {
                bigRoom.study();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },"小南").start();
        new Thread(()->{
            try {
                bigRoom.sleep();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },"小女").start();
    }
};
class BigRoom{
    private final Object studyRoom = new Object();
    private final Object sleepRoom = new Object();

    //用不同的锁
    public void sleep() throws InterruptedException {
        synchronized (sleepRoom){
            System.out.println("sleeping 2 小时");
            Thread.sleep(2000);
        }
    }
    public void study() throws InterruptedException {
        synchronized (studyRoom){
            System.out.println("study 1 小时");
            Thread.sleep(2000);
        }
    }
}
