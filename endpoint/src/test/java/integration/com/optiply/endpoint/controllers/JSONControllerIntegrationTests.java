package com.optiply.endpoint.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.optiply.endpoint.environment.TestEnvironment;
import com.optiply.endpoint.models.WebshopFullModel;
import com.optiply.endpoint.models.WebshopModel;
import com.optiply.endpoint.models.WebshopSettingsModel;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.*;

import java.util.List;

/**
 * Integration tests, by unit testing the controller.
 */
@MicronautTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JSONControllerIntegrationTests extends TestEnvironment {

	/**
	 * The Test Client.
	 */
	@Inject
	@Client("/")
	HttpClient client;

	/**
	 * Jackson Object Mapper.
	 */
	@Inject
	ObjectMapper objectMapper;

	/**
	 * The Endpoint controller.
	 */
	@Inject
	com.optiply.endpoint.controllers.JSONController JSONController;

	/**
	 * Test create webshop.
	 *
	 * @throws JsonProcessingException the json processing exception
	 */
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

	/**
	 * Test create various webshops.
	 *
	 * @throws JsonProcessingException the json processing exception
	 */
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

	/**
	 * Test get webshop.
	 *
	 * @throws JsonProcessingException the json processing exception
	 */
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


	/**
	 * Test find webshop by interest rate.
	 */
	@Test
	@Order(4)
	void testFindWebshopByInterestRate() {

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

	/**
	 * Test get webshop settings.
	 */
	@Test
	@Order(5)
	void testGetWebshopSettings() {

		String body = """
					{
							"handle": "test2",
							"currency": "EUR",
							"runJobs": true,
							"multiSupplier": false
					}
				"""
				.replace(" ", "")
				.replace("\t", "")
				.replace("\n", "");

		HttpRequest<WebshopSettingsModel> request = HttpRequest.GET("/test2/settings");
		String result = client.toBlocking().retrieve(request, String.class);

		Assertions.assertEquals(body, result);
	}

	/**
	 * Test update webshop.
	 */
	@Test
	@Order(6)
	void testUpdateWebshop() {

		String urlBody = """
				  {
						"url": "https://www.test22.com"
				  }
				""";

		HttpRequest<String> urlRequest = HttpRequest.PUT("/test2/url", urlBody);
		String urlResult = client.toBlocking().retrieve(urlRequest, String.class);

		Assertions.assertEquals("Webshop updated.", urlResult);

		String serviceLevelsBody = """
					{
						"serviceLevelA": 33.3,
						"serviceLevelB": 33.3,
						"serviceLevelC": 33.4
				  }
				""";

		HttpRequest<String> serviceLevelsRequest = HttpRequest.PUT("/test2/serviceLevels", serviceLevelsBody);
		String serviceLevelsResult = client.toBlocking().retrieve(serviceLevelsRequest, String.class);

		Assertions.assertEquals("Webshop updated.", serviceLevelsResult);

		String interestRateBody = """
				  {
						"interestRate": 22
				  }
				""";

		HttpRequest<String> interestRateRequest = HttpRequest.PUT("/test2/interestRate", interestRateBody);
		String interestRateResult = client.toBlocking().retrieve(interestRateRequest, String.class);

		Assertions.assertEquals("Webshop updated.", interestRateResult);

		String emailsBody = """
				  {
					  "emails": [
					  	"ti34@test22.pt",
					  	"multiview@test22.pt"
					  ]
				   }
				""";

		HttpRequest<String> emailsRequest = HttpRequest.PUT("/test2/emails", emailsBody);
		String emailsResult = client.toBlocking().retrieve(emailsRequest, String.class);

		Assertions.assertEquals("Webshop emails updated.", emailsResult);

		String handleBody = """
				  {
						"handle": "test22"
				  }
				""";

		HttpRequest<String> handleRequest = HttpRequest.PUT("/test2/handle", handleBody);
		String handleResult = client.toBlocking().retrieve(handleRequest, String.class);

		Assertions.assertEquals("Webshop updated.", handleResult);

		String body = """
					{
					    "handle": "test22",
					    "url": "https://www.test22.com",
						  "interestRate": 22,
					    "serviceLevelA": 33.3,
					    "serviceLevelB": 33.3,
					    "serviceLevelC": 33.4,
					    "emails": [
					  	  "ti34@test22.pt",
					  	  "multiview@test22.pt"
					    ]
					}
				"""
				.replace(" ", "")
				.replace("\t", "")
				.replace("\n", "");

		HttpRequest<WebshopModel> request = HttpRequest.GET("/test22");
		String result = client.toBlocking().retrieve(request, String.class);

		Assertions.assertEquals(body, result);

	}

	/**
	 * Test update webshop settings.
	 *
	 * @throws JsonProcessingException the json processing exception
	 */
	@Test
	@Order(7)
	void testUpdateWebshopSettings() throws JsonProcessingException {

		String body = """
					{
							"currency": "USD",
							"runJobs": true,
							"multiSupplier": true
					}
				""";

		HttpRequest<WebshopSettingsModel> request = HttpRequest.PUT("/test22/settings", objectMapper.readValue(body, WebshopSettingsModel.class));
		String result = client.toBlocking().retrieve(request, String.class);

		Assertions.assertEquals("Webshop updated.", result);

		HttpRequest<WebshopSettingsModel> request2 = HttpRequest.GET("/test22/settings");
		String result2 = client.toBlocking().retrieve(request2, String.class);

		body = body
				.replace(" ", "")
				.replace("\t", "")
				.replace("\n", "")
				.replace("{", "{\"handle\":\"test22\",");

		Assertions.assertEquals(body, result2);
	}

	/**
	 * Test full webshop update.
	 *
	 * @throws JsonProcessingException the json processing exception
	 */
	@Test
	@Order(8)
	void testFullWebshopUpdate() throws JsonProcessingException {

		String fullBody = """
					{
					    "handle": "test2",
					    "url": "https://test2.com",
						  "interestRate": 24,
					    "serviceLevelA": 25.0,
					    "serviceLevelB": 25.0,
					    "serviceLevelC": 50.0,
					    "currency": "EUR",
						  "runJobs": true,
						  "multiSupplier": false,
					    "emails": [
					    	"ti84plus@test2.com",
					    	"zelenski@test2.com",
					    	"putin@test2.com"
					    ]
					}
				""";

		HttpRequest<WebshopFullModel> request = HttpRequest.PUT("/test22", objectMapper.readValue(fullBody, WebshopFullModel.class));
		String result = client.toBlocking().retrieve(request, String.class);

		Assertions.assertEquals("Webshop updated.", result);

		HttpRequest<WebshopModel> request2 = HttpRequest.GET("/test2");
		String result2 = client.toBlocking().retrieve(request2, String.class);

		fullBody = fullBody
				.replace(" ", "")
				.replace("\t", "")
				.replace("\n", "")
				.replace("\"currency\":\"EUR\",\"runJobs\":true,\"multiSupplier\":false,", "");

		Assertions.assertEquals(fullBody, result2);

		HttpRequest<WebshopSettingsModel> request3 = HttpRequest.GET("/test2/settings");
		String result3 = client.toBlocking().retrieve(request3, String.class);

		String settingsBody = """
					{
							"handle": "test2",
							"currency": "EUR",
							"runJobs": true,
							"multiSupplier": false
					}
				"""
				.replace(" ", "")
				.replace("\t", "")
				.replace("\n", "");

		Assertions.assertEquals(settingsBody, result3);

	}

	/**
	 * Test get sorted by int rate desc.
	 */
	@Test
	@Order(9)
	void testGetSortedByIntRateDesc() {

		String body = """
					[
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
						},
					  {
					  	"handle": "test2",
					  	"url": "https://test2.com",
							"interestRate": 24,
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
					  }
					]
				"""
				.replace(" ", "")
				.replace("\t", "")
				.replace("\n", "");

		// < = %3C
		// > = %3E

		HttpRequest<List<WebshopModel>> request = HttpRequest.GET("/find/interestRate%3E10?sort=interestRate&order=desc");
		String result = client.toBlocking().retrieve(request, String.class);

		Assertions.assertEquals(body, result);

	}

	/**
	 * Test get sorted by url asc.
	 */
	@Test
	@Order(10)
	void testGetSortedByUrlAsc() {

		String body = """
					[
						
					  {
					  	"handle": "test2",
					  	"url": "https://test2.com",
							"interestRate": 24,
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
				"""
				.replace(" ", "")
				.replace("\t", "")
				.replace("\n", "");

		// < = %3C
		// > = %3E

		HttpRequest<List<WebshopModel>> request = HttpRequest.GET("/find/interestRate%3E10?sort=url&order=asc");
		String result = client.toBlocking().retrieve(request, String.class);

		Assertions.assertEquals(body, result);

	}

	/**
	 * Test get sorted by handle desc.
	 */
	@Test
	@Order(11)
	void testGetSortedByHandleDesc() {


		String body = """
					[
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
						},
					  {
					  	"handle": "test2",
					  	"url": "https://test2.com",
							"interestRate": 24,
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
					  }
					]
				"""
				.replace(" ", "")
				.replace("\t", "")
				.replace("\n", "");

		// < = %3C
		// > = %3E

		HttpRequest<List<WebshopModel>> request = HttpRequest.GET("/find/interestRate%3E10?sort=handle&order=desc");
		String result = client.toBlocking().retrieve(request, String.class);

		Assertions.assertEquals(body, result);

	}


	/**
	 * Test delete webshop.
	 */
	@Test
	@Order(12)
	void testDeleteWebshop() {

		HttpRequest<Boolean> request = HttpRequest.DELETE("/test2");
		HttpResponse<Object> result = client.toBlocking().exchange(request);

		Assertions.assertNull(result.body());

	}

}
