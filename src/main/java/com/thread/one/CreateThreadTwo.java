package com.thread.one;

public class CreateThreadTwo {
    public static void main( String[] args )
    {
        //方法二：使用 Runnable 配合 Thread。把【线程】和【任务】（要执行的代码）分开
        //createMethodTwo();

        //方法二： lambda 精简代码，一个接口只有一个方法的时候才能使用lambda
        //createMethodTwoLambda();

        /*
        方法1 是把线程和任务合并在了一起，方法2 是把线程和任务分开了
        用 Runnable 更容易与线程池等高级 API 配合
        用 Runnable 让任务类脱离了 Thread 继承体系，更灵活
        */


    }

    private static void createMethodTwoLambda() {
        // 创建任务对象
        Runnable task2 = () -> {System.out.println("hello");};

        // 参数1 是任务对象; 参数2 是线程名字，推荐
        Thread t2 = new Thread(task2, "t2");
        t2.start();
    }

    private static void createMethodTwo() {
        Runnable runnable = new Runnable() {
            public void run(){
                // 要执行的任务
                System.out.println("MethodTwo inner");
            }
        };
        // 创建线程对象
        Thread t = new Thread( runnable );
        t.setName("t1");
        // 启动线程
        t.start();
        System.out.println("MethodTwo outer");
    }

}
