package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.AccountCatagory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AccountCatagory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AccountCatagoryRepository extends JpaRepository<AccountCatagory, Long> {

}
