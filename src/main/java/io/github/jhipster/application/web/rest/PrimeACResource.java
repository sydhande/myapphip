package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.PrimeAC;
import io.github.jhipster.application.repository.PrimeACRepository;
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
 * REST controller for managing PrimeAC.
 */
@RestController
@RequestMapping("/api")
public class PrimeACResource {

    private final Logger log = LoggerFactory.getLogger(PrimeACResource.class);

    private static final String ENTITY_NAME = "primeAC";

    private PrimeACRepository primeACRepository;

    public PrimeACResource(PrimeACRepository primeACRepository) {
        this.primeACRepository = primeACRepository;
    }

    /**
     * POST  /prime-acs : Create a new primeAC.
     *
     * @param primeAC the primeAC to create
     * @return the ResponseEntity with status 201 (Created) and with body the new primeAC, or with status 400 (Bad Request) if the primeAC has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/prime-acs")
    @Timed
    public ResponseEntity<PrimeAC> createPrimeAC(@RequestBody PrimeAC primeAC) throws URISyntaxException {
        log.debug("REST request to save PrimeAC : {}", primeAC);
        if (primeAC.getId() != null) {
            throw new BadRequestAlertException("A new primeAC cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PrimeAC result = primeACRepository.save(primeAC);
        return ResponseEntity.created(new URI("/api/prime-acs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /prime-acs : Updates an existing primeAC.
     *
     * @param primeAC the primeAC to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated primeAC,
     * or with status 400 (Bad Request) if the primeAC is not valid,
     * or with status 500 (Internal Server Error) if the primeAC couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/prime-acs")
    @Timed
    public ResponseEntity<PrimeAC> updatePrimeAC(@RequestBody PrimeAC primeAC) throws URISyntaxException {
        log.debug("REST request to update PrimeAC : {}", primeAC);
        if (primeAC.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PrimeAC result = primeACRepository.save(primeAC);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, primeAC.getId().toString()))
            .body(result);
    }

    /**
     * GET  /prime-acs : get all the primeACS.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of primeACS in body
     */
    @GetMapping("/prime-acs")
    @Timed
    public List<PrimeAC> getAllPrimeACS() {
        log.debug("REST request to get all PrimeACS");
        return primeACRepository.findAll();
    }

    /**
     * GET  /prime-acs/:id : get the "id" primeAC.
     *
     * @param id the id of the primeAC to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the primeAC, or with status 404 (Not Found)
     */
    @GetMapping("/prime-acs/{id}")
    @Timed
    public ResponseEntity<PrimeAC> getPrimeAC(@PathVariable Long id) {
        log.debug("REST request to get PrimeAC : {}", id);
        Optional<PrimeAC> primeAC = primeACRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(primeAC);
    }

    /**
     * DELETE  /prime-acs/:id : delete the "id" primeAC.
     *
     * @param id the id of the primeAC to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/prime-acs/{id}")
    @Timed
    public ResponseEntity<Void> deletePrimeAC(@PathVariable Long id) {
        log.debug("REST request to delete PrimeAC : {}", id);

        primeACRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
