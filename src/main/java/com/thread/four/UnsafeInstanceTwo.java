package com.thread.four;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

//使用自定义的 AtomicData 实现之前线程安全的原子整数 Account 实现
public class UnsafeInstanceTwo {
    public static void main(String[] args){
        AccountUnsafeInterface  myAtomicInteger = new MyAtomicInteger(10000);
        AccountUnsafeInterface.demo(myAtomicInteger);
    }
}
interface AccountUnsafeInterface{
    //获取余额
    Integer getBalance();
    //取款
    void withdraw(Integer amount);
    /**
     * 方法内会启动1000个线程,每个线程做-10元操作
     * 如果初始余额为10000，那么正确结果应当是0
     */
    static void demo(AccountUnsafeInterface accountUnsafeInterface){
        List<Thread> ts = new ArrayList<>();
        for(int i=0;i<1000;i++){
            ts.add(new Thread(()->{
                accountUnsafeInterface.withdraw(10);
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
        System.out.println(accountUnsafeInterface.getBalance()+"cost:"+(end-start)/1000_000+"ms");
    }
}

class UnsafeAccessor{
    static Unsafe unsafe;
    static {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            unsafe = (Unsafe) theUnsafe.get(null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    static Unsafe getUnsafe(){
        return unsafe;
    }
}
class MyAtomicInteger implements AccountUnsafeInterface{
    private volatile int value;
    private static final long valueOffset;
    private static final Unsafe UNSAFE;

    static {
        UNSAFE = UnsafeAccessor.getUnsafe();
        try{
            valueOffset = UNSAFE.objectFieldOffset(MyAtomicInteger.class.getDeclaredField("value"));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    private int getValue(){
        return  value;
    }

    public void decrement(int amount){
        while (true){
            int prev = this.value;
            int next = prev - amount;
            if(UNSAFE.compareAndSwapInt(this,valueOffset,prev,next)){
                break;
            }
        }
    }

    public MyAtomicInteger(int value){
        this.value = value;
    }
    @Override
    public Integer getBalance() {
        return getValue();
    }

    @Override
    public void withdraw(Integer amount) {
        decrement(amount);
    }
}