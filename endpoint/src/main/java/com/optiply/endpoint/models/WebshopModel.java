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
import org.apache.commons.validator.routines.UrlValidator;

import java.util.Currency;
import java.util.Set;

/**
 * The type Webshop body model.
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
		"multiSupplier"
})
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonSerialize
@JsonDeserialize
@AllArgsConstructor
@NoArgsConstructor
public class WebshopModel {

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
	 * The A.
	 */
	@JsonProperty("serviceLevelA")
	private Double serviceLevelA;
	/**
	 * The B.
	 */
	@JsonProperty("serviceLevelB")
	private Double serviceLevelB;
	/**
	 * The C.
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
	 * Instantiates a new Webshop body model.
	 *
	 * @param webshop the webshop
	 */
	public WebshopModel(Webshop webshop) {
		this.handle = webshop.getHandle();
		this.url = webshop.getUrl();
		this.interestRate = webshop.getInterestRate();
		this.serviceLevelA = webshop.getA();
		this.serviceLevelB = webshop.getB();
		this.serviceLevelC = webshop.getC();
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

		return this.isValidUrl(this.url) &&
				this.isValidCurrency(this.currency) &&
				this.isValidServiceSum(this.serviceLevelA, this.serviceLevelB, this.serviceLevelC);
	}

	/**
	 * Is valid url boolean.
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

	/**
	 * Is valid service sum boolean.
	 *
	 * @param A the a
	 * @param B the b
	 * @param C the c
	 * @return the boolean
	 */
	private Boolean isValidServiceSum(Double A, Double B, Double C) {
		return A + B + C == 100;
	}

}