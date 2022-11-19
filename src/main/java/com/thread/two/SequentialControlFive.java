package com.thread.two;

import java.util.concurrent.locks.LockSupport;

public class SequentialControlFive {
    static Thread t1;
    static Thread t2;
    static Thread t3;
    public static void main(String[] args){
        ParkUnpark pu = new ParkUnpark(5);
        t1 = new Thread(()->{
            pu.print("a",t2);
        });
        t2 = new Thread(()->{
            pu.print("b",t3);
        });
        t3 = new Thread(()->{
            pu.print("c",t1);
        });

        t1.start();
        t2.start();
        t3.start();
        LockSupport.unpark(t1);
    }
};
class ParkUnpark{
    private int loopNumber;

    public ParkUnpark(int loopNumber){
        this.loopNumber = loopNumber;
    }
    public void print(String str,Thread next){
        for(int i=0;i<loopNumber;i++){
            LockSupport.park();
            System.out.println(str);
            LockSupport.unpark(next);
        }
    }
}
