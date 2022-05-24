package com.optiply.endpoint.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.commons.validator.routines.UrlValidator;

import javax.validation.Valid;
import java.util.Currency;
import java.util.List;
import java.util.Set;

/**
 * JSON Model for the Webshop + Settings
 */
@Data
@JsonPropertyOrder({
		"handle",
		"url",
		"interestRate",
		"serviceLevelA",
		"serviceLevelB",
		"serviceLevelC",
		"currency",
		"runJobs",
		"multiSupplier",
		"emails"
})
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonSerialize
@JsonDeserialize
@AllArgsConstructor
@NoArgsConstructor
public class WebshopFullModel {

	/**
	 * The Handle.
	 */
	@JsonProperty("handle")
	private String handle;
	/**
	 * The Url.
	 */
	@JsonProperty("url")
	private String url;
	/**
	 * The Interest rate.
	 */
	@JsonProperty("interestRate")
	private Short interestRate = 20;
	/**
	 * The Servicel Level A Percentage.
	 */
	@JsonProperty("serviceLevelA")
	private Double serviceLevelA;
	/**
	 * The Servicel Level B Percentage.
	 */
	@JsonProperty("serviceLevelB")
	private Double serviceLevelB;
	/**
	 * The Servicel Level C Percentage.
	 */
	@JsonProperty("serviceLevelC")
	private Double serviceLevelC;
	/**
	 * The Currency.
	 */
	@JsonProperty("currency")
	private String currency = "EUR";
	/**
	 * The Run jobs.
	 */
	@JsonProperty("runJobs")
	private Boolean runJobs = true;
	/**
	 * The Multi supplier.
	 */
	@JsonProperty("multiSupplier")
	private Boolean multiSupplier = false;
	/**
	 * The Emails.
	 */
	@JsonProperty("emails")
	@Valid
	private List<String> emails = null;

	/**
	 * Run validation checks..
	 *
	 * @return is valid?
	 */
	@JsonIgnore
	public Boolean isValid() {

		return this.isValidUrl(this.url) &&
				this.isValidCurrency(this.currency) &&
				this.areValidEmailAddresses() &&
				this.isValidServiceSum(this.serviceLevelA, this.serviceLevelB, this.serviceLevelC);
	}

	/**
	 * Is valid url?
	 *
	 * @param url the url
	 * @return the boolean
	 */
	private Boolean isValidUrl(String url) {
		String[] schemes = {"http", "https"};
		UrlValidator urlValidator = new UrlValidator(schemes);
		return urlValidator.isValid(url);
	}

	/**
	 * Is valid currency?
	 *
	 * @param currency the currency
	 * @return the boolean
	 */
	private Boolean isValidCurrency(String currency) {
		Set<Currency> currencies = Currency.getAvailableCurrencies();
		for (Currency c : currencies) {
			if (c.getCurrencyCode().equals(currency)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Is valid service level sum? Must be 100%
	 *
	 * @param A the a
	 * @param B the b
	 * @param C the c
	 * @return the boolean
	 */
	private Boolean isValidServiceSum(Double A, Double B, Double C) {
		return A + B + C == 100;
	}


	/**
	 * Is valid email?
	 *
	 * @param email the email
	 * @return the boolean
	 */
	private Boolean isValidEmailAddress(String email) {
		return EmailValidator.getInstance().isValid(email);
	}

	/**
	 * Are all email addresses valid?
	 *
	 * @return the boolean
	 */
	private Boolean areValidEmailAddresses() {
		Boolean valid = true;
		for (String email : emails) {
			if (!this.isValidEmailAddress(email)) {
				valid = false;
			}
		}
		return valid;
	}
}