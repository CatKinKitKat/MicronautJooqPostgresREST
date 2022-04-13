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
import lombok.extern.java.Log;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
	public WebshopemailsRepository(@Named("dsl") DSLContext dslContext,
	                               R2dbcOperations operations) {

		this.dslContext = dslContext;
		this.operations = operations;
	}

	/**
	 * Gets the webshop_id for a given webshop via its handle.
	 *
	 * @param handle the webshop handle
	 * @return Mono with the webshop_id
	 */
	private Long webshopId(String handle) {
		return dslContext.select(Tables.WEBSHOP.WEBSHOP_ID).from(Tables.WEBSHOP).where(Tables.WEBSHOP.HANDLE.eq(handle)).fetchOne(Tables.WEBSHOP.WEBSHOP_ID);
	}

	/**
	 * Create a new email for a given webshop.
	 *
	 * @param id    the webshop_id
	 * @param email the email address
	 * @return Mono with Boolean indicating success
	 */
	@Override
	public Mono<Boolean> create(Long id, String email) {
		log.info("Creating email: " + email);
		return Mono.from(operations.withTransaction(
				new DefaultTransactionDefinition(
						TransactionDefinition.Propagation.REQUIRES_NEW
				), status -> Mono
						.from(DSL
								.using(status.getConnection(), SQLDialect.POSTGRES, dslContext.settings())
								.insertInto(Tables.WEBSHOPEMAILS)
								.columns(Tables.WEBSHOPEMAILS.WEBSHOP_ID, Tables.WEBSHOPEMAILS.ADDRESS)
								.values(id, email))
						.map(result -> result == QueryResult.SUCCESS.ordinal())));
	}

	/**
	 * Create a new email for a given webshop.
	 *
	 * @param handle the webshop handle
	 * @param email  the email address
	 * @return Mono with Boolean indicating success
	 */
	@Override
	public Mono<Boolean> create(String handle, String email) {
		log.info("Creating email: " + email);
		return Mono.from(operations.withTransaction(
				new DefaultTransactionDefinition(
						TransactionDefinition.Propagation.REQUIRES_NEW
				), status -> Mono
						.from(DSL
								.using(status.getConnection(), SQLDialect.POSTGRES, dslContext.settings())
								.insertInto(Tables.WEBSHOPEMAILS)
								.columns(Tables.WEBSHOPEMAILS.WEBSHOP_ID, Tables.WEBSHOPEMAILS.ADDRESS)
								.values(webshopId(handle), email))
						.map(result -> result == QueryResult.SUCCESS.ordinal())));
	}

	/**
	 * Find emails for a given webshop via its handle.
	 *
	 * @param handle the webshop handle
	 * @return Flux with emails as strings (instead of Webshopemails fields)
	 */
	@Override
	public Flux<String> findEmails(String handle) {
		log.info("Finding emails for handle: " + handle);
		return Flux.from(operations.withTransaction(TransactionDefinition.READ_ONLY, status -> Mono
				.from(DSL
						.using(status.getConnection(), SQLDialect.POSTGRES, dslContext.settings())
						.select(Tables.WEBSHOPEMAILS.ADDRESS)
						.from(Tables.WEBSHOPEMAILS)
						.where(Tables.WEBSHOPEMAILS.WEBSHOP_ID.eq(
								dslContext.select(Tables.WEBSHOP.WEBSHOP_ID)
										.from(Tables.WEBSHOP)
										.where(Tables.WEBSHOP.HANDLE
												.equalIgnoreCase(handle))
						)))
				.map(result -> result.into(Webshopemails.class)).map(Webshopemails::getAddress)));
	}

	/**
	 * Delete an email for a given webshop via its handle, just incase the email exists for various webshops.
	 *
	 * @param handle the webshop handle
	 * @param email  the email address
	 * @return Mono with Boolean indicating success
	 */
	@Override
	public Mono<Boolean> delete(String handle, String email) {
		log.info("Deleting " + email + " for handle: " + handle);
		return Mono.from(operations.withTransaction(
				new DefaultTransactionDefinition(
						TransactionDefinition.Propagation.REQUIRES_NEW
				), status -> Mono
						.from(DSL
								.using(status.getConnection(), SQLDialect.POSTGRES, dslContext.settings())
								.deleteFrom(Tables.WEBSHOPEMAILS)
								.where(Tables.WEBSHOPEMAILS.ADDRESS.equalIgnoreCase(email))
								.and(Tables.WEBSHOPEMAILS.WEBSHOP_ID.eq(
										dslContext.select(Tables.WEBSHOP.WEBSHOP_ID)
												.from(Tables.WEBSHOP)
												.where(Tables.WEBSHOP.HANDLE
														.equalIgnoreCase(handle)))))
						.map(result -> result == QueryResult.SUCCESS.ordinal())));

	}
}