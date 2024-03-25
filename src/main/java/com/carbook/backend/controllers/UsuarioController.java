package com.carbook.backend.controllers;

import com.carbook.backend.dtos.AuthResponse;
import com.carbook.backend.dtos.CrearUsuarioDto;
import com.carbook.backend.dtos.IdentificarUsuarioDto;
import com.carbook.backend.entities.Usuario;
import com.carbook.backend.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> find() {
        return ResponseEntity.ok(usuarioService.find());
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
