package com.nickmafra.exception;

/**
 * Um Runnable que pode lançar exceção.
 *
 * @param <E>
 */
@FunctionalInterface
public interface CheckedRunnable<E extends Exception> {

    void run() throws E;
}
