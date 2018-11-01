package io.github.jhipster.application.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Owner.
 */
@Entity
@Table(name = "owner")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Owner implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "accountcode")
    private String accountcode;

    @Column(name = "flat")
    private String flat;

    @Column(name = "building")
    private String building;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "middlename")
    private String middlename;

    @Column(name = "lastname")
    private String lastname;

    @OneToOne    @JoinColumn(unique = true)
    private AllAccountCode allaccountcode;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "owner_owneraccount",
               joinColumns = @JoinColumn(name = "owners_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "owneraccounts_id", referencedColumnName = "id"))
    private Set<OwnerAccount> owneraccounts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountcode() {
        return accountcode;
    }

    public Owner accountcode(String accountcode) {
        this.accountcode = accountcode;
        return this;
    }

    public void setAccountcode(String accountcode) {
        this.accountcode = accountcode;
    }

    public String getFlat() {
        return flat;
    }

    public Owner flat(String flat) {
        this.flat = flat;
        return this;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

    public String getBuilding() {
        return building;
    }

    public Owner building(String building) {
        this.building = building;
        return this;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getFirstname() {
        return firstname;
    }

    public Owner firstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public Owner middlename(String middlename) {
        this.middlename = middlename;
        return this;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getLastname() {
        return lastname;
    }

    public Owner lastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public AllAccountCode getAllaccountcode() {
        return allaccountcode;
    }

    public Owner allaccountcode(AllAccountCode allAccountCode) {
        this.allaccountcode = allAccountCode;
        return this;
    }

    public void setAllaccountcode(AllAccountCode allAccountCode) {
        this.allaccountcode = allAccountCode;
    }

    public Set<OwnerAccount> getOwneraccounts() {
        return owneraccounts;
    }

    public Owner owneraccounts(Set<OwnerAccount> ownerAccounts) {
        this.owneraccounts = ownerAccounts;
        return this;
    }

    public Owner addOwneraccount(OwnerAccount ownerAccount) {
        this.owneraccounts.add(ownerAccount);
        ownerAccount.getOwners().add(this);
        return this;
    }

    public Owner removeOwneraccount(OwnerAccount ownerAccount) {
        this.owneraccounts.remove(ownerAccount);
        ownerAccount.getOwners().remove(this);
        return this;
    }

    public void setOwneraccounts(Set<OwnerAccount> ownerAccounts) {
        this.owneraccounts = ownerAccounts;
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
        Owner owner = (Owner) o;
        if (owner.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), owner.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Owner{" +
            "id=" + getId() +
            ", accountcode='" + getAccountcode() + "'" +
            ", flat='" + getFlat() + "'" +
            ", building='" + getBuilding() + "'" +
            ", firstname='" + getFirstname() + "'" +
            ", middlename='" + getMiddlename() + "'" +
            ", lastname='" + getLastname() + "'" +
            "}";
    }
}
