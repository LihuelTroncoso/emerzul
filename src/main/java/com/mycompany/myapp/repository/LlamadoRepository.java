package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Llamado;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Llamado entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LlamadoRepository extends JpaRepository<Llamado, Long> {}
