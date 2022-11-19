package com.thread.two;

public class SynchronizedProcessTwo {
    public static void main( String[] args ) throws InterruptedException {
        Room room = new Room();
        Thread t1 = new Thread(()->{
            for (int i = 0; i<5000;i++){
                room.increment();
            }
        },"t1");

        Thread t2 = new Thread(()->{
            for (int i = 0; i<5000;i++){
                room.decrement();
            }
        },"t2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println( room.getCounter());

    }


};

class Room{
    private int counter = 0;
    public void increment(){
        synchronized (this){
            counter++;
        }
    }
    public void decrement(){
        synchronized (this){
            counter--;
        }
    }
    public int getCounter(){
        synchronized (this){
            return counter;
        }
    }
};

