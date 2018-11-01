package io.github.jhipster.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A BillingAC.
 */
@Entity
@Table(name = "billing_ac")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BillingAC implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "account_name", nullable = false)
    private String accountName;

    @Column(name = "account_code")
    private String accountCode;

    @ManyToOne
    @JsonIgnoreProperties("billingacs")
    private MainAC mainAC;

    @OneToOne    @JoinColumn(unique = true)
    private AllAccountCode allaccountcode;

    @OneToMany(mappedBy = "billingAC")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<BillingRelate> relatedracs = new HashSet<>();
    @OneToMany(mappedBy = "billingAC")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<BillingRelate> relatecracs = new HashSet<>();
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

    public BillingAC accountName(String accountName) {
        this.accountName = accountName;
        return this;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public BillingAC accountCode(String accountCode) {
        this.accountCode = accountCode;
        return this;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public MainAC getMainAC() {
        return mainAC;
    }

    public BillingAC mainAC(MainAC mainAC) {
        this.mainAC = mainAC;
        return this;
    }

    public void setMainAC(MainAC mainAC) {
        this.mainAC = mainAC;
    }

    public AllAccountCode getAllaccountcode() {
        return allaccountcode;
    }

    public BillingAC allaccountcode(AllAccountCode allAccountCode) {
        this.allaccountcode = allAccountCode;
        return this;
    }

    public void setAllaccountcode(AllAccountCode allAccountCode) {
        this.allaccountcode = allAccountCode;
    }

    public Set<BillingRelate> getRelatedracs() {
        return relatedracs;
    }

    public BillingAC relatedracs(Set<BillingRelate> billingRelates) {
        this.relatedracs = billingRelates;
        return this;
    }

    public BillingAC addRelatedrac(BillingRelate billingRelate) {
        this.relatedracs.add(billingRelate);
        billingRelate.setBillingAC(this);
        return this;
    }

    public BillingAC removeRelatedrac(BillingRelate billingRelate) {
        this.relatedracs.remove(billingRelate);
        billingRelate.setBillingAC(null);
        return this;
    }

    public void setRelatedracs(Set<BillingRelate> billingRelates) {
        this.relatedracs = billingRelates;
    }

    public Set<BillingRelate> getRelatecracs() {
        return relatecracs;
    }

    public BillingAC relatecracs(Set<BillingRelate> billingRelates) {
        this.relatecracs = billingRelates;
        return this;
    }

    public BillingAC addRelatecrac(BillingRelate billingRelate) {
        this.relatecracs.add(billingRelate);
        billingRelate.setBillingAC(this);
        return this;
    }

    public BillingAC removeRelatecrac(BillingRelate billingRelate) {
        this.relatecracs.remove(billingRelate);
        billingRelate.setBillingAC(null);
        return this;
    }

    public void setRelatecracs(Set<BillingRelate> billingRelates) {
        this.relatecracs = billingRelates;
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
        BillingAC billingAC = (BillingAC) o;
        if (billingAC.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), billingAC.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BillingAC{" +
            "id=" + getId() +
            ", accountName='" + getAccountName() + "'" +
            ", accountCode='" + getAccountCode() + "'" +
            "}";
    }
}
