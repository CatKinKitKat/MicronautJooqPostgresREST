package com.optiply.infrastructure.data.support.sql;

/**
 * The enum QueryResult.
 * Using .ordinal() to get the index of the enum value
 * Used to equate the result of a transaction to a specific enum value
 */
public enum QueryResult {
	/**
	 * Failed query result.
	 */
	FAILED,
	/**
	 * Success query result.
	 */
	SUCCESS
}
