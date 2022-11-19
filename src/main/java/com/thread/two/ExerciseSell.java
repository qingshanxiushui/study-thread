package com.thread.two;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

public class ExerciseSell {
    //Random线程安全
    static Random random = new Random();

    public static void main( String[] args ){
        TicketWindow ticketWindow = new TicketWindow(2000);
        List<Thread> list = new ArrayList<>();
        //用来存储买出去多少张票
        List<Integer> sellCount = new Vector<>();
        for(int i=0; i<2000; i++){
            Thread t = new Thread(()->{
                //分析这里的竞态条件
                int count = ticketWindow.sell(randomAmount());
                sellCount.add(count);
            });
            list.add(t);
            t.start();
        }
        list.forEach((t)->{
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        //买出去的票求和
        System.out.println("selled count:"+sellCount.stream().mapToInt(c -> c).sum());
        //剩余票数
        System.out.println("remainder count:"+ticketWindow.getCount());
    }

    public static int randomAmount(){
        return random.nextInt(5)+1;
    }
};
class TicketWindow{
    private int count;
    public TicketWindow(int count){
        this.count=count;
    }
    //获取余票数量
    public int getCount(){
        return count;
    }
    //售票
    public synchronized int sell(int amount){ //增加synchronized,保证线程安全
        if(this.count >= amount){
            this.count -= amount;
            return amount;
        }else{
            return 0;
        }
    }
}