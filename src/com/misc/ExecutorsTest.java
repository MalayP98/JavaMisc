package com.misc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import static com.misc.Utils.print;

public class ExecutorsTest {

    static class Task {

        public void performTask() {
            try {
                int waitTime = Utils.getRandomNumber(10);
                print(Utils.getThreadName() + " is working. Will take " + waitTime + "s to complete this task.");
                Thread.sleep(waitTime*1000L);
                print(Utils.getThreadName() + " completed the task.");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {
        try {
            awaitTerminationExample();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void awaitTerminationExample() throws InterruptedException {
        ExecutorService executorService = java.util.concurrent.Executors.newFixedThreadPool(10);
        Task task = new Task();
        for(int i=0; i<10; i++){
            executorService.submit(task::performTask);
        }

        print("Shutting down executor service. Will not accept new task.");
        executorService.shutdown();
        print("Trying to submit task...");
        try {
            executorService.submit(task::performTask);
        }catch (Exception e){
            print("Exception thrown. Error : " + e.getMessage());
        }
        print("Awaiting termination...");
        // this call will block the main thread for the specified time. In this case 5 seconds;
        if(!executorService.awaitTermination(5L, TimeUnit.SECONDS)){
            executorService.shutdownNow();
        }
        print("Service shutdown!");
    }
}
