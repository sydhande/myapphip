package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.VendorAccount;
import io.github.jhipster.application.repository.VendorAccountRepository;
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
 * REST controller for managing VendorAccount.
 */
@RestController
@RequestMapping("/api")
public class VendorAccountResource {

    private final Logger log = LoggerFactory.getLogger(VendorAccountResource.class);

    private static final String ENTITY_NAME = "vendorAccount";

    private VendorAccountRepository vendorAccountRepository;

    public VendorAccountResource(VendorAccountRepository vendorAccountRepository) {
        this.vendorAccountRepository = vendorAccountRepository;
    }

    /**
     * POST  /vendor-accounts : Create a new vendorAccount.
     *
     * @param vendorAccount the vendorAccount to create
     * @return the ResponseEntity with status 201 (Created) and with body the new vendorAccount, or with status 400 (Bad Request) if the vendorAccount has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/vendor-accounts")
    @Timed
    public ResponseEntity<VendorAccount> createVendorAccount(@RequestBody VendorAccount vendorAccount) throws URISyntaxException {
        log.debug("REST request to save VendorAccount : {}", vendorAccount);
        if (vendorAccount.getId() != null) {
            throw new BadRequestAlertException("A new vendorAccount cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VendorAccount result = vendorAccountRepository.save(vendorAccount);
        return ResponseEntity.created(new URI("/api/vendor-accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /vendor-accounts : Updates an existing vendorAccount.
     *
     * @param vendorAccount the vendorAccount to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated vendorAccount,
     * or with status 400 (Bad Request) if the vendorAccount is not valid,
     * or with status 500 (Internal Server Error) if the vendorAccount couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/vendor-accounts")
    @Timed
    public ResponseEntity<VendorAccount> updateVendorAccount(@RequestBody VendorAccount vendorAccount) throws URISyntaxException {
        log.debug("REST request to update VendorAccount : {}", vendorAccount);
        if (vendorAccount.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VendorAccount result = vendorAccountRepository.save(vendorAccount);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, vendorAccount.getId().toString()))
            .body(result);
    }

    /**
     * GET  /vendor-accounts : get all the vendorAccounts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of vendorAccounts in body
     */
    @GetMapping("/vendor-accounts")
    @Timed
    public List<VendorAccount> getAllVendorAccounts() {
        log.debug("REST request to get all VendorAccounts");
        return vendorAccountRepository.findAll();
    }

    /**
     * GET  /vendor-accounts/:id : get the "id" vendorAccount.
     *
     * @param id the id of the vendorAccount to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the vendorAccount, or with status 404 (Not Found)
     */
    @GetMapping("/vendor-accounts/{id}")
    @Timed
    public ResponseEntity<VendorAccount> getVendorAccount(@PathVariable Long id) {
        log.debug("REST request to get VendorAccount : {}", id);
        Optional<VendorAccount> vendorAccount = vendorAccountRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(vendorAccount);
    }

    /**
     * DELETE  /vendor-accounts/:id : delete the "id" vendorAccount.
     *
     * @param id the id of the vendorAccount to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/vendor-accounts/{id}")
    @Timed
    public ResponseEntity<Void> deleteVendorAccount(@PathVariable Long id) {
        log.debug("REST request to delete VendorAccount : {}", id);

        vendorAccountRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
