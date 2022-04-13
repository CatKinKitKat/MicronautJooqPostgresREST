package com.optiply.infrastructure.data.repositories;

import com.optiply.infrastructure.data.models.Tables;
import com.optiply.infrastructure.data.models.tables.pojos.Webshop;
import com.optiply.infrastructure.data.support.sql.QueryResult;
import io.micronaut.data.r2dbc.operations.R2dbcOperations;
import io.micronaut.transaction.TransactionDefinition;
import io.micronaut.transaction.support.DefaultTransactionDefinition;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import lombok.extern.java.Log;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.SortField;
import org.jooq.impl.DSL;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The Webshop table Repository.
 * Used to access the Webshop table with JOOQ type safe operations via the R2DBC driver in a reactive fashion.
 *
 * @author G. Amaro
 */
@Log
@Singleton
public class WebshopRepository implements com.optiply.infrastructure.data.repositories.interfaces.IWebshopRepository {

	/**
	 * The DSL context, used to generate SQL queries or get its dialect and other properties.
	 */
	private final DSLContext dslContext;
	/**
	 * The R2DBC driver, used to execute SQL queries in a reactive fashion, needs DSLContext properties.
	 */
	private final R2dbcOperations operations;

	/**
	 * Instantiates a new Webshop repository.
	 *
	 * @param dslContext the dsl context
	 * @param operations the operations
	 */
	@Inject
	public WebshopRepository(@Named("dsl") DSLContext dslContext,
	                         R2dbcOperations operations) {

		this.dslContext = dslContext;
		this.operations = operations;
	}

	/**
	 * Creates a new webshop with only non-default fields required.
	 *
	 * @param handle the webshop handle
	 * @param url    the webshop
	 * @param A      the percentage of service level A
	 * @param B      the percentage of service level B
	 * @param C      the percentage of service level C
	 * @return the mono
	 */
	@Override
	public Mono<Boolean> create(String handle, String url,
	                            Double A, Double B, Double C) {
		log.info("Creating webshop: " + handle);
		return Mono.from(operations.withTransaction(
				new DefaultTransactionDefinition(
						TransactionDefinition.Propagation.REQUIRES_NEW
				), status -> Mono
						.from(DSL
								.using(status.getConnection(), SQLDialect.POSTGRES, dslContext.settings())
								.insertInto(Tables.WEBSHOP)
								.columns(Tables.WEBSHOP.HANDLE, Tables.WEBSHOP.URL, Tables.WEBSHOP.A, Tables.WEBSHOP.B, Tables.WEBSHOP.C)
								.values(handle, url, A, B, C))
						.map(result -> result == QueryResult.SUCCESS.ordinal())
						.onErrorReturn(false)));
	}

	/**
	 * Creates a new webshop with all fields.
	 *
	 * @param handle        the webshop handle
	 * @param url           the webshop url
	 * @param A             the percentage of service level A
	 * @param B             the percentage of service level B
	 * @param C             the percentage of service level C
	 * @param interestRate  the webshop interest rate
	 * @param currency      the currency used in ISO 4217 format
	 * @param runJobs       the ability to run jobs
	 * @param multiSupplier if it has multi suppliers
	 * @return the mono
	 */
	@Override
	public Mono<Boolean> create(String handle, String url,
	                            Double A, Double B, Double C,
	                            Short interestRate, String currency,
	                            Boolean runJobs, Boolean multiSupplier) {
		log.info("Creating webshop: " + handle);
		return Mono.from(operations.withTransaction(
				new DefaultTransactionDefinition(
						TransactionDefinition.Propagation.REQUIRES_NEW
				), status -> Mono
						.from(DSL
								.using(status.getConnection(), SQLDialect.POSTGRES, dslContext.settings())
								.insertInto(Tables.WEBSHOP)
								.columns(Tables.WEBSHOP.HANDLE, Tables.WEBSHOP.URL, Tables.WEBSHOP.A, Tables.WEBSHOP.B, Tables.WEBSHOP.C, Tables.WEBSHOP.INTEREST_RATE, Tables.WEBSHOP.CURRENCY, Tables.WEBSHOP.RUN_JOBS, Tables.WEBSHOP.MULTI_SUPPLY)
								.values(handle, url, A, B, C, interestRate, currency, runJobs, multiSupplier))
						.map(result -> result == QueryResult.SUCCESS.ordinal())
						.onErrorReturn(false)));
	}

