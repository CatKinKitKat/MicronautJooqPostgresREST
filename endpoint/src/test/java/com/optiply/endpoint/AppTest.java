package com.optiply.endpoint;

import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

@MicronautTest(application=EndpointApplication.class)
public class AppTest {

	@Inject
	EmbeddedApplication<?> application;

	@Test
	void testItWorks() {
		assertTrue(application.isRunning());
	}


}
