package com.optiply.endpoint.parsers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.optiply.endpoint.models.WebshopModel;
import lombok.extern.java.Log;

/**
 * The type Webshop model parser.
 */
@Log
public class WebshopModelParser {

	/**
	 * Parse model boolean.
	 *
	 * @param model the model
	 * @return the boolean
	 */
	public Boolean parseModel(WebshopModel model) {

		FieldsParser parser = new FieldsParser();

		if (model != null || parser.isValidUrl(model.getUrl()) || parser.isValidCurrency(model.getCurrency())) {
			for (String email : model.getEmails()) {
				if (!parser.isValidEmailAddress(email)) {
					return false;
				}
			}
			return true;
		}

		return false;
	}

	/**
	 * Parse json boolean.
	 *
	 * @param json the json
	 * @return the boolean
	 */
	public Boolean parseJSON(String json) {

		ObjectMapper mapper = new ObjectMapper();

		try {
			WebshopModel model = mapper.readValue(json, WebshopModel.class);
			return parseModel(model);
		} catch (JsonProcessingException e) {
			log.info("Invalid JSON");
			return false;
		}
	}

}
