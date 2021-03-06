package com.optiply.endpoint.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.optiply.infrastructure.data.models.tables.pojos.Webshop;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.commons.validator.routines.UrlValidator;

import javax.validation.Valid;
import java.util.List;

/**
 * JSON Model for the Webshop
 */
@Data
@JsonPropertyOrder({
		"handle",
		"url",
		"interestRate",
		"serviceLevelA",
		"serviceLevelB",
		"serviceLevelC",
		"emails"
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
	 * The Emails.
	 */
	@JsonProperty("emails")
	@Valid
	private List<String> emails = null;

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
	}

	/**
	 * Run validation checks..
	 *
	 * @return is valid?
	 */
	@JsonIgnore
	public Boolean isValid() {

		return this.isValidUrl(this.url) &&
				this.areValidEmailAddresses() &&
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


	/**
	 * Is valid email address boolean.
	 *
	 * @param email the email
	 * @return the boolean
	 */
	private Boolean isValidEmailAddress(String email) {
		return EmailValidator.getInstance().isValid(email);
	}

	/**
	 * Is valid boolean.
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