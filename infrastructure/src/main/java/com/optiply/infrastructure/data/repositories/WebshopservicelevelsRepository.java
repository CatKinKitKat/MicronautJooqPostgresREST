package com.optiply.infrastructure.data.repositories;

import com.optiply.infrastructure.data.models.Tables;
import com.optiply.infrastructure.data.models.tables.pojos.Webshopservicelevels;
import com.optiply.infrastructure.data.repositories.interfaces.IWebshopservicelevelsRepository;
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
 * The type Webshopservicelevels repository.
 */
@Singleton
public class WebshopservicelevelsRepository implements IWebshopservicelevelsRepository {

	/**
	 * The Dsl context.
	 */
	private final DSLContext dslContext;
	/**
	 * The Operations.
	 */
	private final R2dbcOperations operations;


	/**
	 * Instantiates a new Webshopservicelevels repository.
	 *
	 * @param dslContext the dsl context
	 * @param operations the operations
	 */
	@Inject
	public WebshopservicelevelsRepository(@Named("dsl") DSLContext dslContext, @Named("r2dbc") R2dbcOperations operations) {
		this.dslContext = dslContext;
		this.operations = operations;
	}

	/**
	 * Create mono.
	 *
	 * @param webshopId the webshop id
	 * @param slcA      the slc a
	 * @param slcB      the slc b
	 * @param slcC      the slc c
	 * @return the mono
	 */
	@Override
	public Mono<Boolean> create(Long webshopId, Double slcA, Double slcB, Double slcC) {
		return Mono.from(operations.withTransaction(
				new DefaultTransactionDefinition(
						TransactionDefinition.Propagation.MANDATORY
				), status -> Mono
						.from(DSL
								.using(status.getConnection(), SQLDialect.POSTGRES, dslContext.settings())
								.insertInto(Tables.WEBSHOPSERVICELEVELS)
								.columns(
										Tables.WEBSHOPSERVICELEVELS.WEBSHOPID,
										Tables.WEBSHOPSERVICELEVELS.SLCA,
										Tables.WEBSHOPSERVICELEVELS.SLCB,
										Tables.WEBSHOPSERVICELEVELS.SLCC
								).values(webshopId, slcA, slcB, slcC))
						.map(result -> result == QueryResult.SUCCESS.ordinal())));

	}

	/**
	 * Read mono.
	 *
	 * @param webshopId the webshop id
	 * @return the mono
	 */
	@Override
	public Mono<Webshopservicelevels> read(Long webshopId) {
		return Mono
				.from(operations.withTransaction(TransactionDefinition.READ_ONLY, status -> Mono
						.from(DSL
								.using(status.getConnection(), SQLDialect.POSTGRES, dslContext.settings())
								.selectFrom(Tables.WEBSHOPSERVICELEVELS)
								.where(Tables.WEBSHOPSERVICELEVELS.WEBSHOPID.eq(webshopId)))
						.map(result -> result.into(Webshopservicelevels.class))));
	}

	/**
	 * Update mono.
	 *
	 * @param webshopId the webshop id
	 * @param slcA      the slc a
	 * @param slcB      the slc b
	 * @param slcC      the slc c
	 * @return the mono
	 */
	@Override
	public Mono<Boolean> update(Long webshopId, Double slcA, Double slcB, Double slcC) {
		return Mono.from(operations.withTransaction(
				new DefaultTransactionDefinition(
						TransactionDefinition.Propagation.MANDATORY
				), status -> Mono
						.from(DSL
								.using(status.getConnection(), SQLDialect.POSTGRES, dslContext.settings())
								.update(Tables.WEBSHOPSERVICELEVELS)
								.set(Tables.WEBSHOPSERVICELEVELS.SLCA, slcA)
								.set(Tables.WEBSHOPSERVICELEVELS.SLCB, slcB)
								.set(Tables.WEBSHOPSERVICELEVELS.SLCC, slcC)
								.where(Tables.WEBSHOPSERVICELEVELS.WEBSHOPID.eq(webshopId)))
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
				), status -> Mono
						.from(DSL
								.using(status.getConnection(), SQLDialect.POSTGRES, dslContext.settings())
								.delete(Tables.WEBSHOPSERVICELEVELS)
								.where(Tables.WEBSHOPSERVICELEVELS.WEBSHOPID.eq(webshopId)))
						.map(result -> result == QueryResult.SUCCESS.ordinal())));
	}
}
