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
 * A PrimeAC.
 */
@Entity
@Table(name = "prime_ac")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PrimeAC implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_name")
    private String accountName;

    @Column(name = "account_type")
    private String accountType;

    @OneToMany(mappedBy = "primeAC")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<GeneralAC> generalacs = new HashSet<>();
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

    public PrimeAC accountName(String accountName) {
        this.accountName = accountName;
        return this;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountType() {
        return accountType;
    }

    public PrimeAC accountType(String accountType) {
        this.accountType = accountType;
        return this;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Set<GeneralAC> getGeneralacs() {
        return generalacs;
    }

    public PrimeAC generalacs(Set<GeneralAC> generalACS) {
        this.generalacs = generalACS;
        return this;
    }

    public PrimeAC addGeneralac(GeneralAC generalAC) {
        this.generalacs.add(generalAC);
        generalAC.setPrimeAC(this);
        return this;
    }

    public PrimeAC removeGeneralac(GeneralAC generalAC) {
        this.generalacs.remove(generalAC);
        generalAC.setPrimeAC(null);
        return this;
    }

    public void setGeneralacs(Set<GeneralAC> generalACS) {
        this.generalacs = generalACS;
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
        PrimeAC primeAC = (PrimeAC) o;
        if (primeAC.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), primeAC.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PrimeAC{" +
            "id=" + getId() +
            ", accountName='" + getAccountName() + "'" +
            ", accountType='" + getAccountType() + "'" +
            "}";
    }
}