	/**
	 * Find various webshops by a given set of conditions calculated by the url query parameters.
	 *
	 * @param condition the condition list
	 * @param sort      the sortfield with the field and order
	 * @return Flux of webshops found
	 */
	@Override
	public Flux<Webshop> findVarious(Condition condition, SortField<?> sort) {
		log.info("Finding webshops with variable conditions sorted by specific field");

		return Flux.from(operations.withTransaction(TransactionDefinition.READ_ONLY, status -> DSL
						.using(status.getConnection(), SQLDialect.POSTGRES, dslContext.settings())
						.select(Tables.WEBSHOP.asterisk())
						.from(Tables.WEBSHOP)
						.where(condition)
						.orderBy(sort)
				))
				.map(result -> result.into(Webshop.class));
	}

	/**
	 * Find all webshops in the database.
	 *
	 * @return Flux with all the webshops
	 */
	@Override
	public Flux<Webshop> findAll() {
		log.info("Finding all webshops");
		return Flux.from(operations.withTransaction(TransactionDefinition.READ_ONLY, status -> DSL
						.using(status.getConnection(), SQLDialect.POSTGRES, dslContext.settings())
						.select(Tables.WEBSHOP.asterisk())
						.from(Tables.WEBSHOP)
				))
				.map(result -> result.into(Webshop.class));
	}

	/**
	 * Find a webshop by its handle.
	 *
	 * @param handle the handle
	 * @return Mono with the webshop found
	 */
	@Override
	public Mono<Webshop> find(String handle) {
		log.info("Finding webshop: " + handle);
		return Mono
				.from(operations.withTransaction(TransactionDefinition.READ_ONLY, status -> DSL
						.using(status.getConnection(), SQLDialect.POSTGRES, dslContext.settings())
						.select(Tables.WEBSHOP.asterisk())
						.from(Tables.WEBSHOP)
						.where(Tables.WEBSHOP.HANDLE.equalIgnoreCase(handle))))
				.map(result -> result.into(Webshop.class));
	}

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
	@Override
	public Mono<Boolean> updateWebshop(String handle, String url,
	                                   Double A, Double B, Double C,
	                                   Short INTEREST_RATE, String currency,
	                                   Boolean RUN_JOBS, Boolean multiSupplier) {
		log.info("Updating webshop: " + handle);
		return Mono.from(operations.withTransaction(
				new DefaultTransactionDefinition(
						TransactionDefinition.Propagation.REQUIRES_NEW
				), status -> Mono
						.from(DSL
								.using(status.getConnection(), SQLDialect.POSTGRES, dslContext.settings())
								.update(Tables.WEBSHOP)
								.set(Tables.WEBSHOP.HANDLE, handle)
								.set(Tables.WEBSHOP.URL, url)
								.set(Tables.WEBSHOP.A, A)
								.set(Tables.WEBSHOP.B, B)
								.set(Tables.WEBSHOP.C, C)
								.set(Tables.WEBSHOP.INTEREST_RATE, INTEREST_RATE)
								.set(Tables.WEBSHOP.CURRENCY, currency)
								.set(Tables.WEBSHOP.RUN_JOBS, RUN_JOBS)
								.set(Tables.WEBSHOP.MULTI_SUPPLY, multiSupplier)
								.where(Tables.WEBSHOP.HANDLE.equalIgnoreCase(handle)))
						.map(result -> result == QueryResult.SUCCESS.ordinal())));

	}

	/**
	 * Delete a webshop by its handle.
	 *
	 * @param handle the handle
	 * @return Mono with boolean indicating success
	 */
	@Override
	public Mono<Boolean> deleteWebshop(String handle) {
		log.info("Deleting webshop: " + handle);
		return Mono.from(operations.withTransaction(
				new DefaultTransactionDefinition(
						TransactionDefinition.Propagation.REQUIRES_NEW
				), status -> Mono
						.from(DSL
								.using(status.getConnection(), SQLDialect.POSTGRES, dslContext.settings())
								.delete(Tables.WEBSHOP)
								.where(Tables.WEBSHOP.HANDLE.equalIgnoreCase(handle)))
						.map(result -> result == QueryResult.SUCCESS.ordinal())));
	}
}
