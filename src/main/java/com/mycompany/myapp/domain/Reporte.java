package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Reporte.
 */
@Entity
@Table(name = "reporte")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Reporte implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "area", nullable = false)
    private String area;

    @NotNull
    @Column(name = "origen", nullable = false)
    private String origen;

    @NotNull
    @Column(name = "hora", nullable = false)
    private Instant hora;

    @JsonIgnoreProperties(value = { "pacientes" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Llamado llamado;

    @OneToMany(mappedBy = "reporte")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "reporte" }, allowSetters = true)
    private Set<Enfermero> enfermeros = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Reporte id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArea() {
        return this.area;
    }

    public Reporte area(String area) {
        this.setArea(area);
        return this;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getOrigen() {
        return this.origen;
    }

    public Reporte origen(String origen) {
        this.setOrigen(origen);
        return this;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public Instant getHora() {
        return this.hora;
    }

    public Reporte hora(Instant hora) {
        this.setHora(hora);
        return this;
    }

    public void setHora(Instant hora) {
        this.hora = hora;
    }

    public Llamado getLlamado() {
        return this.llamado;
    }

    public void setLlamado(Llamado llamado) {
        this.llamado = llamado;
    }

    public Reporte llamado(Llamado llamado) {
        this.setLlamado(llamado);
        return this;
    }

    public Set<Enfermero> getEnfermeros() {
        return this.enfermeros;
    }

    public void setEnfermeros(Set<Enfermero> enfermeros) {
        if (this.enfermeros != null) {
            this.enfermeros.forEach(i -> i.setReporte(null));
        }
        if (enfermeros != null) {
            enfermeros.forEach(i -> i.setReporte(this));
        }
        this.enfermeros = enfermeros;
    }

    public Reporte enfermeros(Set<Enfermero> enfermeros) {
        this.setEnfermeros(enfermeros);
        return this;
    }

    public Reporte addEnfermero(Enfermero enfermero) {
        this.enfermeros.add(enfermero);
        enfermero.setReporte(this);
        return this;
    }

    public Reporte removeEnfermero(Enfermero enfermero) {
        this.enfermeros.remove(enfermero);
        enfermero.setReporte(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Reporte)) {
            return false;
        }
        return id != null && id.equals(((Reporte) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Reporte{" +
            "id=" + getId() +
            ", area='" + getArea() + "'" +
            ", origen='" + getOrigen() + "'" +
            ", hora='" + getHora() + "'" +
            "}";
    }
}
