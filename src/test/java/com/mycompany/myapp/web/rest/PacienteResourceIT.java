package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Paciente;
import com.mycompany.myapp.repository.PacienteRepository;
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
 * Integration tests for the {@link PacienteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PacienteResourceIT {

    private static final String DEFAULT_INTERVENCIONES = "AAAAAAAAAA";
    private static final String UPDATED_INTERVENCIONES = "BBBBBBBBBB";

    private static final String DEFAULT_ANTECEDENTES_FAMILIARES = "AAAAAAAAAA";
    private static final String UPDATED_ANTECEDENTES_FAMILIARES = "BBBBBBBBBB";

    private static final String DEFAULT_ESTADO_GENERAL = "AAAAAAAAAA";
    private static final String UPDATED_ESTADO_GENERAL = "BBBBBBBBBB";

    private static final String DEFAULT_ENFERMEDADES = "AAAAAAAAAA";
    private static final String UPDATED_ENFERMEDADES = "BBBBBBBBBB";

    private static final String DEFAULT_DISCAPACIDADES = "AAAAAAAAAA";
    private static final String UPDATED_DISCAPACIDADES = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO_SANGRE = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_SANGRE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/pacientes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPacienteMockMvc;

    private Paciente paciente;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Paciente createEntity(EntityManager em) {
        Paciente paciente = new Paciente()
            .intervenciones(DEFAULT_INTERVENCIONES)
            .antecedentesFamiliares(DEFAULT_ANTECEDENTES_FAMILIARES)
            .estadoGeneral(DEFAULT_ESTADO_GENERAL)
            .enfermedades(DEFAULT_ENFERMEDADES)
            .discapacidades(DEFAULT_DISCAPACIDADES)
            .tipoSangre(DEFAULT_TIPO_SANGRE);
        return paciente;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Paciente createUpdatedEntity(EntityManager em) {
        Paciente paciente = new Paciente()
            .intervenciones(UPDATED_INTERVENCIONES)
            .antecedentesFamiliares(UPDATED_ANTECEDENTES_FAMILIARES)
            .estadoGeneral(UPDATED_ESTADO_GENERAL)
            .enfermedades(UPDATED_ENFERMEDADES)
            .discapacidades(UPDATED_DISCAPACIDADES)
            .tipoSangre(UPDATED_TIPO_SANGRE);
        return paciente;
    }

    @BeforeEach
    public void initTest() {
        paciente = createEntity(em);
    }

    @Test
    @Transactional
    void createPaciente() throws Exception {
        int databaseSizeBeforeCreate = pacienteRepository.findAll().size();
        // Create the Paciente
        restPacienteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paciente)))
            .andExpect(status().isCreated());

        // Validate the Paciente in the database
        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeCreate + 1);
        Paciente testPaciente = pacienteList.get(pacienteList.size() - 1);
        assertThat(testPaciente.getIntervenciones()).isEqualTo(DEFAULT_INTERVENCIONES);
        assertThat(testPaciente.getAntecedentesFamiliares()).isEqualTo(DEFAULT_ANTECEDENTES_FAMILIARES);
        assertThat(testPaciente.getEstadoGeneral()).isEqualTo(DEFAULT_ESTADO_GENERAL);
        assertThat(testPaciente.getEnfermedades()).isEqualTo(DEFAULT_ENFERMEDADES);
        assertThat(testPaciente.getDiscapacidades()).isEqualTo(DEFAULT_DISCAPACIDADES);
        assertThat(testPaciente.getTipoSangre()).isEqualTo(DEFAULT_TIPO_SANGRE);
    }

    @Test
    @Transactional
    void createPacienteWithExistingId() throws Exception {
        // Create the Paciente with an existing ID
        paciente.setId(1L);

        int databaseSizeBeforeCreate = pacienteRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPacienteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paciente)))
            .andExpect(status().isBadRequest());

        // Validate the Paciente in the database
        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkEstadoGeneralIsRequired() throws Exception {
        int databaseSizeBeforeTest = pacienteRepository.findAll().size();
        // set the field null
        paciente.setEstadoGeneral(null);

        // Create the Paciente, which fails.

        restPacienteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paciente)))
            .andExpect(status().isBadRequest());

        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPacientes() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList
        restPacienteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paciente.getId().intValue())))
            .andExpect(jsonPath("$.[*].intervenciones").value(hasItem(DEFAULT_INTERVENCIONES)))
            .andExpect(jsonPath("$.[*].antecedentesFamiliares").value(hasItem(DEFAULT_ANTECEDENTES_FAMILIARES)))
            .andExpect(jsonPath("$.[*].estadoGeneral").value(hasItem(DEFAULT_ESTADO_GENERAL)))
            .andExpect(jsonPath("$.[*].enfermedades").value(hasItem(DEFAULT_ENFERMEDADES)))
            .andExpect(jsonPath("$.[*].discapacidades").value(hasItem(DEFAULT_DISCAPACIDADES)))
            .andExpect(jsonPath("$.[*].tipoSangre").value(hasItem(DEFAULT_TIPO_SANGRE)));
    }

    @Test
    @Transactional
    void getPaciente() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get the paciente
        restPacienteMockMvc
            .perform(get(ENTITY_API_URL_ID, paciente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paciente.getId().intValue()))
            .andExpect(jsonPath("$.intervenciones").value(DEFAULT_INTERVENCIONES))
            .andExpect(jsonPath("$.antecedentesFamiliares").value(DEFAULT_ANTECEDENTES_FAMILIARES))
            .andExpect(jsonPath("$.estadoGeneral").value(DEFAULT_ESTADO_GENERAL))
            .andExpect(jsonPath("$.enfermedades").value(DEFAULT_ENFERMEDADES))
            .andExpect(jsonPath("$.discapacidades").value(DEFAULT_DISCAPACIDADES))
            .andExpect(jsonPath("$.tipoSangre").value(DEFAULT_TIPO_SANGRE));
    }

    @Test
    @Transactional
    void getNonExistingPaciente() throws Exception {
        // Get the paciente
        restPacienteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPaciente() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        int databaseSizeBeforeUpdate = pacienteRepository.findAll().size();

        // Update the paciente
        Paciente updatedPaciente = pacienteRepository.findById(paciente.getId()).get();
        // Disconnect from session so that the updates on updatedPaciente are not directly saved in db
        em.detach(updatedPaciente);
        updatedPaciente
            .intervenciones(UPDATED_INTERVENCIONES)
            .antecedentesFamiliares(UPDATED_ANTECEDENTES_FAMILIARES)
            .estadoGeneral(UPDATED_ESTADO_GENERAL)
            .enfermedades(UPDATED_ENFERMEDADES)
            .discapacidades(UPDATED_DISCAPACIDADES)
            .tipoSangre(UPDATED_TIPO_SANGRE);

        restPacienteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPaciente.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPaciente))
            )
            .andExpect(status().isOk());

        // Validate the Paciente in the database
        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeUpdate);
        Paciente testPaciente = pacienteList.get(pacienteList.size() - 1);
        assertThat(testPaciente.getIntervenciones()).isEqualTo(UPDATED_INTERVENCIONES);
        assertThat(testPaciente.getAntecedentesFamiliares()).isEqualTo(UPDATED_ANTECEDENTES_FAMILIARES);
        assertThat(testPaciente.getEstadoGeneral()).isEqualTo(UPDATED_ESTADO_GENERAL);
        assertThat(testPaciente.getEnfermedades()).isEqualTo(UPDATED_ENFERMEDADES);
        assertThat(testPaciente.getDiscapacidades()).isEqualTo(UPDATED_DISCAPACIDADES);
        assertThat(testPaciente.getTipoSangre()).isEqualTo(UPDATED_TIPO_SANGRE);
    }

    @Test
    @Transactional
    void putNonExistingPaciente() throws Exception {
        int databaseSizeBeforeUpdate = pacienteRepository.findAll().size();
        paciente.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPacienteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paciente.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paciente))
            )
            .andExpect(status().isBadRequest());

        // Validate the Paciente in the database
        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPaciente() throws Exception {
        int databaseSizeBeforeUpdate = pacienteRepository.findAll().size();
        paciente.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPacienteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paciente))
            )
            .andExpect(status().isBadRequest());

        // Validate the Paciente in the database
        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPaciente() throws Exception {
        int databaseSizeBeforeUpdate = pacienteRepository.findAll().size();
        paciente.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPacienteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paciente)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Paciente in the database
        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePacienteWithPatch() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        int databaseSizeBeforeUpdate = pacienteRepository.findAll().size();

        // Update the paciente using partial update
        Paciente partialUpdatedPaciente = new Paciente();
        partialUpdatedPaciente.setId(paciente.getId());

        partialUpdatedPaciente
            .intervenciones(UPDATED_INTERVENCIONES)
            .estadoGeneral(UPDATED_ESTADO_GENERAL)
            .discapacidades(UPDATED_DISCAPACIDADES)
            .tipoSangre(UPDATED_TIPO_SANGRE);

        restPacienteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaciente.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPaciente))
            )
            .andExpect(status().isOk());

        // Validate the Paciente in the database
        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeUpdate);
        Paciente testPaciente = pacienteList.get(pacienteList.size() - 1);
        assertThat(testPaciente.getIntervenciones()).isEqualTo(UPDATED_INTERVENCIONES);
        assertThat(testPaciente.getAntecedentesFamiliares()).isEqualTo(DEFAULT_ANTECEDENTES_FAMILIARES);
        assertThat(testPaciente.getEstadoGeneral()).isEqualTo(UPDATED_ESTADO_GENERAL);
        assertThat(testPaciente.getEnfermedades()).isEqualTo(DEFAULT_ENFERMEDADES);
        assertThat(testPaciente.getDiscapacidades()).isEqualTo(UPDATED_DISCAPACIDADES);
        assertThat(testPaciente.getTipoSangre()).isEqualTo(UPDATED_TIPO_SANGRE);
    }

    @Test
    @Transactional
    void fullUpdatePacienteWithPatch() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        int databaseSizeBeforeUpdate = pacienteRepository.findAll().size();

        // Update the paciente using partial update
        Paciente partialUpdatedPaciente = new Paciente();
        partialUpdatedPaciente.setId(paciente.getId());

        partialUpdatedPaciente
            .intervenciones(UPDATED_INTERVENCIONES)
            .antecedentesFamiliares(UPDATED_ANTECEDENTES_FAMILIARES)
            .estadoGeneral(UPDATED_ESTADO_GENERAL)
            .enfermedades(UPDATED_ENFERMEDADES)
            .discapacidades(UPDATED_DISCAPACIDADES)
            .tipoSangre(UPDATED_TIPO_SANGRE);

        restPacienteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaciente.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPaciente))
            )
            .andExpect(status().isOk());

        // Validate the Paciente in the database
        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeUpdate);
        Paciente testPaciente = pacienteList.get(pacienteList.size() - 1);
        assertThat(testPaciente.getIntervenciones()).isEqualTo(UPDATED_INTERVENCIONES);
        assertThat(testPaciente.getAntecedentesFamiliares()).isEqualTo(UPDATED_ANTECEDENTES_FAMILIARES);
        assertThat(testPaciente.getEstadoGeneral()).isEqualTo(UPDATED_ESTADO_GENERAL);
        assertThat(testPaciente.getEnfermedades()).isEqualTo(UPDATED_ENFERMEDADES);
        assertThat(testPaciente.getDiscapacidades()).isEqualTo(UPDATED_DISCAPACIDADES);
        assertThat(testPaciente.getTipoSangre()).isEqualTo(UPDATED_TIPO_SANGRE);
    }

    @Test
    @Transactional
    void patchNonExistingPaciente() throws Exception {
        int databaseSizeBeforeUpdate = pacienteRepository.findAll().size();
        paciente.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPacienteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, paciente.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paciente))
            )
            .andExpect(status().isBadRequest());

        // Validate the Paciente in the database
        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPaciente() throws Exception {
        int databaseSizeBeforeUpdate = pacienteRepository.findAll().size();
        paciente.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPacienteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paciente))
            )
            .andExpect(status().isBadRequest());

        // Validate the Paciente in the database
        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPaciente() throws Exception {
        int databaseSizeBeforeUpdate = pacienteRepository.findAll().size();
        paciente.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPacienteMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(paciente)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Paciente in the database
        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePaciente() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        int databaseSizeBeforeDelete = pacienteRepository.findAll().size();

        // Delete the paciente
        restPacienteMockMvc
            .perform(delete(ENTITY_API_URL_ID, paciente.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
