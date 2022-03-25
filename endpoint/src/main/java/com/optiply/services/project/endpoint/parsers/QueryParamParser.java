package com.optiply.services.project.endpoint.parsers;

public class QueryParamParser {

	private final char QUERYSIGN = '?';
	private final char AND = '&';
	private final char EQ = ':';
	private final char ILIKE = '%';
	private final char GREATER = '>';
	private final char LESS = '<';

	public String[] parse(String query) {

		if (query == null || query.isEmpty()) {
			return null;
		}

		if (query.charAt(0) == AND || query.charAt(0) == QUERYSIGN) {
			query = query.substring(1);
		}

		return query.split(String.valueOf(AND));
	}



}
