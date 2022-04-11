package com.optiply.endpoint.models;


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
 * The type Webshop emails model.
 */
@Data
@JsonPropertyOrder({
        "handle",
        "emails"
})
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonSerialize
@JsonDeserialize
@AllArgsConstructor
@NoArgsConstructor
public class WebshopEmailsModel {

    @JsonProperty("handle")
    private String handle;
    @JsonProperty("emails")
    @Valid
    private List<String> emails = null;

    /**
     * Is valid boolean.
     *
     * @return the boolean
     */
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
     * Is valid email address boolean.
     *
     * @param email the email
     * @return the boolean
     */
    private Boolean isValidEmailAddress(String email) {
        return EmailValidator.getInstance().isValid(email);
    }
}