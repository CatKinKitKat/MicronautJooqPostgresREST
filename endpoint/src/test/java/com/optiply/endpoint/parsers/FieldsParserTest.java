package com.optiply.endpoint.parsers;

import org.jooq.Condition;
import org.jooq.SortField;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

import static org.jooq.impl.DSL.condition;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


public class FieldsParserTest {

	private FieldsParser parser;

	@BeforeEach
	public void setUp() {
		parser = new FieldsParser();
	}

	@Test
	public void emailTests() {

		assertEquals(true, parser.isValidEmailAddress("goncalo.cand.amaro@gmail.com"));
		assertEquals(true, parser.isValidEmailAddress("17440@stu.ipbeja.pt"));
		assertEquals(false, parser.isValidEmailAddress("zec@zec@email.net"));
		assertEquals(false, parser.isValidEmailAddress("almoco@jantar-com"));

	}

	@Test
	public void urlTests() {

		assertEquals(true, parser.isValidUrl("http://www.google.com"));
		assertEquals(true, parser.isValidUrl("https://www.google.com"));
		assertEquals(false, parser.isValidUrl("google.com"));
		assertEquals(false, parser.isValidUrl("www.google.com"));

	}

}
