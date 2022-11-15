package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Reporte;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Reporte entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReporteRepository extends JpaRepository<Reporte, Long> {}
