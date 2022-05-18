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
import org.apache.commons.validator.routines.EmailValidator;

/**
 * The type Email model.
 */
@Data
@JsonPropertyOrder({
		"email"
})
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonSerialize
@JsonDeserialize
@AllArgsConstructor
@NoArgsConstructor
public class EmailModel {

	/**
	 * The Emails.
	 */
	@JsonProperty("email")
	private String email;

	/**
	 * Is valid email address boolean.
	 *
	 * @return the boolean
	 */
	@JsonIgnore
	public Boolean isValid() {
		return EmailValidator.getInstance().isValid(this.email);
	}

}
