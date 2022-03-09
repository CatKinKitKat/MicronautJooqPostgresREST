package com.optiply.infrastructure.data.jooq.repositories;

import com.optiply.infrastructure.data.jooq.repositories.tables.pojos.Example;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Example repository
 * Delete when no longer necessary
 */
public interface ExampleRepository {

    Mono<Example> findById(UUID uuid);

    Mono<Boolean> insert(Example example);
}
