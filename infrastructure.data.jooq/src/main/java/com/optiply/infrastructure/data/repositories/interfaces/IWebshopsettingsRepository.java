package com.optiply.infrastructure.data.repositories.interfaces;

import com.optiply.infrastructure.data.models.tables.pojos.Webshopsettings;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * The interface Webshopsettings repository.
 */
public interface IWebshopsettingsRepository {

    /**
     * Create mono.
     *
     * @param webshopId     the webshop id
     * @param currency      the currency
     * @param runJobs       the run jobs
     * @param multiSupplier the multi supplier
     * @return the mono
     */
    Mono<Void> create(UUID webshopId, String currency, Boolean runJobs, Boolean multiSupplier);

    /**
     * Read mono.
     *
     * @param webshopId the webshop id
     * @return the mono
     */
    Mono<Webshopsettings> read(UUID webshopId);

    /**
     * Update mono.
     *
     * @param webshopId     the webshop id
     * @param currency      the currency
     * @param runJobs       the run jobs
     * @param multiSupplier the multi supplier
     * @return the mono
     */
    Mono<Void> update(UUID webshopId, String currency, Boolean runJobs, Boolean multiSupplier);

    /**
     * Delete mono.
     *
     * @param webshopId the webshop id
     * @return the mono
     */
    Mono<Void> delete(UUID webshopId);
}
