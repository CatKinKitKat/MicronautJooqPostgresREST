package com.optiply.core.reactor;

import org.reactivestreams.Publisher;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Operators;

import java.time.Duration;

/**
 * Represents a boolean publisher with a value of false.
 */
public class MonoFalse extends Mono<Boolean> implements Fuseable.ScalarCallable<Boolean>, Fuseable {
    static final Publisher<Boolean> INSTANCE = new MonoFalse();

    /**
     * Returns a properly parametrized instance of this false Publisher.
     *
     * @param <T> the output type
     * @return a properly parametrized instance of this false Publisher
     */
    static <T> Mono<T> instance() {
        return (Mono<T>) INSTANCE;
    }

    @Override
    public void subscribe(CoreSubscriber<? super Boolean> actual) {
        actual.onSubscribe(Operators.scalarSubscription(actual, false));
    }

    @Override
    public Boolean call() {
        return false;
    }

    @Override
    public Boolean block(Duration m) {
        return false;
    }

    @Override
    public Boolean block() {
        return false;
    }
}
