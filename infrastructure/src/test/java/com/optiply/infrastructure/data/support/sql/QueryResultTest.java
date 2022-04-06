package com.optiply.infrastructure.data.support.sql;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The type Query result test.
 */
public class QueryResultTest {

	/**
	 * Test ordinal.
	 */
	@Test
	public void testOrdinal() {
		Integer result_success = 1;
		Integer result_failed = 0;

		assertEquals(result_success, QueryResult.SUCCESS.ordinal());
		assertEquals(result_failed, QueryResult.FAILED.ordinal());
	}

}
