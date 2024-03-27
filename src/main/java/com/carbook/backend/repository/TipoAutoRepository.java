package com.carbook.backend.repository;

import com.carbook.backend.entities.TipoAuto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoAutoRepository extends JpaRepository<TipoAuto, Long> {
}
