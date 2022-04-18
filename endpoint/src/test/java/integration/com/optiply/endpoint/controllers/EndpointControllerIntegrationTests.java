package com.optiply.endpoint.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.optiply.endpoint.models.WebshopBodyModel;
import com.optiply.endpoint.models.WebshopSimpleModel;
import com.optiply.infrastructure.data.repositories.WebshopRepository;
import com.optiply.infrastructure.data.repositories.WebshopemailsRepository;
import io.micronaut.context.ApplicationContext;
import io.micronaut.context.annotation.PropertySource;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@MicronautTest
public class EndpointControllerIntegrationTests {

	@Container
	static PostgreSQLContainer<?> postgreSQLContainer =
			new PostgreSQLContainer<>("postgres:14.2-alpine")
					.withDatabaseName("test")
					.withUsername("postgres")
					.withPassword("postgres");

	@Inject
	@Client("/")
	HttpClient client;

	@Inject
	DataSource dataSource;

	@Inject
	ObjectMapper objectMapper;

	@Inject
	ApplicationContext applicationContext;

	@Inject
	WebshopRepository webshopRepository;

	@Inject
	WebshopemailsRepository webshopemailsRepository;

	@BeforeAll
	void setup() {
		postgreSQLContainer.start();
	}

	@Test
	void testCreateTestWebshopSimple() throws JsonProcessingException {

		String body = """
					{
						"handle": "test",
						"url": "https://www.test.com",
						"A": 33.0,
						"B": 33.0,
						"C": 34.0
					}
				""";

		WebshopBodyModel webshop = objectMapper.readValue(body, WebshopBodyModel.class);


		HttpRequest<WebshopBodyModel> request = HttpRequest.POST("/create/simple", webshop);
		Boolean result = client.toBlocking().retrieve(request, Boolean.class);

		Assertions.assertTrue(result);

	}

	@Test
	void testCreateTestWebshop() throws JsonProcessingException {

		String body = """
					{
						"handle": "optiply",
						"url": "https://www.optiply.nl",
						"interestRate": 25,
						"A": 50.0,
						"B": 25.0,
						"C": 25.0,
						"currency": "USD",
						"runJobs": false,
						"multiSupplier": true
					}
				""";

		WebshopBodyModel webshop = objectMapper.readValue(body, WebshopBodyModel.class);

		HttpRequest<WebshopBodyModel> request = HttpRequest.POST("/create", webshop);
		Boolean result = client.toBlocking().retrieve(request, Boolean.class);

		Assertions.assertTrue(result);

	}


	@Test
	void testGetWebshop() {

		WebshopSimpleModel expected = new WebshopSimpleModel();
		expected.setHandle("optiply");
		expected.setUrl("http://www.optiply.nl");
		expected.setA(50.0);
		expected.setB(25.0);
		expected.setC(25.0);
		expected.setInterestRate((short) 25);

		HttpRequest<WebshopSimpleModel> request = HttpRequest.GET("/get/optiply");
		WebshopSimpleModel result = client.toBlocking()
				.retrieve(request, WebshopSimpleModel.class);

		Assertions.assertEquals(expected, result);

	}

	@Test
	void testDeleteWebshop() {

		HttpRequest<Boolean> requestT = HttpRequest.DELETE("/delete/test");
		Boolean resultT = client.toBlocking()
				.retrieve(requestT, Boolean.class);

		HttpRequest<Boolean> requestO = HttpRequest.DELETE("/delete/optiply");
		Boolean resultO = client.toBlocking()
				.retrieve(requestO, Boolean.class);

		Assertions.assertTrue(resultT);
		Assertions.assertTrue(resultO);
		Assertions.assertEquals(resultT, resultO);

	}

}
