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
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.commons.validator.routines.UrlValidator;

/**
 * The JSON representation of a Webshop (Simple)
 * Should represent a Webshop Result without the webshop_id,
 * neither the email list or its settings.
 *
 * @author G. Amaro
 */
@Data
@JsonPropertyOrder({
		"handle",
		"url",
		"interestRate",
		"A",
		"B",
		"C"
})
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonSerialize
@JsonDeserialize
@AllArgsConstructor
@NoArgsConstructor
public class WebshopSimpleModel {

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
	@JsonProperty("A")
	private Double a;
	/**
	 * The Servicel Level B Percentage.
	 */
	@JsonProperty("B")
	private Double b;
	/**
	 * The Servicel Level C Percentage.
	 */
	@JsonProperty("C")
	private Double c;

	/**
	 * Instantiates a new Webshop simple model.
	 *
	 * @param webshop the webshop model
	 */
	public WebshopSimpleModel(Webshop webshop) {
		this.handle = webshop.getHandle();
		this.url = webshop.getUrl();
		this.interestRate = webshop.getInterestRate();
		this.a = webshop.getA();
		this.b = webshop.getB();
		this.c = webshop.getC();
	}

	/**
	 * Run validation checks.
	 *
	 * @return is valid?
	 */
	public Boolean isValid() {
		return this.isValidUrl(this.url) &&
				this.isValidServiceSum(this.a, this.b, this.c);
	}

	/**
	 * Is the URL valid?
	 * It must be a valid URL and it must not be null
	 * The URL must only use the following protocols: http, https
	 *
	 * @param url the url
	 * @return is valid?
	 */
	private Boolean isValidUrl(String url) {
		String[] schemes = {"http", "https"};
		UrlValidator urlValidator = new UrlValidator(schemes);
		return urlValidator.isValid(url);
	}

	/**
	 * The sum of the service levels must be 100%
	 *
	 * @param A the percentage of the servicel level A
	 * @param B the percentage of the servicel level B
	 * @param C the percentage of the servicel level C
	 * @return is the sum of the service levels 100%?
	 */
	private Boolean isValidServiceSum(Double A, Double B, Double C) {
		return A + B + C == 100;
	}

}