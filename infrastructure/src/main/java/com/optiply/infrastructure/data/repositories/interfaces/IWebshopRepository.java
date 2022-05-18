package com.optiply.infrastructure.data.repositories.interfaces;

import com.optiply.infrastructure.data.models.tables.pojos.Webshop;
import org.jooq.Condition;
import org.jooq.SortField;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The interface for the webshop table repository.
 * This interface is used to interact with the webshop table.
 *
 * @author G. Amaro
 */
public interface IWebshopRepository {

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
	 * Find various webshops, filtered by various conditions and sorted by a field with a certain order.
	 * This method is used to find webshops that match the conditions.
	 *
	 * @param condition the condition(s) to filter the webshops
	 * @param sort      the sortfield which sorts the webshops
	 * @return Flux with the webshops found
	 */
	Flux<String> findVarious(Condition condition, SortField<?> sort);

	/**
	 * Find a webshop by its handle.
	 *
	 * @param handle the handle
	 * @return Mono with the webshop
	 */
	Mono<Webshop> find(String handle);

	/**
	 * Updates a webshop given all the fields.
	 *
	 * @param handle        the handle
	 * @param newHandle     the new handle
	 * @param url           the url
	 * @param serviceLevelA the service level A percentage
	 * @param serviceLevelB the service level B percentage
	 * @param serviceLevelC the service level C percentage
	 * @param interestRate  the interest rate
	 * @param currency      the currency in ISO 4217 format
	 * @param runJobs       the ability to run jobs
	 * @param multiSupplier if it has multiple suppliers
	 * @return Mono with boolean indicating success
	 */
	Mono<Boolean> updateWebshop(String handle, String newHandle, String url,
	                            Double serviceLevelA, Double serviceLevelB, Double serviceLevelC,
	                            Short interestRate, String currency,
	                            Boolean runJobs, Boolean multiSupplier);

	/**
	 * Update webshop handle mono.
	 *
	 * @param handle    the handle
	 * @param newHandle the new handle
	 * @return the mono
	 */
	Mono<Boolean> updateWebshopHandle(String handle, String newHandle);

	/**
	 * Update webshop url mono.
	 *
	 * @param handle the handle
	 * @param url    the url
	 * @return the mono
	 */
	Mono<Boolean> updateWebshopUrl(String handle, String url);

	/**
	 * Update webshop interest rate mono.
	 *
	 * @param handle       the handle
	 * @param interestRate the interest rate
	 * @return the mono
	 */
	Mono<Boolean> updateWebshopInterestRate(String handle, Short interestRate);

	/**
	 * Update webshop settings mono.
	 *
	 * @param handle        the handle
	 * @param currency      the currency
	 * @param runJobs       the run jobs
	 * @param multiSupplier the multi supplier
	 * @return the mono
	 */
	Mono<Boolean> updateWebshopSettings(String handle, String currency,
	                                    Boolean runJobs, Boolean multiSupplier);

	/**
	 * Update webshop service levels mono.
	 *
	 * @param handle        the handle
	 * @param serviceLevelA the service level a
	 * @param serviceLevelB the service level b
	 * @param serviceLevelC the service level c
	 * @return the mono
	 */
	Mono<Boolean> updateWebshopServiceLevels(String handle, Double serviceLevelA,
	                                         Double serviceLevelB, Double serviceLevelC);

	/**
	 * Delete a webshop by its handle.
	 *
	 * @param handle the handle
	 * @return Mono with boolean indicating success
	 */
	Mono<Boolean> deleteWebshop(String handle);
}
