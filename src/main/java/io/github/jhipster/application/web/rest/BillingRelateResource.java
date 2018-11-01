package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.BillingRelate;
import io.github.jhipster.application.repository.BillingRelateRepository;
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
 * REST controller for managing BillingRelate.
 */
@RestController
@RequestMapping("/api")
public class BillingRelateResource {

    private final Logger log = LoggerFactory.getLogger(BillingRelateResource.class);

    private static final String ENTITY_NAME = "billingRelate";

    private BillingRelateRepository billingRelateRepository;

    public BillingRelateResource(BillingRelateRepository billingRelateRepository) {
        this.billingRelateRepository = billingRelateRepository;
    }

    /**
     * POST  /billing-relates : Create a new billingRelate.
     *
     * @param billingRelate the billingRelate to create
     * @return the ResponseEntity with status 201 (Created) and with body the new billingRelate, or with status 400 (Bad Request) if the billingRelate has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/billing-relates")
    @Timed
    public ResponseEntity<BillingRelate> createBillingRelate(@RequestBody BillingRelate billingRelate) throws URISyntaxException {
        log.debug("REST request to save BillingRelate : {}", billingRelate);
        if (billingRelate.getId() != null) {
            throw new BadRequestAlertException("A new billingRelate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BillingRelate result = billingRelateRepository.save(billingRelate);
        return ResponseEntity.created(new URI("/api/billing-relates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /billing-relates : Updates an existing billingRelate.
     *
     * @param billingRelate the billingRelate to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated billingRelate,
     * or with status 400 (Bad Request) if the billingRelate is not valid,
     * or with status 500 (Internal Server Error) if the billingRelate couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/billing-relates")
    @Timed
    public ResponseEntity<BillingRelate> updateBillingRelate(@RequestBody BillingRelate billingRelate) throws URISyntaxException {
        log.debug("REST request to update BillingRelate : {}", billingRelate);
        if (billingRelate.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BillingRelate result = billingRelateRepository.save(billingRelate);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, billingRelate.getId().toString()))
            .body(result);
    }

    /**
     * GET  /billing-relates : get all the billingRelates.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of billingRelates in body
     */
    @GetMapping("/billing-relates")
    @Timed
    public List<BillingRelate> getAllBillingRelates() {
        log.debug("REST request to get all BillingRelates");
        return billingRelateRepository.findAll();
    }

    /**
     * GET  /billing-relates/:id : get the "id" billingRelate.
     *
     * @param id the id of the billingRelate to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the billingRelate, or with status 404 (Not Found)
     */
    @GetMapping("/billing-relates/{id}")
    @Timed
    public ResponseEntity<BillingRelate> getBillingRelate(@PathVariable Long id) {
        log.debug("REST request to get BillingRelate : {}", id);
        Optional<BillingRelate> billingRelate = billingRelateRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(billingRelate);
    }

    /**
     * DELETE  /billing-relates/:id : delete the "id" billingRelate.
     *
     * @param id the id of the billingRelate to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/billing-relates/{id}")
    @Timed
    public ResponseEntity<Void> deleteBillingRelate(@PathVariable Long id) {
        log.debug("REST request to delete BillingRelate : {}", id);

        billingRelateRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
