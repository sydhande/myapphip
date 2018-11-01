package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.AllAccountCode;
import io.github.jhipster.application.repository.AllAccountCodeRepository;
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
 * REST controller for managing AllAccountCode.
 */
@RestController
@RequestMapping("/api")
public class AllAccountCodeResource {

    private final Logger log = LoggerFactory.getLogger(AllAccountCodeResource.class);

    private static final String ENTITY_NAME = "allAccountCode";

    private AllAccountCodeRepository allAccountCodeRepository;

    public AllAccountCodeResource(AllAccountCodeRepository allAccountCodeRepository) {
        this.allAccountCodeRepository = allAccountCodeRepository;
    }

    /**
     * POST  /all-account-codes : Create a new allAccountCode.
     *
     * @param allAccountCode the allAccountCode to create
     * @return the ResponseEntity with status 201 (Created) and with body the new allAccountCode, or with status 400 (Bad Request) if the allAccountCode has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/all-account-codes")
    @Timed
    public ResponseEntity<AllAccountCode> createAllAccountCode(@RequestBody AllAccountCode allAccountCode) throws URISyntaxException {
        log.debug("REST request to save AllAccountCode : {}", allAccountCode);
        if (allAccountCode.getId() != null) {
            throw new BadRequestAlertException("A new allAccountCode cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AllAccountCode result = allAccountCodeRepository.save(allAccountCode);
        return ResponseEntity.created(new URI("/api/all-account-codes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /all-account-codes : Updates an existing allAccountCode.
     *
     * @param allAccountCode the allAccountCode to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated allAccountCode,
     * or with status 400 (Bad Request) if the allAccountCode is not valid,
     * or with status 500 (Internal Server Error) if the allAccountCode couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/all-account-codes")
    @Timed
    public ResponseEntity<AllAccountCode> updateAllAccountCode(@RequestBody AllAccountCode allAccountCode) throws URISyntaxException {
        log.debug("REST request to update AllAccountCode : {}", allAccountCode);
        if (allAccountCode.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AllAccountCode result = allAccountCodeRepository.save(allAccountCode);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, allAccountCode.getId().toString()))
            .body(result);
    }

    /**
     * GET  /all-account-codes : get all the allAccountCodes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of allAccountCodes in body
     */
    @GetMapping("/all-account-codes")
    @Timed
    public List<AllAccountCode> getAllAllAccountCodes() {
        log.debug("REST request to get all AllAccountCodes");
        return allAccountCodeRepository.findAll();
    }

    /**
     * GET  /all-account-codes/:id : get the "id" allAccountCode.
     *
     * @param id the id of the allAccountCode to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the allAccountCode, or with status 404 (Not Found)
     */
    @GetMapping("/all-account-codes/{id}")
    @Timed
    public ResponseEntity<AllAccountCode> getAllAccountCode(@PathVariable Long id) {
        log.debug("REST request to get AllAccountCode : {}", id);
        Optional<AllAccountCode> allAccountCode = allAccountCodeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(allAccountCode);
    }

    /**
     * DELETE  /all-account-codes/:id : delete the "id" allAccountCode.
     *
     * @param id the id of the allAccountCode to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/all-account-codes/{id}")
    @Timed
    public ResponseEntity<Void> deleteAllAccountCode(@PathVariable Long id) {
        log.debug("REST request to delete AllAccountCode : {}", id);

        allAccountCodeRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
