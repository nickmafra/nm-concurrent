package com.nickmafra.concurrent;

import com.nickmafra.exception.CheckedRunnable;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Uma thread que executa um processo periodicamente.
 * <br>
 * Se uma execução demorar mais que o período especificado, ocorre uma defasagem na periodicidade.
 * <br>
 * É possível interromper o tempo de espera e adiantar a próxima execução chamando o método
 * {@link #notify()}.
 */
public class LimitedRateThread extends Thread {

    private final long period;
    private final AtomicReference<CheckedRunnable<InterruptedException>> runnable;

    public LimitedRateThread(String name, long period, CheckedRunnable<InterruptedException> runnable) {
        super(name);
        this.period = period;
        this.runnable = new AtomicReference<>(runnable);
    }

    public LimitedRateThread(long period, CheckedRunnable<InterruptedException> runnable) {
        this("LimitedRateThread", period, runnable);
    }

    public void setRunnable(CheckedRunnable<InterruptedException> runnable) {
        this.runnable.set(runnable);
    }

    @Override
    public void run() {
        try {
            execute();
        } catch (InterruptedException e) {
            interrupt();
        }
    }

    private synchronized void execute() throws InterruptedException {
        long startTime = now();
        while (!isInterrupted()) {

            if (runnable.get() == null) {
                throw new IllegalStateException("Runnable não definido.");
            }
            runnable.get().run();
            long duration = now() - startTime;

            long remaining = (period - duration) - 1;
            if (remaining > 0)
                wait(remaining);

            startTime = now();
        }
    }

    public long now() {
        return System.currentTimeMillis();
    }

}
