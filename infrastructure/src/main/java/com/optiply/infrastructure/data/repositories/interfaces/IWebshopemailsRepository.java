package com.optiply.infrastructure.data.repositories.interfaces;

import reactor.core.publisher.Mono;

import java.util.List;

/**
 * The interface for the webshopemails table repository.
 * This interface is used to interact with the webshopemails table.
 *
 * @author G. Amaro
 */
public interface IWebshopemailsRepository {

	/**
	 * Create various mono.
	 *
	 * @param handle the handle
	 * @param emails the emails
	 * @return the mono
	 */
	Mono<Boolean> createVarious(String handle, List<String> emails);

	/**
	 * Find emails pertaining to a certain webshop via its handle.
	 *
	 * @param handle the handle
	 * @return Flux with webshopemails records matching the handle
	 */
	Mono<List<String>> findEmails(String handle);

	/**
	 * Delete all mono.
	 *
	 * @param handle the handle
	 * @return the mono
	 */
	Mono<Boolean> deleteAll(String handle);

	/**
	 * Update webshop emails mono.
	 *
	 * @param handle the handle
	 * @param emails the emails
	 * @return the mono
	 */
	Mono<Boolean> updateWebshopEmails(String handle, List<String> emails);
}
