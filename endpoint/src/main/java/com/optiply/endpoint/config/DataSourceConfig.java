package com.optiply.endpoint.config;

import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.r2dbc.spi.ConnectionFactory;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.conf.RenderQuotedNames;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;

/**
 * Data source config.
 * Using application.yml to configure the data source, and injecting the data source into the application.
 */
@Factory
@Singleton
public class DataSourceConfig {

	/**
	 * The Connection factory for the data source.
	 * Will be injected into the application.
	 */
	private final ConnectionFactory connectionFactory;

	/**
	 * Instantiates a new Data source config.
	 *
	 * @param connectionFactory the connection factory with the data source
	 */
	public DataSourceConfig(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	/**
	 * Default configuration.
	 * Using the data source to create a jooq configuration.
	 *
	 * @return the default configuration
	 */
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

	/**
	 * The DSL context.
	 * Using the jooq configuration to create a jooq DSL context.
	 *
	 * @return the dsl context from the jooq configuration
	 */
	@Bean
	@Named("dsl")
	public DSLContext dsl() {
		return new DefaultDSLContext(configuration());
	}
}
