package com.thread.four;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class AtomicAdderInstance {
    public static void main(String[] args){

        for(int i=0;i<5;i++){
            demo(
                    ()->new AtomicLong(0),
                    (adder)->adder.getAndIncrement(),
                    "t1"
            );
        }

        for(int i=0;i<5;i++){
            demo(
                    ()->new LongAdder(),
                    adder -> adder.increment(),
                    "t2"
            );
        }

    }

    /**
     *
     * @param adderSupplier  ()->结果 提供累加器对象
     * @param action (参数)-> 执行累加操作
     * @param <T>
     */
    private static <T> void demo(Supplier<T> adderSupplier, Consumer<T> action, String flag){
        T adder = adderSupplier.get();
        List<Thread> ts = new ArrayList<>();
        for(int i=0;i<4;i++){
            ts.add(new Thread(()->{
                for(int j=0;j<500000;j++){
                    action.accept(adder);
                }
            }));
        }
        long start = System.nanoTime();
        ts.forEach(t->t.start());
        ts.forEach(t-> {
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        long end = System.nanoTime();
        System.out.println(flag + ":"+ adder + " cost: " + (end - start)/1000_000);
    }
}
