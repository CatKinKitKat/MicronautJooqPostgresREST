package com.optiply.endpoint.controllers.shared.interfaces;

import org.jooq.Condition;
import org.jooq.SortField;

/**
 * Interface for a base controller.
 */
public interface IBaseController {

	/**
	 * Parse params and return a condition.
	 *
	 * @param params the params
	 * @return the condition
	 */
	Condition parseParamsWebshop(String... params);

	/**
	 * Sort parser for the webshops.
	 *
	 * @param sort  the sort
	 * @param order the order
	 * @return the sort field
	 */
	SortField<?> sortParserWebshop(String sort, String order);
}
