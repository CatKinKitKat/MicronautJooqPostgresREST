package com.optiply.endpoint.parsers;

import org.apache.commons.validator.routines.EmailValidator;
import org.apache.commons.validator.routines.UrlValidator;

public class FieldsParser {

	public Boolean isValidEmailAddress(String email) {
		return EmailValidator.getInstance().isValid(email);
	}

	public Boolean isValidUrl(String url) {
		String[] schemes = { "http", "https" };
		UrlValidator urlValidator = new UrlValidator(schemes);
		return urlValidator.isValid(url);
	}
}
