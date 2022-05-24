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
import org.apache.commons.validator.routines.UrlValidator;

/**
 * JSON Model for the Url Update
 */
@Data
@JsonPropertyOrder({
		"url"
})
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonSerialize
@JsonDeserialize
@AllArgsConstructor
@NoArgsConstructor
public class UrlModel {

	/**
	 * The Emails.
	 */
	@JsonProperty("url")
	private String url;

	/**
	 * Run validation checks..
	 *
	 * @return the boolean
	 */
	@JsonIgnore
	public Boolean isValid() {
		return UrlValidator.getInstance().isValid(this.url);
	}

}
