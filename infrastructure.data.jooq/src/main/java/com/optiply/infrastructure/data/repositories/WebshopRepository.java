package com.optiply.infrastructure.data.repositories;

import com.optiply.infrastructure.data.models.tables.pojos.Webshop;
import com.optiply.infrastructure.data.repositories.interfaces.IWebshopRepository;
import io.micronaut.data.r2dbc.operations.R2dbcOperations;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import org.jooq.DSLContext;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * The type Webshop repository.
 */
@Singleton
public class WebshopRepository implements IWebshopRepository {

    /**
     * The Dsl context.
     */
    private final DSLContext dslContext;
    /**
     * The Operations.
     */
    private final R2dbcOperations operations;

    /**
     * Instantiates a new Webshop repository.
     *
     * @param dslContext the dsl context
     * @param operations the operations
     */
    @Inject
    public WebshopRepository(@Named("dsl") DSLContext dslContext, @Named("r2dbc") R2dbcOperations operations) {
        this.dslContext = dslContext;
        this.operations = operations;
    }

    /**
     * Create mono.
     *
     * @param handle       the handle
     * @param url          the url
     * @param interestRate the interest rate
     * @return the mono
     */
    @Override
    public Mono<Void> create(String handle, String url, Integer interestRate) {
        return null;
    }

    /**
     * Read by handle mono.
     *
     * @param handle the handle
     * @return the mono
     */
    @Override
    public Mono<Webshop> readByHandle(String handle) {
        return null;
    }

    /**
     * Read by handles flux.
     *
     * @param handles the handles
     * @return the flux
     */
    @Override
    public Flux<Webshop> readByHandles(String... handles) {
        return null;
    }

    /**
     * Update mono.
     *
     * @param id           the id
     * @param handle       the handle
     * @param url          the url
     * @param interestRate the interest rate
     * @return the mono
     */
    @Override
    public Mono<Void> update(UUID id, String handle, String url, Integer interestRate) {
        return null;
    }

    /**
     * Delete mono.
     *
     * @param id the id
     * @return the mono
     */
    @Override
    public Mono<Void> delete(UUID id) {
        return null;
    }
}
