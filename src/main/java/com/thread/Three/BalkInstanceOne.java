package com.thread.Three;

public class BalkInstanceOne {
    public static void main( String[] args ){
        //多次调用start方法时 就会创建多个线程 而监控线程只需要一个就可以
        TwoPhase twoPhase = new TwoPhase();
        twoPhase.start();
        twoPhase.start();
    }
};
class TwoPhase{
    //监控线程
    private Thread monitorThread;
    //停止标记
    private boolean stop = false;

    //启动监控线程
    public void start(){
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
