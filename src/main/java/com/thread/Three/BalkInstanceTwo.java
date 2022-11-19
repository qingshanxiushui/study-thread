package com.thread.Three;

public class BalkInstanceTwo {
    public static void main( String[] args ){
        //多次调用start方法时 就会创建多个线程 而监控线程只需要一个就可以
        TwoPhase twoPhase = new TwoPhase();
        twoPhase.start();
        twoPhase.start();
    }
};
class TwoPhaseTwo{
    //监控线程
    private Thread monitorThread;
    //停止标记
    private boolean stop = false;
    //判断是否执行过strat方法
    private boolean starting = false;

    //启动监控线程
    public void start(){
        synchronized (this){ //如果有两个线程同时调用start 第一个线程进来了 判断starting为false，还没来得及对它修改为true，第二个线程进来了 发现还是false 拦不住
            if(starting){  //判断是否执行过strat方法
                return;
            }
            starting = true;
        }
        monitorThread = new Thread(()->{
            while (true){
                Thread current = Thread.currentThread();
                if(stop){
                    System.out.println("料理后事");
                    break;
                }
                try {
                    Thread.sleep(1000);
                    System.out.println("执行监控记录");
                }catch (InterruptedException e){
                    e.printStackTrace();
                    //重新设置打断标记
                    current.interrupt();
                }
            }
        },"monitorThread");
        monitorThread.start();
    }

    //停止监控线程
    public void stop(){
        stop = true;
        monitorThread.interrupt();
    }
}
