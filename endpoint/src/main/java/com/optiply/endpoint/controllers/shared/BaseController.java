package com.optiply.endpoint.controllers.shared;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.optiply.endpoint.controllers.shared.interfaces.IBaseController;
import com.optiply.endpoint.services.RepositoryService;
import com.optiply.infrastructure.data.models.Tables;
import jakarta.inject.Inject;
import org.jooq.Condition;
import org.jooq.SortField;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The type Base controller.
 */
public abstract class BaseController implements IBaseController {

	/**
	 * The repository service.
	 */
	@Inject
	public RepositoryService repositoryService;
	/**
	 * The Object mapper.
	 */
	@Inject
	public ObjectMapper objectMapper;


	/**
	 * Parse params webshop condition.
	 *
	 * @param params the params
	 * @return the condition
	 */
	@Override
	public Condition parseParamsWebshop(String... params) {

		if (params == null || params.length == 0) {
			return null;
		}

		List<Condition> filterList = new ArrayList<>();

		for (String param : params) {

			String operation = "";
			final Matcher m = Pattern.compile("[:%<>]").matcher(param);
			if (m.find())
				switch (m.group().charAt(0)) {
					case ':' -> operation = ":";
					case '%' -> operation = "%";
					case '<' -> operation = "<";
					case '>' -> operation = ">";
				}

			if (operation.isEmpty()) return null;

			String[] split = param.split(operation);
			switch (split[0]) {
				case "handle" -> {
					if (operation.equals(":")) {
						filterList.add(Tables.WEBSHOP.HANDLE.equalIgnoreCase(split[1]));
					} else if (operation.equals("%")) {
						filterList.add(Tables.WEBSHOP.HANDLE.likeIgnoreCase(split[1]));
					}
				}
				case "interestRate" -> {
					if (operation.equals(">")) {
						filterList.add(Tables.WEBSHOP.INTEREST_RATE.greaterThan(Short.parseShort(split[1])));
					} else if (operation.equals("<")) {
						filterList.add(Tables.WEBSHOP.INTEREST_RATE.lessThan(Short.parseShort(split[1])));
					}
				}
				// To add more later
			}
		}


		Condition condition = filterList.get(0);
		filterList.remove(0);
		for (Condition c : filterList) {
			condition = condition.and(c);
		}

		return condition;
	}

	/**
	 * Sort parser webshop sort field.
	 *
	 * @param sort  the sort
	 * @param order the order
	 * @return the sort field
	 */
	@Override
	public SortField<?> sortParserWebshop(String sort, String order) {

		if (sort == null || sort.isEmpty()) {
			sort = "handle";
		}

		if (order == null || order.isEmpty()) {
			order = "asc";
		}

		sort = sort.toLowerCase();
		order = order.toLowerCase();

		switch (sort) {
			case "handle" -> {
				if (order.equals("asc")) {
					return Tables.WEBSHOP.HANDLE.asc();
				} else if (order.equals("desc")) {
					return Tables.WEBSHOP.HANDLE.desc();
				}
				return Tables.WEBSHOP.HANDLE.asc();
			}
			case "url" -> {
				if (order.equals("asc")) {
					return Tables.WEBSHOP.URL.asc();
				} else if (order.equals("desc")) {
					return Tables.WEBSHOP.URL.desc();
				}
				return Tables.WEBSHOP.URL.asc();
			}
			case "interestrate" -> {
				if (order.equals("asc")) {
					return Tables.WEBSHOP.INTEREST_RATE.asc();
				} else if (order.equals("desc")) {
					return Tables.WEBSHOP.INTEREST_RATE.desc();
				}
				return Tables.WEBSHOP.INTEREST_RATE.asc();
			}
			// To add more later
		}

		return Tables.WEBSHOP.HANDLE.asc();
	}
}
