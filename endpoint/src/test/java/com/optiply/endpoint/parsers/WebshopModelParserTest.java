package com.optiply.endpoint.parsers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.optiply.endpoint.models.WebshopModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The type Webshop model parser test.
 */
public class WebshopModelParserTest {

	private WebshopModelParserTest parser;

	/**
	 * Sets up.
	 */
	@BeforeEach
	public void setUp() {
		parser = new WebshopModelParserTest();
	}


	/**
	 * Test model conversion.
	 *
	 * @throws JsonProcessingException the json processing exception
	 */
	@Test
	public void testModelConversion() throws JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();

		String ex = """
				{
				\t"handle": "optiply",
				\t"url": "https://optiply.nl",
				\t"interestRate": 20,
				\t"A": 33.3,
				\t"B": 33.3,
				\t"C": 33.4,
				\t"currency": "EUR",
				\t"runJobs": true,
				\t"multiSupplier": false,
				\t"emails": [
				\t\t"management@optiply.nl",
				\t\t"social@optiply.nl"
				\t]
				}""";

		WebshopModel model1 = mapper.readValue(ex, WebshopModel.class);
		WebshopModel model2 = new WebshopModel();
		List<String> emails = new ArrayList<>();
		emails.add("management@optiply.nl");
		emails.add("social@optiply.nl");

		model2.setHandle("optiply");
		model2.setUrl("https://optiply.nl");
		model2.setInterestRate(20);
		model2.setA(33.3);
		model2.setB(33.3);
		model2.setC(33.4);
		model2.setCurrency("EUR");
		model2.setRunJobs(true);
		model2.setMultiSupplier(false);
		model2.setEmails(emails);

		/*assertEquals(model1.handle, model2.handle);
		assertEquals(model1.url, model2.url);
		assertEquals(model1.interestRate, model2.interestRate);
		assertEquals(model1.A, model2.A);
		assertEquals(model1.B, model2.B);
		assertEquals(model1.C, model2.C);
		assertEquals(model1.currency, model2.currency);
		assertEquals(model1.runJobs, model2.runJobs);
		assertEquals(model1.multiSupplier, model2.multiSupplier);
		assertEquals(model1.emails, model2.emails);*/

		assertEquals(model1, model2);

	}

	/**
	 * Test model.
	 */
	@Test
	public void testModel() {

		WebshopModel model1 = new WebshopModel();
		List<String> emails1 = new ArrayList<>();
		emails1.add("management@optiply.nl");
		emails1.add("social@optiply.nl");

		model1.setHandle("optiply");
		model1.setUrl("https://optiply.nl");
		model1.setInterestRate(20);
		model1.setA(33.3);
		model1.setB(33.3);
		model1.setC(33.4);
		model1.setCurrency("EUR");
		model1.setRunJobs(true);
		model1.setMultiSupplier(false);
		model1.setEmails(emails1);

		WebshopModel model2 = new WebshopModel();
		List<String> emails2 = new ArrayList<>();
		emails2.add("management@optiply.nl");
		emails2.add("social@optiply");

		model2.setHandle("optiply");
		model2.setUrl("optiply.nl");
		model2.setInterestRate(20);
		model2.setA(33.3);
		model2.setB(33.3);
		model2.setC(33.3);
		model2.setCurrency("EURO");
		model2.setRunJobs(true);
		model2.setMultiSupplier(false);
		model2.setEmails(emails2);

		WebshopModelParser parser = new WebshopModelParser();

		assertEquals(true, parser.parseModel(model1));
		assertEquals(false, parser.parseModel(model2));

	}

	/**
	 * Test model json.
	 *
	 * @throws JsonProcessingException the json processing exception
	 */
	@Test
	public void testModelJSON() throws JsonProcessingException {

		WebshopModelParser parser = new WebshopModelParser();

		String ex1 = """
				{
				\t"handle": "optiply",
				\t"url": "https://optiply.nl",
				\t"interestRate": 20,
				\t"A": 33.3,
				\t"B": 33.3,
				\t"C": 33.4,
				\t"currency": "EUR",
				\t"runJobs": true,
				\t"multiSupplier": false,
				\t"emails": [
				\t\t"management@optiply.nl",
				\t\t"social@optiply.nl"
				\t]
				}""";

		String ex2 = """
				{
				\t"handle": "optiply",
				\t"url": "optiply.nl",
				\t"interestRate": 20,
				\t"A": 33.3,
				\t"B": 33.3,
				\t"C": 33.3,
				\t"currency": "EURO",
				\t"runJobs": true,
				\t"multiSupplier": false,
				\t"emails": [
				\t\t"management@optiply.nl",
				\t\t"social@optiply"
				\t]
				}""";

		assertEquals(true, parser.parseJSON(ex1));

		assertEquals(false, parser.parseJSON(ex2));


	}
}
