package com.thread.two;

import java.util.concurrent.locks.ReentrantLock;

public class PhilosophersDinnerTwo {
    public static void main(String[] args){
        ChopstickTwo c1 = new ChopstickTwo("1");
        ChopstickTwo c2 = new ChopstickTwo("2");
        ChopstickTwo c3 = new ChopstickTwo("3");
        ChopstickTwo c4 = new ChopstickTwo("4");
        ChopstickTwo c5 = new ChopstickTwo("5");
        new PhilosopherTwo("苏格拉底",c1,c2).start();
        new PhilosopherTwo("柏拉图",c2,c3).start();
        new PhilosopherTwo("亚里士多德",c3,c4).start();
        new PhilosopherTwo("赫拉克利特",c4,c5).start();
        new PhilosopherTwo("阿基米德",c5,c1).start();
    }
};
class ChopstickTwo extends ReentrantLock {
  String name;
  public ChopstickTwo(String name){
      this.name = name;
  }
  public String toString(){
      return "筷子{"+name+"}";
  }
};
class PhilosopherTwo extends Thread{
    ChopstickTwo left;
    ChopstickTwo right;
    public PhilosopherTwo(String name,ChopstickTwo left,ChopstickTwo right){
        super(name);
        this.left = left;
        this.right = right;
    }
    public void eat() throws InterruptedException {
        System.out.println(Thread.currentThread().getName()+"eating.....");
        sleep(1000);
    }
    public void run(){
        while (true){
            //尝试获得左手筷子
            if(left.tryLock()){
                try{
                    //尝试获得右手筷子
                    if(right.tryLock()){
                        try{
                            try {
                                eat();
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }finally {
                            right.unlock();
                        }
                    }
                }finally {
                    left.unlock();
                }
            }
        }
    }
}

