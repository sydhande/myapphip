package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.MainAC;
import io.github.jhipster.application.repository.MainACRepository;
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
 * REST controller for managing MainAC.
 */
@RestController
@RequestMapping("/api")
public class MainACResource {

    private final Logger log = LoggerFactory.getLogger(MainACResource.class);

    private static final String ENTITY_NAME = "mainAC";

    private MainACRepository mainACRepository;

    public MainACResource(MainACRepository mainACRepository) {
        this.mainACRepository = mainACRepository;
    }

    /**
     * POST  /main-acs : Create a new mainAC.
     *
     * @param mainAC the mainAC to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mainAC, or with status 400 (Bad Request) if the mainAC has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/main-acs")
    @Timed
    public ResponseEntity<MainAC> createMainAC(@RequestBody MainAC mainAC) throws URISyntaxException {
        log.debug("REST request to save MainAC : {}", mainAC);
        if (mainAC.getId() != null) {
            throw new BadRequestAlertException("A new mainAC cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MainAC result = mainACRepository.save(mainAC);
        return ResponseEntity.created(new URI("/api/main-acs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /main-acs : Updates an existing mainAC.
     *
     * @param mainAC the mainAC to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mainAC,
     * or with status 400 (Bad Request) if the mainAC is not valid,
     * or with status 500 (Internal Server Error) if the mainAC couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/main-acs")
    @Timed
    public ResponseEntity<MainAC> updateMainAC(@RequestBody MainAC mainAC) throws URISyntaxException {
        log.debug("REST request to update MainAC : {}", mainAC);
        if (mainAC.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MainAC result = mainACRepository.save(mainAC);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mainAC.getId().toString()))
            .body(result);
    }

    /**
     * GET  /main-acs : get all the mainACS.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of mainACS in body
     */
    @GetMapping("/main-acs")
    @Timed
    public List<MainAC> getAllMainACS() {
        log.debug("REST request to get all MainACS");
        return mainACRepository.findAll();
    }

    /**
     * GET  /main-acs/:id : get the "id" mainAC.
     *
     * @param id the id of the mainAC to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mainAC, or with status 404 (Not Found)
     */
    @GetMapping("/main-acs/{id}")
    @Timed
    public ResponseEntity<MainAC> getMainAC(@PathVariable Long id) {
        log.debug("REST request to get MainAC : {}", id);
        Optional<MainAC> mainAC = mainACRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(mainAC);
    }

    /**
     * DELETE  /main-acs/:id : delete the "id" mainAC.
     *
     * @param id the id of the mainAC to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/main-acs/{id}")
    @Timed
    public ResponseEntity<Void> deleteMainAC(@PathVariable Long id) {
        log.debug("REST request to delete MainAC : {}", id);

        mainACRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
