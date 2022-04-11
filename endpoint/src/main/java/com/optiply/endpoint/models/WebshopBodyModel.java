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
import org.apache.commons.validator.routines.UrlValidator;

import javax.validation.Valid;
import java.util.Currency;
import java.util.List;
import java.util.Set;

/**
 * The type Webshop body model.
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
@JsonSerialize
@JsonDeserialize
@AllArgsConstructor
@NoArgsConstructor
public class WebshopBodyModel {

    @JsonProperty("handle")
    private String handle;
    @JsonProperty("url")
    private String url;
    @JsonProperty("interestRate")
    private Short interestRate = 20;
    @JsonProperty("A")
    private Double a;
    @JsonProperty("B")
    private Double b;
    @JsonProperty("C")
    private Double c;
    @JsonProperty("currency")
    private String currency = "EUR";
    @JsonProperty("runJobs")
    private Boolean runJobs = true;
    @JsonProperty("multiSupplier")
    private Boolean multiSupplier = false;
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

        Boolean valids = this.isValidUrl(this.url) &&
                this.isValidCurrency(this.currency) &&
                this.isValidServiceSum(this.a, this.b, this.c);

        if (!valids) {
            valid = false;
        }

        for (String email : emails) {
            if (!this.isValidEmailAddress(email)) {
                valid = false;
            }
        }
        return valid;
    }


    /**
     * Is valid url boolean.
     *
     * @param url the url
     * @return the boolean
     */
    private Boolean isValidUrl(String url) {
        String[] schemes = {"http", "https"};
        UrlValidator urlValidator = new UrlValidator(schemes);
        return urlValidator.isValid(url);
    }

    /**
     * Is valid currency boolean.
     *
     * @param currency the currency
     * @return the boolean
     */
    private Boolean isValidCurrency(String currency) {
        Set<Currency> currencies = Currency.getAvailableCurrencies();
        for (Currency c : currencies) {
            if (c.getCurrencyCode().equals(currency)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Is valid service sum boolean.
     *
     * @param A the a
     * @param B the b
     * @param C the c
     * @return the boolean
     */
    private Boolean isValidServiceSum(Double A, Double B, Double C) {
        return A + B + C == 100;
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