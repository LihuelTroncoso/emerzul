package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Llamado;
import com.mycompany.myapp.repository.LlamadoRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Llamado}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class LlamadoResource {

    private final Logger log = LoggerFactory.getLogger(LlamadoResource.class);

    private static final String ENTITY_NAME = "llamado";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LlamadoRepository llamadoRepository;

    public LlamadoResource(LlamadoRepository llamadoRepository) {
        this.llamadoRepository = llamadoRepository;
    }

    /**
     * {@code POST  /llamados} : Create a new llamado.
     *
     * @param llamado the llamado to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new llamado, or with status {@code 400 (Bad Request)} if the llamado has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/llamados")
    public ResponseEntity<Llamado> createLlamado(@Valid @RequestBody Llamado llamado) throws URISyntaxException {
        log.debug("REST request to save Llamado : {}", llamado);
        if (llamado.getId() != null) {
            throw new BadRequestAlertException("A new llamado cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Llamado result = llamadoRepository.save(llamado);
        return ResponseEntity
            .created(new URI("/api/llamados/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /llamados/:id} : Updates an existing llamado.
     *
     * @param id the id of the llamado to save.
     * @param llamado the llamado to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated llamado,
     * or with status {@code 400 (Bad Request)} if the llamado is not valid,
     * or with status {@code 500 (Internal Server Error)} if the llamado couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/llamados/{id}")
    public ResponseEntity<Llamado> updateLlamado(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Llamado llamado
    ) throws URISyntaxException {
        log.debug("REST request to update Llamado : {}, {}", id, llamado);
        if (llamado.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, llamado.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!llamadoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Llamado result = llamadoRepository.save(llamado);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, llamado.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /llamados/:id} : Partial updates given fields of an existing llamado, field will ignore if it is null
     *
     * @param id the id of the llamado to save.
     * @param llamado the llamado to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated llamado,
     * or with status {@code 400 (Bad Request)} if the llamado is not valid,
     * or with status {@code 404 (Not Found)} if the llamado is not found,
     * or with status {@code 500 (Internal Server Error)} if the llamado couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/llamados/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Llamado> partialUpdateLlamado(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Llamado llamado
    ) throws URISyntaxException {
        log.debug("REST request to partial update Llamado partially : {}, {}", id, llamado);
        if (llamado.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, llamado.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!llamadoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Llamado> result = llamadoRepository
            .findById(llamado.getId())
            .map(existingLlamado -> {
                if (llamado.getHoraInicio() != null) {
                    existingLlamado.setHoraInicio(llamado.getHoraInicio());
                }
                if (llamado.getHoraFinal() != null) {
                    existingLlamado.setHoraFinal(llamado.getHoraFinal());
                }
                if (llamado.getTipo() != null) {
                    existingLlamado.setTipo(llamado.getTipo());
                }
                if (llamado.getAtendido() != null) {
                    existingLlamado.setAtendido(llamado.getAtendido());
                }

                return existingLlamado;
            })
            .map(llamadoRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, llamado.getId().toString())
        );
    }

    /**
     * {@code GET  /llamados} : get all the llamados.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of llamados in body.
     */
    @GetMapping("/llamados")
    public List<Llamado> getAllLlamados() {
        log.debug("REST request to get all Llamados");
        return llamadoRepository.findAll();
    }

    /**
     * {@code GET  /llamados/:id} : get the "id" llamado.
     *
     * @param id the id of the llamado to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the llamado, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/llamados/{id}")
    public ResponseEntity<Llamado> getLlamado(@PathVariable Long id) {
        log.debug("REST request to get Llamado : {}", id);
        Optional<Llamado> llamado = llamadoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(llamado);
    }

    /**
     * {@code DELETE  /llamados/:id} : delete the "id" llamado.
     *
     * @param id the id of the llamado to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/llamados/{id}")
    public ResponseEntity<Void> deleteLlamado(@PathVariable Long id) {
        log.debug("REST request to delete Llamado : {}", id);
        llamadoRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
