package com.thread.two;

import java.util.ArrayList;

public class VariableThreadSafe {
    static final int THREAD_NUMBER = 2;
    static final int LOOP_NUMBER = 200;
    public static void main( String[] args ){
        ThreadSafe test = new ThreadSafe();
        for(int i=0; i<THREAD_NUMBER; i++){
            new Thread(()->{
                    test.method1(LOOP_NUMBER);
            },"Thread"+(i+1)).start();
        }

    }
};
class ThreadSafe{
//list 是局部变量，每个线程调用时会创建其不同实例，没有共享
//而 method2 的参数是从 method1 中传递过来的，与 method1 中引用同一个对象
//method3 的参数分析与 method2 相同
    public  void method1(int loopNumber){
        ArrayList<String> list = new ArrayList<>();
        for(int i=0;i<loopNumber;i++){
            method2(list);
            method3(list);
        }
    }

    private void method2( ArrayList<String> list) {
        list.add("1");
    }

    private void method3( ArrayList<String> list) {
        list.remove(0);
    }
}
