package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Llamado;
import com.mycompany.myapp.repository.LlamadoRepository;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link LlamadoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LlamadoResourceIT {

    private static final LocalDate DEFAULT_HORA_INICIO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_HORA_INICIO = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_HORA_FINAL = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_HORA_FINAL = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_TIPO = 1L;
    private static final Long UPDATED_TIPO = 2L;

    private static final Boolean DEFAULT_ATENDIDO = false;
    private static final Boolean UPDATED_ATENDIDO = true;

    private static final String ENTITY_API_URL = "/api/llamados";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LlamadoRepository llamadoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLlamadoMockMvc;

    private Llamado llamado;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Llamado createEntity(EntityManager em) {
        Llamado llamado = new Llamado()
            .horaInicio(DEFAULT_HORA_INICIO)
            .horaFinal(DEFAULT_HORA_FINAL)
            .tipo(DEFAULT_TIPO)
            .atendido(DEFAULT_ATENDIDO);
        return llamado;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Llamado createUpdatedEntity(EntityManager em) {
        Llamado llamado = new Llamado()
            .horaInicio(UPDATED_HORA_INICIO)
            .horaFinal(UPDATED_HORA_FINAL)
            .tipo(UPDATED_TIPO)
            .atendido(UPDATED_ATENDIDO);
        return llamado;
    }

    @BeforeEach
    public void initTest() {
        llamado = createEntity(em);
    }

    @Test
    @Transactional
    void createLlamado() throws Exception {
        int databaseSizeBeforeCreate = llamadoRepository.findAll().size();
        // Create the Llamado
        restLlamadoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(llamado)))
            .andExpect(status().isCreated());

        // Validate the Llamado in the database
        List<Llamado> llamadoList = llamadoRepository.findAll();
        assertThat(llamadoList).hasSize(databaseSizeBeforeCreate + 1);
        Llamado testLlamado = llamadoList.get(llamadoList.size() - 1);
        assertThat(testLlamado.getHoraInicio()).isEqualTo(DEFAULT_HORA_INICIO);
        assertThat(testLlamado.getHoraFinal()).isEqualTo(DEFAULT_HORA_FINAL);
        assertThat(testLlamado.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testLlamado.getAtendido()).isEqualTo(DEFAULT_ATENDIDO);
    }

    @Test
    @Transactional
    void createLlamadoWithExistingId() throws Exception {
        // Create the Llamado with an existing ID
        llamado.setId(1L);

        int databaseSizeBeforeCreate = llamadoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLlamadoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(llamado)))
            .andExpect(status().isBadRequest());

        // Validate the Llamado in the database
        List<Llamado> llamadoList = llamadoRepository.findAll();
        assertThat(llamadoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkHoraInicioIsRequired() throws Exception {
        int databaseSizeBeforeTest = llamadoRepository.findAll().size();
        // set the field null
        llamado.setHoraInicio(null);

        // Create the Llamado, which fails.

        restLlamadoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(llamado)))
            .andExpect(status().isBadRequest());

        List<Llamado> llamadoList = llamadoRepository.findAll();
        assertThat(llamadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkHoraFinalIsRequired() throws Exception {
        int databaseSizeBeforeTest = llamadoRepository.findAll().size();
        // set the field null
        llamado.setHoraFinal(null);

        // Create the Llamado, which fails.

        restLlamadoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(llamado)))
            .andExpect(status().isBadRequest());

        List<Llamado> llamadoList = llamadoRepository.findAll();
        assertThat(llamadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTipoIsRequired() throws Exception {
        int databaseSizeBeforeTest = llamadoRepository.findAll().size();
        // set the field null
        llamado.setTipo(null);

        // Create the Llamado, which fails.

        restLlamadoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(llamado)))
            .andExpect(status().isBadRequest());

        List<Llamado> llamadoList = llamadoRepository.findAll();
        assertThat(llamadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAtendidoIsRequired() throws Exception {
        int databaseSizeBeforeTest = llamadoRepository.findAll().size();
        // set the field null
        llamado.setAtendido(null);

        // Create the Llamado, which fails.

        restLlamadoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(llamado)))
            .andExpect(status().isBadRequest());

        List<Llamado> llamadoList = llamadoRepository.findAll();
        assertThat(llamadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLlamados() throws Exception {
        // Initialize the database
        llamadoRepository.saveAndFlush(llamado);

        // Get all the llamadoList
        restLlamadoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(llamado.getId().intValue())))
            .andExpect(jsonPath("$.[*].horaInicio").value(hasItem(DEFAULT_HORA_INICIO.toString())))
            .andExpect(jsonPath("$.[*].horaFinal").value(hasItem(DEFAULT_HORA_FINAL.toString())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.intValue())))
            .andExpect(jsonPath("$.[*].atendido").value(hasItem(DEFAULT_ATENDIDO.booleanValue())));
    }

    @Test
    @Transactional
    void getLlamado() throws Exception {
        // Initialize the database
        llamadoRepository.saveAndFlush(llamado);

        // Get the llamado
        restLlamadoMockMvc
            .perform(get(ENTITY_API_URL_ID, llamado.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(llamado.getId().intValue()))
            .andExpect(jsonPath("$.horaInicio").value(DEFAULT_HORA_INICIO.toString()))
            .andExpect(jsonPath("$.horaFinal").value(DEFAULT_HORA_FINAL.toString()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.intValue()))
            .andExpect(jsonPath("$.atendido").value(DEFAULT_ATENDIDO.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingLlamado() throws Exception {
        // Get the llamado
        restLlamadoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLlamado() throws Exception {
        // Initialize the database
        llamadoRepository.saveAndFlush(llamado);

        int databaseSizeBeforeUpdate = llamadoRepository.findAll().size();

        // Update the llamado
        Llamado updatedLlamado = llamadoRepository.findById(llamado.getId()).get();
        // Disconnect from session so that the updates on updatedLlamado are not directly saved in db
        em.detach(updatedLlamado);
        updatedLlamado.horaInicio(UPDATED_HORA_INICIO).horaFinal(UPDATED_HORA_FINAL).tipo(UPDATED_TIPO).atendido(UPDATED_ATENDIDO);

        restLlamadoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLlamado.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLlamado))
            )
            .andExpect(status().isOk());

        // Validate the Llamado in the database
        List<Llamado> llamadoList = llamadoRepository.findAll();
        assertThat(llamadoList).hasSize(databaseSizeBeforeUpdate);
        Llamado testLlamado = llamadoList.get(llamadoList.size() - 1);
        assertThat(testLlamado.getHoraInicio()).isEqualTo(UPDATED_HORA_INICIO);
        assertThat(testLlamado.getHoraFinal()).isEqualTo(UPDATED_HORA_FINAL);
        assertThat(testLlamado.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testLlamado.getAtendido()).isEqualTo(UPDATED_ATENDIDO);
    }

    @Test
    @Transactional
    void putNonExistingLlamado() throws Exception {
        int databaseSizeBeforeUpdate = llamadoRepository.findAll().size();
        llamado.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLlamadoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, llamado.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(llamado))
            )
            .andExpect(status().isBadRequest());

        // Validate the Llamado in the database
        List<Llamado> llamadoList = llamadoRepository.findAll();
        assertThat(llamadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLlamado() throws Exception {
        int databaseSizeBeforeUpdate = llamadoRepository.findAll().size();
        llamado.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLlamadoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(llamado))
            )
            .andExpect(status().isBadRequest());

        // Validate the Llamado in the database
        List<Llamado> llamadoList = llamadoRepository.findAll();
        assertThat(llamadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLlamado() throws Exception {
        int databaseSizeBeforeUpdate = llamadoRepository.findAll().size();
        llamado.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLlamadoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(llamado)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Llamado in the database
        List<Llamado> llamadoList = llamadoRepository.findAll();
        assertThat(llamadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLlamadoWithPatch() throws Exception {
        // Initialize the database
        llamadoRepository.saveAndFlush(llamado);

        int databaseSizeBeforeUpdate = llamadoRepository.findAll().size();

        // Update the llamado using partial update
        Llamado partialUpdatedLlamado = new Llamado();
        partialUpdatedLlamado.setId(llamado.getId());

        partialUpdatedLlamado.tipo(UPDATED_TIPO).atendido(UPDATED_ATENDIDO);

        restLlamadoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLlamado.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLlamado))
            )
            .andExpect(status().isOk());

        // Validate the Llamado in the database
        List<Llamado> llamadoList = llamadoRepository.findAll();
        assertThat(llamadoList).hasSize(databaseSizeBeforeUpdate);
        Llamado testLlamado = llamadoList.get(llamadoList.size() - 1);
        assertThat(testLlamado.getHoraInicio()).isEqualTo(DEFAULT_HORA_INICIO);
        assertThat(testLlamado.getHoraFinal()).isEqualTo(DEFAULT_HORA_FINAL);
        assertThat(testLlamado.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testLlamado.getAtendido()).isEqualTo(UPDATED_ATENDIDO);
    }

    @Test
    @Transactional
    void fullUpdateLlamadoWithPatch() throws Exception {
        // Initialize the database
        llamadoRepository.saveAndFlush(llamado);

        int databaseSizeBeforeUpdate = llamadoRepository.findAll().size();

        // Update the llamado using partial update
        Llamado partialUpdatedLlamado = new Llamado();
        partialUpdatedLlamado.setId(llamado.getId());

        partialUpdatedLlamado.horaInicio(UPDATED_HORA_INICIO).horaFinal(UPDATED_HORA_FINAL).tipo(UPDATED_TIPO).atendido(UPDATED_ATENDIDO);

        restLlamadoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLlamado.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLlamado))
            )
            .andExpect(status().isOk());

        // Validate the Llamado in the database
        List<Llamado> llamadoList = llamadoRepository.findAll();
        assertThat(llamadoList).hasSize(databaseSizeBeforeUpdate);
        Llamado testLlamado = llamadoList.get(llamadoList.size() - 1);
        assertThat(testLlamado.getHoraInicio()).isEqualTo(UPDATED_HORA_INICIO);
        assertThat(testLlamado.getHoraFinal()).isEqualTo(UPDATED_HORA_FINAL);
        assertThat(testLlamado.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testLlamado.getAtendido()).isEqualTo(UPDATED_ATENDIDO);
    }

    @Test
    @Transactional
    void patchNonExistingLlamado() throws Exception {
        int databaseSizeBeforeUpdate = llamadoRepository.findAll().size();
        llamado.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLlamadoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, llamado.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(llamado))
            )
            .andExpect(status().isBadRequest());

        // Validate the Llamado in the database
        List<Llamado> llamadoList = llamadoRepository.findAll();
        assertThat(llamadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLlamado() throws Exception {
        int databaseSizeBeforeUpdate = llamadoRepository.findAll().size();
        llamado.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLlamadoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(llamado))
            )
            .andExpect(status().isBadRequest());

        // Validate the Llamado in the database
        List<Llamado> llamadoList = llamadoRepository.findAll();
        assertThat(llamadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLlamado() throws Exception {
        int databaseSizeBeforeUpdate = llamadoRepository.findAll().size();
        llamado.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLlamadoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(llamado)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Llamado in the database
        List<Llamado> llamadoList = llamadoRepository.findAll();
        assertThat(llamadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLlamado() throws Exception {
        // Initialize the database
        llamadoRepository.saveAndFlush(llamado);

        int databaseSizeBeforeDelete = llamadoRepository.findAll().size();

        // Delete the llamado
        restLlamadoMockMvc
            .perform(delete(ENTITY_API_URL_ID, llamado.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Llamado> llamadoList = llamadoRepository.findAll();
        assertThat(llamadoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
