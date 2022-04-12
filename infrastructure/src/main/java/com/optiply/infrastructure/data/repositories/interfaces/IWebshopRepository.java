package com.optiply.infrastructure.data.repositories.interfaces;

import com.optiply.infrastructure.data.models.tables.pojos.Webshop;
import org.jooq.Condition;
import org.jooq.SortField;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The interface for the webshop table repository.
 * This interface is used to interact with the webshop table.
 * @author G. Amaro
 */
public interface IWebshopRepository {
    /**
     * Create a new webshop (simple).
     * Meaning, it will create a new webshop with only the fields that do not have default values.
     *
     * @param handle the handle
     * @param url    the url
     * @param A      the service level A percentage
     * @param B      the service level B percentage
     * @param C      the service level C percentage
     * @return Mono with boolean indicating success
     */
    Mono<Boolean> create(String handle, String url,
                         Double A, Double B, Double C);

    /**
     * Create a new webshop (complex).
     * Meaning, it will create a new webshop with all the fields.
     *
     * @param handle        the handle
     * @param url           the url
     * @param A             the service level A percentage
     * @param B             the service level B percentage
     * @param C             the service level C percentage
     * @param interestRate  the interest rate
     * @param currency      the currency in ISO 4217 format
     * @param runJobs       if the webshop will run jobs
     * @param multiSupplier if it has multiple suppliers
     * @return the mono
     */
    Mono<Boolean> create(String handle, String url,
                         Double A, Double B, Double C,
                         Short interestRate, String currency,
                         Boolean runJobs, Boolean multiSupplier);

    /**
     * Gets webshop_id of a webshop by its handle.
     *
     * @param handle the handle
     * @return Mono with the webshop_id
     */
    Mono<Long> getWebshopId(String handle);

    /**
     * Find various webshops, filtered by various conditions and sorted by a field with a certain order.
     * This method is used to find webshops that match the conditions.
     *
     * @param condition the condition(s) to filter the webshops
     * @param sort      the sortfield which sorts the webshops
     * @return Flux with the webshops found
     */
    Flux<Webshop> findVarious(Condition condition, SortField<?> sort);

    /**
     * Find all webshops in the database.
     *
     * @return Flux with all the webshops
     */
    Flux<Webshop> findAll();

    /**
     * Find a webshop by its handle.
     *
     * @param handle the handle
     * @return Mono with the webshop
     */
    Mono<Webshop> find(String handle);

    /**
     * Find webshop by webshop_id.
     *
     * @param id the webshop_id
     * @return Mono with the webshop_id
     */
    Mono<Webshop> find(Long id);

    /**
     * Updates a webshop given all the fields.
     *
     * @param handle        the handle
     * @param url           the url
     * @param A             the service level A percentage
     * @param B             the service level B percentage
     * @param C             the service level C percentage
     * @param INTEREST_RATE the interest rate
     * @param currency      the currency in ISO 4217 format
     * @param RUN_JOBS      the ability to run jobs
     * @param multiSupplier if it has multiple suppliers
     * @return Mono with boolean indicating success
     */
    Mono<Boolean> updateWebshop(String handle, String url,
                                Double A, Double B, Double C,
                                Short INTEREST_RATE, String currency,
                                Boolean RUN_JOBS, Boolean multiSupplier);

    /**
     * Update a webshop given all the fields (and the webshop_id).
     *
     * @param id            the webshop_id
     * @param handle        the handle
     * @param url           the url
     * @param A             the service level A percentage
     * @param B             the service level B percentage
     * @param C             the service level C percentage
     * @param INTEREST_RATE the interest rate
     * @param currency      the currency in ISO 4217 format
     * @param RUN_JOBS      the ability to run jobs
     * @param multiSupplier if it has multiple suppliers
     * @return Mono with boolean indicating success
     */
    Mono<Boolean> updateWebshop(Long id, String handle, String url,
                                Double A, Double B, Double C,
                                Short INTEREST_RATE, String currency,
                                Boolean RUN_JOBS, Boolean multiSupplier);

    /**
     * Delete a webshop by its webshop_id.
     *
     * @param id the webshop_id
     * @return Mono with boolean indicating success
     */
    Mono<Boolean> deleteWebshop(Long id);

    /**
     * Delete a webshop by its handle.
     *
     * @param handle the handle
     * @return Mono with boolean indicating success
     */
    Mono<Boolean> deleteWebshop(String handle);
}
