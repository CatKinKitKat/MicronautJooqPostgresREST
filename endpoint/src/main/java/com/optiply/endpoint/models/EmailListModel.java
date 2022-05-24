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

import javax.validation.Valid;
import java.util.List;

/**
 * JSON Model for the EmailList Update
 */
@Data
@JsonPropertyOrder({
		"emails"
})
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonSerialize
@JsonDeserialize
@AllArgsConstructor
@NoArgsConstructor
public class EmailListModel {

	/**
	 * The Emails.
	 */
	@JsonProperty("emails")
	@Valid
	private List<String> emails = null;

	/**
	 * Run validation checks..
	 *
	 * @return is valid?
	 */
	@JsonIgnore
	public Boolean isValid() {
		Boolean valid = true;
		for (String email : emails) {
			if (!this.isValidEmailAddress(email)) {
				valid = false;
			}
		}
		return valid;
	}

	/**
	 * Is the email address valid?
	 *
	 * @param email the email
	 * @return the boolean
	 */
	private Boolean isValidEmailAddress(String email) {
		return EmailValidator.getInstance().isValid(email);
	}

}