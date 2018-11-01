package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.MyapphipApp;

import io.github.jhipster.application.domain.OwnerAccount;
import io.github.jhipster.application.repository.OwnerAccountRepository;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the OwnerAccountResource REST controller.
 *
 * @see OwnerAccountResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyapphipApp.class)
public class OwnerAccountResourceIntTest {

    private static final String DEFAULT_ACCOUNT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_TYPE = "BBBBBBBBBB";

    @Autowired
    private OwnerAccountRepository ownerAccountRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOwnerAccountMockMvc;

    private OwnerAccount ownerAccount;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OwnerAccountResource ownerAccountResource = new OwnerAccountResource(ownerAccountRepository);
        this.restOwnerAccountMockMvc = MockMvcBuilders.standaloneSetup(ownerAccountResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OwnerAccount createEntity(EntityManager em) {
        OwnerAccount ownerAccount = new OwnerAccount()
            .accountType(DEFAULT_ACCOUNT_TYPE);
        return ownerAccount;
    }

    @Before
    public void initTest() {
        ownerAccount = createEntity(em);
    }

    @Test
    @Transactional
    public void createOwnerAccount() throws Exception {
        int databaseSizeBeforeCreate = ownerAccountRepository.findAll().size();

        // Create the OwnerAccount
        restOwnerAccountMockMvc.perform(post("/api/owner-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ownerAccount)))
            .andExpect(status().isCreated());

        // Validate the OwnerAccount in the database
        List<OwnerAccount> ownerAccountList = ownerAccountRepository.findAll();
        assertThat(ownerAccountList).hasSize(databaseSizeBeforeCreate + 1);
        OwnerAccount testOwnerAccount = ownerAccountList.get(ownerAccountList.size() - 1);
        assertThat(testOwnerAccount.getAccountType()).isEqualTo(DEFAULT_ACCOUNT_TYPE);
    }

    @Test
    @Transactional
    public void createOwnerAccountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ownerAccountRepository.findAll().size();

        // Create the OwnerAccount with an existing ID
        ownerAccount.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOwnerAccountMockMvc.perform(post("/api/owner-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ownerAccount)))
            .andExpect(status().isBadRequest());

        // Validate the OwnerAccount in the database
        List<OwnerAccount> ownerAccountList = ownerAccountRepository.findAll();
        assertThat(ownerAccountList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOwnerAccounts() throws Exception {
        // Initialize the database
        ownerAccountRepository.saveAndFlush(ownerAccount);

        // Get all the ownerAccountList
        restOwnerAccountMockMvc.perform(get("/api/owner-accounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ownerAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].accountType").value(hasItem(DEFAULT_ACCOUNT_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getOwnerAccount() throws Exception {
        // Initialize the database
        ownerAccountRepository.saveAndFlush(ownerAccount);

        // Get the ownerAccount
        restOwnerAccountMockMvc.perform(get("/api/owner-accounts/{id}", ownerAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ownerAccount.getId().intValue()))
            .andExpect(jsonPath("$.accountType").value(DEFAULT_ACCOUNT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOwnerAccount() throws Exception {
        // Get the ownerAccount
        restOwnerAccountMockMvc.perform(get("/api/owner-accounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOwnerAccount() throws Exception {
        // Initialize the database
        ownerAccountRepository.saveAndFlush(ownerAccount);

        int databaseSizeBeforeUpdate = ownerAccountRepository.findAll().size();

        // Update the ownerAccount
        OwnerAccount updatedOwnerAccount = ownerAccountRepository.findById(ownerAccount.getId()).get();
        // Disconnect from session so that the updates on updatedOwnerAccount are not directly saved in db
        em.detach(updatedOwnerAccount);
        updatedOwnerAccount
            .accountType(UPDATED_ACCOUNT_TYPE);

        restOwnerAccountMockMvc.perform(put("/api/owner-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOwnerAccount)))
            .andExpect(status().isOk());

        // Validate the OwnerAccount in the database
        List<OwnerAccount> ownerAccountList = ownerAccountRepository.findAll();
        assertThat(ownerAccountList).hasSize(databaseSizeBeforeUpdate);
        OwnerAccount testOwnerAccount = ownerAccountList.get(ownerAccountList.size() - 1);
        assertThat(testOwnerAccount.getAccountType()).isEqualTo(UPDATED_ACCOUNT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingOwnerAccount() throws Exception {
        int databaseSizeBeforeUpdate = ownerAccountRepository.findAll().size();

        // Create the OwnerAccount

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOwnerAccountMockMvc.perform(put("/api/owner-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ownerAccount)))
            .andExpect(status().isBadRequest());

        // Validate the OwnerAccount in the database
        List<OwnerAccount> ownerAccountList = ownerAccountRepository.findAll();
        assertThat(ownerAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOwnerAccount() throws Exception {
        // Initialize the database
        ownerAccountRepository.saveAndFlush(ownerAccount);

        int databaseSizeBeforeDelete = ownerAccountRepository.findAll().size();

        // Get the ownerAccount
        restOwnerAccountMockMvc.perform(delete("/api/owner-accounts/{id}", ownerAccount.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<OwnerAccount> ownerAccountList = ownerAccountRepository.findAll();
        assertThat(ownerAccountList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OwnerAccount.class);
        OwnerAccount ownerAccount1 = new OwnerAccount();
        ownerAccount1.setId(1L);
        OwnerAccount ownerAccount2 = new OwnerAccount();
        ownerAccount2.setId(ownerAccount1.getId());
        assertThat(ownerAccount1).isEqualTo(ownerAccount2);
        ownerAccount2.setId(2L);
        assertThat(ownerAccount1).isNotEqualTo(ownerAccount2);
        ownerAccount1.setId(null);
        assertThat(ownerAccount1).isNotEqualTo(ownerAccount2);
    }
}
