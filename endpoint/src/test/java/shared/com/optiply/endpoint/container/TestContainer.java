package com.optiply.endpoint.container;

import org.testcontainers.containers.PostgreSQLContainer;

/**
 * The TestContainer class to get a container for our tests.
 */
public class TestContainer extends PostgreSQLContainer<TestContainer> {

	/**
	 * The container's image name. Using AlpineLinux as base, it's tiny and not GNU.
	 */
	public static final String IMAGE_VERSION = "postgres:14.2-alpine";
	/**
	 * The container's database name.
	 */
	public static final String DATABASE_NAME = "test";
	/**
	 * The TestContainers class to spin up a Postgres Container.
	 */
	public static PostgreSQLContainer container;

	/**
	 * Instantiates this Class.
	 */
	public TestContainer() {
		super(IMAGE_VERSION);
	}

	/**
	 * Returns the container instance, or creates a new one if it doesn't exist.
	 *
	 * @return the instance
	 */
	public static PostgreSQLContainer getInstance() {
		if (container == null) {
			container = new TestContainer().withDatabaseName(DATABASE_NAME);
		}
		return container;
	}

	/**
	 * Start container. Overrides the default start method to allow for the
	 * container to be started with the correct database name and sets the
	 * application.yaml file to use this container's database.
	 */
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