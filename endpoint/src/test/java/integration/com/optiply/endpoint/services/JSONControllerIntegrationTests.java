package com.optiply.endpoint.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.optiply.endpoint.environment.TestEnvironment;
import com.optiply.endpoint.models.WebshopFullModel;
import com.optiply.endpoint.models.WebshopModel;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.*;

import java.util.List;

/**
 * The type Endpoint controller integration tests.
 */
@MicronautTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class JSONControllerIntegrationTests extends TestEnvironment {

	/**
	 * The Client.
	 */
	@Inject
	@Client("/")
	HttpClient client;

	/**
	 * The Object mapper.
	 */
	@Inject
	ObjectMapper objectMapper;

	/**
	 * The Endpoint controller.
	 */
	@Inject
	com.optiply.endpoint.controllers.JSONController JSONController;

	@Test
	@Order(1)
	void testCreateWebshop() throws JsonProcessingException {

		String body = """
					{
						"handle": "test1",
						"url": "https://www.test1.com",
						"serviceLevelA": 33.0,
						"serviceLevelB": 33.0,
						"serviceLevelC": 34.0,
						"emails": [
							"test@test1.com",
							"tester@test1.com"
						]
					}
				""";

		WebshopFullModel webshop = objectMapper.readValue(body, WebshopFullModel.class);

		HttpRequest<WebshopFullModel> request = HttpRequest.POST("/", webshop);
		String result = client.toBlocking().retrieve(request, String.class);

		Assertions.assertEquals("Webshop created.", result);
	}

	@Test
	@Order(2)
	void testCreateVariousWebshops() throws JsonProcessingException {

		String body = """
					[
						{
							"handle": "test2",
							"url": "https://www.test2.com",
							"serviceLevelA": 25.0,
							"serviceLevelB": 25.0,
							"serviceLevelC": 50.0,
							"emails": [
								"ti84plus@test2.com",
								"zelenski@test2.com",
								"putin@test2.com"
							]
						},
						{
							"handle": "test3",
							"url": "https://www.test3.com",
							"interestRate": 25,
							"serviceLevelA": 20.0,
							"serviceLevelB": 35.0,
							"serviceLevelC": 45.0,
							"emails": [
								"beau@test3.com",
								"fifthcolumn@test3.com"
							]
						}
					]
				""";

		List<WebshopFullModel> webshops = objectMapper.readValue(body,
				objectMapper.getTypeFactory().constructCollectionType(List.class, WebshopFullModel.class)
		);

		HttpRequest<List<WebshopFullModel>> request = HttpRequest.POST("/various", webshops);
		String result = client.toBlocking().retrieve(request, String.class);

		Assertions.assertEquals("Webshops created.", result);
	}

	@Test
	@Order(3)
	void testGetWebshop() throws JsonProcessingException {

		String body = """
					{
							"handle": "test2",
							"url": "https://www.test2.com",
							"serviceLevelA": 25.0,
							"serviceLevelB": 25.0,
							"serviceLevelC": 50.0,
							"emails": [
								"ti84plus@test2.com",
								"zelenski@test2.com",
								"putin@test2.com"
							]
						}
				""";

		WebshopModel webshop = objectMapper.readValue(body, WebshopModel.class);

		HttpRequest<WebshopModel> request = HttpRequest.GET("/test2");
		String result = client.toBlocking().retrieve(request, String.class);

		Assertions.assertEquals(objectMapper.writeValueAsString(webshop), result);
	}


	@Test
	@Order(4)
	void testFindWebshopByInterestRate() throws JsonProcessingException {

		String body = """
					[
				    {
					  	"handle": "test1",
					  	"url": "https://www.test1.com",
							"interestRate": 20,
					  	"serviceLevelA": 33.0,
					  	"serviceLevelB": 33.0,
					  	"serviceLevelC": 34.0,
					  	"emails": [
					  		"test@test1.com",
					  		"tester@test1.com"
					  	]
					  },
					  {
					  	"handle": "test2",
					  	"url": "https://www.test2.com",
							"interestRate": 20,
					  	"serviceLevelA": 25.0,
					  	"serviceLevelB": 25.0,
					  	"serviceLevelC": 50.0,
					  	"emails": [
					  		"ti84plus@test2.com",
					  		"zelenski@test2.com",
					  		"putin@test2.com"
					  	]
					  }
					]
				"""
				.replace(" ", "")
				.replace("\t", "")
				.replace("\n", "");

		// < = %3C
		// > = %3E

		HttpRequest<List<WebshopModel>> request = HttpRequest.GET("/find/interestRate%3C25");
		String result = client.toBlocking().retrieve(request, String.class);

		Assertions.assertEquals(body, result);

	}
}
