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
 * The type Webshop simple model.
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

	@JsonProperty("handle")
	private String handle;
	@JsonProperty("url")
	private String url;
	@JsonProperty("interestRate")
	private Short interestRate = 20;
	@JsonProperty("A")
	private Double a;
	@JsonProperty("B")
	private Double b;
	@JsonProperty("C")
	private Double c;

	/**
	 * Instantiates a new Webshop simple model.
	 *
	 * @param webshop the webshop
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
	 * Is valid boolean.
	 *
	 * @return the boolean
	 */
	public Boolean isValid() {
		return this.isValidUrl(this.url) &&
				this.isValidServiceSum(this.a, this.b, this.c);
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
}