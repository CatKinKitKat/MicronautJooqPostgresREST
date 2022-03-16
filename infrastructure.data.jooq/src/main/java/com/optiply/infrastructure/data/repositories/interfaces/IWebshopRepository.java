package com.optiply.infrastructure.data.repositories.interfaces;

import com.optiply.infrastructure.data.models.tables.pojos.Webshop;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * The interface Webshop repository.
 */
public interface IWebshopRepository {

    /**
     * Create mono.
     *
     * @param handle       the handle
     * @param url          the url
     * @param interestRate the interest rate
     * @return the mono
     */
    Mono<Void> create(String handle, String url, Integer interestRate);

    /**
     * Read by handle mono.
     *
     * @param handle the handle
     * @return the mono
     */
    Mono<Webshop> readByHandle(String handle);

    /**
     * Read by handles flux.
     *
     * @param handles the handles
     * @return the flux
     */
    Flux<Webshop> readByHandles(String... handles);

    /**
     * Update mono.
     *
     * @param id           the id
     * @param handle       the handle
     * @param url          the url
     * @param interestRate the interest rate
     * @return the mono
     */
    Mono<Void> update(UUID id, String handle, String url, Integer interestRate);

    /**
     * Delete mono.
     *
     * @param id the id
     * @return the mono
     */
    Mono<Void> delete(UUID id);

}
