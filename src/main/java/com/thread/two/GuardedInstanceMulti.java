package com.thread.two;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static java.lang.Thread.sleep;

public class GuardedInstanceMulti {
    public static void main(String[] args) throws InterruptedException {
        for(int i=0;i<3;i++){
            new People().start();
        }
        sleep(1000);
        for(Integer id:Mailboxes.getIds()){
            new Postman(id,"内容"+id).start();
        }
    }
};
class GuardedObjectTwo{

    //标识Guarded Object
    private int id;
    public GuardedObjectTwo(int id){
        this.id = id;
    }
    public int getId(){
        return id;
    }

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
class Mailboxes{
    private static Map<Integer,GuardedObjectTwo> boxes = new Hashtable<>();
    private static int id = 1;
    //产生唯一id
    private static synchronized int generateId(){
        return id++;
    }
    public static GuardedObjectTwo getGuardedObject(int id){
        return boxes.remove(id);
    }
    public static GuardedObjectTwo createGuardedObject(){
        GuardedObjectTwo go = new GuardedObjectTwo(generateId());
        boxes.put(go.getId(),go);
        return go;
    }
    public static Set<Integer> getIds(){
        return boxes.keySet();
    }
};
class People extends Thread{
    public void run(){
        //收信
        GuardedObjectTwo guardedObject = Mailboxes.createGuardedObject();
        System.out.println("开始收信id"+guardedObject.getId());
        Object mail = guardedObject.get(5000);
        System.out.println("收到信id"+guardedObject.getId()+";内容"+mail);
    }
};
class Postman extends Thread{
    private int id;
    private String mail;
    public Postman(int id,String mail){
        this.id = id;
        this.mail = mail;
    }
    public void run(){
        GuardedObjectTwo guardedObject = Mailboxes.getGuardedObject(id);
        System.out.println("送信id"+id+";内容"+mail);
        guardedObject.complete(mail);
    }
}
