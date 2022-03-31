package com.optiply.endpoint.parsers;

import org.jooq.Condition;

import java.util.ArrayList;
import java.util.List;

import static org.jooq.impl.DSL.condition;

/**
 * The type Query param parser.
 */
public class QueryParamParser {

	/**
	 * The Querysign.
	 */
	private final char QUERYSIGN = '?';
	/**
	 * The And.
	 */
	private final char AND = '&';
	/**
	 * The Eq.
	 */
	private final char EQ = ':';
	/**
	 * The Ilike.
	 */
	private final char ILIKE = '%';
	/**
	 * The Greater.
	 */
	private final char GREATER = '>';
	/**
	 * The Less.
	 */
	private final char LESS = '<';

	/**
	 * Parse query string [ ].
	 *
	 * @param query the query
	 * @return the string [ ]
	 */
	public String[] parseQuery(String query) {

		if (query == null || query.isEmpty()) {
			return null;
		}

		if (query.charAt(0) == AND || query.charAt(0) == QUERYSIGN) {
			query = query.substring(1);
		}

		return query.split(String.valueOf(AND));
	}

	/**
	 * Parse params list.
	 *
	 * @param params the params
	 * @return the list
	 */
	public List<Condition> parseParams(String... params) {

		if (params == null || params.length == 0) {
			return null;
		}

		List<Condition> filterList = new ArrayList<>();

		for (String param : params) {
			if (param.contains("handle") ||
					param.contains("url") ||
					param.contains("currency")) {

				if (param.contains(String.valueOf(EQ))) {
					filterList.add(condition(
							param.substring(0, param.indexOf(EQ)),
							param.substring(param.indexOf(EQ) + 1))
					);
				} else if (param.contains(String.valueOf(ILIKE))) {
					filterList.add(condition(
							param.substring(0, param.indexOf(ILIKE)),
							param.substring(param.indexOf(ILIKE) + 1))
					);
				}

			} else if (param.contains("A") ||
					param.contains("B") ||
					param.contains("C") ||
					param.contains("interestRate")) {

				if (param.contains(String.valueOf(GREATER))) {
					filterList.add(condition(
							param.substring(0, param.indexOf(GREATER)),
							param.substring(param.indexOf(GREATER) + 1))
					);
				} else if (param.contains(String.valueOf(LESS))) {
					filterList.add(condition(
							param.substring(0, param.indexOf(LESS)),
							param.substring(param.indexOf(LESS) + 1))
					);
				} else if (param.contains(String.valueOf(EQ))) {
					filterList.add(condition(
							param.substring(0, param.indexOf(EQ)),
							param.substring(param.indexOf(EQ) + 1))
					);
				}

			} else if (param.contains("runJobs") ||
					param.contains("multiSupply") ||
					param.contains("multiSupplier")) {

				if (param.contains(String.valueOf(EQ))) {
					filterList.add(
							condition(param.substring(0, param.indexOf(EQ)),
									param.substring(param.indexOf(EQ) + 1))
					);
				}

			}

		}

		return filterList;
	}
}
