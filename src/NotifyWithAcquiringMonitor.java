public class NotifyWithAcquiringMonitor {

    static class Task implements Runnable {

        protected boolean flg = false;

        @Override
        public void run() {
            for (int i = 1; i <= 5; i++) {
                System.out.println(Utils.getThreadName() + ": " + i);
            }
            try {
                Thread.sleep(Utils.getRandomNumber(5) * 1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            flg = true;
            synchronized (this) {
                notifyAll();
            }
        }

        public synchronized void syncFun() throws InterruptedException {
            while (!flg) wait();
            System.out.println(Utils.getThreadName() + ": " + Utils.getRandomNumber(5));
        }
    }

    static class TaskV2 extends Task {

        @Override
        public void run() {
            try{
                for (int i = 1; i <= 5; i++) {
                    System.out.println(Utils.getThreadName() + ": " + i);
                }
                try {
                    Thread.sleep(Utils.getRandomNumber(5) * 1000L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                flg = true;
                notifyAll();
            }catch (Exception e){
                System.out.println(Utils.getThreadName() + " unable to acquire monitor.");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // will fine as notify is called after acquiring monitor.
        Task[] arr = new Task[5];
        for (int i = 0; i < 5; i++) {
            arr[i] = new Task();
            new Thread(arr[i]).start();
        }

        for (int i = 0; i < 5; i++) {
            arr[i].syncFun();
        }

        // -------------------------

        // will not work as notifyAll() is called without acquiring the monitor
        TaskV2[] arr2 = new TaskV2[5];
        for (int i = 0; i < 5; i++) {
            arr2[i] = new TaskV2();
            new Thread(arr2[i]).start();
        }

        for (int i = 0; i < 5; i++) {
            arr2[i].syncFun();
        }
    }
}
