package com.mycompany.myapp.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Habitacion.
 */
@Entity
@Table(name = "habitacion")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Habitacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Column(name = "urgencia", nullable = false)
    private Long urgencia;

    @NotNull
    @Column(name = "zona", nullable = false)
    private String zona;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Habitacion id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Habitacion nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getUrgencia() {
        return this.urgencia;
    }

    public Habitacion urgencia(Long urgencia) {
        this.setUrgencia(urgencia);
        return this;
    }

    public void setUrgencia(Long urgencia) {
        this.urgencia = urgencia;
    }

    public String getZona() {
        return this.zona;
    }

    public Habitacion zona(String zona) {
        this.setZona(zona);
        return this;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Habitacion)) {
            return false;
        }
        return id != null && id.equals(((Habitacion) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Habitacion{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", urgencia=" + getUrgencia() +
            ", zona='" + getZona() + "'" +
            "}";
    }
}
