package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.PrimeAC;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PrimeAC entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrimeACRepository extends JpaRepository<PrimeAC, Long> {

}
