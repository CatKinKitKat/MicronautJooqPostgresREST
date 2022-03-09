package com.optiply.infrastructure.data.jooq.repositories;

import com.optiply.infrastructure.data.jooq.repositories.tables.pojos.Example;
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

import java.util.UUID;

import static com.optiply.infrastructure.data.jooq.repositories.tables.Example.EXAMPLE;

/**
 * Example repository to showcase jOOQ
 * Delete this when no longer necessary
 */
@Singleton
public class JooqExampleRepository implements ExampleRepository {

    private final DSLContext dslContext;
    private final R2dbcOperations operations;

    @Inject
    public JooqExampleRepository(@Named("dsl") DSLContext dslContext, R2dbcOperations operations) {
        this.dslContext = dslContext;
        this.operations = operations;
    }

    @Override
    public Mono<Example> findById(UUID uuid) {
        return Mono
                .from(operations.withTransaction(TransactionDefinition.READ_ONLY, status -> DSL
                        .using(status.getConnection(), SQLDialect.POSTGRES, dslContext.settings())
                        .select(EXAMPLE.asterisk())
                        .from(EXAMPLE)
                        .where(EXAMPLE.UUID.eq(uuid))))
                .map(result -> result.into(Example.class));
    }

    @Override
    public Mono<Boolean> insert(Example example) {
        TransactionDefinition txDefinition =
                new DefaultTransactionDefinition(TransactionDefinition.Propagation.MANDATORY);

        return Mono.from(operations.withTransaction(txDefinition, status -> Mono
                .from(DSL
                        .using(status.getConnection(), SQLDialect.POSTGRES, dslContext.settings())
                        .insertInto(EXAMPLE)
                        .columns(EXAMPLE.ID, EXAMPLE.UUID)
                        .values(example.getId(), example.getUuid()))
                .map(result -> result == QueryResult.SUCCESS.ordinal())));
    }
}
