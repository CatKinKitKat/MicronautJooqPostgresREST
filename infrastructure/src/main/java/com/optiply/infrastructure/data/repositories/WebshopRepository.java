package com.optiply.infrastructure.data.repositories;

import com.optiply.infrastructure.data.models.Tables;
import com.optiply.infrastructure.data.models.tables.pojos.Webshop;
import com.optiply.infrastructure.data.repositories.interfaces.IWebshopRepository;
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
 * The type Webshop repository.
 */
@Log
@Singleton
public class WebshopRepository implements IWebshopRepository {

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
     * Gets webshop id.
     *
     * @param handle the handle
     * @return the webshop id
     */
    @Override
    public Mono<Long> getWebshopId(String handle) {
        log.info("Getting webshop id: " + handle);
        return Mono
                .from(operations.withTransaction(TransactionDefinition.READ_ONLY, status -> DSL
                        .using(status.getConnection(), SQLDialect.POSTGRES, dslContext.settings())
                        .select(Tables.WEBSHOP.WEBSHOP_ID)
                        .from(Tables.WEBSHOP)
                        .where(Tables.WEBSHOP.HANDLE.equalIgnoreCase(handle))))
                .map(result -> result.into(Long.class));
    }

    /**
     * Find various flux.
     *
     * @param condition the condition
     * @param sort      the sort
     * @return the flux
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
     * Find all flux.
     *
     * @return the flux
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
     * Find mono.
     *
     * @param handle the handle
     * @return the mono
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
     * Find mono.
     *
     * @param id the id
     * @return the mono
     */
    @Override
    public Mono<Webshop> find(Long id) {
        log.info("Finding webshop: " + id);
        return Mono
                .from(operations.withTransaction(TransactionDefinition.READ_ONLY, status -> DSL
                        .using(status.getConnection(), SQLDialect.POSTGRES, dslContext.settings())
                        .select(Tables.WEBSHOP.asterisk())
                        .from(Tables.WEBSHOP)
                        .where(Tables.WEBSHOP.WEBSHOP_ID.eq(id))))
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
     * @param INTEREST_RATE the interest rate
     * @param currency      the currency
     * @param RUN_JOBS      the run jobs
     * @param multiSupplier the multi supplier
     * @return the mono
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
     * Update webshop mono.
     *
     * @param id            the id
     * @param handle        the handle
     * @param url           the url
     * @param A             the a
     * @param B             the b
     * @param C             the c
     * @param INTEREST_RATE the interest rate
     * @param currency      the currency
     * @param RUN_JOBS      the run jobs
     * @param multiSupplier the multi supplier
     * @return the mono
     */
    @Override
    public Mono<Boolean> updateWebshop(Long id, String handle, String url,
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
                                .where(Tables.WEBSHOP.WEBSHOP_ID.eq(id)))
                        .map(result -> result == QueryResult.SUCCESS.ordinal())));

    }

    /**
     * Delete webshop mono.
     *
     * @param id the id
     * @return the mono
     */
    @Override
    public Mono<Boolean> deleteWebshop(Long id) {
        log.info("Deleting webshop: " + id);
        return Mono.from(operations.withTransaction(
                new DefaultTransactionDefinition(
                        TransactionDefinition.Propagation.REQUIRES_NEW
                ), status -> Mono
                        .from(DSL
                                .using(status.getConnection(), SQLDialect.POSTGRES, dslContext.settings())
                                .delete(Tables.WEBSHOP)
                                .where(Tables.WEBSHOP.WEBSHOP_ID.eq(id)))
                        .map(result -> result == QueryResult.SUCCESS.ordinal())));
    }

    /**
     * Delete webshop mono.
     *
     * @param handle the handle
     * @return the mono
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
