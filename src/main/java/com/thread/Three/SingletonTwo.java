package com.thread.Three;

public final class SingletonTwo {
    private SingletonTwo(){}
    //volatile 可以禁用指令重排
    private  static volatile SingletonTwo INSTANCE = null;
    //懒惰实例化
    //首次使用 getInstance() 才使用 synchronized 加锁，后续使用时无需加锁
    //有隐含的，但很关键的一点：第一个 if 使用了 INSTANCE 变量，是在同步块之外
    public static synchronized SingletonTwo getInstance(){
        //实例没创建,才会进入内部的synchronized代码块
        if(INSTANCE==null){
            //也许有其它线程已经创建实例,所以再判断一次
            synchronized (Singleton.class){
                if (INSTANCE == null){
                    INSTANCE = new SingletonTwo();
                }
            }
        }
        return INSTANCE;
    }
}
