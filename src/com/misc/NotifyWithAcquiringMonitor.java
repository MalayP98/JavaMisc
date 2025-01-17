package com.misc;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * Shows that {@link Object#notifyAll()} requires a monitor to notify.
 */
public class NotifyWithAcquiringMonitor {

    static class TaskWillAcquireLock implements Runnable {
        /**
         * All thread come concurrently and stop at the synchronized function call. The thread first to enter waits
         * for 1 second and then notify all the threads. Out of all the threads waiting one of them will enter this block.
         */
        @Override
        public void run() {
            System.out.println("Time: "+ LocalDateTime.now() + " " + "Inside Task#run(). From thread: " + Utils.getThreadName());
            try {
                Thread.sleep(Utils.getRandomNumber(5) * 1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            synchronized (this) {
                System.out.println("Time: "+ LocalDateTime.now() + " " + "Inside Task#run()'s sync call. From thread: " + Utils.getThreadName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                notifyAll();
            }
        }
    }

    /**
     * This task will call `notifyAll` method without any lock, thus will throw exception.
     */
    static class TaskWillNotAcquireLock implements Runnable {

        @Override
        public void run() {
            try{
                System.out.println("Time: "+ LocalDateTime.now() + " " + "Inside TaskV2#run(). From thread: " + Utils.getThreadName());
                try {
                    Thread.sleep(Utils.getRandomNumber(5) * 1000L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                notifyAll();
            }catch (Exception e){
                System.out.println("Time: "+ LocalDateTime.now() + " " + "Inside TaskV2#run(). " + Utils.getThreadName() + " unable to acquire monitor.");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Starting Task which acquires monitor.");
        Thread[] taskThreads = new Thread[5];
        for(int i=0; i<5; i++){
            taskThreads[i] = new Thread(new TaskWillAcquireLock());
            taskThreads[i].start();
        }
        Arrays.stream(taskThreads).forEach(taskThread -> {
            try {
                taskThread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        System.out.println("---------------------------------------------------");

        System.out.println("Starting Task which do not acquires monitor.");
        Thread[] taskV2Threads = new Thread[5];
        for(int i=0; i<5; i++){
            taskV2Threads[i] = new Thread(new TaskWillNotAcquireLock());
            taskV2Threads[i].start();
        }
        Arrays.stream(taskV2Threads).forEach(taskV2Thread -> {
            try {
                taskV2Thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
