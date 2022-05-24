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

/**
 * JSON Model for the Service Levels Update
 */
@Data
@JsonPropertyOrder({
		"serviceLevelA",
		"serviceLevelB",
		"serviceLevelC"
})
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonSerialize
@JsonDeserialize
@AllArgsConstructor
@NoArgsConstructor
public class ServiceLevelsModel {

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
	 * Run validation checks..
	 *
	 * @return is valid?
	 */
	@JsonIgnore
	public Boolean isValid() {
		return this.isValidServiceSum(this.serviceLevelA, this.serviceLevelB, this.serviceLevelC);
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