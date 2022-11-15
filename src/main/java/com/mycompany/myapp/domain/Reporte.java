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
    @Column(name = "hora_inicio", nullable = false)
    private Instant horaInicio;

    @NotNull
    @Column(name = "hora_final", nullable = false)
    private Instant horaFinal;

    @NotNull
    @Column(name = "tipo", nullable = false)
    private String tipo;

    @NotNull
    @Column(name = "alerta", nullable = false)
    private Boolean alerta;

    @OneToMany(mappedBy = "reporte")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "zona", "reporte" }, allowSetters = true)
    private Set<Usuario> usuarios = new HashSet<>();

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

    public Instant getHoraInicio() {
        return this.horaInicio;
    }

    public Reporte horaInicio(Instant horaInicio) {
        this.setHoraInicio(horaInicio);
        return this;
    }

    public void setHoraInicio(Instant horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Instant getHoraFinal() {
        return this.horaFinal;
    }

    public Reporte horaFinal(Instant horaFinal) {
        this.setHoraFinal(horaFinal);
        return this;
    }

    public void setHoraFinal(Instant horaFinal) {
        this.horaFinal = horaFinal;
    }

    public String getTipo() {
        return this.tipo;
    }

    public Reporte tipo(String tipo) {
        this.setTipo(tipo);
        return this;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Boolean getAlerta() {
        return this.alerta;
    }

    public Reporte alerta(Boolean alerta) {
        this.setAlerta(alerta);
        return this;
    }

    public void setAlerta(Boolean alerta) {
        this.alerta = alerta;
    }

    public Set<Usuario> getUsuarios() {
        return this.usuarios;
    }

    public void setUsuarios(Set<Usuario> usuarios) {
        if (this.usuarios != null) {
            this.usuarios.forEach(i -> i.setReporte(null));
        }
        if (usuarios != null) {
            usuarios.forEach(i -> i.setReporte(this));
        }
        this.usuarios = usuarios;
    }

    public Reporte usuarios(Set<Usuario> usuarios) {
        this.setUsuarios(usuarios);
        return this;
    }

    public Reporte addUsuario(Usuario usuario) {
        this.usuarios.add(usuario);
        usuario.setReporte(this);
        return this;
    }

    public Reporte removeUsuario(Usuario usuario) {
        this.usuarios.remove(usuario);
        usuario.setReporte(null);
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
            ", horaInicio='" + getHoraInicio() + "'" +
            ", horaFinal='" + getHoraFinal() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", alerta='" + getAlerta() + "'" +
            "}";
    }
}
