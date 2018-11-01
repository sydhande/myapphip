package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.MyapphipApp;

import io.github.jhipster.application.domain.BillingAC;
import io.github.jhipster.application.repository.BillingACRepository;
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
 * Test class for the BillingACResource REST controller.
 *
 * @see BillingACResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyapphipApp.class)
public class BillingACResourceIntTest {

    private static final String DEFAULT_ACCOUNT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_CODE = "BBBBBBBBBB";

    @Autowired
    private BillingACRepository billingACRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBillingACMockMvc;

    private BillingAC billingAC;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BillingACResource billingACResource = new BillingACResource(billingACRepository);
        this.restBillingACMockMvc = MockMvcBuilders.standaloneSetup(billingACResource)
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
    public static BillingAC createEntity(EntityManager em) {
        BillingAC billingAC = new BillingAC()
            .accountName(DEFAULT_ACCOUNT_NAME)
            .accountCode(DEFAULT_ACCOUNT_CODE);
        return billingAC;
    }

    @Before
    public void initTest() {
        billingAC = createEntity(em);
    }

    @Test
    @Transactional
    public void createBillingAC() throws Exception {
        int databaseSizeBeforeCreate = billingACRepository.findAll().size();

        // Create the BillingAC
        restBillingACMockMvc.perform(post("/api/billing-acs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billingAC)))
            .andExpect(status().isCreated());

        // Validate the BillingAC in the database
        List<BillingAC> billingACList = billingACRepository.findAll();
        assertThat(billingACList).hasSize(databaseSizeBeforeCreate + 1);
        BillingAC testBillingAC = billingACList.get(billingACList.size() - 1);
        assertThat(testBillingAC.getAccountName()).isEqualTo(DEFAULT_ACCOUNT_NAME);
        assertThat(testBillingAC.getAccountCode()).isEqualTo(DEFAULT_ACCOUNT_CODE);
    }

    @Test
    @Transactional
    public void createBillingACWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = billingACRepository.findAll().size();

        // Create the BillingAC with an existing ID
        billingAC.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBillingACMockMvc.perform(post("/api/billing-acs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billingAC)))
            .andExpect(status().isBadRequest());

        // Validate the BillingAC in the database
        List<BillingAC> billingACList = billingACRepository.findAll();
        assertThat(billingACList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAccountNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = billingACRepository.findAll().size();
        // set the field null
        billingAC.setAccountName(null);

        // Create the BillingAC, which fails.

        restBillingACMockMvc.perform(post("/api/billing-acs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billingAC)))
            .andExpect(status().isBadRequest());

        List<BillingAC> billingACList = billingACRepository.findAll();
        assertThat(billingACList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBillingACS() throws Exception {
        // Initialize the database
        billingACRepository.saveAndFlush(billingAC);

        // Get all the billingACList
        restBillingACMockMvc.perform(get("/api/billing-acs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(billingAC.getId().intValue())))
            .andExpect(jsonPath("$.[*].accountName").value(hasItem(DEFAULT_ACCOUNT_NAME.toString())))
            .andExpect(jsonPath("$.[*].accountCode").value(hasItem(DEFAULT_ACCOUNT_CODE.toString())));
    }
    
    @Test
    @Transactional
    public void getBillingAC() throws Exception {
        // Initialize the database
        billingACRepository.saveAndFlush(billingAC);

        // Get the billingAC
        restBillingACMockMvc.perform(get("/api/billing-acs/{id}", billingAC.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(billingAC.getId().intValue()))
            .andExpect(jsonPath("$.accountName").value(DEFAULT_ACCOUNT_NAME.toString()))
            .andExpect(jsonPath("$.accountCode").value(DEFAULT_ACCOUNT_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBillingAC() throws Exception {
        // Get the billingAC
        restBillingACMockMvc.perform(get("/api/billing-acs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBillingAC() throws Exception {
        // Initialize the database
        billingACRepository.saveAndFlush(billingAC);

        int databaseSizeBeforeUpdate = billingACRepository.findAll().size();

        // Update the billingAC
        BillingAC updatedBillingAC = billingACRepository.findById(billingAC.getId()).get();
        // Disconnect from session so that the updates on updatedBillingAC are not directly saved in db
        em.detach(updatedBillingAC);
        updatedBillingAC
            .accountName(UPDATED_ACCOUNT_NAME)
            .accountCode(UPDATED_ACCOUNT_CODE);

        restBillingACMockMvc.perform(put("/api/billing-acs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBillingAC)))
            .andExpect(status().isOk());

        // Validate the BillingAC in the database
        List<BillingAC> billingACList = billingACRepository.findAll();
        assertThat(billingACList).hasSize(databaseSizeBeforeUpdate);
        BillingAC testBillingAC = billingACList.get(billingACList.size() - 1);
        assertThat(testBillingAC.getAccountName()).isEqualTo(UPDATED_ACCOUNT_NAME);
        assertThat(testBillingAC.getAccountCode()).isEqualTo(UPDATED_ACCOUNT_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingBillingAC() throws Exception {
        int databaseSizeBeforeUpdate = billingACRepository.findAll().size();

        // Create the BillingAC

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBillingACMockMvc.perform(put("/api/billing-acs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billingAC)))
            .andExpect(status().isBadRequest());

        // Validate the BillingAC in the database
        List<BillingAC> billingACList = billingACRepository.findAll();
        assertThat(billingACList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBillingAC() throws Exception {
        // Initialize the database
        billingACRepository.saveAndFlush(billingAC);

        int databaseSizeBeforeDelete = billingACRepository.findAll().size();

        // Get the billingAC
        restBillingACMockMvc.perform(delete("/api/billing-acs/{id}", billingAC.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BillingAC> billingACList = billingACRepository.findAll();
        assertThat(billingACList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BillingAC.class);
        BillingAC billingAC1 = new BillingAC();
        billingAC1.setId(1L);
        BillingAC billingAC2 = new BillingAC();
        billingAC2.setId(billingAC1.getId());
        assertThat(billingAC1).isEqualTo(billingAC2);
        billingAC2.setId(2L);
        assertThat(billingAC1).isNotEqualTo(billingAC2);
        billingAC1.setId(null);
        assertThat(billingAC1).isNotEqualTo(billingAC2);
    }
}
