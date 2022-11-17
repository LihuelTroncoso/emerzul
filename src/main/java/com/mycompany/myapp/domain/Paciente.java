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

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Column(name = "dni", nullable = false)
    private Long dni;

    @NotNull
    @Column(name = "sexo", nullable = false)
    private String sexo;

    @NotNull
    @Column(name = "edad", nullable = false)
    private Long edad;

    @Column(name = "intervenciones")
    private String intervenciones;

    @Column(name = "antecedentes_familiares")
    private String antecedentesFamiliares;

    @NotNull
    @Column(name = "estado", nullable = false)
    private String estado;

    @Column(name = "enfermedades")
    private String enfermedades;

    @Column(name = "discapacidades")
    private String discapacidades;

    @NotNull
    @Column(name = "tipo_sangre", nullable = false)
    private String tipoSangre;

    @OneToOne
    @JoinColumn(unique = true)
    private Habitacion habitacion;

    @ManyToOne
    @JsonIgnoreProperties(value = { "pacientes" }, allowSetters = true)
    private Llamado llamado;

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

    public String getNombre() {
        return this.nombre;
    }

    public Paciente nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getDni() {
        return this.dni;
    }

    public Paciente dni(Long dni) {
        this.setDni(dni);
        return this;
    }

    public void setDni(Long dni) {
        this.dni = dni;
    }

    public String getSexo() {
        return this.sexo;
    }

    public Paciente sexo(String sexo) {
        this.setSexo(sexo);
        return this;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Long getEdad() {
        return this.edad;
    }

    public Paciente edad(Long edad) {
        this.setEdad(edad);
        return this;
    }

    public void setEdad(Long edad) {
        this.edad = edad;
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

    public String getEstado() {
        return this.estado;
    }

    public Paciente estado(String estado) {
        this.setEstado(estado);
        return this;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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

    public Habitacion getHabitacion() {
        return this.habitacion;
    }

    public void setHabitacion(Habitacion habitacion) {
        this.habitacion = habitacion;
    }

    public Paciente habitacion(Habitacion habitacion) {
        this.setHabitacion(habitacion);
        return this;
    }

    public Llamado getLlamado() {
        return this.llamado;
    }

    public void setLlamado(Llamado llamado) {
        this.llamado = llamado;
    }

    public Paciente llamado(Llamado llamado) {
        this.setLlamado(llamado);
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
            ", nombre='" + getNombre() + "'" +
            ", dni=" + getDni() +
            ", sexo='" + getSexo() + "'" +
            ", edad=" + getEdad() +
            ", intervenciones='" + getIntervenciones() + "'" +
            ", antecedentesFamiliares='" + getAntecedentesFamiliares() + "'" +
            ", estado='" + getEstado() + "'" +
            ", enfermedades='" + getEnfermedades() + "'" +
            ", discapacidades='" + getDiscapacidades() + "'" +
            ", tipoSangre='" + getTipoSangre() + "'" +
            "}";
    }
}
