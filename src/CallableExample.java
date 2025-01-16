import java.util.concurrent.*;

public class CallableExample {

    static class Task implements Callable<Integer>{

        @Override
        public Integer call() throws Exception {
            System.out.println(Utils.getThreadName() + " generating random integer.");
            Thread.sleep(1000L);
            return Utils.getRandomNumber(5);
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // using Executors.
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Future<Integer> future1 = executorService.submit(new Task());
        Future<Integer> future2 = executorService.submit(new Task());
        System.out.println(future1.get() + future2.get());
        executorService.shutdown();

        // using FutureTask
        FutureTask<Integer> ft1 = new FutureTask<>(new Task());
        FutureTask<Integer> ft2 = new FutureTask<>(new Task());
        new Thread(ft1).start();
        new Thread(ft2).start();
        System.out.println(ft1.get() + ft2.get());
    }
}
