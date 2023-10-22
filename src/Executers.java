import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Executers {

    static class Task {

        public void performTask() {
            try {
                for (int i = 1; i <= 5; i++) {
                    System.out.println(Thread.currentThread().getName() + ": " + i);
                    Thread.sleep(100L);
                }
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
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Task task = new Task();
        executorService.submit(task::performTask);
        executorService.submit(task::performTask);
        // waits for task to complete for 10 seconds, it fine if they complete.
        // Will move to next instruction irrespective of task completion.
        executorService.awaitTermination(10L, TimeUnit.SECONDS);

        System.out.println(Thread.currentThread().getName());
    }

}
