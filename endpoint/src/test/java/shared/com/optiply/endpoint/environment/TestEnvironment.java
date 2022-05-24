package com.optiply.endpoint.environment;

import com.optiply.endpoint.container.TestContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * Wrapper class for our test container (an evironment of sorts).
 */
@Testcontainers
public class TestEnvironment {

	/**
	 * Gets the test container.
	 */
	@Container
	public static PostgreSQLContainer postgreSQLContainer = TestContainer.getInstance();
}