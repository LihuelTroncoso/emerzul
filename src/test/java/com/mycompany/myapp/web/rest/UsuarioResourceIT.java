package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Usuario;
import com.mycompany.myapp.repository.UsuarioRepository;
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
 * Integration tests for the {@link UsuarioResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UsuarioResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTRASENA = "AAAAAAAAAA";
    private static final String UPDATED_CONTRASENA = "BBBBBBBBBB";

    private static final String DEFAULT_ROL = "AAAAAAAAAA";
    private static final String UPDATED_ROL = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE_USUARIO = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_USUARIO = "BBBBBBBBBB";

    private static final String DEFAULT_SEXO = "AAAAAAAAAA";
    private static final String UPDATED_SEXO = "BBBBBBBBBB";

    private static final String DEFAULT_EDAD = "AAAAAAAAAA";
    private static final String UPDATED_EDAD = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDO = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/usuarios";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUsuarioMockMvc;

    private Usuario usuario;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Usuario createEntity(EntityManager em) {
        Usuario usuario = new Usuario()
            .nombre(DEFAULT_NOMBRE)
            .contrasena(DEFAULT_CONTRASENA)
            .rol(DEFAULT_ROL)
            .nombreUsuario(DEFAULT_NOMBRE_USUARIO)
            .sexo(DEFAULT_SEXO)
            .edad(DEFAULT_EDAD)
            .apellido(DEFAULT_APELLIDO);
        return usuario;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Usuario createUpdatedEntity(EntityManager em) {
        Usuario usuario = new Usuario()
            .nombre(UPDATED_NOMBRE)
            .contrasena(UPDATED_CONTRASENA)
            .rol(UPDATED_ROL)
            .nombreUsuario(UPDATED_NOMBRE_USUARIO)
            .sexo(UPDATED_SEXO)
            .edad(UPDATED_EDAD)
            .apellido(UPDATED_APELLIDO);
        return usuario;
    }

    @BeforeEach
    public void initTest() {
        usuario = createEntity(em);
    }

    @Test
    @Transactional
    void createUsuario() throws Exception {
        int databaseSizeBeforeCreate = usuarioRepository.findAll().size();
        // Create the Usuario
        restUsuarioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(usuario)))
            .andExpect(status().isCreated());

        // Validate the Usuario in the database
        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeCreate + 1);
        Usuario testUsuario = usuarioList.get(usuarioList.size() - 1);
        assertThat(testUsuario.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testUsuario.getContrasena()).isEqualTo(DEFAULT_CONTRASENA);
        assertThat(testUsuario.getRol()).isEqualTo(DEFAULT_ROL);
        assertThat(testUsuario.getNombreUsuario()).isEqualTo(DEFAULT_NOMBRE_USUARIO);
        assertThat(testUsuario.getSexo()).isEqualTo(DEFAULT_SEXO);
        assertThat(testUsuario.getEdad()).isEqualTo(DEFAULT_EDAD);
        assertThat(testUsuario.getApellido()).isEqualTo(DEFAULT_APELLIDO);
    }

    @Test
    @Transactional
    void createUsuarioWithExistingId() throws Exception {
        // Create the Usuario with an existing ID
        usuario.setId(1L);

        int databaseSizeBeforeCreate = usuarioRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUsuarioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(usuario)))
            .andExpect(status().isBadRequest());

        // Validate the Usuario in the database
        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = usuarioRepository.findAll().size();
        // set the field null
        usuario.setNombre(null);

        // Create the Usuario, which fails.

        restUsuarioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(usuario)))
            .andExpect(status().isBadRequest());

        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkContrasenaIsRequired() throws Exception {
        int databaseSizeBeforeTest = usuarioRepository.findAll().size();
        // set the field null
        usuario.setContrasena(null);

        // Create the Usuario, which fails.

        restUsuarioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(usuario)))
            .andExpect(status().isBadRequest());

        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRolIsRequired() throws Exception {
        int databaseSizeBeforeTest = usuarioRepository.findAll().size();
        // set the field null
        usuario.setRol(null);

        // Create the Usuario, which fails.

        restUsuarioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(usuario)))
            .andExpect(status().isBadRequest());

        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNombreUsuarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = usuarioRepository.findAll().size();
        // set the field null
        usuario.setNombreUsuario(null);

        // Create the Usuario, which fails.

        restUsuarioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(usuario)))
            .andExpect(status().isBadRequest());

        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSexoIsRequired() throws Exception {
        int databaseSizeBeforeTest = usuarioRepository.findAll().size();
        // set the field null
        usuario.setSexo(null);

        // Create the Usuario, which fails.

        restUsuarioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(usuario)))
            .andExpect(status().isBadRequest());

        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEdadIsRequired() throws Exception {
        int databaseSizeBeforeTest = usuarioRepository.findAll().size();
        // set the field null
        usuario.setEdad(null);

        // Create the Usuario, which fails.

        restUsuarioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(usuario)))
            .andExpect(status().isBadRequest());

        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkApellidoIsRequired() throws Exception {
        int databaseSizeBeforeTest = usuarioRepository.findAll().size();
        // set the field null
        usuario.setApellido(null);

        // Create the Usuario, which fails.

        restUsuarioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(usuario)))
            .andExpect(status().isBadRequest());

        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllUsuarios() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList
        restUsuarioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(usuario.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].contrasena").value(hasItem(DEFAULT_CONTRASENA)))
            .andExpect(jsonPath("$.[*].rol").value(hasItem(DEFAULT_ROL)))
            .andExpect(jsonPath("$.[*].nombreUsuario").value(hasItem(DEFAULT_NOMBRE_USUARIO)))
            .andExpect(jsonPath("$.[*].sexo").value(hasItem(DEFAULT_SEXO)))
            .andExpect(jsonPath("$.[*].edad").value(hasItem(DEFAULT_EDAD)))
            .andExpect(jsonPath("$.[*].apellido").value(hasItem(DEFAULT_APELLIDO)));
    }

    @Test
    @Transactional
    void getUsuario() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get the usuario
        restUsuarioMockMvc
            .perform(get(ENTITY_API_URL_ID, usuario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(usuario.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.contrasena").value(DEFAULT_CONTRASENA))
            .andExpect(jsonPath("$.rol").value(DEFAULT_ROL))
            .andExpect(jsonPath("$.nombreUsuario").value(DEFAULT_NOMBRE_USUARIO))
            .andExpect(jsonPath("$.sexo").value(DEFAULT_SEXO))
            .andExpect(jsonPath("$.edad").value(DEFAULT_EDAD))
            .andExpect(jsonPath("$.apellido").value(DEFAULT_APELLIDO));
    }

    @Test
    @Transactional
    void getNonExistingUsuario() throws Exception {
        // Get the usuario
        restUsuarioMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingUsuario() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        int databaseSizeBeforeUpdate = usuarioRepository.findAll().size();

        // Update the usuario
        Usuario updatedUsuario = usuarioRepository.findById(usuario.getId()).get();
        // Disconnect from session so that the updates on updatedUsuario are not directly saved in db
        em.detach(updatedUsuario);
        updatedUsuario
            .nombre(UPDATED_NOMBRE)
            .contrasena(UPDATED_CONTRASENA)
            .rol(UPDATED_ROL)
            .nombreUsuario(UPDATED_NOMBRE_USUARIO)
            .sexo(UPDATED_SEXO)
            .edad(UPDATED_EDAD)
            .apellido(UPDATED_APELLIDO);

        restUsuarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedUsuario.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedUsuario))
            )
            .andExpect(status().isOk());

        // Validate the Usuario in the database
        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeUpdate);
        Usuario testUsuario = usuarioList.get(usuarioList.size() - 1);
        assertThat(testUsuario.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testUsuario.getContrasena()).isEqualTo(UPDATED_CONTRASENA);
        assertThat(testUsuario.getRol()).isEqualTo(UPDATED_ROL);
        assertThat(testUsuario.getNombreUsuario()).isEqualTo(UPDATED_NOMBRE_USUARIO);
        assertThat(testUsuario.getSexo()).isEqualTo(UPDATED_SEXO);
        assertThat(testUsuario.getEdad()).isEqualTo(UPDATED_EDAD);
        assertThat(testUsuario.getApellido()).isEqualTo(UPDATED_APELLIDO);
    }

    @Test
    @Transactional
    void putNonExistingUsuario() throws Exception {
        int databaseSizeBeforeUpdate = usuarioRepository.findAll().size();
        usuario.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUsuarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, usuario.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(usuario))
            )
            .andExpect(status().isBadRequest());

        // Validate the Usuario in the database
        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUsuario() throws Exception {
        int databaseSizeBeforeUpdate = usuarioRepository.findAll().size();
        usuario.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUsuarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(usuario))
            )
            .andExpect(status().isBadRequest());

        // Validate the Usuario in the database
        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUsuario() throws Exception {
        int databaseSizeBeforeUpdate = usuarioRepository.findAll().size();
        usuario.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUsuarioMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(usuario)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Usuario in the database
        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUsuarioWithPatch() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        int databaseSizeBeforeUpdate = usuarioRepository.findAll().size();

        // Update the usuario using partial update
        Usuario partialUpdatedUsuario = new Usuario();
        partialUpdatedUsuario.setId(usuario.getId());

        partialUpdatedUsuario.nombre(UPDATED_NOMBRE).contrasena(UPDATED_CONTRASENA).sexo(UPDATED_SEXO);

        restUsuarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUsuario.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUsuario))
            )
            .andExpect(status().isOk());

        // Validate the Usuario in the database
        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeUpdate);
        Usuario testUsuario = usuarioList.get(usuarioList.size() - 1);
        assertThat(testUsuario.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testUsuario.getContrasena()).isEqualTo(UPDATED_CONTRASENA);
        assertThat(testUsuario.getRol()).isEqualTo(DEFAULT_ROL);
        assertThat(testUsuario.getNombreUsuario()).isEqualTo(DEFAULT_NOMBRE_USUARIO);
        assertThat(testUsuario.getSexo()).isEqualTo(UPDATED_SEXO);
        assertThat(testUsuario.getEdad()).isEqualTo(DEFAULT_EDAD);
        assertThat(testUsuario.getApellido()).isEqualTo(DEFAULT_APELLIDO);
    }

    @Test
    @Transactional
    void fullUpdateUsuarioWithPatch() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        int databaseSizeBeforeUpdate = usuarioRepository.findAll().size();

        // Update the usuario using partial update
        Usuario partialUpdatedUsuario = new Usuario();
        partialUpdatedUsuario.setId(usuario.getId());

        partialUpdatedUsuario
            .nombre(UPDATED_NOMBRE)
            .contrasena(UPDATED_CONTRASENA)
            .rol(UPDATED_ROL)
            .nombreUsuario(UPDATED_NOMBRE_USUARIO)
            .sexo(UPDATED_SEXO)
            .edad(UPDATED_EDAD)
            .apellido(UPDATED_APELLIDO);

        restUsuarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUsuario.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUsuario))
            )
            .andExpect(status().isOk());

        // Validate the Usuario in the database
        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeUpdate);
        Usuario testUsuario = usuarioList.get(usuarioList.size() - 1);
        assertThat(testUsuario.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testUsuario.getContrasena()).isEqualTo(UPDATED_CONTRASENA);
        assertThat(testUsuario.getRol()).isEqualTo(UPDATED_ROL);
        assertThat(testUsuario.getNombreUsuario()).isEqualTo(UPDATED_NOMBRE_USUARIO);
        assertThat(testUsuario.getSexo()).isEqualTo(UPDATED_SEXO);
        assertThat(testUsuario.getEdad()).isEqualTo(UPDATED_EDAD);
        assertThat(testUsuario.getApellido()).isEqualTo(UPDATED_APELLIDO);
    }

    @Test
    @Transactional
    void patchNonExistingUsuario() throws Exception {
        int databaseSizeBeforeUpdate = usuarioRepository.findAll().size();
        usuario.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUsuarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, usuario.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(usuario))
            )
            .andExpect(status().isBadRequest());

        // Validate the Usuario in the database
        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUsuario() throws Exception {
        int databaseSizeBeforeUpdate = usuarioRepository.findAll().size();
        usuario.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUsuarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(usuario))
            )
            .andExpect(status().isBadRequest());

        // Validate the Usuario in the database
        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUsuario() throws Exception {
        int databaseSizeBeforeUpdate = usuarioRepository.findAll().size();
        usuario.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUsuarioMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(usuario)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Usuario in the database
        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUsuario() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        int databaseSizeBeforeDelete = usuarioRepository.findAll().size();

        // Delete the usuario
        restUsuarioMockMvc
            .perform(delete(ENTITY_API_URL_ID, usuario.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
