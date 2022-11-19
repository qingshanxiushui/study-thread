package com.thread.two;

import java.util.Random;

public class ExerciseTransfer {
    static Random random = new Random();
    public static void main( String[] args ) throws InterruptedException {
        Account a = new Account(1000);
        Account b = new Account(1000);
        System.out.println(a.getMoney()+b.getMoney());
        Thread t1 = new Thread(()->{
            for (int i=0;i<1000;i++){
                a.transfer(b,randomAmount());
            }
        },"t1");
        Thread t2 = new Thread(()->{
            for (int i=0;i<1000;i++){
                b.transfer(a,randomAmount());
            }
        },"t2");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(a.getMoney()+b.getMoney());
    }
    public static int randomAmount(){
        return random.nextInt(100)+1;
    }
};

class Account{
    private int money;
    public Account(int money){ this.money = money; }
    public int getMoney(){return money;}
    public void setMoney(int money){this.money = money;}
    //转账
    //public void synchronized transfer() 不行，因为在临界区有两个共享变量a b，this只能保护一个 访问另一个的时候还是可以访问
    public void transfer(Account target,int amount){
        synchronized (Account.class){  //可以，因为他们都使用account类
            if(this.money>=money){
                this.setMoney(this.getMoney() - amount);
                target.setMoney(target.getMoney() + amount);
            }
        }
    }
}
