package com.optiply.infrastructure.data.repositories;

import com.optiply.infrastructure.data.models.tables.pojos.Webshopemails;
import com.optiply.infrastructure.data.repositories.interfaces.IWebshopemailsRepository;
import io.micronaut.data.r2dbc.operations.R2dbcOperations;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import org.jooq.DSLContext;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The type Webshopemails repository.
 */
@Singleton
public class WebshopemailsRepository implements IWebshopemailsRepository {

	/**
	 * The Dsl context.
	 */
	private final DSLContext dslContext;
	/**
	 * The Operations.
	 */
	private final R2dbcOperations operations;


	/**
	 * Instantiates a new Webshopemails repository.
	 *
	 * @param dslContext the dsl context
	 * @param operations the operations
	 */
	@Inject
	public WebshopemailsRepository(@Named("dsl") DSLContext dslContext, @Named("r2dbc") R2dbcOperations operations) {
		this.dslContext = dslContext;
		this.operations = operations;
	}

	/**
	 * Create mono.
	 *
	 * @param webshopId the webshop id
	 * @param email     the email
	 * @return the mono
	 */
	@Override
	public Mono<Boolean> create(Long webshopId, String email) {
		return null;
	}

	/**
	 * Read by webshop id flux.
	 *
	 * @param webshopId the webshop id
	 * @return the flux
	 */
	@Override
	public Flux<Webshopemails> readByWebshopId(Long webshopId) {
		return null;
	}

	/**
	 * Update mono.
	 *
	 * @param id    the id
	 * @param email the email
	 * @return the mono
	 */
	@Override
	public Mono<Boolean> update(Long id, String email) {
		return null;
	}

	/**
	 * Delete mono.
	 *
	 * @param id the id
	 * @return the mono
	 */
	@Override
	public Mono<Boolean> delete(Long id) {
		return null;
	}
}
