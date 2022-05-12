package com.optiply.endpoint.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Email model.
 */
@Data
@JsonPropertyOrder({
		"interestRate"
})
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonSerialize
@JsonDeserialize
@AllArgsConstructor
@NoArgsConstructor
public class InterestRateModel {

	/**
	 * The Emails.
	 */
	@JsonProperty("interestRate")
	private Short interestRate;

	/**
	 * Is valid email address boolean.
	 *
	 * @return the boolean
	 */
	public Boolean isValid() {
		//return this.interestRate >= 0 && this.interestRate <= 100;
		return this.interestRate >= 0;
	}

}
