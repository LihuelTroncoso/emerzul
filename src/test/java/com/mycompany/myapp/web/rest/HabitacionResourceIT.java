package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Habitacion;
import com.mycompany.myapp.repository.HabitacionRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link HabitacionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HabitacionResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO = "BBBBBBBBBB";

    private static final String DEFAULT_ZONA = "AAAAAAAAAA";
    private static final String UPDATED_ZONA = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/habitacions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private HabitacionRepository habitacionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHabitacionMockMvc;

    private Habitacion habitacion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Habitacion createEntity(EntityManager em) {
        Habitacion habitacion = new Habitacion().nombre(DEFAULT_NOMBRE).tipo(DEFAULT_TIPO).zona(DEFAULT_ZONA);
        return habitacion;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Habitacion createUpdatedEntity(EntityManager em) {
        Habitacion habitacion = new Habitacion().nombre(UPDATED_NOMBRE).tipo(UPDATED_TIPO).zona(UPDATED_ZONA);
        return habitacion;
    }

    @BeforeEach
    public void initTest() {
        habitacion = createEntity(em);
    }

    @Test
    @Transactional
    void createHabitacion() throws Exception {
        int databaseSizeBeforeCreate = habitacionRepository.findAll().size();
        // Create the Habitacion
        restHabitacionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(habitacion)))
            .andExpect(status().isCreated());

        // Validate the Habitacion in the database
        List<Habitacion> habitacionList = habitacionRepository.findAll();
        assertThat(habitacionList).hasSize(databaseSizeBeforeCreate + 1);
        Habitacion testHabitacion = habitacionList.get(habitacionList.size() - 1);
        assertThat(testHabitacion.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testHabitacion.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testHabitacion.getZona()).isEqualTo(DEFAULT_ZONA);
    }

    @Test
    @Transactional
    void createHabitacionWithExistingId() throws Exception {
        // Create the Habitacion with an existing ID
        habitacion.setId(1L);

        int databaseSizeBeforeCreate = habitacionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHabitacionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(habitacion)))
            .andExpect(status().isBadRequest());

        // Validate the Habitacion in the database
        List<Habitacion> habitacionList = habitacionRepository.findAll();
        assertThat(habitacionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = habitacionRepository.findAll().size();
        // set the field null
        habitacion.setNombre(null);

        // Create the Habitacion, which fails.

        restHabitacionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(habitacion)))
            .andExpect(status().isBadRequest());

        List<Habitacion> habitacionList = habitacionRepository.findAll();
        assertThat(habitacionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTipoIsRequired() throws Exception {
        int databaseSizeBeforeTest = habitacionRepository.findAll().size();
        // set the field null
        habitacion.setTipo(null);

        // Create the Habitacion, which fails.

        restHabitacionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(habitacion)))
            .andExpect(status().isBadRequest());

        List<Habitacion> habitacionList = habitacionRepository.findAll();
        assertThat(habitacionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkZonaIsRequired() throws Exception {
        int databaseSizeBeforeTest = habitacionRepository.findAll().size();
        // set the field null
        habitacion.setZona(null);

        // Create the Habitacion, which fails.

        restHabitacionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(habitacion)))
            .andExpect(status().isBadRequest());

        List<Habitacion> habitacionList = habitacionRepository.findAll();
        assertThat(habitacionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllHabitacions() throws Exception {
        // Initialize the database
        habitacionRepository.saveAndFlush(habitacion);

        // Get all the habitacionList
        restHabitacionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(habitacion.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO)))
            .andExpect(jsonPath("$.[*].zona").value(hasItem(DEFAULT_ZONA)));
    }

    @Test
    @Transactional
    void getHabitacion() throws Exception {
        // Initialize the database
        habitacionRepository.saveAndFlush(habitacion);

        // Get the habitacion
        restHabitacionMockMvc
            .perform(get(ENTITY_API_URL_ID, habitacion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(habitacion.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO))
            .andExpect(jsonPath("$.zona").value(DEFAULT_ZONA));
    }

    @Test
    @Transactional
    void getNonExistingHabitacion() throws Exception {
        // Get the habitacion
        restHabitacionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingHabitacion() throws Exception {
        // Initialize the database
        habitacionRepository.saveAndFlush(habitacion);

        int databaseSizeBeforeUpdate = habitacionRepository.findAll().size();

        // Update the habitacion
        Habitacion updatedHabitacion = habitacionRepository.findById(habitacion.getId()).get();
        // Disconnect from session so that the updates on updatedHabitacion are not directly saved in db
        em.detach(updatedHabitacion);
        updatedHabitacion.nombre(UPDATED_NOMBRE).tipo(UPDATED_TIPO).zona(UPDATED_ZONA);

        restHabitacionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedHabitacion.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedHabitacion))
            )
            .andExpect(status().isOk());

        // Validate the Habitacion in the database
        List<Habitacion> habitacionList = habitacionRepository.findAll();
        assertThat(habitacionList).hasSize(databaseSizeBeforeUpdate);
        Habitacion testHabitacion = habitacionList.get(habitacionList.size() - 1);
        assertThat(testHabitacion.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testHabitacion.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testHabitacion.getZona()).isEqualTo(UPDATED_ZONA);
    }

    @Test
    @Transactional
    void putNonExistingHabitacion() throws Exception {
        int databaseSizeBeforeUpdate = habitacionRepository.findAll().size();
        habitacion.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHabitacionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, habitacion.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(habitacion))
            )
            .andExpect(status().isBadRequest());

        // Validate the Habitacion in the database
        List<Habitacion> habitacionList = habitacionRepository.findAll();
        assertThat(habitacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHabitacion() throws Exception {
        int databaseSizeBeforeUpdate = habitacionRepository.findAll().size();
        habitacion.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHabitacionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(habitacion))
            )
            .andExpect(status().isBadRequest());

        // Validate the Habitacion in the database
        List<Habitacion> habitacionList = habitacionRepository.findAll();
        assertThat(habitacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHabitacion() throws Exception {
        int databaseSizeBeforeUpdate = habitacionRepository.findAll().size();
        habitacion.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHabitacionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(habitacion)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Habitacion in the database
        List<Habitacion> habitacionList = habitacionRepository.findAll();
        assertThat(habitacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHabitacionWithPatch() throws Exception {
        // Initialize the database
        habitacionRepository.saveAndFlush(habitacion);

        int databaseSizeBeforeUpdate = habitacionRepository.findAll().size();

        // Update the habitacion using partial update
        Habitacion partialUpdatedHabitacion = new Habitacion();
        partialUpdatedHabitacion.setId(habitacion.getId());

        partialUpdatedHabitacion.nombre(UPDATED_NOMBRE).tipo(UPDATED_TIPO);

        restHabitacionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHabitacion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHabitacion))
            )
            .andExpect(status().isOk());

        // Validate the Habitacion in the database
        List<Habitacion> habitacionList = habitacionRepository.findAll();
        assertThat(habitacionList).hasSize(databaseSizeBeforeUpdate);
        Habitacion testHabitacion = habitacionList.get(habitacionList.size() - 1);
        assertThat(testHabitacion.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testHabitacion.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testHabitacion.getZona()).isEqualTo(DEFAULT_ZONA);
    }

    @Test
    @Transactional
    void fullUpdateHabitacionWithPatch() throws Exception {
        // Initialize the database
        habitacionRepository.saveAndFlush(habitacion);

        int databaseSizeBeforeUpdate = habitacionRepository.findAll().size();

        // Update the habitacion using partial update
        Habitacion partialUpdatedHabitacion = new Habitacion();
        partialUpdatedHabitacion.setId(habitacion.getId());

        partialUpdatedHabitacion.nombre(UPDATED_NOMBRE).tipo(UPDATED_TIPO).zona(UPDATED_ZONA);

        restHabitacionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHabitacion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHabitacion))
            )
            .andExpect(status().isOk());

        // Validate the Habitacion in the database
        List<Habitacion> habitacionList = habitacionRepository.findAll();
        assertThat(habitacionList).hasSize(databaseSizeBeforeUpdate);
        Habitacion testHabitacion = habitacionList.get(habitacionList.size() - 1);
        assertThat(testHabitacion.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testHabitacion.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testHabitacion.getZona()).isEqualTo(UPDATED_ZONA);
    }

    @Test
    @Transactional
    void patchNonExistingHabitacion() throws Exception {
        int databaseSizeBeforeUpdate = habitacionRepository.findAll().size();
        habitacion.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHabitacionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, habitacion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(habitacion))
            )
            .andExpect(status().isBadRequest());

        // Validate the Habitacion in the database
        List<Habitacion> habitacionList = habitacionRepository.findAll();
        assertThat(habitacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHabitacion() throws Exception {
        int databaseSizeBeforeUpdate = habitacionRepository.findAll().size();
        habitacion.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHabitacionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(habitacion))
            )
            .andExpect(status().isBadRequest());

        // Validate the Habitacion in the database
        List<Habitacion> habitacionList = habitacionRepository.findAll();
        assertThat(habitacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHabitacion() throws Exception {
        int databaseSizeBeforeUpdate = habitacionRepository.findAll().size();
        habitacion.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHabitacionMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(habitacion))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Habitacion in the database
        List<Habitacion> habitacionList = habitacionRepository.findAll();
        assertThat(habitacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHabitacion() throws Exception {
        // Initialize the database
        habitacionRepository.saveAndFlush(habitacion);

        int databaseSizeBeforeDelete = habitacionRepository.findAll().size();

        // Delete the habitacion
        restHabitacionMockMvc
            .perform(delete(ENTITY_API_URL_ID, habitacion.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Habitacion> habitacionList = habitacionRepository.findAll();
        assertThat(habitacionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
