package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.OwnerAccount;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the OwnerAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OwnerAccountRepository extends JpaRepository<OwnerAccount, Long> {

}
