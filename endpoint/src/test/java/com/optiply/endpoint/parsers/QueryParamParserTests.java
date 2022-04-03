package com.optiply.endpoint.parsers;

import org.jooq.Condition;
import org.jooq.SortField;
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
	public void webshopParser() {

	}

	@Test
	public void webshopParser_withNulls() {

	}

	@Test
	public void emailsParser() {

	}

	@Test
	public void emailsParser_withNulls() {

	}

}
