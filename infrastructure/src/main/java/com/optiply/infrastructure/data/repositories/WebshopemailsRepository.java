package com.optiply.infrastructure.data.repositories;

import com.optiply.infrastructure.data.models.Tables;
import com.optiply.infrastructure.data.models.tables.pojos.Webshopemails;
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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The type Webshopemails repository.
 */
@Singleton
public class WebshopemailsRepository {

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

	public Mono<Boolean> create(Long webshopId, String email) {
		return Mono.from(operations.withTransaction(
				new DefaultTransactionDefinition(
						TransactionDefinition.Propagation.MANDATORY
				), status -> Mono.from(DSL
								.using(status.getConnection(), SQLDialect.POSTGRES, dslContext.settings())
								.insertInto(Tables.WEBSHOPEMAILS)
								.columns(Tables.WEBSHOPEMAILS.WEBSHOPID, Tables.WEBSHOPEMAILS.ADDRESS)
								.values(webshopId, email))
						.map(result -> result == QueryResult.SUCCESS.ordinal())));
	}

	/**
	 * Read by webshop id flux.
	 *
	 * @param webshopId the webshop id
	 * @return the flux
	 */

	public Flux<Webshopemails> readByWebshopId(Long webshopId) {
		return Flux
				.from(operations.withTransaction(TransactionDefinition.READ_ONLY, status -> Mono.from(DSL
								.using(status.getConnection(), SQLDialect.POSTGRES, dslContext.settings())
								.select(Tables.WEBSHOPEMAILS.asterisk())
								.from(Tables.WEBSHOPEMAILS)
								.where(Tables.WEBSHOPEMAILS.WEBSHOPID.eq(webshopId)))
						.map(result -> result.into(Webshopemails.class))));
	}

	/**
	 * Update mono.
	 *
	 * @param id    the id
	 * @param email the email
	 * @return the mono
	 */

	public Mono<Boolean> update(Long id, String email) {
		return Mono.from(operations.withTransaction(
				new DefaultTransactionDefinition(
						TransactionDefinition.Propagation.MANDATORY
				), status -> Mono.from(DSL
								.using(status.getConnection(), SQLDialect.POSTGRES, dslContext.settings())
								.update(Tables.WEBSHOPEMAILS)
								.set(Tables.WEBSHOPEMAILS.ADDRESS, email)
								.where(Tables.WEBSHOPEMAILS.WEBSHOPID.eq(id)))
						.map(result -> result == QueryResult.SUCCESS.ordinal())));
	}

	/**
	 * Delete mono.
	 *
	 * @param id the id
	 * @return the mono
	 */

	public Mono<Boolean> delete(Long id) {
		return Mono.from(operations.withTransaction(
				new DefaultTransactionDefinition(
						TransactionDefinition.Propagation.MANDATORY
				), status -> Mono.from(DSL
								.using(status.getConnection(), SQLDialect.POSTGRES, dslContext.settings())
								.delete(Tables.WEBSHOPEMAILS)
								.where(Tables.WEBSHOPEMAILS.WEBSHOPID.eq(id)))
						.map(result -> result == QueryResult.SUCCESS.ordinal())));
	}
}
