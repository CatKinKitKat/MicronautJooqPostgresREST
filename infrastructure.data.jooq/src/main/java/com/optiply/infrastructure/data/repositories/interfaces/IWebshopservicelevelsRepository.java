package com.optiply.infrastructure.data.repositories.interfaces;

import com.optiply.infrastructure.data.models.tables.pojos.Webshopservicelevels;
import reactor.core.publisher.Mono;

/**
 * The interface Webshopservicelevels repository.
 */
public interface IWebshopservicelevelsRepository {

    /**
     * Create mono.
     *
     * @param webshopId the webshop id
     * @param slcA      the slc a
     * @param slcB      the slc b
     * @param slcC      the slc c
     * @return the mono
     */
    Mono<Void> create(Long webshopId, Double slcA, Double slcB, Double slcC);

    /**
     * Read mono.
     *
     * @param webshopId the webshop id
     * @return the mono
     */
    Mono<Webshopservicelevels> read(Long webshopId);

    /**
     * Update mono.
     *
     * @param webshopId the webshop id
     * @param slcA      the slc a
     * @param slcB      the slc b
     * @param slcC      the slc c
     * @return the mono
     */
    Mono<Void> update(Long webshopId, Double slcA, Double slcB, Double slcC);

    /**
     * Delete mono.
     *
     * @param webshopId the webshop id
     * @return the mono
     */
    Mono<Void> delete(Long webshopId);
}
