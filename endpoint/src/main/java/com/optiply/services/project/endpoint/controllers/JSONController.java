package com.optiply.services.project.endpoint.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.optiply.services.project.endpoint.parsers.QueryParamParser;


/**
 * The type Json controller.
 */
public class JSONController {

	/**
	 * The Mapper.
	 */
	private final ObjectMapper mapper;
	/**
	 * The Parser.
	 */
	private final QueryParamParser parser;

	/**
	 * Instantiates a new Json controller.
	 */
	public JSONController() {
		this.mapper = new ObjectMapper();
		this.parser = new QueryParamParser();
	}

}
