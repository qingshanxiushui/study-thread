package com.thread.four;

import java.util.concurrent.atomic.AtomicMarkableReference;

import static java.lang.Thread.sleep;

public class ABAProblemThree {
    //并不关心引用变量更改了几次，只是单纯的关心是否更改过，所以就有了 AtomicMarkableReference
    public static void main(String[] args) throws InterruptedException {
        GarbageBag bag = new GarbageBag("装满了垃圾");
        //参数2 mark可以看作一个标记，表示垃圾袋满了
        AtomicMarkableReference<GarbageBag> ref = new AtomicMarkableReference<>(bag,true);
        System.out.println("main strat...");
        GarbageBag prev = ref.getReference();
        System.out.println(prev.toString());


        new  Thread(()->{
            System.out.println("sub strat...");
            bag.setDesc("空垃圾袋");
            ref.compareAndSet(bag,bag,true,false);
            System.out.println("sub "+bag.toString());
        },"保洁阿姨").start();

        sleep(2000);
        System.out.println("想换一只新垃圾袋？");
        boolean success = ref.compareAndSet(prev,new GarbageBag("空垃圾袋"),true,false);
        System.out.println("换了吗？"+success);
        System.out.println(ref.getReference().toString());
    }
}
class GarbageBag{
    String desc;
    public GarbageBag(String desc){
        this.desc = desc;
    }
    public void setDesc(String desc){
        this.desc = desc;
    }
    public String toString(){
        return super.toString() + " "  + desc;
    }
}
