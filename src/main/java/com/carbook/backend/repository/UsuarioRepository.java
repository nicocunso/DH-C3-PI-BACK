package com.carbook.backend.repository;

import com.carbook.backend.entities.RolUsuario;
import com.carbook.backend.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);
    List<Usuario> findAllByRol(RolUsuario rol);
}
