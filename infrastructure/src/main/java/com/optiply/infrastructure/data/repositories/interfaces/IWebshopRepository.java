package com.optiply.infrastructure.data.repositories.interfaces;

import com.optiply.infrastructure.data.models.tables.pojos.Webshop;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
    Mono<Boolean> create(String handle, String url, Short interestRate);

    /**
     * Read by handle mono.
     *
     * @param handle the handle
     * @return the mono
     */
    Mono<Webshop> readByHandle(String handle);

    /**
     * Update mono.
     *
     * @param handle       the handle
     * @param url          the url
     * @param interestRate the interest rate
     * @return the mono
     */
    Mono<Boolean> update(String handle, String url, Short interestRate);

    /**
     * Read by handles flux.
     *
     * @param handles the handles
     * @return the flux
     */
    Flux<Webshop> readByHandles(String... handles);

    /**
     * Read by handle like flux.
     *
     * @param handle the handle
     * @return the flux
     */
    Flux<Webshop> readByHandleLike(String handle);

    /**
     * Update mono.
     *
     * @param id           the id
     * @param handle       the handle
     * @param url          the url
     * @param interestRate the interest rate
     * @return the mono
     */
    Mono<Boolean> update(Long id, String handle, String url, Short interestRate);

    /**
     * Delete mono.
     *
     * @param id the id
     * @return the mono
     */
    Mono<Boolean> delete(Long id);

}
