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
 * The type Webshopemails repository.
 */
@Log
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
    public WebshopemailsRepository(@Named("dsl") DSLContext dslContext,
                                   R2dbcOperations operations) {

        this.dslContext = dslContext;
        this.operations = operations;
    }

    /**
     * Create mono.
     *
     * @param id    the id
     * @param email the email
     * @return the mono
     */
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
     * Create mono.
     *
     * @param handle the handle
     * @param email  the email
     * @return the mono
     */
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
     * Webshop id long.
     *
     * @param handle the handle
     * @return the long
     */
    private Long webshopId(String handle) {
        return dslContext.select(Tables.WEBSHOP.WEBSHOP_ID).from(Tables.WEBSHOP).where(Tables.WEBSHOP.HANDLE.eq(handle)).fetchOne(Tables.WEBSHOP.WEBSHOP_ID);
    }

    /**
     * Find mono.
     *
     * @param email the email
     * @return the mono
     */
    public Mono<Webshopemails> find(String email) {
        log.info("Finding email: " + email);
        return Mono.from(operations.withTransaction(TransactionDefinition.READ_ONLY, status -> Mono
                .from(DSL
                        .using(status.getConnection(), SQLDialect.POSTGRES, dslContext.settings())
                        .select(Tables.WEBSHOPEMAILS.ADDRESS)
                        .from(Tables.WEBSHOPEMAILS)
                        .where(Tables.WEBSHOPEMAILS.ADDRESS.equalIgnoreCase(email)))
                .map(result -> result.into(Webshopemails.class))));
    }

    /**
     * Find emails flux.
     *
     * @param handle the handle
     * @return the flux
     */
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
     * Find flux.
     *
     * @param id the id
     * @return the flux
     */
    public Flux<Webshopemails> find(Long id) {
        log.info("Finding emails for id: " + id);
        return Flux.from(operations.withTransaction(TransactionDefinition.READ_ONLY, status -> Mono
                .from(DSL
                        .using(status.getConnection(), SQLDialect.POSTGRES, dslContext.settings())
                        .select(Tables.WEBSHOPEMAILS.ADDRESS)
                        .from(Tables.WEBSHOPEMAILS)
                        .where(Tables.WEBSHOPEMAILS.WEBSHOP_ID.eq(id)))
                .map(result -> result.into(Webshopemails.class))));
    }

    /**
     * Delete mono.
     *
     * @param id the id
     * @return the mono
     */
    public Mono<Boolean> delete(Long id) {
        log.info("Deleting emails for id: " + id);
        return Mono.from(operations.withTransaction(
                new DefaultTransactionDefinition(
                        TransactionDefinition.Propagation.REQUIRES_NEW
                ), status -> Mono
                        .from(DSL
                                .using(status.getConnection(), SQLDialect.POSTGRES, dslContext.settings())
                                .deleteFrom(Tables.WEBSHOPEMAILS)
                                .where(Tables.WEBSHOPEMAILS.WEBSHOP_ID.eq(id)))
                        .map(result -> result == QueryResult.SUCCESS.ordinal())));
    }


    /**
     * Delete mono.
     *
     * @param handle the handle
     * @param email  the email
     * @return the mono
     */
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