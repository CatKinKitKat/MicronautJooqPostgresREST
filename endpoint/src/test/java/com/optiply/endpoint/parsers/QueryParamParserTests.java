package com.optiply.endpoint.parsers;

import org.jooq.Condition;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

import static org.jooq.impl.DSL.condition;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class QueryParamParserTests {

	private QueryParamParser parser;

	@BeforeEach
	public void setUp() {
		parser = new QueryParamParser();
	}

	@Test
	public void testParse() {

		String query = "?name=John&age=25&sons=2&daughters=1";
		String[] params = parser.parseQuery(query);

		assertEquals(4, params.length);
		assertEquals("name=John", params[0]);
		assertEquals("age=25", params[1]);
		assertEquals("sons=2", params[2]);
		assertEquals("daughters=1", params[3]);
	}

	@Test
	public void testParse_NoQuery() {

		String query = "";
		String[] params = parser.parseQuery(query);

		assertNull(params);
	}

	@Test
	public void testConditions() {

		String query = "?handle:lol&interestRate>20";
		String[] params = parser.parseQuery(query);
		List<Condition> conditions = parser.parseParams(params);

		assertEquals(2, conditions.size());
		assertEquals(conditions.get(0), condition("handle" + "=" + "lol"));
		assertEquals(conditions.get(1), condition("interestRate" + ">" + "20"));

	}

}
