package com.carbook.backend.controllers;

import com.carbook.backend.dtos.AuthResponse;
import com.carbook.backend.dtos.CrearUsuarioDto;
import com.carbook.backend.dtos.DetalleUsuarioDto;
import com.carbook.backend.dtos.IdentificarUsuarioDto;
import com.carbook.backend.entities.Auto;
import com.carbook.backend.entities.Usuario;
import com.carbook.backend.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;


    @GetMapping("/username")
    public long currentUserid(Authentication authentication) {
        Usuario usuario = (Usuario) authentication.getPrincipal();
        return usuario.getId();
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> find() {
        return ResponseEntity.ok(usuarioService.find());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleUsuarioDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.findById(id));
    }

    @PostMapping
    public ResponseEntity<AuthResponse> create(@RequestBody CrearUsuarioDto crearUsuario){
        return ResponseEntity.ok(usuarioService.create(crearUsuario));
    }

    @PostMapping("/identificar")
    public ResponseEntity<AuthResponse> login(@RequestBody IdentificarUsuarioDto identificarUsuario){
        return ResponseEntity.ok(usuarioService.login(identificarUsuario));
    }

    @PutMapping("/{id}")
    public String updateRole(@PathVariable Long id){
        usuarioService.updateRole(id);
        return "Rol de usuario actualizado";
    }
}
