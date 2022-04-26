package com.optiply.endpoint.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.optiply.endpoint.environment.TestEnvironment;
import com.optiply.endpoint.models.WebshopBodyModel;
import com.optiply.endpoint.models.WebshopEmailsModel;
import com.optiply.endpoint.models.WebshopSettingsModel;
import com.optiply.endpoint.models.WebshopSimpleModel;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
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
	JSONController JSONController;

	/**
	 * Test create test webshop simple.
	 *
	 * @throws JsonProcessingException the json processing exception
	 */
	@Test
	@Order(1)
	void testCreateTestWebshopSimple() throws JsonProcessingException {

		String body = """
					{
						"handle": "test2",
						"url": "https://www.test2.com",
						"A": 33.0,
						"B": 33.0,
						"C": 34.0
					}
				""";

		WebshopBodyModel webshop = objectMapper.readValue(body, WebshopBodyModel.class);

		HttpRequest<WebshopBodyModel> request = HttpRequest.POST("/create/simple", webshop);
		String result = client.toBlocking().retrieve(request, String.class);

		Assertions.assertEquals("Webshop created.", result);

	}

	/**
	 * Test create test webshop.
	 *
	 * @throws JsonProcessingException the json processing exception
	 */
	@Test
	@Order(2)
	void testCreateTestWebshop() throws JsonProcessingException {

		String body = """
					{
						"handle": "test3",
						"url": "https://www.test3.nl",
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
		String result = client.toBlocking().retrieve(request, String.class);

		Assertions.assertEquals("Webshop created.", result);

	}


	/**
	 * Test get webshop.
	 */
	@Test
	@Order(3)
	void testGetWebshop() {

		WebshopSimpleModel expected = new WebshopSimpleModel();
		expected.setHandle("test3");
		expected.setUrl("https://www.test3.nl");
		expected.setA(50.0);
		expected.setB(25.0);
		expected.setC(25.0);
		expected.setInterestRate((short) 25);

		HttpRequest<WebshopSimpleModel> request = HttpRequest.GET("/get/test3");
		WebshopSimpleModel result = client.toBlocking()
				.retrieve(request, WebshopSimpleModel.class);

		Assertions.assertEquals(expected, result);

	}

	/**
	 * Test get webshop settings.
	 */
	@Test
	@Order(4)
	void testGetWebshopSettings() {

		WebshopSettingsModel expected = new WebshopSettingsModel();
		expected.setHandle("test3");
		expected.setCurrency("USD");
		expected.setRunJobs(false);
		expected.setMultiSupplier(true);

		HttpRequest<WebshopSettingsModel> request = HttpRequest.GET("/get/test3/settings");
		WebshopSettingsModel result = client.toBlocking()
				.retrieve(request, WebshopSettingsModel.class);

		Assertions.assertEquals(expected, result);

	}


	/**
	 * Test add email to webshop.
	 */
	@Test
	@Order(5)
	void testAddEmailToWebshop() {

		HttpRequest<String> request = HttpRequest.POST("/add/email/test3/lol@test.pt", null);
		String result = client.toBlocking().retrieve(request, String.class);

		Assertions.assertEquals("Email added.", result);

	}

	/**
	 * Test get webshop emails.
	 */
	@Test
	@Order(6)
	void testGetWebshopEmails() {

		WebshopEmailsModel expected = new WebshopEmailsModel();
		expected.setHandle("test3");
		expected.setEmails(List.of("lol@test.pt"));


		HttpRequest<WebshopEmailsModel> request = HttpRequest.GET("/get/test3/emails");
		WebshopEmailsModel result = client.toBlocking().retrieve(request, WebshopEmailsModel.class);

		Assertions.assertEquals(expected, result);

	}

	/**
	 * Remove email from webshop.
	 */
	@Test
	@Order(7)
	void removeEmailFromWebshop() {

		HttpRequest<String> request = HttpRequest.DELETE("/remove/email/test3/lol@test.pt");
		String result = client.toBlocking().retrieve(request, String.class);

		Assertions.assertEquals("Email removed.", result);

	}

	/**
	 * Test update webshop.
	 *
	 * @throws JsonProcessingException the json processing exception
	 */
	@Test
	@Order(8)
	void testUpdateWebshop() throws JsonProcessingException {

		String body = """
					{
						"handle": "test3",
						"url": "https://www.test3.nl",
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

		HttpRequest<WebshopBodyModel> request = HttpRequest.PUT("/update", webshop);
		String result = client.toBlocking().retrieve(request, String.class);

		Assertions.assertEquals("Webshop updated.", result);

	}

	/**
	 * Test delete webshop.
	 */
	@Test
	@Order(9)
	void testDeleteWebshop() {

		HttpRequest<Boolean> requestT = HttpRequest.DELETE("/delete/test2");
		HttpResponse<Object> resultT = client.toBlocking().exchange(requestT);

		HttpRequest<Boolean> requestO = HttpRequest.DELETE("/delete/test3");
		HttpResponse<Object> resultO = client.toBlocking().exchange(requestO);

		Assertions.assertNull(resultT.body());
		Assertions.assertNull(resultO.body());

	}

}
