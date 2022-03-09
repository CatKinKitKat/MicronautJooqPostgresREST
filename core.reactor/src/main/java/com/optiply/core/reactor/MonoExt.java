package com.optiply.core.reactor;

import reactor.core.publisher.Mono;

/**
 * Common reactor operations that are used frequently in order to optimize memory usage.
 */
public final class MonoExt {

    /**
     * Emits a new mono with the boolean value true.
     * This is equivalent to Mono.just(true) but saves one memory allocation.
     *
     * @param <T>
     * @return
     */
    public static <T> Mono<T> succeeded() {
        return MonoTrue.instance();
    }

    /**
     * Emits a new mono with the boolean value false.
     * This is equivalent to Mono.just(true) but saves one memory allocation.
     *
     * @param <T>
     * @return
     */
    public static <T> Mono<T> failed() {
        return MonoFalse.instance();
    }
}
