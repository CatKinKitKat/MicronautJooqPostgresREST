package com.optiply.infrastructure.data.repositories;

import com.optiply.infrastructure.data.models.tables.pojos.Webshopsettings;
import com.optiply.infrastructure.data.repositories.interfaces.IWebshopsettingsRepository;
import io.micronaut.data.r2dbc.operations.R2dbcOperations;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import org.jooq.DSLContext;
import reactor.core.publisher.Mono;

/**
 * The type Webshopsettings repository.
 */
@Singleton
public class WebshopsettingsRepository implements IWebshopsettingsRepository {

	/**
	 * The Dsl context.
	 */
	private final DSLContext dslContext;
	/**
	 * The Operations.
	 */
	private final R2dbcOperations operations;


	/**
	 * Instantiates a new Webshopsettings repository.
	 *
	 * @param dslContext the dsl context
	 * @param operations the operations
	 */
	@Inject
	public WebshopsettingsRepository(@Named("dsl") DSLContext dslContext, @Named("r2dbc") R2dbcOperations operations) {
		this.dslContext = dslContext;
		this.operations = operations;
	}

	/**
	 * Create mono.
	 *
	 * @param webshopId     the webshop id
	 * @param currency      the currency
	 * @param runJobs       the run jobs
	 * @param multiSupplier the multi supplier
	 * @return the mono
	 */
	@Override
	public Mono<Boolean> create(Long webshopId, String currency, Boolean runJobs, Boolean multiSupplier) {
		return null;
	}

	/**
	 * Read mono.
	 *
	 * @param webshopId the webshop id
	 * @return the mono
	 */
	@Override
	public Mono<Webshopsettings> read(Long webshopId) {
		return null;
	}

	/**
	 * Update mono.
	 *
	 * @param webshopId     the webshop id
	 * @param currency      the currency
	 * @param runJobs       the run jobs
	 * @param multiSupplier the multi supplier
	 * @return the mono
	 */
	@Override
	public Mono<Boolean> update(Long webshopId, String currency, Boolean runJobs, Boolean multiSupplier) {
		return null;
	}

	/**
	 * Delete mono.
	 *
	 * @param webshopId the webshop id
	 * @return the mono
	 */
	@Override
	public Mono<Boolean> delete(Long webshopId) {
		return null;
	}
}
