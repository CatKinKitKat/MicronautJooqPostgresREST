package com.optiply.endpoint.container;

import org.testcontainers.containers.PostgreSQLContainer;

/**
 * The type Test container.
 */
public class TestContainer extends PostgreSQLContainer<TestContainer> {

	/**
	 * The constant IMAGE_VERSION.
	 */
	public static final String IMAGE_VERSION = "postgres:14.2-alpine";
	/**
	 * The constant DATABASE_NAME.
	 */
	public static final String DATABASE_NAME = "test";
	/**
	 * The constant container.
	 */
	public static PostgreSQLContainer container;

	/**
	 * Instantiates a new Test container.
	 */
	public TestContainer() {
		super(IMAGE_VERSION);
	}

	/**
	 * Gets instance.
	 *
	 * @return the instance
	 */
	public static PostgreSQLContainer getInstance() {
		if (container == null) {
			container = new TestContainer().withDatabaseName(DATABASE_NAME);
		}
		return container;
	}

	@Override
	public void start() {
		super.start();
		System.setProperty("DB_USERNAME", container.getUsername());
		System.setProperty("DB_PASSWORD", container.getPassword());
		System.setProperty("DB_JDBC_URL", container.getJdbcUrl());
		System.setProperty("DB_R2DBC_URL", container.getJdbcUrl()
				.replace("jdbc", "r2dbc"));
	}

	@Override
	public void stop() {
	}
}