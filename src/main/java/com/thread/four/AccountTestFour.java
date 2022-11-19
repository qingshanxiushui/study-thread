package com.thread.four;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

//改进
public class AccountTestFour {
     public static void main(String[] args){
         AccountFour accountFour = new AccountCasFour(10000);
         AccountFour.demo(accountFour);
     }
}
interface AccountFour{
    //获取余额
    Integer getBalance();
    //取款
    void withdraw(Integer amount);
    /**
     * 方法内会启动1000个线程,每个线程做-10元操作
     * 如果初始余额为10000，那么正确结果应当是0
     */
    static void demo(AccountFour account){
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
class AccountCasFour implements AccountFour{

    private AtomicInteger balance;
    public AccountCasFour(Integer balance){
        this.balance = new AtomicInteger(balance);
    }
    @Override
    public Integer getBalance() {
        return balance.get();
    }

    @Override
    public void withdraw(Integer amount) {
       balance.getAndAdd(-1*amount);
    }
}
