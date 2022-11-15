package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Reporte;
import com.mycompany.myapp.repository.ReporteRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link ReporteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ReporteResourceIT {

    private static final Instant DEFAULT_HORA_INICIO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HORA_INICIO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_HORA_FINAL = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HORA_FINAL = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_TIPO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ALERTA = false;
    private static final Boolean UPDATED_ALERTA = true;

    private static final String ENTITY_API_URL = "/api/reportes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ReporteRepository reporteRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restReporteMockMvc;

    private Reporte reporte;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Reporte createEntity(EntityManager em) {
        Reporte reporte = new Reporte()
            .horaInicio(DEFAULT_HORA_INICIO)
            .horaFinal(DEFAULT_HORA_FINAL)
            .tipo(DEFAULT_TIPO)
            .alerta(DEFAULT_ALERTA);
        return reporte;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Reporte createUpdatedEntity(EntityManager em) {
        Reporte reporte = new Reporte()
            .horaInicio(UPDATED_HORA_INICIO)
            .horaFinal(UPDATED_HORA_FINAL)
            .tipo(UPDATED_TIPO)
            .alerta(UPDATED_ALERTA);
        return reporte;
    }

    @BeforeEach
    public void initTest() {
        reporte = createEntity(em);
    }

    @Test
    @Transactional
    void createReporte() throws Exception {
        int databaseSizeBeforeCreate = reporteRepository.findAll().size();
        // Create the Reporte
        restReporteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reporte)))
            .andExpect(status().isCreated());

        // Validate the Reporte in the database
        List<Reporte> reporteList = reporteRepository.findAll();
        assertThat(reporteList).hasSize(databaseSizeBeforeCreate + 1);
        Reporte testReporte = reporteList.get(reporteList.size() - 1);
        assertThat(testReporte.getHoraInicio()).isEqualTo(DEFAULT_HORA_INICIO);
        assertThat(testReporte.getHoraFinal()).isEqualTo(DEFAULT_HORA_FINAL);
        assertThat(testReporte.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testReporte.getAlerta()).isEqualTo(DEFAULT_ALERTA);
    }

    @Test
    @Transactional
    void createReporteWithExistingId() throws Exception {
        // Create the Reporte with an existing ID
        reporte.setId(1L);

        int databaseSizeBeforeCreate = reporteRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restReporteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reporte)))
            .andExpect(status().isBadRequest());

        // Validate the Reporte in the database
        List<Reporte> reporteList = reporteRepository.findAll();
        assertThat(reporteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkHoraInicioIsRequired() throws Exception {
        int databaseSizeBeforeTest = reporteRepository.findAll().size();
        // set the field null
        reporte.setHoraInicio(null);

        // Create the Reporte, which fails.

        restReporteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reporte)))
            .andExpect(status().isBadRequest());

        List<Reporte> reporteList = reporteRepository.findAll();
        assertThat(reporteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkHoraFinalIsRequired() throws Exception {
        int databaseSizeBeforeTest = reporteRepository.findAll().size();
        // set the field null
        reporte.setHoraFinal(null);

        // Create the Reporte, which fails.

        restReporteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reporte)))
            .andExpect(status().isBadRequest());

        List<Reporte> reporteList = reporteRepository.findAll();
        assertThat(reporteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTipoIsRequired() throws Exception {
        int databaseSizeBeforeTest = reporteRepository.findAll().size();
        // set the field null
        reporte.setTipo(null);

        // Create the Reporte, which fails.

        restReporteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reporte)))
            .andExpect(status().isBadRequest());

        List<Reporte> reporteList = reporteRepository.findAll();
        assertThat(reporteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAlertaIsRequired() throws Exception {
        int databaseSizeBeforeTest = reporteRepository.findAll().size();
        // set the field null
        reporte.setAlerta(null);

        // Create the Reporte, which fails.

        restReporteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reporte)))
            .andExpect(status().isBadRequest());

        List<Reporte> reporteList = reporteRepository.findAll();
        assertThat(reporteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllReportes() throws Exception {
        // Initialize the database
        reporteRepository.saveAndFlush(reporte);

        // Get all the reporteList
        restReporteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reporte.getId().intValue())))
            .andExpect(jsonPath("$.[*].horaInicio").value(hasItem(DEFAULT_HORA_INICIO.toString())))
            .andExpect(jsonPath("$.[*].horaFinal").value(hasItem(DEFAULT_HORA_FINAL.toString())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO)))
            .andExpect(jsonPath("$.[*].alerta").value(hasItem(DEFAULT_ALERTA.booleanValue())));
    }

    @Test
    @Transactional
    void getReporte() throws Exception {
        // Initialize the database
        reporteRepository.saveAndFlush(reporte);

        // Get the reporte
        restReporteMockMvc
            .perform(get(ENTITY_API_URL_ID, reporte.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(reporte.getId().intValue()))
            .andExpect(jsonPath("$.horaInicio").value(DEFAULT_HORA_INICIO.toString()))
            .andExpect(jsonPath("$.horaFinal").value(DEFAULT_HORA_FINAL.toString()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO))
            .andExpect(jsonPath("$.alerta").value(DEFAULT_ALERTA.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingReporte() throws Exception {
        // Get the reporte
        restReporteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingReporte() throws Exception {
        // Initialize the database
        reporteRepository.saveAndFlush(reporte);

        int databaseSizeBeforeUpdate = reporteRepository.findAll().size();

        // Update the reporte
        Reporte updatedReporte = reporteRepository.findById(reporte.getId()).get();
        // Disconnect from session so that the updates on updatedReporte are not directly saved in db
        em.detach(updatedReporte);
        updatedReporte.horaInicio(UPDATED_HORA_INICIO).horaFinal(UPDATED_HORA_FINAL).tipo(UPDATED_TIPO).alerta(UPDATED_ALERTA);

        restReporteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedReporte.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedReporte))
            )
            .andExpect(status().isOk());

        // Validate the Reporte in the database
        List<Reporte> reporteList = reporteRepository.findAll();
        assertThat(reporteList).hasSize(databaseSizeBeforeUpdate);
        Reporte testReporte = reporteList.get(reporteList.size() - 1);
        assertThat(testReporte.getHoraInicio()).isEqualTo(UPDATED_HORA_INICIO);
        assertThat(testReporte.getHoraFinal()).isEqualTo(UPDATED_HORA_FINAL);
        assertThat(testReporte.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testReporte.getAlerta()).isEqualTo(UPDATED_ALERTA);
    }

    @Test
    @Transactional
    void putNonExistingReporte() throws Exception {
        int databaseSizeBeforeUpdate = reporteRepository.findAll().size();
        reporte.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReporteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, reporte.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reporte))
            )
            .andExpect(status().isBadRequest());

        // Validate the Reporte in the database
        List<Reporte> reporteList = reporteRepository.findAll();
        assertThat(reporteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchReporte() throws Exception {
        int databaseSizeBeforeUpdate = reporteRepository.findAll().size();
        reporte.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReporteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reporte))
            )
            .andExpect(status().isBadRequest());

        // Validate the Reporte in the database
        List<Reporte> reporteList = reporteRepository.findAll();
        assertThat(reporteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamReporte() throws Exception {
        int databaseSizeBeforeUpdate = reporteRepository.findAll().size();
        reporte.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReporteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reporte)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Reporte in the database
        List<Reporte> reporteList = reporteRepository.findAll();
        assertThat(reporteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateReporteWithPatch() throws Exception {
        // Initialize the database
        reporteRepository.saveAndFlush(reporte);

        int databaseSizeBeforeUpdate = reporteRepository.findAll().size();

        // Update the reporte using partial update
        Reporte partialUpdatedReporte = new Reporte();
        partialUpdatedReporte.setId(reporte.getId());

        partialUpdatedReporte.horaFinal(UPDATED_HORA_FINAL);

        restReporteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReporte.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedReporte))
            )
            .andExpect(status().isOk());

        // Validate the Reporte in the database
        List<Reporte> reporteList = reporteRepository.findAll();
        assertThat(reporteList).hasSize(databaseSizeBeforeUpdate);
        Reporte testReporte = reporteList.get(reporteList.size() - 1);
        assertThat(testReporte.getHoraInicio()).isEqualTo(DEFAULT_HORA_INICIO);
        assertThat(testReporte.getHoraFinal()).isEqualTo(UPDATED_HORA_FINAL);
        assertThat(testReporte.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testReporte.getAlerta()).isEqualTo(DEFAULT_ALERTA);
    }

    @Test
    @Transactional
    void fullUpdateReporteWithPatch() throws Exception {
        // Initialize the database
        reporteRepository.saveAndFlush(reporte);

        int databaseSizeBeforeUpdate = reporteRepository.findAll().size();

        // Update the reporte using partial update
        Reporte partialUpdatedReporte = new Reporte();
        partialUpdatedReporte.setId(reporte.getId());

        partialUpdatedReporte.horaInicio(UPDATED_HORA_INICIO).horaFinal(UPDATED_HORA_FINAL).tipo(UPDATED_TIPO).alerta(UPDATED_ALERTA);

        restReporteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReporte.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedReporte))
            )
            .andExpect(status().isOk());

        // Validate the Reporte in the database
        List<Reporte> reporteList = reporteRepository.findAll();
        assertThat(reporteList).hasSize(databaseSizeBeforeUpdate);
        Reporte testReporte = reporteList.get(reporteList.size() - 1);
        assertThat(testReporte.getHoraInicio()).isEqualTo(UPDATED_HORA_INICIO);
        assertThat(testReporte.getHoraFinal()).isEqualTo(UPDATED_HORA_FINAL);
        assertThat(testReporte.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testReporte.getAlerta()).isEqualTo(UPDATED_ALERTA);
    }

    @Test
    @Transactional
    void patchNonExistingReporte() throws Exception {
        int databaseSizeBeforeUpdate = reporteRepository.findAll().size();
        reporte.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReporteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, reporte.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(reporte))
            )
            .andExpect(status().isBadRequest());

        // Validate the Reporte in the database
        List<Reporte> reporteList = reporteRepository.findAll();
        assertThat(reporteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchReporte() throws Exception {
        int databaseSizeBeforeUpdate = reporteRepository.findAll().size();
        reporte.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReporteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(reporte))
            )
            .andExpect(status().isBadRequest());

        // Validate the Reporte in the database
        List<Reporte> reporteList = reporteRepository.findAll();
        assertThat(reporteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamReporte() throws Exception {
        int databaseSizeBeforeUpdate = reporteRepository.findAll().size();
        reporte.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReporteMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(reporte)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Reporte in the database
        List<Reporte> reporteList = reporteRepository.findAll();
        assertThat(reporteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteReporte() throws Exception {
        // Initialize the database
        reporteRepository.saveAndFlush(reporte);

        int databaseSizeBeforeDelete = reporteRepository.findAll().size();

        // Delete the reporte
        restReporteMockMvc
            .perform(delete(ENTITY_API_URL_ID, reporte.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Reporte> reporteList = reporteRepository.findAll();
        assertThat(reporteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
