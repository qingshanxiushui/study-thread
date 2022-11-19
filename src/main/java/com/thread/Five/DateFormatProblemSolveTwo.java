package com.thread.Five;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

public class DateFormatProblemSolveTwo {
    public static void main(String[] args){
        DateTimeFormatter sdf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for(int i=0;i<10;i++){
            new Thread(()->{
                TemporalAccessor parse = sdf.parse("2022-10-10");
                System.out.println(parse);
            }).start();
        }
    }
}
