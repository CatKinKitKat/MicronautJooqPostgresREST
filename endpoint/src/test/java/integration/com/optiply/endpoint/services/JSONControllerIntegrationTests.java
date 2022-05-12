package com.optiply.endpoint.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.optiply.endpoint.environment.TestEnvironment;
import com.optiply.endpoint.models.*;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpRequest;
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

	/**
	 * Test create test webshop simple.
	 *
	 * @throws JsonProcessingException the json processing exception
	 */
	@Test
	@Order(1)
	void testCreateTestWebshop1() throws JsonProcessingException {

		String body = """
					{
						"handle": "test2",
						"url": "https://www.test2.com",
						"serviceLevelA": 33.0,
						"serviceLevelB": 33.0,
						"serviceLevelC": 34.0
					}
				""";

		WebshopBodyModel webshop = objectMapper.readValue(body, WebshopBodyModel.class);

		HttpRequest<WebshopBodyModel> request = HttpRequest.POST("/", webshop);
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
	void testCreateTestWebshop2() throws JsonProcessingException {

		String body = """
					{
						"handle": "test3",
						"url": "https://www.test3.nl",
						"interestRate": 25,
						"serviceLevelA": 50.0,
						"serviceLevelB": 25.0,
						"serviceLevelC": 25.0,
						"currency": "USD",
						"runJobs": false,
						"multiSupplier": true
					}
				""";

		WebshopBodyModel webshop = objectMapper.readValue(body, WebshopBodyModel.class);

		HttpRequest<WebshopBodyModel> request = HttpRequest.POST("/", webshop);
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
		expected.setServiceLevelA(50.0);
		expected.setServiceLevelB(25.0);
		expected.setServiceLevelC(25.0);
		expected.setInterestRate((short) 25);

		HttpRequest<WebshopSimpleModel> request = HttpRequest.GET("/test3");
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

		HttpRequest<WebshopSettingsModel> request = HttpRequest.GET("/test3/settings");
		WebshopSettingsModel result = client.toBlocking()
				.retrieve(request, WebshopSettingsModel.class);

		Assertions.assertEquals(expected, result);

	}


	/**
	 * Test add email to webshop.
	 */
	@Test
	@Order(5)
	void testAddEmailToWebshop() throws JsonProcessingException {

		String body = """
					{
						"email": "lol@test.pt"
					}
				""";

		EmailModel emailModel = objectMapper.readValue(body, EmailModel.class);

		MutableHttpRequest<EmailModel> request = HttpRequest.POST("/email/test3/", emailModel);
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


		HttpRequest<WebshopEmailsModel> request = HttpRequest.GET("/test3/emails");
		WebshopEmailsModel result = client.toBlocking().retrieve(request, WebshopEmailsModel.class);

		Assertions.assertEquals(expected, result);

	}

	/**
	 * Remove email from webshop.
	 */
	@Test
	@Order(7)
	void removeEmailFromWebshop() throws JsonProcessingException {

		String body = """
					{
						"email": "lol@test.pt"
					}
				""";

		EmailModel emailModel = objectMapper.readValue(body, EmailModel.class);

		MutableHttpRequest<EmailModel> request = HttpRequest.DELETE("/email/test3/", emailModel);
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
						"serviceLevelA": 50.0,
						"serviceLevelB": 25.0,
						"serviceLevelC": 25.0,
						"currency": "USD",
						"runJobs": false,
						"multiSupplier": true
					}
				""";

		WebshopBodyModel webshop = objectMapper.readValue(body, WebshopBodyModel.class);

		HttpRequest<WebshopBodyModel> request = HttpRequest.PUT("/", webshop);
		String result = client.toBlocking().retrieve(request, String.class);

		Assertions.assertEquals("Webshop updated.", result);

	}

	/**
	 * Test delete webshop.
	 */
	@Test
	@Order(9)
	void testDeleteWebshop() {

		HttpRequest<Boolean> requestT = HttpRequest.DELETE("/test2");
		HttpResponse<Object> resultT = client.toBlocking().exchange(requestT);

		HttpRequest<Boolean> requestO = HttpRequest.DELETE("/test3");
		HttpResponse<Object> resultO = client.toBlocking().exchange(requestO);

		Assertions.assertNull(resultT.body());
		Assertions.assertNull(resultO.body());

	}

}
