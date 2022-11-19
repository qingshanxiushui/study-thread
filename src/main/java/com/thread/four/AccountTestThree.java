package com.thread.four;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

//改进
public class AccountTestThree {
     public static void main(String[] args){
         AccountThree accountThree = new AccountCas(10000);
         AccountThree.demo(accountThree);
     }
}
interface AccountThree{
    //获取余额
    Integer getBalance();
    //取款
    void withdraw(Integer amount);
    /**
     * 方法内会启动1000个线程,每个线程做-10元操作
     * 如果初始余额为10000，那么正确结果应当是0
     */
    static void demo(AccountThree account){
        List<Thread> ts = new ArrayList<>();
        for(int i=0;i<1000;i++){
            ts.add(new Thread(()->{
                account.withdraw(10);
            }));
        }
        long start = System.nanoTime();
        ts.forEach(Thread::start);
        ts.forEach(t->{
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        long end = System.nanoTime();
        System.out.println(account.getBalance()+"cost:"+(end-start)/1000_000+"ms");
    }
}
class AccountCas implements AccountThree{

    private AtomicInteger balance;
    public AccountCas(Integer balance){
        this.balance = new AtomicInteger(balance);
    }
    @Override
    public Integer getBalance() {
        return balance.get();
    }

    @Override
    public void withdraw(Integer amount) {
       while (true){
           //获取余额的最新值
           int prev = balance.get();
           //要修改的余额
           int next = prev - amount;
           //真正修改
           if(balance.compareAndSet(prev,next)){
               break;
           }
       }
    }
}
