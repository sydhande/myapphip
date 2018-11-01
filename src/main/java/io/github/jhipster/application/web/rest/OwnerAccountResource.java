package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.OwnerAccount;
import io.github.jhipster.application.repository.OwnerAccountRepository;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing OwnerAccount.
 */
@RestController
@RequestMapping("/api")
public class OwnerAccountResource {

    private final Logger log = LoggerFactory.getLogger(OwnerAccountResource.class);

    private static final String ENTITY_NAME = "ownerAccount";

    private OwnerAccountRepository ownerAccountRepository;

    public OwnerAccountResource(OwnerAccountRepository ownerAccountRepository) {
        this.ownerAccountRepository = ownerAccountRepository;
    }

    /**
     * POST  /owner-accounts : Create a new ownerAccount.
     *
     * @param ownerAccount the ownerAccount to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ownerAccount, or with status 400 (Bad Request) if the ownerAccount has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/owner-accounts")
    @Timed
    public ResponseEntity<OwnerAccount> createOwnerAccount(@RequestBody OwnerAccount ownerAccount) throws URISyntaxException {
        log.debug("REST request to save OwnerAccount : {}", ownerAccount);
        if (ownerAccount.getId() != null) {
            throw new BadRequestAlertException("A new ownerAccount cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OwnerAccount result = ownerAccountRepository.save(ownerAccount);
        return ResponseEntity.created(new URI("/api/owner-accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /owner-accounts : Updates an existing ownerAccount.
     *
     * @param ownerAccount the ownerAccount to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ownerAccount,
     * or with status 400 (Bad Request) if the ownerAccount is not valid,
     * or with status 500 (Internal Server Error) if the ownerAccount couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/owner-accounts")
    @Timed
    public ResponseEntity<OwnerAccount> updateOwnerAccount(@RequestBody OwnerAccount ownerAccount) throws URISyntaxException {
        log.debug("REST request to update OwnerAccount : {}", ownerAccount);
        if (ownerAccount.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OwnerAccount result = ownerAccountRepository.save(ownerAccount);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ownerAccount.getId().toString()))
            .body(result);
    }

    /**
     * GET  /owner-accounts : get all the ownerAccounts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of ownerAccounts in body
     */
    @GetMapping("/owner-accounts")
    @Timed
    public List<OwnerAccount> getAllOwnerAccounts() {
        log.debug("REST request to get all OwnerAccounts");
        return ownerAccountRepository.findAll();
    }

    /**
     * GET  /owner-accounts/:id : get the "id" ownerAccount.
     *
     * @param id the id of the ownerAccount to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ownerAccount, or with status 404 (Not Found)
     */
    @GetMapping("/owner-accounts/{id}")
    @Timed
    public ResponseEntity<OwnerAccount> getOwnerAccount(@PathVariable Long id) {
        log.debug("REST request to get OwnerAccount : {}", id);
        Optional<OwnerAccount> ownerAccount = ownerAccountRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(ownerAccount);
    }

    /**
     * DELETE  /owner-accounts/:id : delete the "id" ownerAccount.
     *
     * @param id the id of the ownerAccount to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/owner-accounts/{id}")
    @Timed
    public ResponseEntity<Void> deleteOwnerAccount(@PathVariable Long id) {
        log.debug("REST request to delete OwnerAccount : {}", id);

        ownerAccountRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
