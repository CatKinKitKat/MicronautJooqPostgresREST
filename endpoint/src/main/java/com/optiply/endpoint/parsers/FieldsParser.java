package com.optiply.endpoint.parsers;

import org.apache.commons.validator.routines.EmailValidator;
import org.apache.commons.validator.routines.UrlValidator;

import java.util.Currency;
import java.util.Set;

/**
 * The type Fields parser.
 */
public class FieldsParser {

    /**
     * Is valid email address boolean.
     *
     * @param email the email
     * @return the boolean
     */
    public Boolean isValidEmailAddress(String email) {
        return EmailValidator.getInstance().isValid(email);
    }

    /**
     * Is valid url boolean.
     *
     * @param url the url
     * @return the boolean
     */
    public Boolean isValidUrl(String url) {
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
    public Boolean isValidCurrency(String currency) {
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
    public Boolean isValidServiceSum(Double A, Double B, Double C) {
        return A + B + C == 100;
    }
}
