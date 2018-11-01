package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.BillingRelate;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BillingRelate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BillingRelateRepository extends JpaRepository<BillingRelate, Long> {

}
