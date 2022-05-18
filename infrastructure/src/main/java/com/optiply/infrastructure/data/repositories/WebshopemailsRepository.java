package com.optiply.infrastructure.data.repositories;


import com.optiply.infrastructure.data.models.Tables;
import com.optiply.infrastructure.data.models.tables.records.WebshopemailsRecord;
import com.optiply.infrastructure.data.support.sql.QueryResult;
import io.micronaut.data.r2dbc.operations.R2dbcOperations;
import io.micronaut.transaction.TransactionDefinition;
import io.micronaut.transaction.support.DefaultTransactionDefinition;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import lombok.extern.java.Log;
import org.jooq.DSLContext;
import org.jooq.InsertValuesStep2;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;


/**
 * The Webshopemails table Repository.
 * Used to access the Webshopemails table with JOOQ type safe operations via the R2DBC driver in a reactive fashion.
 *
 * @author G. Amaro
 */
@Log
@Singleton
public class WebshopemailsRepository implements com.optiply.infrastructure.data.repositories.interfaces.IWebshopemailsRepository {

	/**
	 * The DSL context, used to generate SQL queries or get its dialect and other properties.
	 */
	private final DSLContext dslContext;
	/**
	 * The R2DBC driver, used to execute SQL queries in a reactive fashion, needs DSLContext properties.
	 */
	private final R2dbcOperations operations;

	/**
	 * Instantiates a new Webshopemails repository.
	 *
	 * @param dslContext the dsl context
	 * @param operations the operations
	 */
	@Inject
	public WebshopemailsRepository(@Named("dsl") DSLContext dslContext, R2dbcOperations operations) {

		this.dslContext = dslContext;
		this.operations = operations;
	}

	/**
	 * Create various emails for a given webshop.
	 *
	 * @param handle the webshop handle
	 * @param emails the emails
	 */
	@Override
	public Mono<Boolean> createVarious(String handle, List<String> emails) {

		log.info("Creating emails: " + emails);
		return Mono.from(operations.withTransaction(
				new DefaultTransactionDefinition(
						TransactionDefinition.Propagation.REQUIRES_NEW
				), status -> Mono
						.from(DSL
								.using(status.getConnection(), SQLDialect.POSTGRES, dslContext.settings())
								.select(Tables.WEBSHOP.WEBSHOP_ID)
								.from(Tables.WEBSHOP)
								.where(Tables.WEBSHOP.HANDLE.eq(handle)))
						.flatMap(id -> {
							List<InsertValuesStep2<WebshopemailsRecord, Long, String>> instructions = new java.util.ArrayList<>();
							for (String email : emails) {
								instructions.add(
										DSL.insertInto(Tables.WEBSHOPEMAILS)
												.columns(Tables.WEBSHOPEMAILS.WEBSHOP_ID, Tables.WEBSHOPEMAILS.ADDRESS)
												.values(id.value1(), email));
							}
							return Mono.from(DSL
									.using(status.getConnection(), SQLDialect.POSTGRES, dslContext.settings())
									.batch(instructions));
						}).flatMap(result -> Mono.just(true))
						.onErrorResume(e -> Mono.just(false))));
	}

	/**
	 * Find emails for a given webshop via its handle.
	 *
	 * @param handle the webshop handle
	 * @return Flux with emails as strings (instead of Webshopemails fields)
	 */
	@Override
	public Mono<List<String>> findEmails(String handle) {

		log.info("Finding emails for handle: " + handle);
		return Mono.from(operations.withTransaction(
				new DefaultTransactionDefinition(
						TransactionDefinition.Propagation.REQUIRES_NEW
				), status -> Flux
						.from(DSL
								.using(status.getConnection(), SQLDialect.POSTGRES, dslContext.settings())
								.select(Tables.WEBSHOPEMAILS.ADDRESS)
								.from(Tables.WEBSHOPEMAILS)
								.where(Tables.WEBSHOPEMAILS.WEBSHOP_ID.in(
										DSL.select(Tables.WEBSHOP.WEBSHOP_ID)
												.from(Tables.WEBSHOP)
												.where(Tables.WEBSHOP.HANDLE.eq(handle)))))
						.map(record -> record.into(WebshopemailsRecord.class).getAddress())
						.collectList().flatMap(Mono::just)));
	}

	/**
	 * Delete all emails for a given webshop via its handle.
	 *
	 * @param handle the webshop handle
	 * @return Mono with Boolean indicating success
	 */
	@Override
	public Mono<Boolean> deleteAll(String handle) {

		log.info("Deleting all emails for handle: " + handle);
		return Mono.from(operations.withTransaction(
				new DefaultTransactionDefinition(
						TransactionDefinition.Propagation.REQUIRES_NEW
				), status -> Mono
						.from(DSL
								.using(status.getConnection(), SQLDialect.POSTGRES, dslContext.settings())
								.deleteFrom(Tables.WEBSHOPEMAILS)
								.where(Tables.WEBSHOPEMAILS.WEBSHOP_ID.eq(
										dslContext.select(Tables.WEBSHOP.WEBSHOP_ID)
												.from(Tables.WEBSHOP)
												.where(Tables.WEBSHOP.HANDLE
														.equalIgnoreCase(handle)))))
						.map(result -> result == QueryResult.SUCCESS.ordinal())));

	}


	/**
	 * Updates the emails for a given webshop via its handle and the email list.
	 *
	 * @param handle the webshop handle
	 * @param emails the email list
	 */
	@Override
	public Mono<Boolean> updateWebshopEmails(String handle, List<String> emails) {

		log.info("Updating emails for handle: " + handle);
		return Mono.from(operations.withTransaction(
				new DefaultTransactionDefinition(
						TransactionDefinition.Propagation.REQUIRES_NEW
				), status -> this.deleteAll(handle)
						.then(this.createVarious(handle, emails))));
	}
}
