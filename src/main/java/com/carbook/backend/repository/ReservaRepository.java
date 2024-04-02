package com.carbook.backend.repository;

import com.carbook.backend.entities.Reserva;
import com.carbook.backend.entities.Usuario;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.function.Function;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    @Query("SELECT r FROM Reserva r WHERE r.usuarios.id = :idUsuario")
    public List<Reserva> findByUsuariosId(Long idUsuario);
}
