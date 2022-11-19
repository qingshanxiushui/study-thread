package com.thread.two;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.sleep;

public class ReentrantLockFour {

    static final Object room = new Object();
    static boolean hasCigarette = false;
    static boolean hasTakeout = false;
    static ReentrantLock ROOM = new ReentrantLock();
    //等待烟的休息室
    static Condition waitCigaretteSet = ROOM.newCondition();
    //等外卖的休息室
    static Condition waitTakeoutSet = ROOM.newCondition();

    public static void main(String[] args) throws InterruptedException {
        new Thread(()->{
            ROOM.lock();
            try{
                System.out.println("小南：有烟没"+hasCigarette);
                while (!hasCigarette){
                    System.out.println("小南：没烟,先歇会！");
                    try{
                        waitCigaretteSet.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("小南：可以开始干活了");
            }finally {
                ROOM.unlock();
            }
        },"小南").start();

        new Thread(()->{
            ROOM.lock();
            try{
                System.out.println("小女：外卖送到没"+hasTakeout);
                while (!hasTakeout){
                    System.out.println("小女：没外卖，歇会");
                    try{
                        waitTakeoutSet.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("小女：可以开始干活了");
            }finally {
                ROOM.unlock();
            }
        },"小女").start();

        sleep(1000);
        new Thread(()->{
            ROOM.lock();
            try{
                hasTakeout = true;
                waitTakeoutSet.signal();
            }finally {
                ROOM.unlock();
            }
        },"送外卖").start();

        sleep(1000);
        new Thread(()->{
            ROOM.lock();
            try{
                hasCigarette = true;
                waitCigaretteSet.signal();
            }finally {
                ROOM.unlock();
            }
        },"送烟的").start();

    }
}
