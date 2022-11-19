package com.thread.Three;

public class TwoPhaseTermination {

    private  Thread monitor;

    public static void main( String[] args ){
        TwoPhaseTermination twoPhaseTermination = new TwoPhaseTermination();
        twoPhaseTermination.start();
        twoPhaseTermination.stop();
        //如果不在catch里执行current.interrupt 打断后 会抛出异常 但会被清除打断标记 所以接下来还会监控下去
        /*isInterrupted() 判断是否被打断 不会清楚打断标记
        interrupted() static 判断当前线程是否被打断 会清楚打断标记*/
    }
    //启动监控线程
    public void start(){
        monitor = new Thread(()->{
            while (true){
                Thread current = Thread.currentThread();
                if(current.isInterrupted()){
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
        });
        monitor.start();
    }

    //停止监控线程
    public void stop(){
        monitor.interrupt();
    }
}
