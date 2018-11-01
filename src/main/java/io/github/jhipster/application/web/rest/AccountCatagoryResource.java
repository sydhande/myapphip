package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.AccountCatagory;
import io.github.jhipster.application.repository.AccountCatagoryRepository;
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
 * REST controller for managing AccountCatagory.
 */
@RestController
@RequestMapping("/api")
public class AccountCatagoryResource {

    private final Logger log = LoggerFactory.getLogger(AccountCatagoryResource.class);

    private static final String ENTITY_NAME = "accountCatagory";

    private AccountCatagoryRepository accountCatagoryRepository;

    public AccountCatagoryResource(AccountCatagoryRepository accountCatagoryRepository) {
        this.accountCatagoryRepository = accountCatagoryRepository;
    }

    /**
     * POST  /account-catagories : Create a new accountCatagory.
     *
     * @param accountCatagory the accountCatagory to create
     * @return the ResponseEntity with status 201 (Created) and with body the new accountCatagory, or with status 400 (Bad Request) if the accountCatagory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/account-catagories")
    @Timed
    public ResponseEntity<AccountCatagory> createAccountCatagory(@RequestBody AccountCatagory accountCatagory) throws URISyntaxException {
        log.debug("REST request to save AccountCatagory : {}", accountCatagory);
        if (accountCatagory.getId() != null) {
            throw new BadRequestAlertException("A new accountCatagory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AccountCatagory result = accountCatagoryRepository.save(accountCatagory);
        return ResponseEntity.created(new URI("/api/account-catagories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /account-catagories : Updates an existing accountCatagory.
     *
     * @param accountCatagory the accountCatagory to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated accountCatagory,
     * or with status 400 (Bad Request) if the accountCatagory is not valid,
     * or with status 500 (Internal Server Error) if the accountCatagory couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/account-catagories")
    @Timed
    public ResponseEntity<AccountCatagory> updateAccountCatagory(@RequestBody AccountCatagory accountCatagory) throws URISyntaxException {
        log.debug("REST request to update AccountCatagory : {}", accountCatagory);
        if (accountCatagory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AccountCatagory result = accountCatagoryRepository.save(accountCatagory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, accountCatagory.getId().toString()))
            .body(result);
    }

    /**
     * GET  /account-catagories : get all the accountCatagories.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of accountCatagories in body
     */
    @GetMapping("/account-catagories")
    @Timed
    public List<AccountCatagory> getAllAccountCatagories() {
        log.debug("REST request to get all AccountCatagories");
        return accountCatagoryRepository.findAll();
    }

    /**
     * GET  /account-catagories/:id : get the "id" accountCatagory.
     *
     * @param id the id of the accountCatagory to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the accountCatagory, or with status 404 (Not Found)
     */
    @GetMapping("/account-catagories/{id}")
    @Timed
    public ResponseEntity<AccountCatagory> getAccountCatagory(@PathVariable Long id) {
        log.debug("REST request to get AccountCatagory : {}", id);
        Optional<AccountCatagory> accountCatagory = accountCatagoryRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(accountCatagory);
    }

    /**
     * DELETE  /account-catagories/:id : delete the "id" accountCatagory.
     *
     * @param id the id of the accountCatagory to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/account-catagories/{id}")
    @Timed
    public ResponseEntity<Void> deleteAccountCatagory(@PathVariable Long id) {
        log.debug("REST request to delete AccountCatagory : {}", id);

        accountCatagoryRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
