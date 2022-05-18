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
 * The type Email model.
 */
@Data
@JsonPropertyOrder({
		"handle"
})
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonSerialize
@JsonDeserialize
@AllArgsConstructor
@NoArgsConstructor
public class HandleModel {

	/**
	 * The Emails.
	 */
	@JsonProperty("handle")
	private String handle;

	/**
	 * Is valid email address boolean.
	 *
	 * @return the boolean
	 */
	@JsonIgnore
	public Boolean isValid() {
		return !this.handle.isEmpty();
	}

}
