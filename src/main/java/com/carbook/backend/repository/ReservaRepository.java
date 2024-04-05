package com.carbook.backend.repository;

import com.carbook.backend.entities.Auto;
import com.carbook.backend.entities.Reserva;
import com.carbook.backend.entities.Usuario;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    List<Reserva> findAllByAutoId(Long autoId);
    List<Reserva> findByUsuarioId(Long usuarioId);

    @Query(
            value = "SELECT id_auto " +
                    "FROM reservas r " +
                    "WHERE " +
                    "(r.fecha_inicio BETWEEN ?1 AND ?2) OR " +
                    "(r.fecha_devolucion BETWEEN ?1 AND ?2) OR " +
                    "(r.fecha_inicio < ?1 AND fecha_devolucion > ?2) OR " +
                    "(r.fecha_inicio > ?1 AND fecha_devolucion < ?2) " +
                    "GROUP BY id_auto",
            nativeQuery = true)
    List<Long> findAllByDate(LocalDate fechaInicio, LocalDate fechaDevolucion);
}