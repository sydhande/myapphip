package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.GeneralAC;
import io.github.jhipster.application.repository.GeneralACRepository;
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
 * REST controller for managing GeneralAC.
 */
@RestController
@RequestMapping("/api")
public class GeneralACResource {

    private final Logger log = LoggerFactory.getLogger(GeneralACResource.class);

    private static final String ENTITY_NAME = "generalAC";

    private GeneralACRepository generalACRepository;

    public GeneralACResource(GeneralACRepository generalACRepository) {
        this.generalACRepository = generalACRepository;
    }

    /**
     * POST  /general-acs : Create a new generalAC.
     *
     * @param generalAC the generalAC to create
     * @return the ResponseEntity with status 201 (Created) and with body the new generalAC, or with status 400 (Bad Request) if the generalAC has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/general-acs")
    @Timed
    public ResponseEntity<GeneralAC> createGeneralAC(@RequestBody GeneralAC generalAC) throws URISyntaxException {
        log.debug("REST request to save GeneralAC : {}", generalAC);
        if (generalAC.getId() != null) {
            throw new BadRequestAlertException("A new generalAC cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GeneralAC result = generalACRepository.save(generalAC);
        return ResponseEntity.created(new URI("/api/general-acs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /general-acs : Updates an existing generalAC.
     *
     * @param generalAC the generalAC to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated generalAC,
     * or with status 400 (Bad Request) if the generalAC is not valid,
     * or with status 500 (Internal Server Error) if the generalAC couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/general-acs")
    @Timed
    public ResponseEntity<GeneralAC> updateGeneralAC(@RequestBody GeneralAC generalAC) throws URISyntaxException {
        log.debug("REST request to update GeneralAC : {}", generalAC);
        if (generalAC.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GeneralAC result = generalACRepository.save(generalAC);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, generalAC.getId().toString()))
            .body(result);
    }

    /**
     * GET  /general-acs : get all the generalACS.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of generalACS in body
     */
    @GetMapping("/general-acs")
    @Timed
    public List<GeneralAC> getAllGeneralACS() {
        log.debug("REST request to get all GeneralACS");
        return generalACRepository.findAll();
    }

    /**
     * GET  /general-acs/:id : get the "id" generalAC.
     *
     * @param id the id of the generalAC to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the generalAC, or with status 404 (Not Found)
     */
    @GetMapping("/general-acs/{id}")
    @Timed
    public ResponseEntity<GeneralAC> getGeneralAC(@PathVariable Long id) {
        log.debug("REST request to get GeneralAC : {}", id);
        Optional<GeneralAC> generalAC = generalACRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(generalAC);
    }

    /**
     * DELETE  /general-acs/:id : delete the "id" generalAC.
     *
     * @param id the id of the generalAC to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/general-acs/{id}")
    @Timed
    public ResponseEntity<Void> deleteGeneralAC(@PathVariable Long id) {
        log.debug("REST request to delete GeneralAC : {}", id);

        generalACRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
