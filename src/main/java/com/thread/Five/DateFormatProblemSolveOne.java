package com.thread.Five;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateFormatProblemSolveOne {
    public static void main(String[] args){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for(int i=0;i<10;i++){
            new Thread(()->{
                synchronized (sdf){
                    try {
                        System.out.println(sdf.parse("2022-10-10"));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
