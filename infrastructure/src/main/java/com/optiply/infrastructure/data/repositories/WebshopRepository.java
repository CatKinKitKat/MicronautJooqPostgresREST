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

import java.util.List;

/**
 * The type Webshop repository.
 */
@Log
@Singleton
public class WebshopRepository {

	/**
	 * The Dsl context.
	 */
	private final DSLContext dslContext;
	/**
	 * The Operations.
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
	 * Create mono.
	 *
	 * @param handle the handle
	 * @param url    the url
	 * @param A      the a
	 * @param B      the b
	 * @param C      the c
	 * @return the mono
	 */
	public Mono<Boolean> create(String handle, String url,
	                            Double A, Double B, Double C) {

		return Mono.from(operations.withTransaction(
				new DefaultTransactionDefinition(
						TransactionDefinition.Propagation.MANDATORY
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
	 * Create mono.
	 *
	 * @param handle        the handle
	 * @param url           the url
	 * @param A             the a
	 * @param B             the b
	 * @param C             the c
	 * @param interestRate  the interest rate
	 * @param currency      the currency
	 * @param runJobs       the run jobs
	 * @param multiSupplier the multi supplier
	 * @return the mono
	 */
	public Mono<Boolean> create(String handle, String url,
	                            Double A, Double B, Double C,
	                            Short interestRate, String currency,
	                            Boolean runJobs, Boolean multiSupplier) {

		log.info("Creating webshop: " + handle);
		return Mono.from(operations.withTransaction(
				new DefaultTransactionDefinition(
						TransactionDefinition.Propagation.MANDATORY
				), status -> Mono
						.from(DSL
								.using(status.getConnection(), SQLDialect.POSTGRES, dslContext.settings())
								.insertInto(Tables.WEBSHOP)
								.columns(Tables.WEBSHOP.HANDLE, Tables.WEBSHOP.URL, Tables.WEBSHOP.A, Tables.WEBSHOP.B, Tables.WEBSHOP.C, Tables.WEBSHOP.INTERESTRATE, Tables.WEBSHOP.CURRENCY, Tables.WEBSHOP.RUNJOBS, Tables.WEBSHOP.MULTISUPPLY)
								.values(handle, url, A, B, C, interestRate, currency, runJobs, multiSupplier))
						.map(result -> result == QueryResult.SUCCESS.ordinal())
						.onErrorReturn(false)));
	}


	/**
	 * Gets webshop id.
	 *
	 * @param handle the handle
	 * @return the webshop id
	 */
	public Mono<Long> getWebshopId(String handle) {

		return Mono
				.from(operations.withTransaction(TransactionDefinition.READ_ONLY, status -> DSL
						.using(status.getConnection(), SQLDialect.POSTGRES, dslContext.settings())
						.select(Tables.WEBSHOP.WEBSHOPID)
						.from(Tables.WEBSHOP)
						.where(Tables.WEBSHOP.HANDLE.equalIgnoreCase(handle))))
				.map(result -> result.into(Long.class));
	}

	/**
	 * Find various flux.
	 *
	 * @param conditions the conditions
	 * @return the flux
	 */
	public Flux<Webshop> findVarious(List<Condition> conditions, SortField<?> sort) {

		return Flux.from(operations.withTransaction(TransactionDefinition.READ_ONLY, status -> DSL
						.using(status.getConnection(), SQLDialect.POSTGRES, dslContext.settings())
						.select(Tables.WEBSHOP.asterisk())
						.from(Tables.WEBSHOP)
						.where(conditions)
						.orderBy(sort)))
				.map(result -> result.into(Webshop.class));
	}

	/**
	 * Find mono.
	 *
	 * @param handle the handle
	 * @return the mono
	 */
	public Mono<Webshop> find(String handle) {

		return Mono
				.from(operations.withTransaction(TransactionDefinition.READ_ONLY, status -> DSL
						.using(status.getConnection(), SQLDialect.POSTGRES, dslContext.settings())
						.select(Tables.WEBSHOP.asterisk())
						.from(Tables.WEBSHOP)
						.where(Tables.WEBSHOP.HANDLE.equalIgnoreCase(handle))))
				.map(result -> result.into(Webshop.class));
	}

	/**
	 * Find mono.
	 *
	 * @param id the id
	 * @return the mono
	 */
	public Mono<Webshop> find(Long id) {

		return Mono
				.from(operations.withTransaction(TransactionDefinition.READ_ONLY, status -> DSL
						.using(status.getConnection(), SQLDialect.POSTGRES, dslContext.settings())
						.select(Tables.WEBSHOP.asterisk())
						.from(Tables.WEBSHOP)
						.where(Tables.WEBSHOP.WEBSHOPID.eq(id))))
				.map(result -> result.into(Webshop.class));
	}

	/**
	 * Update webshop mono.
	 *
	 * @param handle        the handle
	 * @param url           the url
	 * @param A             the a
	 * @param B             the b
	 * @param C             the c
	 * @param interestRate  the interest rate
	 * @param currency      the currency
	 * @param runJobs       the run jobs
	 * @param multiSupplier the multi supplier
	 * @return the mono
	 */
	public Mono<Boolean> updateWebshop(String handle, String url,
	                                   Double A, Double B, Double C,
	                                   Short interestRate, String currency,
	                                   Boolean runJobs, Boolean multiSupplier) {

		return Mono.from(operations.withTransaction(
				new DefaultTransactionDefinition(
						TransactionDefinition.Propagation.MANDATORY
				), status -> Mono
						.from(DSL
								.using(status.getConnection(), SQLDialect.POSTGRES, dslContext.settings())
								.update(Tables.WEBSHOP)
								.set(Tables.WEBSHOP.HANDLE, handle)
								.set(Tables.WEBSHOP.URL, url)
								.set(Tables.WEBSHOP.A, A)
								.set(Tables.WEBSHOP.B, B)
								.set(Tables.WEBSHOP.C, C)
								.set(Tables.WEBSHOP.INTERESTRATE, interestRate)
								.set(Tables.WEBSHOP.CURRENCY, currency)
								.set(Tables.WEBSHOP.RUNJOBS, runJobs)
								.set(Tables.WEBSHOP.MULTISUPPLY, multiSupplier)
								.where(Tables.WEBSHOP.HANDLE.equalIgnoreCase(handle)))
						.map(result -> result == QueryResult.SUCCESS.ordinal())));

	}

	/**
	 * Update webshop mono.
	 *
	 * @param id            the id
	 * @param handle        the handle
	 * @param url           the url
	 * @param A             the a
	 * @param B             the b
	 * @param C             the c
	 * @param interestRate  the interest rate
	 * @param currency      the currency
	 * @param runJobs       the run jobs
	 * @param multiSupplier the multi supplier
	 * @return the mono
	 */
	public Mono<Boolean> updateWebshop(Long id, String handle, String url,
	                                   Double A, Double B, Double C,
	                                   Short interestRate, String currency,
	                                   Boolean runJobs, Boolean multiSupplier) {

		return Mono.from(operations.withTransaction(
				new DefaultTransactionDefinition(
						TransactionDefinition.Propagation.MANDATORY
				), status -> Mono
						.from(DSL
								.using(status.getConnection(), SQLDialect.POSTGRES, dslContext.settings())
								.update(Tables.WEBSHOP)
								.set(Tables.WEBSHOP.HANDLE, handle)
								.set(Tables.WEBSHOP.URL, url)
								.set(Tables.WEBSHOP.A, A)
								.set(Tables.WEBSHOP.B, B)
								.set(Tables.WEBSHOP.C, C)
								.set(Tables.WEBSHOP.INTERESTRATE, interestRate)
								.set(Tables.WEBSHOP.CURRENCY, currency)
								.set(Tables.WEBSHOP.RUNJOBS, runJobs)
								.set(Tables.WEBSHOP.MULTISUPPLY, multiSupplier)
								.where(Tables.WEBSHOP.WEBSHOPID.eq(id)))
						.map(result -> result == QueryResult.SUCCESS.ordinal())));

	}

	/**
	 * Delete webshop mono.
	 *
	 * @param id the id
	 * @return the mono
	 */
	public Mono<Boolean> deleteWebshop(Long id) {

		return Mono.from(operations.withTransaction(
				new DefaultTransactionDefinition(
						TransactionDefinition.Propagation.MANDATORY
				), status -> Mono
						.from(DSL
								.using(status.getConnection(), SQLDialect.POSTGRES, dslContext.settings())
								.delete(Tables.WEBSHOP)
								.where(Tables.WEBSHOP.WEBSHOPID.eq(id)))
						.map(result -> result == QueryResult.SUCCESS.ordinal())));
	}

	/**
	 * Delete webshop mono.
	 *
	 * @param handle the handle
	 * @return the mono
	 */
	public Mono<Boolean> deleteWebshop(String handle) {

		return Mono.from(operations.withTransaction(
				new DefaultTransactionDefinition(
						TransactionDefinition.Propagation.MANDATORY
				), status -> Mono
						.from(DSL
								.using(status.getConnection(), SQLDialect.POSTGRES, dslContext.settings())
								.delete(Tables.WEBSHOP)
								.where(Tables.WEBSHOP.HANDLE.equalIgnoreCase(handle)))
						.map(result -> result == QueryResult.SUCCESS.ordinal())));
	}
}
