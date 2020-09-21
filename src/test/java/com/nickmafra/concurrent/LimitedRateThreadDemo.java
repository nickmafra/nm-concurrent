package com.nickmafra.concurrent;

import com.nickmafra.exception.CheckedRunnable;

import java.util.Scanner;

public class LimitedRateThreadDemo {

    public static void main(String[] args) {
        new LimitedRateThreadDemo();
    }

    private final LimitedRateThread thread;
    private final long startTime;

    public LimitedRateThreadDemo() {
        startTime = System.currentTimeMillis();

        thread = new LimitedRateThread(3000, new CheckedRunnable<InterruptedException>() {
            @Override
            public void run() throws InterruptedException {
                LimitedRateThreadDemo.this.printTime();
            }
        });
        try {
            thread.start();

            skipOnType();
        } finally {
            thread.interrupt();
        }
    }

    private  void printTime() {
        double deltaTime = (System.currentTimeMillis() - startTime) / 1000.0;
        System.out.println("Time elapsed: " + deltaTime);
    }

    private void skipOnType() {
        Scanner scanner = new Scanner(System.in);
        String text;
        do {
            System.out.println("Type something to skip (or 'exit' to close): ");
            text = scanner.next();
            synchronized (thread) {
                thread.notifyAll();
            }
        } while (!"exit".equalsIgnoreCase(text));
    }
}