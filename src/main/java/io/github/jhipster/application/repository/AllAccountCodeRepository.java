package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.AllAccountCode;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AllAccountCode entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AllAccountCodeRepository extends JpaRepository<AllAccountCode, Long> {

}
