package com.optiply.endpoint;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

@MicronautTest(application=EndpointApplication.class)
public class AppTest {

	@Test
	public void testApp() {
		assertTrue(true);
	}

}
