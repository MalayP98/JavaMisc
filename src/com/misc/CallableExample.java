package com.misc;

import java.util.concurrent.*;

import static com.misc.Utils.print;

public class CallableExample {

    static class Task implements Callable<Integer>{

        @Override
        public Integer call() throws Exception {
            print(Utils.getThreadName() + " generating random integer.");
            Thread.sleep(2000L);
            return Utils.getRandomNumber(5);
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // using ExecutorsTest.
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Future<Integer> future1 = executorService.submit(new Task());
        Future<Integer> future2 = executorService.submit(new Task());
        print(String.valueOf(future1.get() + future2.get()));
        executorService.shutdown();
        executorService.awaitTermination(2, TimeUnit.SECONDS);

        // using FutureTask
        FutureTask<Integer> ft1 = new FutureTask<>(new Task());
        FutureTask<Integer> ft2 = new FutureTask<>(new Task());
        Thread t1 = new Thread(ft1);
        Thread t2 = new Thread(ft2);
        t1.start(); t2.start();
        t1.join(); t2.join();
        print(String.valueOf(ft1.get() + ft2.get()));
    }
}
