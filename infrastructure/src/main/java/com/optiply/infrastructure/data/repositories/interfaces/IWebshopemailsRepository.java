package com.optiply.infrastructure.data.repositories.interfaces;

import com.optiply.infrastructure.data.models.tables.pojos.Webshopemails;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
	Mono<Boolean> create(Long webshopId, String email);

	/**
	 * Read by webshop id flux.
	 *
	 * @param webshopId the webshop id
	 * @return the flux
	 */
	Flux<Webshopemails> readByWebshopId(Long webshopId);

	/**
	 * Update mono.
	 *
	 * @param id    the id
	 * @param email the email
	 * @return the mono
	 */
	Mono<Boolean> update(Long id, String email);

	/**
	 * Delete mono.
	 *
	 * @param id the id
	 * @return the mono
	 */
	Mono<Boolean> delete(Long id);

}
