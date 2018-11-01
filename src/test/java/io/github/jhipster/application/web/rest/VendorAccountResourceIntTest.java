package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.MyapphipApp;

import io.github.jhipster.application.domain.VendorAccount;
import io.github.jhipster.application.repository.VendorAccountRepository;
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
 * Test class for the VendorAccountResource REST controller.
 *
 * @see VendorAccountResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyapphipApp.class)
public class VendorAccountResourceIntTest {

    private static final String DEFAULT_ACCOUNT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_TYPE = "BBBBBBBBBB";

    @Autowired
    private VendorAccountRepository vendorAccountRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVendorAccountMockMvc;

    private VendorAccount vendorAccount;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VendorAccountResource vendorAccountResource = new VendorAccountResource(vendorAccountRepository);
        this.restVendorAccountMockMvc = MockMvcBuilders.standaloneSetup(vendorAccountResource)
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
    public static VendorAccount createEntity(EntityManager em) {
        VendorAccount vendorAccount = new VendorAccount()
            .accountType(DEFAULT_ACCOUNT_TYPE);
        return vendorAccount;
    }

    @Before
    public void initTest() {
        vendorAccount = createEntity(em);
    }

    @Test
    @Transactional
    public void createVendorAccount() throws Exception {
        int databaseSizeBeforeCreate = vendorAccountRepository.findAll().size();

        // Create the VendorAccount
        restVendorAccountMockMvc.perform(post("/api/vendor-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vendorAccount)))
            .andExpect(status().isCreated());

        // Validate the VendorAccount in the database
        List<VendorAccount> vendorAccountList = vendorAccountRepository.findAll();
        assertThat(vendorAccountList).hasSize(databaseSizeBeforeCreate + 1);
        VendorAccount testVendorAccount = vendorAccountList.get(vendorAccountList.size() - 1);
        assertThat(testVendorAccount.getAccountType()).isEqualTo(DEFAULT_ACCOUNT_TYPE);
    }

    @Test
    @Transactional
    public void createVendorAccountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vendorAccountRepository.findAll().size();

        // Create the VendorAccount with an existing ID
        vendorAccount.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVendorAccountMockMvc.perform(post("/api/vendor-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vendorAccount)))
            .andExpect(status().isBadRequest());

        // Validate the VendorAccount in the database
        List<VendorAccount> vendorAccountList = vendorAccountRepository.findAll();
        assertThat(vendorAccountList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllVendorAccounts() throws Exception {
        // Initialize the database
        vendorAccountRepository.saveAndFlush(vendorAccount);

        // Get all the vendorAccountList
        restVendorAccountMockMvc.perform(get("/api/vendor-accounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vendorAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].accountType").value(hasItem(DEFAULT_ACCOUNT_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getVendorAccount() throws Exception {
        // Initialize the database
        vendorAccountRepository.saveAndFlush(vendorAccount);

        // Get the vendorAccount
        restVendorAccountMockMvc.perform(get("/api/vendor-accounts/{id}", vendorAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vendorAccount.getId().intValue()))
            .andExpect(jsonPath("$.accountType").value(DEFAULT_ACCOUNT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingVendorAccount() throws Exception {
        // Get the vendorAccount
        restVendorAccountMockMvc.perform(get("/api/vendor-accounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVendorAccount() throws Exception {
        // Initialize the database
        vendorAccountRepository.saveAndFlush(vendorAccount);

        int databaseSizeBeforeUpdate = vendorAccountRepository.findAll().size();

        // Update the vendorAccount
        VendorAccount updatedVendorAccount = vendorAccountRepository.findById(vendorAccount.getId()).get();
        // Disconnect from session so that the updates on updatedVendorAccount are not directly saved in db
        em.detach(updatedVendorAccount);
        updatedVendorAccount
            .accountType(UPDATED_ACCOUNT_TYPE);

        restVendorAccountMockMvc.perform(put("/api/vendor-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVendorAccount)))
            .andExpect(status().isOk());

        // Validate the VendorAccount in the database
        List<VendorAccount> vendorAccountList = vendorAccountRepository.findAll();
        assertThat(vendorAccountList).hasSize(databaseSizeBeforeUpdate);
        VendorAccount testVendorAccount = vendorAccountList.get(vendorAccountList.size() - 1);
        assertThat(testVendorAccount.getAccountType()).isEqualTo(UPDATED_ACCOUNT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingVendorAccount() throws Exception {
        int databaseSizeBeforeUpdate = vendorAccountRepository.findAll().size();

        // Create the VendorAccount

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVendorAccountMockMvc.perform(put("/api/vendor-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vendorAccount)))
            .andExpect(status().isBadRequest());

        // Validate the VendorAccount in the database
        List<VendorAccount> vendorAccountList = vendorAccountRepository.findAll();
        assertThat(vendorAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVendorAccount() throws Exception {
        // Initialize the database
        vendorAccountRepository.saveAndFlush(vendorAccount);

        int databaseSizeBeforeDelete = vendorAccountRepository.findAll().size();

        // Get the vendorAccount
        restVendorAccountMockMvc.perform(delete("/api/vendor-accounts/{id}", vendorAccount.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<VendorAccount> vendorAccountList = vendorAccountRepository.findAll();
        assertThat(vendorAccountList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VendorAccount.class);
        VendorAccount vendorAccount1 = new VendorAccount();
        vendorAccount1.setId(1L);
        VendorAccount vendorAccount2 = new VendorAccount();
        vendorAccount2.setId(vendorAccount1.getId());
        assertThat(vendorAccount1).isEqualTo(vendorAccount2);
        vendorAccount2.setId(2L);
        assertThat(vendorAccount1).isNotEqualTo(vendorAccount2);
        vendorAccount1.setId(null);
        assertThat(vendorAccount1).isNotEqualTo(vendorAccount2);
    }
}
