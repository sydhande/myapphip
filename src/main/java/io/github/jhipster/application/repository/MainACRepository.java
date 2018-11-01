package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.MainAC;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MainAC entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MainACRepository extends JpaRepository<MainAC, Long> {

}
