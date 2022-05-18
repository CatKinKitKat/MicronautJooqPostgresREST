package com.optiply.endpoint.services;

import com.optiply.endpoint.environment.TestEnvironment;
import com.optiply.infrastructure.data.repositories.WebshopRepository;
import com.optiply.infrastructure.data.repositories.WebshopemailsRepository;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * The type Endpoint controller unit tests.
 */
@MicronautTest
public class RepositoryServiceUnitTests extends TestEnvironment {

	@InjectMocks
	private RepositoryService repositoryService;

	@Mock
	private WebshopRepository webshopRepository;

	@Mock
	private WebshopemailsRepository webshopemailsRepository;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	// TODO: add more tests

}
