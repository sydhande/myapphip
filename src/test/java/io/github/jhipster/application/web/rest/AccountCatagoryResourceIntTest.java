package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.MyapphipApp;

import io.github.jhipster.application.domain.AccountCatagory;
import io.github.jhipster.application.repository.AccountCatagoryRepository;
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

import io.github.jhipster.application.domain.enumeration.Catuser;
/**
 * Test class for the AccountCatagoryResource REST controller.
 *
 * @see AccountCatagoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyapphipApp.class)
public class AccountCatagoryResourceIntTest {

    private static final String DEFAULT_ACCOUNT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_TYPE = "BBBBBBBBBB";

    private static final Catuser DEFAULT_ACCOUNT_USER = Catuser.OWNER;
    private static final Catuser UPDATED_ACCOUNT_USER = Catuser.VENDOR;

    @Autowired
    private AccountCatagoryRepository accountCatagoryRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAccountCatagoryMockMvc;

    private AccountCatagory accountCatagory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AccountCatagoryResource accountCatagoryResource = new AccountCatagoryResource(accountCatagoryRepository);
        this.restAccountCatagoryMockMvc = MockMvcBuilders.standaloneSetup(accountCatagoryResource)
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
    public static AccountCatagory createEntity(EntityManager em) {
        AccountCatagory accountCatagory = new AccountCatagory()
            .accountType(DEFAULT_ACCOUNT_TYPE)
            .accountUser(DEFAULT_ACCOUNT_USER);
        return accountCatagory;
    }

    @Before
    public void initTest() {
        accountCatagory = createEntity(em);
    }

    @Test
    @Transactional
    public void createAccountCatagory() throws Exception {
        int databaseSizeBeforeCreate = accountCatagoryRepository.findAll().size();

        // Create the AccountCatagory
        restAccountCatagoryMockMvc.perform(post("/api/account-catagories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountCatagory)))
            .andExpect(status().isCreated());

        // Validate the AccountCatagory in the database
        List<AccountCatagory> accountCatagoryList = accountCatagoryRepository.findAll();
        assertThat(accountCatagoryList).hasSize(databaseSizeBeforeCreate + 1);
        AccountCatagory testAccountCatagory = accountCatagoryList.get(accountCatagoryList.size() - 1);
        assertThat(testAccountCatagory.getAccountType()).isEqualTo(DEFAULT_ACCOUNT_TYPE);
        assertThat(testAccountCatagory.getAccountUser()).isEqualTo(DEFAULT_ACCOUNT_USER);
    }

    @Test
    @Transactional
    public void createAccountCatagoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = accountCatagoryRepository.findAll().size();

        // Create the AccountCatagory with an existing ID
        accountCatagory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAccountCatagoryMockMvc.perform(post("/api/account-catagories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountCatagory)))
            .andExpect(status().isBadRequest());

        // Validate the AccountCatagory in the database
        List<AccountCatagory> accountCatagoryList = accountCatagoryRepository.findAll();
        assertThat(accountCatagoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAccountCatagories() throws Exception {
        // Initialize the database
        accountCatagoryRepository.saveAndFlush(accountCatagory);

        // Get all the accountCatagoryList
        restAccountCatagoryMockMvc.perform(get("/api/account-catagories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accountCatagory.getId().intValue())))
            .andExpect(jsonPath("$.[*].accountType").value(hasItem(DEFAULT_ACCOUNT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].accountUser").value(hasItem(DEFAULT_ACCOUNT_USER.toString())));
    }
    
    @Test
    @Transactional
    public void getAccountCatagory() throws Exception {
        // Initialize the database
        accountCatagoryRepository.saveAndFlush(accountCatagory);

        // Get the accountCatagory
        restAccountCatagoryMockMvc.perform(get("/api/account-catagories/{id}", accountCatagory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(accountCatagory.getId().intValue()))
            .andExpect(jsonPath("$.accountType").value(DEFAULT_ACCOUNT_TYPE.toString()))
            .andExpect(jsonPath("$.accountUser").value(DEFAULT_ACCOUNT_USER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAccountCatagory() throws Exception {
        // Get the accountCatagory
        restAccountCatagoryMockMvc.perform(get("/api/account-catagories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAccountCatagory() throws Exception {
        // Initialize the database
        accountCatagoryRepository.saveAndFlush(accountCatagory);

        int databaseSizeBeforeUpdate = accountCatagoryRepository.findAll().size();

        // Update the accountCatagory
        AccountCatagory updatedAccountCatagory = accountCatagoryRepository.findById(accountCatagory.getId()).get();
        // Disconnect from session so that the updates on updatedAccountCatagory are not directly saved in db
        em.detach(updatedAccountCatagory);
        updatedAccountCatagory
            .accountType(UPDATED_ACCOUNT_TYPE)
            .accountUser(UPDATED_ACCOUNT_USER);

        restAccountCatagoryMockMvc.perform(put("/api/account-catagories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAccountCatagory)))
            .andExpect(status().isOk());

        // Validate the AccountCatagory in the database
        List<AccountCatagory> accountCatagoryList = accountCatagoryRepository.findAll();
        assertThat(accountCatagoryList).hasSize(databaseSizeBeforeUpdate);
        AccountCatagory testAccountCatagory = accountCatagoryList.get(accountCatagoryList.size() - 1);
        assertThat(testAccountCatagory.getAccountType()).isEqualTo(UPDATED_ACCOUNT_TYPE);
        assertThat(testAccountCatagory.getAccountUser()).isEqualTo(UPDATED_ACCOUNT_USER);
    }

    @Test
    @Transactional
    public void updateNonExistingAccountCatagory() throws Exception {
        int databaseSizeBeforeUpdate = accountCatagoryRepository.findAll().size();

        // Create the AccountCatagory

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccountCatagoryMockMvc.perform(put("/api/account-catagories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountCatagory)))
            .andExpect(status().isBadRequest());

        // Validate the AccountCatagory in the database
        List<AccountCatagory> accountCatagoryList = accountCatagoryRepository.findAll();
        assertThat(accountCatagoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAccountCatagory() throws Exception {
        // Initialize the database
        accountCatagoryRepository.saveAndFlush(accountCatagory);

        int databaseSizeBeforeDelete = accountCatagoryRepository.findAll().size();

        // Get the accountCatagory
        restAccountCatagoryMockMvc.perform(delete("/api/account-catagories/{id}", accountCatagory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AccountCatagory> accountCatagoryList = accountCatagoryRepository.findAll();
        assertThat(accountCatagoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccountCatagory.class);
        AccountCatagory accountCatagory1 = new AccountCatagory();
        accountCatagory1.setId(1L);
        AccountCatagory accountCatagory2 = new AccountCatagory();
        accountCatagory2.setId(accountCatagory1.getId());
        assertThat(accountCatagory1).isEqualTo(accountCatagory2);
        accountCatagory2.setId(2L);
        assertThat(accountCatagory1).isNotEqualTo(accountCatagory2);
        accountCatagory1.setId(null);
        assertThat(accountCatagory1).isNotEqualTo(accountCatagory2);
    }
}
