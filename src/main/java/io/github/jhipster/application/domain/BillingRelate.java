package io.github.jhipster.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A BillingRelate.
 */
@Entity
@Table(name = "billing_relate")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BillingRelate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "draccount")
    private Long draccount;

    @Column(name = "craccount")
    private Long craccount;

    @ManyToOne
    @JsonIgnoreProperties("relatecracs")
    private BillingAC billingAC;

    @ManyToOne
    @JsonIgnoreProperties("relatecracs")
    private BillingAC billingAC;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDraccount() {
        return draccount;
    }

    public BillingRelate draccount(Long draccount) {
        this.draccount = draccount;
        return this;
    }

    public void setDraccount(Long draccount) {
        this.draccount = draccount;
    }

    public Long getCraccount() {
        return craccount;
    }

    public BillingRelate craccount(Long craccount) {
        this.craccount = craccount;
        return this;
    }

    public void setCraccount(Long craccount) {
        this.craccount = craccount;
    }

    public BillingAC getBillingAC() {
        return billingAC;
    }

    public BillingRelate billingAC(BillingAC billingAC) {
        this.billingAC = billingAC;
        return this;
    }

    public void setBillingAC(BillingAC billingAC) {
        this.billingAC = billingAC;
    }

    public BillingAC getBillingAC() {
        return billingAC;
    }

    public BillingRelate billingAC(BillingAC billingAC) {
        this.billingAC = billingAC;
        return this;
    }

    public void setBillingAC(BillingAC billingAC) {
        this.billingAC = billingAC;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BillingRelate billingRelate = (BillingRelate) o;
        if (billingRelate.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), billingRelate.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BillingRelate{" +
            "id=" + getId() +
            ", draccount=" + getDraccount() +
            ", craccount=" + getCraccount() +
            "}";
    }
}
