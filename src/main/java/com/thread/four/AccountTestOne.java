package com.thread.four;

import java.util.ArrayList;
import java.util.List;

//有线程安全问题 因为account对象是共享资源
public class AccountTestOne {
     public static void main(String[] args){
         Account account = new AccountUnsafe(10000);
         Account.demo(account);
     }
}
interface Account{
    //获取余额
    Integer getBalance();
    //取款
    void withdraw(Integer amount);
    /**
     * 方法内会启动1000个线程,每个线程做-10元操作
     * 如果初始余额为10000，那么正确结果应当是0
     */
    static void demo(Account account){
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
class AccountUnsafe implements Account{

    private Integer balance;
    public AccountUnsafe(Integer balance){
        this.balance = balance;
    }
    @Override
    public Integer getBalance() {
        return this.balance;
    }

    @Override
    public void withdraw(Integer amount) {
        this.balance -=amount;
    }
}
