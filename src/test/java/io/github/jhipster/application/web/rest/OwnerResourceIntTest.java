package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.MyapphipApp;

import io.github.jhipster.application.domain.Owner;
import io.github.jhipster.application.repository.OwnerRepository;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;


import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the OwnerResource REST controller.
 *
 * @see OwnerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyapphipApp.class)
public class OwnerResourceIntTest {

    private static final String DEFAULT_ACCOUNTCODE = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNTCODE = "BBBBBBBBBB";

    private static final String DEFAULT_FLAT = "AAAAAAAAAA";
    private static final String UPDATED_FLAT = "BBBBBBBBBB";

    private static final String DEFAULT_BUILDING = "AAAAAAAAAA";
    private static final String UPDATED_BUILDING = "BBBBBBBBBB";

    private static final String DEFAULT_FIRSTNAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRSTNAME = "BBBBBBBBBB";

    private static final String DEFAULT_MIDDLENAME = "AAAAAAAAAA";
    private static final String UPDATED_MIDDLENAME = "BBBBBBBBBB";

    private static final String DEFAULT_LASTNAME = "AAAAAAAAAA";
    private static final String UPDATED_LASTNAME = "BBBBBBBBBB";

    @Autowired
    private OwnerRepository ownerRepository;

    @Mock
    private OwnerRepository ownerRepositoryMock;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOwnerMockMvc;

    private Owner owner;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OwnerResource ownerResource = new OwnerResource(ownerRepository);
        this.restOwnerMockMvc = MockMvcBuilders.standaloneSetup(ownerResource)
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
    public static Owner createEntity(EntityManager em) {
        Owner owner = new Owner()
            .accountcode(DEFAULT_ACCOUNTCODE)
            .flat(DEFAULT_FLAT)
            .building(DEFAULT_BUILDING)
            .firstname(DEFAULT_FIRSTNAME)
            .middlename(DEFAULT_MIDDLENAME)
            .lastname(DEFAULT_LASTNAME);
        return owner;
    }

    @Before
    public void initTest() {
        owner = createEntity(em);
    }

