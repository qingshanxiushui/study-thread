package com.thread.four;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

import static java.lang.Thread.sleep;

public class ABAProblem {
    //主线程没有办法判断共享变量是否被别的线程修改过
    //主线程仅能判断出共享变量的值与最初值 A 是否相同，不能感知到这种从 A 改为 B 又 改回 A 的情况
    static AtomicReference<String> ref = new AtomicReference<>("A");
    public static void main(String[] args) throws InterruptedException {
        System.out.println("main strat...");
        //获取值A
        //这个共享变量被其它线程修改过
        String prev = ref.get();
        other();
        sleep(1000);
        //尝试修改为C
        System.out.println("chang A-C :"+ref.compareAndSet(prev,"C"));
    }

    private static void other() throws InterruptedException {
        new Thread(()->{
            System.out.println("chang A-B :"+ref.compareAndSet(ref.get(),"B"));
        },"t1").start();
        sleep(500);
        new Thread(()->{
            System.out.println("chang B-A :"+ref.compareAndSet(ref.get(),"A"));
        },"t2").start();
    }
}
