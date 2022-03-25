package com.optiply.services.project.endpoint.models;

import lombok.Data;

/**
 * The type Webshop.
 */
@Data
public class Webshop {
	/**
	 * The Handle.
	 */
	private String handle;
	/**
	 * The Url.
	 */
	private String url;
	/**
	 * The Interest rate.
	 */
	private Integer interestRate;
	/**
	 * The Emails.
	 */
	private WebshopEmails emails;
	/**
	 * The Service levels.
	 */
	private WebshopServiceLevels serviceLevels;
	/**
	 * The Settings.
	 */
	private WebshopSettings settings;
}
