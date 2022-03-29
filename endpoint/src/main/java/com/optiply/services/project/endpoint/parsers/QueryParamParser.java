package com.optiply.services.project.endpoint.parsers;

import com.optiply.services.project.endpoint.config.DataSourceConfig;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

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
			if (param.contains("handle")) {

				if (param.contains(Character.toString(ILIKE))) {
					filterList.add(condition("handle" + "~~*" + param.substring(param.indexOf(ILIKE) + 1)));
				} else if (param.contains(Character.toString(EQ))) {
					filterList.add(condition("handle" + "=" + param.substring(param.indexOf(EQ) + 1)));
				}

			} else if (param.contains("url")) {

				if (param.contains(Character.toString(ILIKE))) {
					filterList.add(condition("url" + "~~*" + param.substring(param.indexOf(ILIKE) + 1)));
				} else if (param.contains(Character.toString(EQ))) {
					filterList.add(condition("url" + "=" + param.substring(param.indexOf(EQ) + 1)));
				}

			} else if (param.contains("interestRate")) {

				if (param.contains(Character.toString(GREATER))) {
					filterList.add(condition("interestRate" + ">" + param.substring(param.indexOf(GREATER) + 1)));
				} else if (param.contains(Character.toString(LESS))) {
					filterList.add(condition("interestRate" + "<" + param.substring(param.indexOf(LESS) + 1)));
				}

			}

		}

		return filterList;
	}
}
