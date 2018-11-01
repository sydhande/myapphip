package io.github.jhipster.application.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A AllAccountCode.
 */
@Entity
@Table(name = "all_account_code")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AllAccountCode implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "accountcode")
    private String accountcode;

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

    public AllAccountCode accountcode(String accountcode) {
        this.accountcode = accountcode;
        return this;
    }

    public void setAccountcode(String accountcode) {
        this.accountcode = accountcode;
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
        AllAccountCode allAccountCode = (AllAccountCode) o;
        if (allAccountCode.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), allAccountCode.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AllAccountCode{" +
            "id=" + getId() +
            ", accountcode='" + getAccountcode() + "'" +
            "}";
    }
}
