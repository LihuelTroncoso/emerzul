package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Paciente.
 */
@Entity
@Table(name = "paciente")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Paciente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "intervenciones")
    private String intervenciones;

    @Column(name = "antecedentes_familiares")
    private String antecedentesFamiliares;

    @NotNull
    @Column(name = "estado_general", nullable = false)
    private String estadoGeneral;

    @Column(name = "enfermedades")
    private String enfermedades;

    @Column(name = "discapacidades")
    private String discapacidades;

    @Column(name = "tipo_sangre")
    private String tipoSangre;

    @JsonIgnoreProperties(value = { "zona", "reporte" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Usuario usuario;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Paciente id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIntervenciones() {
        return this.intervenciones;
    }

    public Paciente intervenciones(String intervenciones) {
        this.setIntervenciones(intervenciones);
        return this;
    }

    public void setIntervenciones(String intervenciones) {
        this.intervenciones = intervenciones;
    }

    public String getAntecedentesFamiliares() {
        return this.antecedentesFamiliares;
    }

    public Paciente antecedentesFamiliares(String antecedentesFamiliares) {
        this.setAntecedentesFamiliares(antecedentesFamiliares);
        return this;
    }

    public void setAntecedentesFamiliares(String antecedentesFamiliares) {
        this.antecedentesFamiliares = antecedentesFamiliares;
    }

    public String getEstadoGeneral() {
        return this.estadoGeneral;
    }

    public Paciente estadoGeneral(String estadoGeneral) {
        this.setEstadoGeneral(estadoGeneral);
        return this;
    }

    public void setEstadoGeneral(String estadoGeneral) {
        this.estadoGeneral = estadoGeneral;
    }

    public String getEnfermedades() {
        return this.enfermedades;
    }

    public Paciente enfermedades(String enfermedades) {
        this.setEnfermedades(enfermedades);
        return this;
    }

    public void setEnfermedades(String enfermedades) {
        this.enfermedades = enfermedades;
    }

    public String getDiscapacidades() {
        return this.discapacidades;
    }

    public Paciente discapacidades(String discapacidades) {
        this.setDiscapacidades(discapacidades);
        return this;
    }

    public void setDiscapacidades(String discapacidades) {
        this.discapacidades = discapacidades;
    }

    public String getTipoSangre() {
        return this.tipoSangre;
    }

    public Paciente tipoSangre(String tipoSangre) {
        this.setTipoSangre(tipoSangre);
        return this;
    }

    public void setTipoSangre(String tipoSangre) {
        this.tipoSangre = tipoSangre;
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Paciente usuario(Usuario usuario) {
        this.setUsuario(usuario);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Paciente)) {
            return false;
        }
        return id != null && id.equals(((Paciente) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Paciente{" +
            "id=" + getId() +
            ", intervenciones='" + getIntervenciones() + "'" +
            ", antecedentesFamiliares='" + getAntecedentesFamiliares() + "'" +
            ", estadoGeneral='" + getEstadoGeneral() + "'" +
            ", enfermedades='" + getEnfermedades() + "'" +
            ", discapacidades='" + getDiscapacidades() + "'" +
            ", tipoSangre='" + getTipoSangre() + "'" +
            "}";
    }
}
