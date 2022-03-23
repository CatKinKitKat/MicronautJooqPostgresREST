/*
 * This file is generated by jOOQ.
 */
package com.optiply.infrastructure.data.models.tables.pojos;


import com.optiply.infrastructure.data.models.tables.interfaces.IWebshopsettings;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Webshopsettings implements IWebshopsettings {

    private static final long serialVersionUID = 1L;

    private Long    webshopid;
    private String  currency;
    private Boolean runjobs;
    private Boolean multisupply;

    public Webshopsettings() {}

    public Webshopsettings(IWebshopsettings value) {
        this.webshopid = value.getWebshopid();
        this.currency = value.getCurrency();
        this.runjobs = value.getRunjobs();
        this.multisupply = value.getMultisupply();
    }

    public Webshopsettings(
        Long    webshopid,
        String  currency,
        Boolean runjobs,
        Boolean multisupply
    ) {
        this.webshopid = webshopid;
        this.currency = currency;
        this.runjobs = runjobs;
        this.multisupply = multisupply;
    }

    /**
     * Getter for <code>webshopSettings.webshopId</code>.
     */
    @Override
    public Long getWebshopid() {
        return this.webshopid;
    }

    /**
     * Setter for <code>webshopSettings.webshopId</code>.
     */
    @Override
    public Webshopsettings setWebshopid(Long webshopid) {
        this.webshopid = webshopid;
        return this;
    }

    /**
     * Getter for <code>webshopSettings.currency</code>.
     */
    @Override
    public String getCurrency() {
        return this.currency;
    }

    /**
     * Setter for <code>webshopSettings.currency</code>.
     */
    @Override
    public Webshopsettings setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    /**
     * Getter for <code>webshopSettings.runJobs</code>.
     */
    @Override
    public Boolean getRunjobs() {
        return this.runjobs;
    }

    /**
     * Setter for <code>webshopSettings.runJobs</code>.
     */
    @Override
    public Webshopsettings setRunjobs(Boolean runjobs) {
        this.runjobs = runjobs;
        return this;
    }

    /**
     * Getter for <code>webshopSettings.multiSupply</code>.
     */
    @Override
    public Boolean getMultisupply() {
        return this.multisupply;
    }

    /**
     * Setter for <code>webshopSettings.multiSupply</code>.
     */
    @Override
    public Webshopsettings setMultisupply(Boolean multisupply) {
        this.multisupply = multisupply;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Webshopsettings other = (Webshopsettings) obj;
        if (webshopid == null) {
            if (other.webshopid != null)
                return false;
        }
        else if (!webshopid.equals(other.webshopid))
            return false;
        if (currency == null) {
            if (other.currency != null)
                return false;
        }
        else if (!currency.equals(other.currency))
            return false;
        if (runjobs == null) {
            if (other.runjobs != null)
                return false;
        }
        else if (!runjobs.equals(other.runjobs))
            return false;
        if (multisupply == null) {
            if (other.multisupply != null)
                return false;
        }
        else if (!multisupply.equals(other.multisupply))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.webshopid == null) ? 0 : this.webshopid.hashCode());
        result = prime * result + ((this.currency == null) ? 0 : this.currency.hashCode());
        result = prime * result + ((this.runjobs == null) ? 0 : this.runjobs.hashCode());
        result = prime * result + ((this.multisupply == null) ? 0 : this.multisupply.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Webshopsettings (");

        sb.append(webshopid);
        sb.append(", ").append(currency);
        sb.append(", ").append(runjobs);
        sb.append(", ").append(multisupply);

        sb.append(")");
        return sb.toString();
    }

    // -------------------------------------------------------------------------
    // FROM and INTO
    // -------------------------------------------------------------------------

    @Override
    public void from(IWebshopsettings from) {
        setWebshopid(from.getWebshopid());
        setCurrency(from.getCurrency());
        setRunjobs(from.getRunjobs());
        setMultisupply(from.getMultisupply());
    }

    @Override
    public <E extends IWebshopsettings> E into(E into) {
        into.from(this);
        return into;
    }
}