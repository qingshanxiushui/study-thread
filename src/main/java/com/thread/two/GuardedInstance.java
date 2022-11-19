package com.thread.two;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class GuardedInstance {
    //与join对比，join有局限性 因为 必须要等待线程执行结束 才能让另一个线程执行 而用了这个方法 t2线程不用非要结束 t1才能运行。
    public static void main(String[] args){
        //methodOne();
        methodTwoTime();
    }

    private static void methodTwoTime() {
        GuardedObject guardedObject = new GuardedObject();
        new Thread(()->{
            //等待结果
            System.out.println("等待结果");
            Object respsonse = (List<String>) guardedObject.get(2000);
            System.out.println("结果时："+respsonse);
        },"t1").start();

        new Thread(()->{
            System.out.println("执行下载：");
            try {
                //sleep(3000);
                sleep(1000); //测试虚假唤醒
                guardedObject.complete(null);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },"t2").start();
    }

    private static void methodOne() {
        GuardedObject guardedObject = new GuardedObject();
        new Thread(()->{
            //等待结果
            System.out.println("等待结果");
            List<String> list = (List<String>) guardedObject.get();
            System.out.println("结果大小："+list.size());
            System.out.println(list.toString());

        },"t1").start();

        new Thread(()->{
            System.out.println("执行下载：");
            try{
                List<String> list = DownLoader.download();
                guardedObject.complete(list);
            }catch (IOException e) {
                throw new RuntimeException(e);
            }
        },"t2").start();
    }
};
class GuardedObject{
    //结果
    private Object response;

    //获取结果
    public Object get(){
        synchronized (this){
            //没有结果
            while(response == null){
                try{
                    this.wait();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
            return response;
        }
    }

    //获取结果。timeout表示要等待多久
    public Object get(long timeout){
        synchronized (this){
            //开始时间
            long begin = System.currentTimeMillis();
            //经历的时间
            long passedTime = 0;
            //没有结果
            while(response == null){
                //这一轮循环应该等待的时间
                long waitTime = timeout - passedTime;
                //经历的时间超过了最大等待时间时，退出循环
                if(waitTime<=0){
                    break;
                }
                try{
                    this.wait(waitTime); //虚假唤醒
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                //求得经历时间
                passedTime = System.currentTimeMillis() - begin;
                System.out.println("经历时间:"+passedTime);
            }
            return response;
        }
        /*可以直接使用this.wait(timeout)吗？不可以 当本轮已经等了一段时间了 之后发生了虚假唤醒 退出本轮循环 进入下一次循环
        判断 response为null 还要进入循环 所以又要等 timeou的时间
        相等于等了 上一轮等的时间+本轮要等的timeout 所以 多等了 因为要求等timeout的时间就行 所以使用
        long waitTime = timeout - timePassed; 不会多等 */
    }

    //产生结果
    public void complete(Object response){
        synchronized (this){
            //给结果成员变量赋值
            this.response = response;
            this.notifyAll();
        }
    }
};

class DownLoader{
    public static List<String> download() throws IOException{
        HttpURLConnection conn = (HttpURLConnection)new URL("https://www.baidu.com").openConnection();
        List<String> lines = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))){
            String line;
            while((line = reader.readLine())!=null){
                lines.add(line);
            }
        }
        return lines;
    }
}
