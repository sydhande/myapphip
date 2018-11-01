package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.BillingAC;
import io.github.jhipster.application.repository.BillingACRepository;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing BillingAC.
 */
@RestController
@RequestMapping("/api")
public class BillingACResource {

    private final Logger log = LoggerFactory.getLogger(BillingACResource.class);

    private static final String ENTITY_NAME = "billingAC";

    private BillingACRepository billingACRepository;

    public BillingACResource(BillingACRepository billingACRepository) {
        this.billingACRepository = billingACRepository;
    }

    /**
     * POST  /billing-acs : Create a new billingAC.
     *
     * @param billingAC the billingAC to create
     * @return the ResponseEntity with status 201 (Created) and with body the new billingAC, or with status 400 (Bad Request) if the billingAC has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/billing-acs")
    @Timed
    public ResponseEntity<BillingAC> createBillingAC(@Valid @RequestBody BillingAC billingAC) throws URISyntaxException {
        log.debug("REST request to save BillingAC : {}", billingAC);
        if (billingAC.getId() != null) {
            throw new BadRequestAlertException("A new billingAC cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BillingAC result = billingACRepository.save(billingAC);
        return ResponseEntity.created(new URI("/api/billing-acs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /billing-acs : Updates an existing billingAC.
     *
     * @param billingAC the billingAC to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated billingAC,
     * or with status 400 (Bad Request) if the billingAC is not valid,
     * or with status 500 (Internal Server Error) if the billingAC couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/billing-acs")
    @Timed
    public ResponseEntity<BillingAC> updateBillingAC(@Valid @RequestBody BillingAC billingAC) throws URISyntaxException {
        log.debug("REST request to update BillingAC : {}", billingAC);
        if (billingAC.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BillingAC result = billingACRepository.save(billingAC);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, billingAC.getId().toString()))
            .body(result);
    }

    /**
     * GET  /billing-acs : get all the billingACS.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of billingACS in body
     */
    @GetMapping("/billing-acs")
    @Timed
    public List<BillingAC> getAllBillingACS() {
        log.debug("REST request to get all BillingACS");
        return billingACRepository.findAll();
    }

    /**
     * GET  /billing-acs/:id : get the "id" billingAC.
     *
     * @param id the id of the billingAC to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the billingAC, or with status 404 (Not Found)
     */
    @GetMapping("/billing-acs/{id}")
    @Timed
    public ResponseEntity<BillingAC> getBillingAC(@PathVariable Long id) {
        log.debug("REST request to get BillingAC : {}", id);
        Optional<BillingAC> billingAC = billingACRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(billingAC);
    }

    /**
     * DELETE  /billing-acs/:id : delete the "id" billingAC.
     *
     * @param id the id of the billingAC to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/billing-acs/{id}")
    @Timed
    public ResponseEntity<Void> deleteBillingAC(@PathVariable Long id) {
        log.debug("REST request to delete BillingAC : {}", id);

        billingACRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
