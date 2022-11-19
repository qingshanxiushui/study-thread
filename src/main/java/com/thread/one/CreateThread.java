package com.thread.one;

public class CreateThread {
    public static void main( String[] args )
    {
        //方式一：直接使用Thread
        createMethodOne();

    }

    private static void createMethodOne() {
        // 构造方法的参数是给线程指定名字，推荐
        Thread t1 = new Thread("t1") {
            @Override // run 方法内实现了要执行的任务
            public void run() {
                System.out.println( "MethodOne inner !" );
            }
        };
        t1.start();

        System.out.println( "MethodOne outter !" );
    }
}
