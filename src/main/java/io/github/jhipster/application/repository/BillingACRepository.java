package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.BillingAC;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BillingAC entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BillingACRepository extends JpaRepository<BillingAC, Long> {

}
