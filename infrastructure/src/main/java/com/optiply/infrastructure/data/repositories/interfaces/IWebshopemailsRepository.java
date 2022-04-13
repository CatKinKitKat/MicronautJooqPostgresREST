package com.optiply.infrastructure.data.repositories.interfaces;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The interface for the webshopemails table repository.
 * This interface is used to interact with the webshopemails table.
 *
 * @author G. Amaro
 */
public interface IWebshopemailsRepository {

	/**
	 * Create a new webshopemails record.
	 *
	 * @param id    the webshop_id
	 * @param email the address
	 * @return Mono with boolean indicating success
	 */
	Mono<Boolean> create(Long id, String email);

	/**
	 * Create a new webshopemails record.
	 *
	 * @param handle the handle from which to get the webshop_id
	 * @param email  the address
	 * @return Mono with boolean indicating success
	 */
	Mono<Boolean> create(String handle, String email);

	/**
	 * Find emails pertaining to a certain webshop via its handle.
	 *
	 * @param handle the handle
	 * @return Flux with webshopemails records matching the handle
	 */
	Flux<String> findEmails(String handle);

	/**
	 * Delete a specific email pertaining to a certain webshop.
	 *
	 * @param handle the webshop handle
	 * @param email  the email address
	 * @return Mono with boolean indicating success
	 */
	Mono<Boolean> delete(String handle, String email);
}
