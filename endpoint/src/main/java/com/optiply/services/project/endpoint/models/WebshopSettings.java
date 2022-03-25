package com.optiply.services.project.endpoint.models;

import lombok.Data;

/**
 * The type Webshop settings.
 */
@Data
public class WebshopSettings {
	/**
	 * The Currency.
	 */
	private String currency;
	/**
	 * The Run jobs.
	 */
	private Boolean runJobs;
	/**
	 * The Multi supplier.
	 */
	private Boolean multiSupplier;
}
