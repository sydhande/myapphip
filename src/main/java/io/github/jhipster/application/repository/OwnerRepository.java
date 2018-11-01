package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Owner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Owner entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {

    @Query(value = "select distinct owner from Owner owner left join fetch owner.owneraccounts",
        countQuery = "select count(distinct owner) from Owner owner")
    Page<Owner> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct owner from Owner owner left join fetch owner.owneraccounts")
    List<Owner> findAllWithEagerRelationships();

    @Query("select owner from Owner owner left join fetch owner.owneraccounts where owner.id =:id")
    Optional<Owner> findOneWithEagerRelationships(@Param("id") Long id);

}
