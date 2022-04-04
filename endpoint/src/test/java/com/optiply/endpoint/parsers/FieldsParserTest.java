package com.optiply.endpoint.parsers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * The type Fields parser test.
 */
public class FieldsParserTest {

	private FieldsParser parser;

	/**
	 * Sets up.
	 */
	@BeforeEach
	public void setUp() {
		parser = new FieldsParser();
	}

	/**
	 * Email tests.
	 */
	@Test
	public void emailTests() {

		assertEquals(true, parser.isValidEmailAddress("goncalo.cand.amaro@gmail.com"));
		assertEquals(true, parser.isValidEmailAddress("17440@stu.ipbeja.pt"));
		assertEquals(false, parser.isValidEmailAddress("zec@zec@email.net"));
		assertEquals(false, parser.isValidEmailAddress("almoco@jantar-com"));

	}

	/**
	 * Url tests.
	 */
	@Test
	public void urlTests() {

		assertEquals(true, parser.isValidUrl("http://www.google.com"));
		assertEquals(true, parser.isValidUrl("https://www.google.com"));
		assertEquals(false, parser.isValidUrl("google.com"));
		assertEquals(false, parser.isValidUrl("www.google.com"));

	}

	/**
	 * Currency tests.
	 */
	@Test
	public void currencyTests() {

		assertEquals(true, parser.isValidCurrency("EUR"));
		assertEquals(true, parser.isValidCurrency("USD"));
		assertEquals(false, parser.isValidCurrency("EURO"));
		assertEquals(false, parser.isValidCurrency("DOLLAR"));

	}

	@Test
	public void serviceLevelSumTest() {

		assertEquals(true, parser.isValidServiceSum(33.3, 33.3, 33.4));
		assertEquals(false, parser.isValidServiceSum(33.3, 33.3, 33.3));

	}

}
