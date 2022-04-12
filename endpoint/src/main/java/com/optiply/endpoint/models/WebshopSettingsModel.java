package com.optiply.endpoint.models;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.optiply.infrastructure.data.models.tables.pojos.Webshop;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Currency;
import java.util.Set;

/**
 * The type Webshop settings model.
 */
@Data
@JsonPropertyOrder({
		"handle",
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
public class WebshopSettingsModel {

	/**
	 * The Handle.
	 */
	@JsonProperty("handle")
	private String handle;
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
	 * Instantiates a new Webshop settings model.
	 *
	 * @param webshop the webshop
	 */
	public WebshopSettingsModel(Webshop webshop) {
		this.handle = webshop.getHandle();
		this.currency = webshop.getCurrency();
		this.runJobs = webshop.getRunJobs();
		this.multiSupplier = webshop.getMultiSupply();
	}

	/**
	 * Is valid boolean.
	 *
	 * @return the boolean
	 */
	public Boolean isValid() {
		return this.isValidCurrency(this.currency);
	}

	/**
	 * Is valid currency boolean.
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
}