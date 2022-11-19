package com.thread.four;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

import static java.lang.Thread.sleep;

public class ABAProblemTwo {
    //如果主线程 希望：只要有其它线程【动过了】共享变量，那么自己的 cas 就算失败，
    // 这时，仅比较值是不够的，需要再加一个版本号AtomicStampedReference
    //谁做了修改 谁让版本号加一
    static AtomicStampedReference<String> ref = new AtomicStampedReference<>("A",0);
    public static void main(String[] args) throws InterruptedException {
        System.out.println("main strat...");
        //获取值A
        //这个共享变量被其它线程修改过
        String prev = ref.getReference();
        //获取版本号
        int stamp = ref.getStamp();
        System.out.println("版本号"+stamp);
        other();
        sleep(1000);
        //尝试修改为C
        System.out.println("chang A-C :"+ref.compareAndSet(prev,"C",stamp,stamp+1));
    }

    private static void other() throws InterruptedException {
        new Thread(()->{
            int stamp = ref.getStamp();
            System.out.println("other版本号1："+stamp);
            System.out.println("chang A-B :"+ref.compareAndSet(ref.getReference(),"B",stamp,stamp+1));
        },"t1").start();
        sleep(500);
        new Thread(()->{
            int stamp = ref.getStamp();
            System.out.println("other版本号2："+stamp);
            System.out.println("chang B-A :"+ref.compareAndSet(ref.getReference(),"A",stamp,stamp+1));
        },"t2").start();
    }
}
