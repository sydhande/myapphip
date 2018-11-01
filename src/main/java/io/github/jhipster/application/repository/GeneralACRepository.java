package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.GeneralAC;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the GeneralAC entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GeneralACRepository extends JpaRepository<GeneralAC, Long> {

}
