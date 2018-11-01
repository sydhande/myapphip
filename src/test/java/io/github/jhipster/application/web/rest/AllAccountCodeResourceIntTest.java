package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.MyapphipApp;

import io.github.jhipster.application.domain.AllAccountCode;
import io.github.jhipster.application.repository.AllAccountCodeRepository;
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
 * Test class for the AllAccountCodeResource REST controller.
 *
 * @see AllAccountCodeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyapphipApp.class)
public class AllAccountCodeResourceIntTest {

    private static final String DEFAULT_ACCOUNTCODE = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNTCODE = "BBBBBBBBBB";

    @Autowired
    private AllAccountCodeRepository allAccountCodeRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAllAccountCodeMockMvc;

    private AllAccountCode allAccountCode;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AllAccountCodeResource allAccountCodeResource = new AllAccountCodeResource(allAccountCodeRepository);
        this.restAllAccountCodeMockMvc = MockMvcBuilders.standaloneSetup(allAccountCodeResource)
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
    public static AllAccountCode createEntity(EntityManager em) {
        AllAccountCode allAccountCode = new AllAccountCode()
            .accountcode(DEFAULT_ACCOUNTCODE);
        return allAccountCode;
    }

    @Before
    public void initTest() {
        allAccountCode = createEntity(em);
    }

    @Test
    @Transactional
    public void createAllAccountCode() throws Exception {
        int databaseSizeBeforeCreate = allAccountCodeRepository.findAll().size();

        // Create the AllAccountCode
        restAllAccountCodeMockMvc.perform(post("/api/all-account-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(allAccountCode)))
            .andExpect(status().isCreated());

        // Validate the AllAccountCode in the database
        List<AllAccountCode> allAccountCodeList = allAccountCodeRepository.findAll();
        assertThat(allAccountCodeList).hasSize(databaseSizeBeforeCreate + 1);
        AllAccountCode testAllAccountCode = allAccountCodeList.get(allAccountCodeList.size() - 1);
        assertThat(testAllAccountCode.getAccountcode()).isEqualTo(DEFAULT_ACCOUNTCODE);
    }

    @Test
    @Transactional
    public void createAllAccountCodeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = allAccountCodeRepository.findAll().size();

        // Create the AllAccountCode with an existing ID
        allAccountCode.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAllAccountCodeMockMvc.perform(post("/api/all-account-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(allAccountCode)))
            .andExpect(status().isBadRequest());

        // Validate the AllAccountCode in the database
        List<AllAccountCode> allAccountCodeList = allAccountCodeRepository.findAll();
        assertThat(allAccountCodeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAllAccountCodes() throws Exception {
        // Initialize the database
        allAccountCodeRepository.saveAndFlush(allAccountCode);

        // Get all the allAccountCodeList
        restAllAccountCodeMockMvc.perform(get("/api/all-account-codes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(allAccountCode.getId().intValue())))
            .andExpect(jsonPath("$.[*].accountcode").value(hasItem(DEFAULT_ACCOUNTCODE.toString())));
    }
    
    @Test
    @Transactional
    public void getAllAccountCode() throws Exception {
        // Initialize the database
        allAccountCodeRepository.saveAndFlush(allAccountCode);

        // Get the allAccountCode
        restAllAccountCodeMockMvc.perform(get("/api/all-account-codes/{id}", allAccountCode.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(allAccountCode.getId().intValue()))
            .andExpect(jsonPath("$.accountcode").value(DEFAULT_ACCOUNTCODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAllAccountCode() throws Exception {
        // Get the allAccountCode
        restAllAccountCodeMockMvc.perform(get("/api/all-account-codes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAllAccountCode() throws Exception {
        // Initialize the database
        allAccountCodeRepository.saveAndFlush(allAccountCode);

        int databaseSizeBeforeUpdate = allAccountCodeRepository.findAll().size();

        // Update the allAccountCode
        AllAccountCode updatedAllAccountCode = allAccountCodeRepository.findById(allAccountCode.getId()).get();
        // Disconnect from session so that the updates on updatedAllAccountCode are not directly saved in db
        em.detach(updatedAllAccountCode);
        updatedAllAccountCode
            .accountcode(UPDATED_ACCOUNTCODE);

        restAllAccountCodeMockMvc.perform(put("/api/all-account-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAllAccountCode)))
            .andExpect(status().isOk());

        // Validate the AllAccountCode in the database
        List<AllAccountCode> allAccountCodeList = allAccountCodeRepository.findAll();
        assertThat(allAccountCodeList).hasSize(databaseSizeBeforeUpdate);
        AllAccountCode testAllAccountCode = allAccountCodeList.get(allAccountCodeList.size() - 1);
        assertThat(testAllAccountCode.getAccountcode()).isEqualTo(UPDATED_ACCOUNTCODE);
    }

    @Test
    @Transactional
    public void updateNonExistingAllAccountCode() throws Exception {
        int databaseSizeBeforeUpdate = allAccountCodeRepository.findAll().size();

        // Create the AllAccountCode

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAllAccountCodeMockMvc.perform(put("/api/all-account-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(allAccountCode)))
            .andExpect(status().isBadRequest());

        // Validate the AllAccountCode in the database
        List<AllAccountCode> allAccountCodeList = allAccountCodeRepository.findAll();
        assertThat(allAccountCodeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAllAccountCode() throws Exception {
        // Initialize the database
        allAccountCodeRepository.saveAndFlush(allAccountCode);

        int databaseSizeBeforeDelete = allAccountCodeRepository.findAll().size();

        // Get the allAccountCode
        restAllAccountCodeMockMvc.perform(delete("/api/all-account-codes/{id}", allAccountCode.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AllAccountCode> allAccountCodeList = allAccountCodeRepository.findAll();
        assertThat(allAccountCodeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AllAccountCode.class);
        AllAccountCode allAccountCode1 = new AllAccountCode();
        allAccountCode1.setId(1L);
        AllAccountCode allAccountCode2 = new AllAccountCode();
        allAccountCode2.setId(allAccountCode1.getId());
        assertThat(allAccountCode1).isEqualTo(allAccountCode2);
        allAccountCode2.setId(2L);
        assertThat(allAccountCode1).isNotEqualTo(allAccountCode2);
        allAccountCode1.setId(null);
        assertThat(allAccountCode1).isNotEqualTo(allAccountCode2);
    }
}
