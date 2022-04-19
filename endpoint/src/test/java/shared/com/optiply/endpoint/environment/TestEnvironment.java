package com.optiply.endpoint.environment;

import com.optiply.endpoint.container.TestContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class TestEnvironment {

	@Container
	public static PostgreSQLContainer postgreSQLContainer = TestContainer.getInstance();
}