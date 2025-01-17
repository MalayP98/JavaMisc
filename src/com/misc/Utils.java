package com.misc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Utils {

    public static long measurePerformance(Runnable runnable) {
        long start = System.currentTimeMillis();
        runnable.run();
        long end = System.currentTimeMillis();
        return end - start;
    }

    public static void printCurrentThread() {
        System.out.println(Thread.currentThread().getName());
    }

    public static String getThreadName() {
        return Thread.currentThread().getName();
    }

    public static Integer getRandomNumber(int n) {
        return new Random().nextInt(n);
    }

    public static void print(String message){
        System.out.println("Time: " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + " | Message: " + message);
    }
}
