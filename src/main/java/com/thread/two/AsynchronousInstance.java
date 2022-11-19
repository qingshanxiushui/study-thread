package com.thread.two;

import java.util.LinkedList;

import static java.lang.Thread.sleep;

public class AsynchronousInstance {
    public static void main(String[] args){
        MessageQueue queue = new MessageQueue(2);
        for (int i=0;i<3;i++){
            int id = i;
            new Thread(()->{
                queue.put(new Message(id,"值"+id));
            },"生产者"+i).start();
        }

        new Thread(()->{
            while (true){
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Message message = queue.take();
            }
        },"消费者").start();
    }
};
final class Message{
    private int id;
    private Object value;
    public Message(int id,Object value){
        this.id = id;
        this.value = value;
    }
    public int getId(){
        return id;
    }
    public Object getValue(){
        return value;
    }
    public String toString(){
        return "Message{"+"id="+id+",value="+value+"}";
    }
};
class MessageQueue{
    //消息队列集合
    private LinkedList<Message> list = new LinkedList<>();
    //队列容量
    private int capcity;
    public MessageQueue(int capcity){
        this.capcity = capcity;
    }
    //获取消息
    public Message take(){
        //检查队列是否为空
        synchronized (list){
            while (list.isEmpty()){
                try {
                    System.out.println("队列为空,消费者线程等待");
                    list.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            //从队列头部获取消息并返回
            Message message = list.removeFirst();
            System.out.println("已消费消息"+message.toString());
            list.notifyAll();
            return message;
        }
    }
    //存入消息
    public void put(Message message){
        synchronized (list){
            //检查对象是否已满
            while (list.size() == capcity ){
                try {
                    System.out.println("队列已满,生产者线程等待");
                    list.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            list.addLast(message);
            System.out.println("已生产消息"+message);
            list.notifyAll();
        }
    }
}
