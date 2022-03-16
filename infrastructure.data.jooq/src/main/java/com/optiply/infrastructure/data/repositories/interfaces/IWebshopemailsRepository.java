package com.optiply.infrastructure.data.repositories.interfaces;

import com.optiply.infrastructure.data.models.tables.pojos.Webshopemails;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * The interface Webshopemails repository.
 */
public interface IWebshopemailsRepository {

    /**
     * Create mono.
     *
     * @param webshopId the webshop id
     * @param email     the email
     * @return the mono
     */
    Mono<Void> create(UUID webshopId, String email);

    /**
     * Read by webshop id flux.
     *
     * @param webshopId the webshop id
     * @return the flux
     */
    Flux<Webshopemails> readByWebshopId(UUID webshopId);

    /**
     * Update mono.
     *
     * @param id    the id
     * @param email the email
     * @return the mono
     */
    Mono<Void> update(UUID id, String email);

    /**
     * Delete mono.
     *
     * @param id the id
     * @return the mono
     */
    Mono<Void> delete(UUID id);

}
