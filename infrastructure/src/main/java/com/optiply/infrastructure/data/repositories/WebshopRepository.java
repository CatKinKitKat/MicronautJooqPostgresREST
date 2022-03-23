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
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The type Webshop repository.
 */
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
    public WebshopRepository(@Named("dsl") DSLContext dslContext, @Named("r2dbc") R2dbcOperations operations) {
        this.dslContext = dslContext;
        this.operations = operations;
    }

    /**
     * Create mono.
     *
     * @param handle       the handle
     * @param url          the url
     * @param interestRate the interest rate
     * @return the mono
     */
    public Mono<Boolean> create(String handle, String url, Short interestRate) {


        return Mono.from(operations.withTransaction(
                new DefaultTransactionDefinition(
                        TransactionDefinition.Propagation.MANDATORY
                ), status -> Mono
                        .from(DSL
                                .using(status.getConnection(), SQLDialect.POSTGRES, dslContext.settings())
                                .insertInto(Tables.WEBSHOP)
                                .columns(Tables.WEBSHOP.HANDLE, Tables.WEBSHOP.URL, Tables.WEBSHOP.INTERESTRATE)
                                .values(handle, url, interestRate))
                        .map(result -> result == QueryResult.SUCCESS.ordinal())));
    }

    /**
     * Read by handle mono.
     *
     * @param handle the handle
     * @return the mono
     */
    @Override
    public Mono<Webshop> readByHandle(String handle) {

        return Mono
                .from(operations.withTransaction(TransactionDefinition.READ_ONLY, status -> DSL
                        .using(status.getConnection(), SQLDialect.POSTGRES, dslContext.settings())
                        .select(Tables.WEBSHOP.asterisk())
                        .from(Tables.WEBSHOP)
                        .where(Tables.WEBSHOP.HANDLE.eq(handle))))
                .map(result -> result.into(Webshop.class));
    }


    /**
     * Update mono.
     *
     * @param handle       the handle
     * @param url          the url
     * @param interestRate the interest rate
     * @return the mono
     */
    @Override
    public Mono<Boolean> update(String handle, String url, Short interestRate) {


        return Mono.from(operations.withTransaction(
                new DefaultTransactionDefinition(
                        TransactionDefinition.Propagation.MANDATORY
                ), status -> Mono
                        .from(DSL
                                .using(status.getConnection(), SQLDialect.POSTGRES, dslContext.settings())
                                .update(Tables.WEBSHOP)
                                .set(Tables.WEBSHOP.URL, url)
                                .set(Tables.WEBSHOP.INTERESTRATE, interestRate)
                                .where(Tables.WEBSHOP.HANDLE.eq(handle)))
                        .map(result -> result == QueryResult.SUCCESS.ordinal())));
    }


    /**
     * Read by handles flux.
     *
     * @param handles the handles
     * @return the flux
     */
    @Override
    public Flux<Webshop> readByHandles(String... handles) {

        return Flux
                .from(operations.withTransaction(TransactionDefinition.READ_ONLY, status -> DSL
                        .using(status.getConnection(), SQLDialect.POSTGRES, dslContext.settings())
                        .select(Tables.WEBSHOP.asterisk())
                        .from(Tables.WEBSHOP)
                        .where(Tables.WEBSHOP.HANDLE.in(handles))))
                .map(result -> result.into(Webshop.class));
    }

    /**
     * Read by handle like flux.
     *
     * @param handle the handle
     * @return the flux
     */
    @Override
    public Flux<Webshop> readByHandleLike(String handle) {

        return Flux
                .from(operations.withTransaction(TransactionDefinition.READ_ONLY, status -> DSL
                        .using(status.getConnection(), SQLDialect.POSTGRES, dslContext.settings())
                        .select(Tables.WEBSHOP.asterisk())
                        .from(Tables.WEBSHOP)
                        .where(Tables.WEBSHOP.HANDLE.like(handle))))
                .map(result -> result.into(Webshop.class));
    }

    /**
     * Update mono.
     *
     * @param id           the id
     * @param handle       the handle
     * @param url          the url
     * @param interestRate the interest rate
     * @return the mono
     */
    @Override
    public Mono<Boolean> update(Long id, String handle, String url, Short interestRate) {

        return Mono.from(operations.withTransaction(
                new DefaultTransactionDefinition(
                        TransactionDefinition.Propagation.MANDATORY
                ), status -> Mono
                        .from(DSL
                                .using(status.getConnection(), SQLDialect.POSTGRES, dslContext.settings())
                                .update(Tables.WEBSHOP)
                                .set(Tables.WEBSHOP.URL, url)
                                .set(Tables.WEBSHOP.INTERESTRATE, interestRate)
                                .where(Tables.WEBSHOP.HANDLE.eq(handle)))
                        .map(result -> result == QueryResult.SUCCESS.ordinal())));
    }

    /**
     * Delete mono.
     *
     * @param id the id
     * @return the mono
     */
    @Override
    public Mono<Boolean> delete(Long id) {

        return Mono.from(operations.withTransaction(
                new DefaultTransactionDefinition(
                        TransactionDefinition.Propagation.MANDATORY
                ), status -> Mono
                        .from(DSL
                                .using(status.getConnection(), SQLDialect.POSTGRES, dslContext.settings())
                                .delete(Tables.WEBSHOP)
                                .where(Tables.WEBSHOP.WEBSHOPID.eq(id)))
                        .map(result -> result == QueryResult.SUCCESS.ordinal())
        ));
    }
}
