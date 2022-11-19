package com.thread.four;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntUnaryOperator;

public class AtomicInstance {
    public static void main(String[] args){
        AtomicInteger i = new AtomicInteger(5);

        //System.out.println(i.updateAndGet(x->x*10));

        /*while (true){
            int prev = i.get();
            int next = prev*10;
            if(i.compareAndSet(prev,next)){
                break;
            }
        }
        System.out.println(i.get());*/

        updateAndGet(i,p->p/2);
        System.out.println(i.get());
    }

    private static void updateAndGet(AtomicInteger i, IntUnaryOperator operator) {
        while (true){
            int prev = i.get();
            int next = operator.applyAsInt(prev);
            if(i.compareAndSet(prev,next)){
                break;
            }
        }
    }
}
