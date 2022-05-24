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

import java.util.Currency;
import java.util.Set;

/**
 * JSON Model for the Settings Update
 */
@Data
@JsonPropertyOrder({
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
public class SettingsModel {

	/**
	 * The Currency in ISO 4217 format.
	 */
	@JsonProperty("currency")
	private String currency = "EUR";
	/**
	 * The ability to run jobs.
	 */
	@JsonProperty("runJobs")
	private Boolean runJobs = true;
	/**
	 * Does it have multiple suppliers?
	 */
	@JsonProperty("multiSupplier")
	private Boolean multiSupplier = false;

	/**
	 * Run validation checks..
	 *
	 * @return is valid?
	 */
	@JsonIgnore
	public Boolean isValid() {
		return this.isValidCurrency(this.currency);
	}

	/**
	 * Is the currency format valid?
	 *
	 * @param currency the currency in ISO 4217 format (hopefully)
	 * @return is valid?
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
}