package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.MyapphipApp;

import io.github.jhipster.application.domain.MainAC;
import io.github.jhipster.application.repository.MainACRepository;
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
 * Test class for the MainACResource REST controller.
 *
 * @see MainACResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyapphipApp.class)
public class MainACResourceIntTest {

    private static final String DEFAULT_ACCOUNT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NAME = "BBBBBBBBBB";

    @Autowired
    private MainACRepository mainACRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMainACMockMvc;

    private MainAC mainAC;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MainACResource mainACResource = new MainACResource(mainACRepository);
        this.restMainACMockMvc = MockMvcBuilders.standaloneSetup(mainACResource)
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
    public static MainAC createEntity(EntityManager em) {
        MainAC mainAC = new MainAC()
            .accountName(DEFAULT_ACCOUNT_NAME);
        return mainAC;
    }

    @Before
    public void initTest() {
        mainAC = createEntity(em);
    }

    @Test
    @Transactional
    public void createMainAC() throws Exception {
        int databaseSizeBeforeCreate = mainACRepository.findAll().size();

        // Create the MainAC
        restMainACMockMvc.perform(post("/api/main-acs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mainAC)))
            .andExpect(status().isCreated());

        // Validate the MainAC in the database
        List<MainAC> mainACList = mainACRepository.findAll();
        assertThat(mainACList).hasSize(databaseSizeBeforeCreate + 1);
        MainAC testMainAC = mainACList.get(mainACList.size() - 1);
        assertThat(testMainAC.getAccountName()).isEqualTo(DEFAULT_ACCOUNT_NAME);
    }

    @Test
    @Transactional
    public void createMainACWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mainACRepository.findAll().size();

        // Create the MainAC with an existing ID
        mainAC.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMainACMockMvc.perform(post("/api/main-acs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mainAC)))
            .andExpect(status().isBadRequest());

        // Validate the MainAC in the database
        List<MainAC> mainACList = mainACRepository.findAll();
        assertThat(mainACList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMainACS() throws Exception {
        // Initialize the database
        mainACRepository.saveAndFlush(mainAC);

        // Get all the mainACList
        restMainACMockMvc.perform(get("/api/main-acs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mainAC.getId().intValue())))
            .andExpect(jsonPath("$.[*].accountName").value(hasItem(DEFAULT_ACCOUNT_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getMainAC() throws Exception {
        // Initialize the database
        mainACRepository.saveAndFlush(mainAC);

        // Get the mainAC
        restMainACMockMvc.perform(get("/api/main-acs/{id}", mainAC.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mainAC.getId().intValue()))
            .andExpect(jsonPath("$.accountName").value(DEFAULT_ACCOUNT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMainAC() throws Exception {
        // Get the mainAC
        restMainACMockMvc.perform(get("/api/main-acs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMainAC() throws Exception {
        // Initialize the database
        mainACRepository.saveAndFlush(mainAC);

        int databaseSizeBeforeUpdate = mainACRepository.findAll().size();

        // Update the mainAC
        MainAC updatedMainAC = mainACRepository.findById(mainAC.getId()).get();
        // Disconnect from session so that the updates on updatedMainAC are not directly saved in db
        em.detach(updatedMainAC);
        updatedMainAC
            .accountName(UPDATED_ACCOUNT_NAME);

        restMainACMockMvc.perform(put("/api/main-acs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMainAC)))
            .andExpect(status().isOk());

        // Validate the MainAC in the database
        List<MainAC> mainACList = mainACRepository.findAll();
        assertThat(mainACList).hasSize(databaseSizeBeforeUpdate);
        MainAC testMainAC = mainACList.get(mainACList.size() - 1);
        assertThat(testMainAC.getAccountName()).isEqualTo(UPDATED_ACCOUNT_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingMainAC() throws Exception {
        int databaseSizeBeforeUpdate = mainACRepository.findAll().size();

        // Create the MainAC

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMainACMockMvc.perform(put("/api/main-acs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mainAC)))
            .andExpect(status().isBadRequest());

        // Validate the MainAC in the database
        List<MainAC> mainACList = mainACRepository.findAll();
        assertThat(mainACList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMainAC() throws Exception {
        // Initialize the database
        mainACRepository.saveAndFlush(mainAC);

        int databaseSizeBeforeDelete = mainACRepository.findAll().size();

        // Get the mainAC
        restMainACMockMvc.perform(delete("/api/main-acs/{id}", mainAC.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MainAC> mainACList = mainACRepository.findAll();
        assertThat(mainACList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MainAC.class);
        MainAC mainAC1 = new MainAC();
        mainAC1.setId(1L);
        MainAC mainAC2 = new MainAC();
        mainAC2.setId(mainAC1.getId());
        assertThat(mainAC1).isEqualTo(mainAC2);
        mainAC2.setId(2L);
        assertThat(mainAC1).isNotEqualTo(mainAC2);
        mainAC1.setId(null);
        assertThat(mainAC1).isNotEqualTo(mainAC2);
    }
}
