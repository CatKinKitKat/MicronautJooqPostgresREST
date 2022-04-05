package com.optiply.endpoint.models;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.optiply.infrastructure.data.models.tables.pojos.Webshop;
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
	public Short interestRate = 20;

	/**
	 * The A.
	 */
	@JsonProperty("A")
	public double A = 0.0;

	/**
	 * The B.
	 */
	@JsonProperty("B")
	public double B = 0.0;

	/**
	 * The C.
	 */
	@JsonProperty("C")
	public double C = 0.0;

	/**
	 * The Currency.
	 */
	@JsonProperty("currency")
	public String currency = "EUR";

	/**
	 * The Run jobs.
	 */
	@JsonProperty("runJobs")
	public boolean runJobs = true;

	/**
	 * The Multi supplier.
	 */
	@JsonProperty("multiSupplier")
	public boolean multiSupplier = false;

	/**
	 * The Emails.
	 */
	@JsonProperty("emails")
	@Valid
	public List<String> emails = null;

    public WebshopModel(Webshop webshop, List<String> webshopemails) {
		this.handle = webshop.getHandle();
		this.url = webshop.getUrl();
		this.interestRate = webshop.getInterestrate();
		this.A = webshop.getA();
		this.B = webshop.getB();
		this.C = webshop.getC();
		this.currency = webshop.getCurrency();
		this.runJobs = webshop.getRunjobs();
		this.multiSupplier = webshop.getMultisupply();
		this.emails = webshopemails;
    }
}