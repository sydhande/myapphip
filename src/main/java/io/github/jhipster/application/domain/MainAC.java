package io.github.jhipster.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * not an ignored comment
 */
@ApiModel(description = "not an ignored comment")
@Entity
@Table(name = "main_ac")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MainAC implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_name")
    private String accountName;

    @ManyToOne
    @JsonIgnoreProperties("mainacs")
    private GeneralAC generalAC;

    @OneToMany(mappedBy = "mainAC")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<BillingAC> billingacs = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountName() {
        return accountName;
    }

    public MainAC accountName(String accountName) {
        this.accountName = accountName;
        return this;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public GeneralAC getGeneralAC() {
        return generalAC;
    }

    public MainAC generalAC(GeneralAC generalAC) {
        this.generalAC = generalAC;
        return this;
    }

    public void setGeneralAC(GeneralAC generalAC) {
        this.generalAC = generalAC;
    }

    public Set<BillingAC> getBillingacs() {
        return billingacs;
    }

    public MainAC billingacs(Set<BillingAC> billingACS) {
        this.billingacs = billingACS;
        return this;
    }

    public MainAC addBillingac(BillingAC billingAC) {
        this.billingacs.add(billingAC);
        billingAC.setMainAC(this);
        return this;
    }

    public MainAC removeBillingac(BillingAC billingAC) {
        this.billingacs.remove(billingAC);
        billingAC.setMainAC(null);
        return this;
    }

    public void setBillingacs(Set<BillingAC> billingACS) {
        this.billingacs = billingACS;
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
        MainAC mainAC = (MainAC) o;
        if (mainAC.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mainAC.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MainAC{" +
            "id=" + getId() +
            ", accountName='" + getAccountName() + "'" +
            "}";
    }
}
