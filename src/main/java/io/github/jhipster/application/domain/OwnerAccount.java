package io.github.jhipster.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A OwnerAccount.
 */
@Entity
@Table(name = "owner_account")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OwnerAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_type")
    private String accountType;

    @ManyToMany(mappedBy = "owneraccounts")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Owner> owners = new HashSet<>();

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

    public OwnerAccount accountType(String accountType) {
        this.accountType = accountType;
        return this;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Set<Owner> getOwners() {
        return owners;
    }

    public OwnerAccount owners(Set<Owner> owners) {
        this.owners = owners;
        return this;
    }

    public OwnerAccount addOwner(Owner owner) {
        this.owners.add(owner);
        owner.getOwneraccounts().add(this);
        return this;
    }

    public OwnerAccount removeOwner(Owner owner) {
        this.owners.remove(owner);
        owner.getOwneraccounts().remove(this);
        return this;
    }

    public void setOwners(Set<Owner> owners) {
        this.owners = owners;
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
        OwnerAccount ownerAccount = (OwnerAccount) o;
        if (ownerAccount.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ownerAccount.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OwnerAccount{" +
            "id=" + getId() +
            ", accountType='" + getAccountType() + "'" +
            "}";
    }
}
