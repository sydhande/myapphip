package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.MyapphipApp;

import io.github.jhipster.application.domain.PrimeAC;
import io.github.jhipster.application.repository.PrimeACRepository;
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
 * Test class for the PrimeACResource REST controller.
 *
 * @see PrimeACResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyapphipApp.class)
public class PrimeACResourceIntTest {

    private static final String DEFAULT_ACCOUNT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_TYPE = "BBBBBBBBBB";

    @Autowired
    private PrimeACRepository primeACRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPrimeACMockMvc;

    private PrimeAC primeAC;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PrimeACResource primeACResource = new PrimeACResource(primeACRepository);
        this.restPrimeACMockMvc = MockMvcBuilders.standaloneSetup(primeACResource)
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
    public static PrimeAC createEntity(EntityManager em) {
        PrimeAC primeAC = new PrimeAC()
            .accountName(DEFAULT_ACCOUNT_NAME)
            .accountType(DEFAULT_ACCOUNT_TYPE);
        return primeAC;
    }

    @Before
    public void initTest() {
        primeAC = createEntity(em);
    }

    @Test
    @Transactional
    public void createPrimeAC() throws Exception {
        int databaseSizeBeforeCreate = primeACRepository.findAll().size();

        // Create the PrimeAC
        restPrimeACMockMvc.perform(post("/api/prime-acs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(primeAC)))
            .andExpect(status().isCreated());

        // Validate the PrimeAC in the database
        List<PrimeAC> primeACList = primeACRepository.findAll();
        assertThat(primeACList).hasSize(databaseSizeBeforeCreate + 1);
        PrimeAC testPrimeAC = primeACList.get(primeACList.size() - 1);
        assertThat(testPrimeAC.getAccountName()).isEqualTo(DEFAULT_ACCOUNT_NAME);
        assertThat(testPrimeAC.getAccountType()).isEqualTo(DEFAULT_ACCOUNT_TYPE);
    }

    @Test
    @Transactional
    public void createPrimeACWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = primeACRepository.findAll().size();

        // Create the PrimeAC with an existing ID
        primeAC.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrimeACMockMvc.perform(post("/api/prime-acs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(primeAC)))
            .andExpect(status().isBadRequest());

        // Validate the PrimeAC in the database
        List<PrimeAC> primeACList = primeACRepository.findAll();
        assertThat(primeACList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPrimeACS() throws Exception {
        // Initialize the database
        primeACRepository.saveAndFlush(primeAC);

        // Get all the primeACList
        restPrimeACMockMvc.perform(get("/api/prime-acs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(primeAC.getId().intValue())))
            .andExpect(jsonPath("$.[*].accountName").value(hasItem(DEFAULT_ACCOUNT_NAME.toString())))
            .andExpect(jsonPath("$.[*].accountType").value(hasItem(DEFAULT_ACCOUNT_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getPrimeAC() throws Exception {
        // Initialize the database
        primeACRepository.saveAndFlush(primeAC);

        // Get the primeAC
        restPrimeACMockMvc.perform(get("/api/prime-acs/{id}", primeAC.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(primeAC.getId().intValue()))
            .andExpect(jsonPath("$.accountName").value(DEFAULT_ACCOUNT_NAME.toString()))
            .andExpect(jsonPath("$.accountType").value(DEFAULT_ACCOUNT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPrimeAC() throws Exception {
        // Get the primeAC
        restPrimeACMockMvc.perform(get("/api/prime-acs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrimeAC() throws Exception {
        // Initialize the database
        primeACRepository.saveAndFlush(primeAC);

        int databaseSizeBeforeUpdate = primeACRepository.findAll().size();

        // Update the primeAC
        PrimeAC updatedPrimeAC = primeACRepository.findById(primeAC.getId()).get();
        // Disconnect from session so that the updates on updatedPrimeAC are not directly saved in db
        em.detach(updatedPrimeAC);
        updatedPrimeAC
            .accountName(UPDATED_ACCOUNT_NAME)
            .accountType(UPDATED_ACCOUNT_TYPE);

        restPrimeACMockMvc.perform(put("/api/prime-acs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPrimeAC)))
            .andExpect(status().isOk());

        // Validate the PrimeAC in the database
        List<PrimeAC> primeACList = primeACRepository.findAll();
        assertThat(primeACList).hasSize(databaseSizeBeforeUpdate);
        PrimeAC testPrimeAC = primeACList.get(primeACList.size() - 1);
        assertThat(testPrimeAC.getAccountName()).isEqualTo(UPDATED_ACCOUNT_NAME);
        assertThat(testPrimeAC.getAccountType()).isEqualTo(UPDATED_ACCOUNT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingPrimeAC() throws Exception {
        int databaseSizeBeforeUpdate = primeACRepository.findAll().size();

        // Create the PrimeAC

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrimeACMockMvc.perform(put("/api/prime-acs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(primeAC)))
            .andExpect(status().isBadRequest());

        // Validate the PrimeAC in the database
        List<PrimeAC> primeACList = primeACRepository.findAll();
        assertThat(primeACList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePrimeAC() throws Exception {
        // Initialize the database
        primeACRepository.saveAndFlush(primeAC);

        int databaseSizeBeforeDelete = primeACRepository.findAll().size();

        // Get the primeAC
        restPrimeACMockMvc.perform(delete("/api/prime-acs/{id}", primeAC.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PrimeAC> primeACList = primeACRepository.findAll();
        assertThat(primeACList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrimeAC.class);
        PrimeAC primeAC1 = new PrimeAC();
        primeAC1.setId(1L);
        PrimeAC primeAC2 = new PrimeAC();
        primeAC2.setId(primeAC1.getId());
        assertThat(primeAC1).isEqualTo(primeAC2);
        primeAC2.setId(2L);
        assertThat(primeAC1).isNotEqualTo(primeAC2);
        primeAC1.setId(null);
        assertThat(primeAC1).isNotEqualTo(primeAC2);
    }
}
