package com.nickmafra.concurrent;

public class ConcurrentUtils {

    private ConcurrentUtils() {}

    public static void stopAllIfSomeoneStops(long sleep, Thread... threads) throws InterruptedException {
        waitSomeoneStops(sleep, threads);
        stopAll(threads);
    }

    public static void waitSomeoneStops(long sleep, Thread... threads) throws InterruptedException {
        Object lock = new Object();

        synchronized (lock) {
            do {
                for (Thread thread : threads) {
                    if (!thread.isAlive()) {
                        return;
                    }
                }
                lock.wait(sleep);
            } while (true);
        }
    }

    public static void stopAll(Thread... threads) {
        for (int i = threads.length - 1; i >= 0; i--) {
            Thread thread = threads[i];
            if (thread.isAlive()) {
                thread.interrupt();
            }
        }
    }
}
