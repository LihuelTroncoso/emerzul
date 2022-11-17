package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Llamado.
 */
@Entity
@Table(name = "llamado")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Llamado implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "hora_inicio", nullable = false)
    private LocalDate horaInicio;

    @NotNull
    @Column(name = "hora_final", nullable = false)
    private LocalDate horaFinal;

    @NotNull
    @Column(name = "tipo", nullable = false)
    private Long tipo;

    @NotNull
    @Column(name = "atendido", nullable = false)
    private Boolean atendido;

    @OneToMany(mappedBy = "llamado")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "habitacion", "llamado" }, allowSetters = true)
    private Set<Paciente> pacientes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Llamado id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getHoraInicio() {
        return this.horaInicio;
    }

    public Llamado horaInicio(LocalDate horaInicio) {
        this.setHoraInicio(horaInicio);
        return this;
    }

    public void setHoraInicio(LocalDate horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalDate getHoraFinal() {
        return this.horaFinal;
    }

    public Llamado horaFinal(LocalDate horaFinal) {
        this.setHoraFinal(horaFinal);
        return this;
    }

    public void setHoraFinal(LocalDate horaFinal) {
        this.horaFinal = horaFinal;
    }

    public Long getTipo() {
        return this.tipo;
    }

    public Llamado tipo(Long tipo) {
        this.setTipo(tipo);
        return this;
    }

    public void setTipo(Long tipo) {
        this.tipo = tipo;
    }

    public Boolean getAtendido() {
        return this.atendido;
    }

    public Llamado atendido(Boolean atendido) {
        this.setAtendido(atendido);
        return this;
    }

    public void setAtendido(Boolean atendido) {
        this.atendido = atendido;
    }

    public Set<Paciente> getPacientes() {
        return this.pacientes;
    }

    public void setPacientes(Set<Paciente> pacientes) {
        if (this.pacientes != null) {
            this.pacientes.forEach(i -> i.setLlamado(null));
        }
        if (pacientes != null) {
            pacientes.forEach(i -> i.setLlamado(this));
        }
        this.pacientes = pacientes;
    }

    public Llamado pacientes(Set<Paciente> pacientes) {
        this.setPacientes(pacientes);
        return this;
    }

    public Llamado addPaciente(Paciente paciente) {
        this.pacientes.add(paciente);
        paciente.setLlamado(this);
        return this;
    }

    public Llamado removePaciente(Paciente paciente) {
        this.pacientes.remove(paciente);
        paciente.setLlamado(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Llamado)) {
            return false;
        }
        return id != null && id.equals(((Llamado) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Llamado{" +
            "id=" + getId() +
            ", horaInicio='" + getHoraInicio() + "'" +
            ", horaFinal='" + getHoraFinal() + "'" +
            ", tipo=" + getTipo() +
            ", atendido='" + getAtendido() + "'" +
            "}";
    }
}
