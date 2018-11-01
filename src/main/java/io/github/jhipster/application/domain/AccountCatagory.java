package io.github.jhipster.application.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import io.github.jhipster.application.domain.enumeration.Catuser;

/**
 * A AccountCatagory.
 */
@Entity
@Table(name = "account_catagory")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AccountCatagory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_type")
    private String accountType;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_user")
    private Catuser accountUser;

    @OneToOne    @JoinColumn(unique = true)
    private OwnerAccount owneraccount;

    @OneToOne    @JoinColumn(unique = true)
    private VendorAccount vendoraccount;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountType() {
        return accountType;
    }

    public AccountCatagory accountType(String accountType) {
        this.accountType = accountType;
        return this;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Catuser getAccountUser() {
        return accountUser;
    }

    public AccountCatagory accountUser(Catuser accountUser) {
        this.accountUser = accountUser;
        return this;
    }

    public void setAccountUser(Catuser accountUser) {
        this.accountUser = accountUser;
    }

    public OwnerAccount getOwneraccount() {
        return owneraccount;
    }

    public AccountCatagory owneraccount(OwnerAccount ownerAccount) {
        this.owneraccount = ownerAccount;
        return this;
    }

    public void setOwneraccount(OwnerAccount ownerAccount) {
        this.owneraccount = ownerAccount;
    }

    public VendorAccount getVendoraccount() {
        return vendoraccount;
    }

    public AccountCatagory vendoraccount(VendorAccount vendorAccount) {
        this.vendoraccount = vendorAccount;
        return this;
    }

    public void setVendoraccount(VendorAccount vendorAccount) {
        this.vendoraccount = vendorAccount;
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
        AccountCatagory accountCatagory = (AccountCatagory) o;
        if (accountCatagory.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), accountCatagory.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AccountCatagory{" +
            "id=" + getId() +
            ", accountType='" + getAccountType() + "'" +
            ", accountUser='" + getAccountUser() + "'" +
            "}";
    }
}
