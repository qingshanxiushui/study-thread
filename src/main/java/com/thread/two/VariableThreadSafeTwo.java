package com.thread.two;

import java.util.ArrayList;

public class VariableThreadSafeTwo {
    static final int THREAD_NUMBER = 2;
    static final int LOOP_NUMBER = 200;
    public static void main( String[] args ){
        ThreadSafeTwoSubClass test = new ThreadSafeTwoSubClass();
        for(int i=0; i<THREAD_NUMBER; i++){
            new Thread(()->{
                    test.method1(LOOP_NUMBER);
            },"Thread"+(i+1)).start();
        }

    }
};
class ThreadSafeTwo{
    /*
    方法访问修饰符带来的思考，如果把 method2 和 method3 的方法修改为 public 会不会代理线程安全问题？
    情况1：有其它线程调用 method2 和 method3 ：不会 因为其他线程调用方法2和方法3传的参数list不一样，不是同一个对象
    情况2：在 情况1 的基础上，为 ThreadSafe 类添加子类，子类覆盖 method2 或 method3 方法，即
    */

    public  void method1(int loopNumber){
        ArrayList<String> list = new ArrayList<>();
        for(int i=0;i<loopNumber;i++){
            method2(list);
            method3(list);
        }
    }

    public void method2( ArrayList<String> list) {
        list.add("1");
    }

    public void method3( ArrayList<String> list) {
        list.remove(0);
    }
};
class ThreadSafeTwoSubClass extends ThreadSafeTwo{
    public void method3( ArrayList<String> list) {
        new Thread(()->{
            list.remove(0);
        }).start();
    }
}
