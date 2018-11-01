package io.github.jhipster.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A GeneralAC.
 */
@Entity
@Table(name = "general_ac")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class GeneralAC implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_name")
    private String accountName;

    @Column(name = "account_type")
    private String accountType;

    @Column(name = "transction_type")
    private String transctionType;

    @ManyToOne
    @JsonIgnoreProperties("generalacs")
    private PrimeAC primeAC;

    @OneToMany(mappedBy = "generalAC")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<MainAC> mainacs = new HashSet<>();
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

    public GeneralAC accountName(String accountName) {
        this.accountName = accountName;
        return this;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountType() {
        return accountType;
    }

    public GeneralAC accountType(String accountType) {
        this.accountType = accountType;
        return this;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getTransctionType() {
        return transctionType;
    }

    public GeneralAC transctionType(String transctionType) {
        this.transctionType = transctionType;
        return this;
    }

    public void setTransctionType(String transctionType) {
        this.transctionType = transctionType;
    }

    public PrimeAC getPrimeAC() {
        return primeAC;
    }

    public GeneralAC primeAC(PrimeAC primeAC) {
        this.primeAC = primeAC;
        return this;
    }

    public void setPrimeAC(PrimeAC primeAC) {
        this.primeAC = primeAC;
    }

    public Set<MainAC> getMainacs() {
        return mainacs;
    }

    public GeneralAC mainacs(Set<MainAC> mainACS) {
        this.mainacs = mainACS;
        return this;
    }

    public GeneralAC addMainac(MainAC mainAC) {
        this.mainacs.add(mainAC);
        mainAC.setGeneralAC(this);
        return this;
    }

    public GeneralAC removeMainac(MainAC mainAC) {
        this.mainacs.remove(mainAC);
        mainAC.setGeneralAC(null);
        return this;
    }

    public void setMainacs(Set<MainAC> mainACS) {
        this.mainacs = mainACS;
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
        GeneralAC generalAC = (GeneralAC) o;
        if (generalAC.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), generalAC.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GeneralAC{" +
            "id=" + getId() +
            ", accountName='" + getAccountName() + "'" +
            ", accountType='" + getAccountType() + "'" +
            ", transctionType='" + getTransctionType() + "'" +
            "}";
    }
}
