package com.thread.four;

import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.sleep;

public class casLockInstance {
    public static void main(String[] args){
        LockCas lock = new LockCas();
        new Thread(()->{
            System.out.println("t1 begin...");
            lock.lock();
            try{
                System.out.println("t1 lock...");
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        }).start();

        new Thread(()->{
            System.out.println("t2 begin...");
            lock.lock();
            try{
                System.out.println("t2 lock...");
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        }).start();
    }
}
class LockCas{
    //0 没加锁
    //1 加锁
    private AtomicInteger state = new AtomicInteger(0);
    public void lock(){
        while (true){
            if (state.compareAndSet(0,1)){
                break;
            }
        }
    }
    public void unlock(){
        System.out.println("unlock....");
        state.set(0);
    }
}