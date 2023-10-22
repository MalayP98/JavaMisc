import java.util.Locale;

public class Monitor {

    private static final String SEPARATOR = "-------------------------------------";

    static class NonSynchronizationMonitor {

        public void taskA() {
            try {
                for (int i = 1; i <= 5; i++) {
                    System.out.println(Thread.currentThread().getName() + ": " + i);
                    Thread.sleep(20L);
                }
            } catch (InterruptedException e) {
                System.out.println("Error.");
            }
        }

        public void taskB() {
            try {
                for (int i = 1; i <= 5; i++) {
                    System.out.println(Thread.currentThread().getName() + ": " + i);
                    Thread.sleep(20L);
                }
            } catch (InterruptedException e) {
                System.out.println("Error.");
            }
        }
    }

    static class MonitorOnCurrentClass extends NonSynchronizationMonitor {

        @Override
        public synchronized void taskA() {
            super.taskA();
        }

        @Override
        public synchronized void taskB() {
            super.taskB();
        }
    }

    static class MonitorOnDifferentObjects extends NonSynchronizationMonitor {

        private final Object lock1 = new Object();

        private final Object lock2 = new Object();

        @Override
        public void taskA() {
            synchronized (lock1) {
                super.taskA();
            }
        }

        @Override
        public void taskB() {
            synchronized (lock2) {
                super.taskB();
            }
        }
    }

    static class ThreadPair {
        private final Thread threadA;

        private final Thread threadB;

        public ThreadPair(Thread threadA, Thread threadB) {
            this.threadA = threadA;
            this.threadB = threadB;
        }

        public void start() {
            threadA.start();
            threadB.start();
        }
    }

    private static ThreadPair getThreadPair(NonSynchronizationMonitor nonSynchronizationMonitor) {
        Thread threadA = new Thread(nonSynchronizationMonitor::taskA);
        threadA.setName(nonSynchronizationMonitor.getClass().getName().toLowerCase(Locale.ROOT) + "-A");
        Thread threadB = new Thread(nonSynchronizationMonitor::taskB);
        threadB.setName(nonSynchronizationMonitor.getClass().getName().toLowerCase(Locale.ROOT) + "-B");
        return new ThreadPair(threadA, threadB);
    }

    public static void main(String[] args) throws InterruptedException {
        getThreadPair(new NonSynchronizationMonitor()).start();

        Thread.sleep(1000L);
        System.out.println(SEPARATOR);

        getThreadPair(new MonitorOnCurrentClass()).start();

        Thread.sleep(1000L);
        System.out.println(SEPARATOR);

        getThreadPair(new MonitorOnDifferentObjects()).start();

        Thread.sleep(1000L);
        System.out.println(SEPARATOR);
    }

}
