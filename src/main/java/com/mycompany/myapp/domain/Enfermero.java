package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Enfermero.
 */
@Entity
@Table(name = "enfermero")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Enfermero implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Column(name = "matricula", nullable = false)
    private Long matricula;

    @ManyToOne
    @JsonIgnoreProperties(value = { "llamado", "enfermeros" }, allowSetters = true)
    private Reporte reporte;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Enfermero id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Enfermero nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getMatricula() {
        return this.matricula;
    }

    public Enfermero matricula(Long matricula) {
        this.setMatricula(matricula);
        return this;
    }

    public void setMatricula(Long matricula) {
        this.matricula = matricula;
    }

    public Reporte getReporte() {
        return this.reporte;
    }

    public void setReporte(Reporte reporte) {
        this.reporte = reporte;
    }

    public Enfermero reporte(Reporte reporte) {
        this.setReporte(reporte);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Enfermero)) {
            return false;
        }
        return id != null && id.equals(((Enfermero) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Enfermero{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", matricula=" + getMatricula() +
            "}";
    }
}