    @Test
    @Transactional
    public void createOwner() throws Exception {
        int databaseSizeBeforeCreate = ownerRepository.findAll().size();

        // Create the Owner
        restOwnerMockMvc.perform(post("/api/owners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(owner)))
            .andExpect(status().isCreated());

        // Validate the Owner in the database
        List<Owner> ownerList = ownerRepository.findAll();
        assertThat(ownerList).hasSize(databaseSizeBeforeCreate + 1);
        Owner testOwner = ownerList.get(ownerList.size() - 1);
        assertThat(testOwner.getAccountcode()).isEqualTo(DEFAULT_ACCOUNTCODE);
        assertThat(testOwner.getFlat()).isEqualTo(DEFAULT_FLAT);
        assertThat(testOwner.getBuilding()).isEqualTo(DEFAULT_BUILDING);
        assertThat(testOwner.getFirstname()).isEqualTo(DEFAULT_FIRSTNAME);
        assertThat(testOwner.getMiddlename()).isEqualTo(DEFAULT_MIDDLENAME);
        assertThat(testOwner.getLastname()).isEqualTo(DEFAULT_LASTNAME);
    }

    @Test
    @Transactional
    public void createOwnerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ownerRepository.findAll().size();

        // Create the Owner with an existing ID
        owner.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOwnerMockMvc.perform(post("/api/owners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(owner)))
            .andExpect(status().isBadRequest());

        // Validate the Owner in the database
        List<Owner> ownerList = ownerRepository.findAll();
        assertThat(ownerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOwners() throws Exception {
        // Initialize the database
        ownerRepository.saveAndFlush(owner);

        // Get all the ownerList
        restOwnerMockMvc.perform(get("/api/owners?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(owner.getId().intValue())))
            .andExpect(jsonPath("$.[*].accountcode").value(hasItem(DEFAULT_ACCOUNTCODE.toString())))
            .andExpect(jsonPath("$.[*].flat").value(hasItem(DEFAULT_FLAT.toString())))
            .andExpect(jsonPath("$.[*].building").value(hasItem(DEFAULT_BUILDING.toString())))
            .andExpect(jsonPath("$.[*].firstname").value(hasItem(DEFAULT_FIRSTNAME.toString())))
            .andExpect(jsonPath("$.[*].middlename").value(hasItem(DEFAULT_MIDDLENAME.toString())))
            .andExpect(jsonPath("$.[*].lastname").value(hasItem(DEFAULT_LASTNAME.toString())));
    }
    
    public void getAllOwnersWithEagerRelationshipsIsEnabled() throws Exception {
        OwnerResource ownerResource = new OwnerResource(ownerRepositoryMock);
        when(ownerRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restOwnerMockMvc = MockMvcBuilders.standaloneSetup(ownerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restOwnerMockMvc.perform(get("/api/owners?eagerload=true"))
        .andExpect(status().isOk());

        verify(ownerRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    public void getAllOwnersWithEagerRelationshipsIsNotEnabled() throws Exception {
        OwnerResource ownerResource = new OwnerResource(ownerRepositoryMock);
            when(ownerRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restOwnerMockMvc = MockMvcBuilders.standaloneSetup(ownerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restOwnerMockMvc.perform(get("/api/owners?eagerload=true"))
        .andExpect(status().isOk());

            verify(ownerRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getOwner() throws Exception {
        // Initialize the database
        ownerRepository.saveAndFlush(owner);

        // Get the owner
        restOwnerMockMvc.perform(get("/api/owners/{id}", owner.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(owner.getId().intValue()))
            .andExpect(jsonPath("$.accountcode").value(DEFAULT_ACCOUNTCODE.toString()))
            .andExpect(jsonPath("$.flat").value(DEFAULT_FLAT.toString()))
            .andExpect(jsonPath("$.building").value(DEFAULT_BUILDING.toString()))
            .andExpect(jsonPath("$.firstname").value(DEFAULT_FIRSTNAME.toString()))
            .andExpect(jsonPath("$.middlename").value(DEFAULT_MIDDLENAME.toString()))
            .andExpect(jsonPath("$.lastname").value(DEFAULT_LASTNAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOwner() throws Exception {
        // Get the owner
        restOwnerMockMvc.perform(get("/api/owners/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOwner() throws Exception {
        // Initialize the database
        ownerRepository.saveAndFlush(owner);

        int databaseSizeBeforeUpdate = ownerRepository.findAll().size();

        // Update the owner
        Owner updatedOwner = ownerRepository.findById(owner.getId()).get();
        // Disconnect from session so that the updates on updatedOwner are not directly saved in db
        em.detach(updatedOwner);
        updatedOwner
            .accountcode(UPDATED_ACCOUNTCODE)
            .flat(UPDATED_FLAT)
            .building(UPDATED_BUILDING)
            .firstname(UPDATED_FIRSTNAME)
            .middlename(UPDATED_MIDDLENAME)
            .lastname(UPDATED_LASTNAME);

        restOwnerMockMvc.perform(put("/api/owners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOwner)))
            .andExpect(status().isOk());

        // Validate the Owner in the database
        List<Owner> ownerList = ownerRepository.findAll();
        assertThat(ownerList).hasSize(databaseSizeBeforeUpdate);
        Owner testOwner = ownerList.get(ownerList.size() - 1);
        assertThat(testOwner.getAccountcode()).isEqualTo(UPDATED_ACCOUNTCODE);
        assertThat(testOwner.getFlat()).isEqualTo(UPDATED_FLAT);
        assertThat(testOwner.getBuilding()).isEqualTo(UPDATED_BUILDING);
        assertThat(testOwner.getFirstname()).isEqualTo(UPDATED_FIRSTNAME);
        assertThat(testOwner.getMiddlename()).isEqualTo(UPDATED_MIDDLENAME);
        assertThat(testOwner.getLastname()).isEqualTo(UPDATED_LASTNAME);
    }

    @Test
    @Transactional
    public void updateNonExistingOwner() throws Exception {
        int databaseSizeBeforeUpdate = ownerRepository.findAll().size();

        // Create the Owner

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOwnerMockMvc.perform(put("/api/owners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(owner)))
            .andExpect(status().isBadRequest());

        // Validate the Owner in the database
        List<Owner> ownerList = ownerRepository.findAll();
        assertThat(ownerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOwner() throws Exception {
        // Initialize the database
        ownerRepository.saveAndFlush(owner);

        int databaseSizeBeforeDelete = ownerRepository.findAll().size();

        // Get the owner
        restOwnerMockMvc.perform(delete("/api/owners/{id}", owner.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Owner> ownerList = ownerRepository.findAll();
        assertThat(ownerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Owner.class);
        Owner owner1 = new Owner();
        owner1.setId(1L);
        Owner owner2 = new Owner();
        owner2.setId(owner1.getId());
        assertThat(owner1).isEqualTo(owner2);
        owner2.setId(2L);
        assertThat(owner1).isNotEqualTo(owner2);
        owner1.setId(null);
        assertThat(owner1).isNotEqualTo(owner2);
    }
}
