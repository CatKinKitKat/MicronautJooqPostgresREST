package com.optiply.endpoint.models;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import javax.validation.Valid;
import java.util.List;

/**
 * The type Webshop model.
 */
@Data
@JsonPropertyOrder({
		"handle",
		"url",
		"interestRate",
		"A",
		"B",
		"C",
		"currency",
		"runJobs",
		"multiSupplier",
		"emails"
})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WebshopModel {

	/**
	 * The Handle.
	 */
	@JsonProperty("handle")
	public String handle;

	/**
	 * The Url.
	 */
	@JsonProperty("url")
	public String url;

	/**
	 * The Interest rate.
	 */
	@JsonProperty("interestRate")
	public long interestRate;

	/**
	 * The A.
	 */
	@JsonProperty("A")
	public double A;

	/**
	 * The B.
	 */
	@JsonProperty("B")
	public double B;

	/**
	 * The C.
	 */
	@JsonProperty("C")
	public double C;

	/**
	 * The Currency.
	 */
	@JsonProperty("currency")
	public String currency;

	/**
	 * The Run jobs.
	 */
	@JsonProperty("runJobs")
	public boolean runJobs;

	/**
	 * The Multi supplier.
	 */
	@JsonProperty("multiSupplier")
	public boolean multiSupplier;

	/**
	 * The Emails.
	 */
	@JsonProperty("emails")
	@Valid
	public List<String> emails = null;

}