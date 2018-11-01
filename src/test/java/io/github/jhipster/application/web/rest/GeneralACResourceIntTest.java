package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.MyapphipApp;

import io.github.jhipster.application.domain.GeneralAC;
import io.github.jhipster.application.repository.GeneralACRepository;
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
 * Test class for the GeneralACResource REST controller.
 *
 * @see GeneralACResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyapphipApp.class)
public class GeneralACResourceIntTest {

    private static final String DEFAULT_ACCOUNT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_TRANSCTION_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TRANSCTION_TYPE = "BBBBBBBBBB";

    @Autowired
    private GeneralACRepository generalACRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGeneralACMockMvc;

    private GeneralAC generalAC;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GeneralACResource generalACResource = new GeneralACResource(generalACRepository);
        this.restGeneralACMockMvc = MockMvcBuilders.standaloneSetup(generalACResource)
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
    public static GeneralAC createEntity(EntityManager em) {
        GeneralAC generalAC = new GeneralAC()
            .accountName(DEFAULT_ACCOUNT_NAME)
            .accountType(DEFAULT_ACCOUNT_TYPE)
            .transctionType(DEFAULT_TRANSCTION_TYPE);
        return generalAC;
    }

    @Before
    public void initTest() {
        generalAC = createEntity(em);
    }

    @Test
    @Transactional
    public void createGeneralAC() throws Exception {
        int databaseSizeBeforeCreate = generalACRepository.findAll().size();

        // Create the GeneralAC
        restGeneralACMockMvc.perform(post("/api/general-acs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(generalAC)))
            .andExpect(status().isCreated());

        // Validate the GeneralAC in the database
        List<GeneralAC> generalACList = generalACRepository.findAll();
        assertThat(generalACList).hasSize(databaseSizeBeforeCreate + 1);
        GeneralAC testGeneralAC = generalACList.get(generalACList.size() - 1);
        assertThat(testGeneralAC.getAccountName()).isEqualTo(DEFAULT_ACCOUNT_NAME);
        assertThat(testGeneralAC.getAccountType()).isEqualTo(DEFAULT_ACCOUNT_TYPE);
        assertThat(testGeneralAC.getTransctionType()).isEqualTo(DEFAULT_TRANSCTION_TYPE);
    }

    @Test
    @Transactional
    public void createGeneralACWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = generalACRepository.findAll().size();

        // Create the GeneralAC with an existing ID
        generalAC.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGeneralACMockMvc.perform(post("/api/general-acs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(generalAC)))
            .andExpect(status().isBadRequest());

        // Validate the GeneralAC in the database
        List<GeneralAC> generalACList = generalACRepository.findAll();
        assertThat(generalACList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllGeneralACS() throws Exception {
        // Initialize the database
        generalACRepository.saveAndFlush(generalAC);

        // Get all the generalACList
        restGeneralACMockMvc.perform(get("/api/general-acs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(generalAC.getId().intValue())))
            .andExpect(jsonPath("$.[*].accountName").value(hasItem(DEFAULT_ACCOUNT_NAME.toString())))
            .andExpect(jsonPath("$.[*].accountType").value(hasItem(DEFAULT_ACCOUNT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].transctionType").value(hasItem(DEFAULT_TRANSCTION_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getGeneralAC() throws Exception {
        // Initialize the database
        generalACRepository.saveAndFlush(generalAC);

        // Get the generalAC
        restGeneralACMockMvc.perform(get("/api/general-acs/{id}", generalAC.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(generalAC.getId().intValue()))
            .andExpect(jsonPath("$.accountName").value(DEFAULT_ACCOUNT_NAME.toString()))
            .andExpect(jsonPath("$.accountType").value(DEFAULT_ACCOUNT_TYPE.toString()))
            .andExpect(jsonPath("$.transctionType").value(DEFAULT_TRANSCTION_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGeneralAC() throws Exception {
        // Get the generalAC
        restGeneralACMockMvc.perform(get("/api/general-acs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGeneralAC() throws Exception {
        // Initialize the database
        generalACRepository.saveAndFlush(generalAC);

        int databaseSizeBeforeUpdate = generalACRepository.findAll().size();

        // Update the generalAC
        GeneralAC updatedGeneralAC = generalACRepository.findById(generalAC.getId()).get();
        // Disconnect from session so that the updates on updatedGeneralAC are not directly saved in db
        em.detach(updatedGeneralAC);
        updatedGeneralAC
            .accountName(UPDATED_ACCOUNT_NAME)
            .accountType(UPDATED_ACCOUNT_TYPE)
            .transctionType(UPDATED_TRANSCTION_TYPE);

        restGeneralACMockMvc.perform(put("/api/general-acs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGeneralAC)))
            .andExpect(status().isOk());

        // Validate the GeneralAC in the database
        List<GeneralAC> generalACList = generalACRepository.findAll();
        assertThat(generalACList).hasSize(databaseSizeBeforeUpdate);
        GeneralAC testGeneralAC = generalACList.get(generalACList.size() - 1);
        assertThat(testGeneralAC.getAccountName()).isEqualTo(UPDATED_ACCOUNT_NAME);
        assertThat(testGeneralAC.getAccountType()).isEqualTo(UPDATED_ACCOUNT_TYPE);
        assertThat(testGeneralAC.getTransctionType()).isEqualTo(UPDATED_TRANSCTION_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingGeneralAC() throws Exception {
        int databaseSizeBeforeUpdate = generalACRepository.findAll().size();

        // Create the GeneralAC

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGeneralACMockMvc.perform(put("/api/general-acs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(generalAC)))
            .andExpect(status().isBadRequest());

        // Validate the GeneralAC in the database
        List<GeneralAC> generalACList = generalACRepository.findAll();
        assertThat(generalACList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGeneralAC() throws Exception {
        // Initialize the database
        generalACRepository.saveAndFlush(generalAC);

        int databaseSizeBeforeDelete = generalACRepository.findAll().size();

        // Get the generalAC
        restGeneralACMockMvc.perform(delete("/api/general-acs/{id}", generalAC.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<GeneralAC> generalACList = generalACRepository.findAll();
        assertThat(generalACList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GeneralAC.class);
        GeneralAC generalAC1 = new GeneralAC();
        generalAC1.setId(1L);
        GeneralAC generalAC2 = new GeneralAC();
        generalAC2.setId(generalAC1.getId());
        assertThat(generalAC1).isEqualTo(generalAC2);
        generalAC2.setId(2L);
        assertThat(generalAC1).isNotEqualTo(generalAC2);
        generalAC1.setId(null);
        assertThat(generalAC1).isNotEqualTo(generalAC2);
    }
}
