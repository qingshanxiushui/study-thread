package com.thread.six;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.lang.Thread.sleep;

public class CopyOnWriteArrayListInstance {
    public static void main(String[] args) throws InterruptedException {
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        Iterator<Integer> iter = list.iterator();
        new Thread(()->{
            list.remove(0);
            System.out.println(list);
        }).start();
        sleep(1000);
        while (iter.hasNext()){
            System.out.println(iter.next());
        }
    }
}
