package com.optiply.infrastructure.data.repositories;

import com.optiply.infrastructure.data.models.Tables;
import com.optiply.infrastructure.data.models.tables.pojos.Webshopsettings;
import com.optiply.infrastructure.data.repositories.interfaces.IWebshopsettingsRepository;
import com.optiply.infrastructure.data.support.sql.QueryResult;
import io.micronaut.data.r2dbc.operations.R2dbcOperations;
import io.micronaut.transaction.TransactionDefinition;
import io.micronaut.transaction.support.DefaultTransactionDefinition;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
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
		return Mono.from(operations.withTransaction(
				new DefaultTransactionDefinition(
						TransactionDefinition.Propagation.MANDATORY
				), status -> Mono.from(DSL
								.using(status.getConnection(), SQLDialect.POSTGRES, dslContext.settings())
								.insertInto(Tables.WEBSHOPSETTINGS)
								.columns(
										Tables.WEBSHOPSETTINGS.WEBSHOPID,
										Tables.WEBSHOPSETTINGS.CURRENCY,
										Tables.WEBSHOPSETTINGS.RUNJOBS,
										Tables.WEBSHOPSETTINGS.MULTISUPPLY
								).values(webshopId, currency, runJobs, multiSupplier))
						.map(result -> result == QueryResult.SUCCESS.ordinal())));
	}

	/**
	 * Read mono.
	 *
	 * @param webshopId the webshop id
	 * @return the mono
	 */
	@Override
	public Mono<Webshopsettings> read(Long webshopId) {
		return Mono
				.from(operations.withTransaction(TransactionDefinition.READ_ONLY, status -> DSL
						.using(status.getConnection(), SQLDialect.POSTGRES, dslContext.settings())
						.select(Tables.WEBSHOPSETTINGS.asterisk())
						.from(Tables.WEBSHOPSETTINGS)
						.where(Tables.WEBSHOPSETTINGS.WEBSHOPID.eq(webshopId))))
				.map(result -> result.into(Webshopsettings.class));
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
		return Mono.from(operations.withTransaction(
				new DefaultTransactionDefinition(
						TransactionDefinition.Propagation.MANDATORY
				), status -> Mono.from(DSL
								.using(status.getConnection(), SQLDialect.POSTGRES, dslContext.settings())
								.update(Tables.WEBSHOPSETTINGS)
								.set(Tables.WEBSHOPSETTINGS.CURRENCY, currency)
								.set(Tables.WEBSHOPSETTINGS.RUNJOBS, runJobs)
								.set(Tables.WEBSHOPSETTINGS.MULTISUPPLY, multiSupplier)
								.where(Tables.WEBSHOPSETTINGS.WEBSHOPID.eq(webshopId)))
						.map(result -> result == QueryResult.SUCCESS.ordinal())));
	}

	/**
	 * Delete mono.
	 *
	 * @param webshopId the webshop id
	 * @return the mono
	 */
	@Override
	public Mono<Boolean> delete(Long webshopId) {
		return Mono.from(operations.withTransaction(
				new DefaultTransactionDefinition(
						TransactionDefinition.Propagation.MANDATORY
				), status -> Mono.from(DSL
								.using(status.getConnection(), SQLDialect.POSTGRES, dslContext.settings())
								.delete(Tables.WEBSHOPSETTINGS)
								.where(Tables.WEBSHOPSETTINGS.WEBSHOPID.eq(webshopId)))
						.map(result -> result == QueryResult.SUCCESS.ordinal())));
	}
}
