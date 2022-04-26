package com.optiply.endpoint.controllers.shared;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.optiply.endpoint.controllers.shared.interfaces.IBaseController;
import com.optiply.endpoint.protobuf.EndpointsGrpc;
import com.optiply.infrastructure.data.models.Tables;
import com.optiply.infrastructure.data.repositories.WebshopRepository;
import com.optiply.infrastructure.data.repositories.WebshopemailsRepository;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.jooq.Condition;
import org.jooq.SortField;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The type Grpc base controller.
 */
@Singleton
public abstract class GrpcBaseController extends EndpointsGrpc.EndpointsImplBase implements IBaseController {

	/**
	 * The Webshop repository.
	 */
	@Inject
	public WebshopRepository webshopRepository;
	/**
	 * The Webshopemails repository.
	 */
	@Inject
	public WebshopemailsRepository webshopemailsRepository;
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
				case "url" -> {
					if (operation.equals(":")) {
						filterList.add(Tables.WEBSHOP.URL.equalIgnoreCase(split[1]));
					} else if (operation.equals("%")) {
						filterList.add(Tables.WEBSHOP.URL.likeIgnoreCase(split[1]));
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
				}
				return Tables.WEBSHOP.HANDLE.desc();
			}
			case "url" -> {
				if (order.equals("asc")) {
					return Tables.WEBSHOP.URL.asc();
				}
				return Tables.WEBSHOP.URL.desc();
			}
			case "currency" -> {
				if (order.equals("asc")) {
					return Tables.WEBSHOP.CURRENCY.asc();
				}
				return Tables.WEBSHOP.CURRENCY.desc();
			}
			case "a" -> {
				if (order.equals("asc")) {
					return Tables.WEBSHOP.A.asc();
				}
				return Tables.WEBSHOP.A.desc();
			}
			case "b" -> {
				if (order.equals("asc")) {
					return Tables.WEBSHOP.B.asc();
				}
				return Tables.WEBSHOP.B.desc();
			}
			case "c" -> {
				if (order.equals("asc")) {
					return Tables.WEBSHOP.C.asc();
				}
				return Tables.WEBSHOP.C.desc();
			}
			case "interestrate" -> {
				if (order.equals("asc")) {
					return Tables.WEBSHOP.INTEREST_RATE.asc();
				}
				return Tables.WEBSHOP.INTEREST_RATE.desc();
			}
		}


		return Tables.WEBSHOP.HANDLE.asc();
	}
}
