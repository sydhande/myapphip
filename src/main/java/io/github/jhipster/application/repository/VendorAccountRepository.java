package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.VendorAccount;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the VendorAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VendorAccountRepository extends JpaRepository<VendorAccount, Long> {

}
