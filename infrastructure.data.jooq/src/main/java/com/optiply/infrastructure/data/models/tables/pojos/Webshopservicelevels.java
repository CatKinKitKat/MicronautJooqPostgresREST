/*
 * This file is generated by jOOQ.
 */
package com.optiply.infrastructure.data.models.tables.pojos;


import com.optiply.infrastructure.data.models.tables.interfaces.IWebshopservicelevels;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Webshopservicelevels implements IWebshopservicelevels {

    private static final long serialVersionUID = 1L;

    private Long   webshopid;
    private Double slca;
    private Double slcb;
    private Double slcc;

    public Webshopservicelevels() {}

    public Webshopservicelevels(IWebshopservicelevels value) {
        this.webshopid = value.getWebshopid();
        this.slca = value.getSlca();
        this.slcb = value.getSlcb();
        this.slcc = value.getSlcc();
    }

    public Webshopservicelevels(
        Long   webshopid,
        Double slca,
        Double slcb,
        Double slcc
    ) {
        this.webshopid = webshopid;
        this.slca = slca;
        this.slcb = slcb;
        this.slcc = slcc;
    }

    /**
     * Getter for <code>webshopServiceLevels.webshopId</code>.
     */
    @Override
    public Long getWebshopid() {
        return this.webshopid;
    }

    /**
     * Setter for <code>webshopServiceLevels.webshopId</code>.
     */
    @Override
    public Webshopservicelevels setWebshopid(Long webshopid) {
        this.webshopid = webshopid;
        return this;
    }

    /**
     * Getter for <code>webshopServiceLevels.slcA</code>.
     */
    @Override
    public Double getSlca() {
        return this.slca;
    }

    /**
     * Setter for <code>webshopServiceLevels.slcA</code>.
     */
    @Override
    public Webshopservicelevels setSlca(Double slca) {
        this.slca = slca;
        return this;
    }

    /**
     * Getter for <code>webshopServiceLevels.slcB</code>.
     */
    @Override
    public Double getSlcb() {
        return this.slcb;
    }

    /**
     * Setter for <code>webshopServiceLevels.slcB</code>.
     */
    @Override
    public Webshopservicelevels setSlcb(Double slcb) {
        this.slcb = slcb;
        return this;
    }

    /**
     * Getter for <code>webshopServiceLevels.slcC</code>.
     */
    @Override
    public Double getSlcc() {
        return this.slcc;
    }

    /**
     * Setter for <code>webshopServiceLevels.slcC</code>.
     */
    @Override
    public Webshopservicelevels setSlcc(Double slcc) {
        this.slcc = slcc;
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
        final Webshopservicelevels other = (Webshopservicelevels) obj;
        if (webshopid == null) {
            if (other.webshopid != null)
                return false;
        }
        else if (!webshopid.equals(other.webshopid))
            return false;
        if (slca == null) {
            if (other.slca != null)
                return false;
        }
        else if (!slca.equals(other.slca))
            return false;
        if (slcb == null) {
            if (other.slcb != null)
                return false;
        }
        else if (!slcb.equals(other.slcb))
            return false;
        if (slcc == null) {
            if (other.slcc != null)
                return false;
        }
        else if (!slcc.equals(other.slcc))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.webshopid == null) ? 0 : this.webshopid.hashCode());
        result = prime * result + ((this.slca == null) ? 0 : this.slca.hashCode());
        result = prime * result + ((this.slcb == null) ? 0 : this.slcb.hashCode());
        result = prime * result + ((this.slcc == null) ? 0 : this.slcc.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Webshopservicelevels (");

        sb.append(webshopid);
        sb.append(", ").append(slca);
        sb.append(", ").append(slcb);
        sb.append(", ").append(slcc);

        sb.append(")");
        return sb.toString();
    }

    // -------------------------------------------------------------------------
    // FROM and INTO
    // -------------------------------------------------------------------------

    @Override
    public void from(IWebshopservicelevels from) {
        setWebshopid(from.getWebshopid());
        setSlca(from.getSlca());
        setSlcb(from.getSlcb());
        setSlcc(from.getSlcc());
    }

    @Override
    public <E extends IWebshopservicelevels> E into(E into) {
        into.from(this);
        return into;
    }
}