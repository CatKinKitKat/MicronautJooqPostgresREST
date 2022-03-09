package com.optiply.services.project.endpoint.config;

import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.r2dbc.spi.ConnectionFactory;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.conf.RenderQuotedNames;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;

import javax.sql.DataSource;

@Factory
@Singleton
public class DataSourceConfig {

    private final ConnectionFactory connectionFactory;

    public DataSourceConfig(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Bean
    public DefaultConfiguration configuration() {
        DefaultConfiguration jooqConfiguration = new DefaultConfiguration();
        jooqConfiguration.settings().setRenderSchema(false);
        jooqConfiguration.settings().setRenderQuotedNames(RenderQuotedNames.NEVER);
        jooqConfiguration.settings().setExecuteWithOptimisticLocking(false);
        jooqConfiguration.setSQLDialect(SQLDialect.POSTGRES);

        jooqConfiguration.set(connectionFactory);

        return jooqConfiguration;
    }

    @Bean
    @Named("dsl")
    public DSLContext dsl() {
        return new DefaultDSLContext(configuration());
    }
}
