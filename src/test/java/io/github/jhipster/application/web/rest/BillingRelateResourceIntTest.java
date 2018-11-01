package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.MyapphipApp;

import io.github.jhipster.application.domain.BillingRelate;
import io.github.jhipster.application.repository.BillingRelateRepository;
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
 * Test class for the BillingRelateResource REST controller.
 *
 * @see BillingRelateResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyapphipApp.class)
public class BillingRelateResourceIntTest {

    private static final Long DEFAULT_DRACCOUNT = 1L;
    private static final Long UPDATED_DRACCOUNT = 2L;

    private static final Long DEFAULT_CRACCOUNT = 1L;
    private static final Long UPDATED_CRACCOUNT = 2L;

    @Autowired
    private BillingRelateRepository billingRelateRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBillingRelateMockMvc;

    private BillingRelate billingRelate;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BillingRelateResource billingRelateResource = new BillingRelateResource(billingRelateRepository);
        this.restBillingRelateMockMvc = MockMvcBuilders.standaloneSetup(billingRelateResource)
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
    public static BillingRelate createEntity(EntityManager em) {
        BillingRelate billingRelate = new BillingRelate()
            .draccount(DEFAULT_DRACCOUNT)
            .craccount(DEFAULT_CRACCOUNT);
        return billingRelate;
    }

    @Before
    public void initTest() {
        billingRelate = createEntity(em);
    }

    @Test
    @Transactional
    public void createBillingRelate() throws Exception {
        int databaseSizeBeforeCreate = billingRelateRepository.findAll().size();

        // Create the BillingRelate
        restBillingRelateMockMvc.perform(post("/api/billing-relates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billingRelate)))
            .andExpect(status().isCreated());

        // Validate the BillingRelate in the database
        List<BillingRelate> billingRelateList = billingRelateRepository.findAll();
        assertThat(billingRelateList).hasSize(databaseSizeBeforeCreate + 1);
        BillingRelate testBillingRelate = billingRelateList.get(billingRelateList.size() - 1);
        assertThat(testBillingRelate.getDraccount()).isEqualTo(DEFAULT_DRACCOUNT);
        assertThat(testBillingRelate.getCraccount()).isEqualTo(DEFAULT_CRACCOUNT);
    }

    @Test
    @Transactional
    public void createBillingRelateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = billingRelateRepository.findAll().size();

        // Create the BillingRelate with an existing ID
        billingRelate.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBillingRelateMockMvc.perform(post("/api/billing-relates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billingRelate)))
            .andExpect(status().isBadRequest());

        // Validate the BillingRelate in the database
        List<BillingRelate> billingRelateList = billingRelateRepository.findAll();
        assertThat(billingRelateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBillingRelates() throws Exception {
        // Initialize the database
        billingRelateRepository.saveAndFlush(billingRelate);

        // Get all the billingRelateList
        restBillingRelateMockMvc.perform(get("/api/billing-relates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(billingRelate.getId().intValue())))
            .andExpect(jsonPath("$.[*].draccount").value(hasItem(DEFAULT_DRACCOUNT.intValue())))
            .andExpect(jsonPath("$.[*].craccount").value(hasItem(DEFAULT_CRACCOUNT.intValue())));
    }
    
    @Test
    @Transactional
    public void getBillingRelate() throws Exception {
        // Initialize the database
        billingRelateRepository.saveAndFlush(billingRelate);

        // Get the billingRelate
        restBillingRelateMockMvc.perform(get("/api/billing-relates/{id}", billingRelate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(billingRelate.getId().intValue()))
            .andExpect(jsonPath("$.draccount").value(DEFAULT_DRACCOUNT.intValue()))
            .andExpect(jsonPath("$.craccount").value(DEFAULT_CRACCOUNT.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingBillingRelate() throws Exception {
        // Get the billingRelate
        restBillingRelateMockMvc.perform(get("/api/billing-relates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBillingRelate() throws Exception {
        // Initialize the database
        billingRelateRepository.saveAndFlush(billingRelate);

        int databaseSizeBeforeUpdate = billingRelateRepository.findAll().size();

        // Update the billingRelate
        BillingRelate updatedBillingRelate = billingRelateRepository.findById(billingRelate.getId()).get();
        // Disconnect from session so that the updates on updatedBillingRelate are not directly saved in db
        em.detach(updatedBillingRelate);
        updatedBillingRelate
            .draccount(UPDATED_DRACCOUNT)
            .craccount(UPDATED_CRACCOUNT);

        restBillingRelateMockMvc.perform(put("/api/billing-relates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBillingRelate)))
            .andExpect(status().isOk());

        // Validate the BillingRelate in the database
        List<BillingRelate> billingRelateList = billingRelateRepository.findAll();
        assertThat(billingRelateList).hasSize(databaseSizeBeforeUpdate);
        BillingRelate testBillingRelate = billingRelateList.get(billingRelateList.size() - 1);
        assertThat(testBillingRelate.getDraccount()).isEqualTo(UPDATED_DRACCOUNT);
        assertThat(testBillingRelate.getCraccount()).isEqualTo(UPDATED_CRACCOUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingBillingRelate() throws Exception {
        int databaseSizeBeforeUpdate = billingRelateRepository.findAll().size();

        // Create the BillingRelate

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBillingRelateMockMvc.perform(put("/api/billing-relates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billingRelate)))
            .andExpect(status().isBadRequest());

        // Validate the BillingRelate in the database
        List<BillingRelate> billingRelateList = billingRelateRepository.findAll();
        assertThat(billingRelateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBillingRelate() throws Exception {
        // Initialize the database
        billingRelateRepository.saveAndFlush(billingRelate);

        int databaseSizeBeforeDelete = billingRelateRepository.findAll().size();

        // Get the billingRelate
        restBillingRelateMockMvc.perform(delete("/api/billing-relates/{id}", billingRelate.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BillingRelate> billingRelateList = billingRelateRepository.findAll();
        assertThat(billingRelateList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BillingRelate.class);
        BillingRelate billingRelate1 = new BillingRelate();
        billingRelate1.setId(1L);
        BillingRelate billingRelate2 = new BillingRelate();
        billingRelate2.setId(billingRelate1.getId());
        assertThat(billingRelate1).isEqualTo(billingRelate2);
        billingRelate2.setId(2L);
        assertThat(billingRelate1).isNotEqualTo(billingRelate2);
        billingRelate1.setId(null);
        assertThat(billingRelate1).isNotEqualTo(billingRelate2);
    }
}
