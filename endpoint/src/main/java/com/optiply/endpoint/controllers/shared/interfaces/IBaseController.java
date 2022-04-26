package com.optiply.endpoint.controllers.shared.interfaces;

import org.jooq.Condition;
import org.jooq.SortField;

/**
 * The interface Base controller.
 */
public interface IBaseController {

	/**
	 * Parse params webshop condition.
	 *
	 * @param params the params
	 * @return the condition
	 */
	Condition parseParamsWebshop(String... params);

	/**
	 * Sort parser webshop sort field.
	 *
	 * @param sort  the sort
	 * @param order the order
	 * @return the sort field
	 */
	SortField<?> sortParserWebshop(String sort, String order);
}
