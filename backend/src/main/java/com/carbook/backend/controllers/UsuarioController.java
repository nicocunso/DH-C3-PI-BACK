package com.carbook.backend.controllers;

import com.carbook.backend.dtos.UserResponse;
import com.carbook.backend.entities.Usuario;
import com.carbook.backend.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @GetMapping("/admin/user")
    public List<UserResponse> listAllUsers(){
        return usuarioService.getAllUsers();
    }

    @PutMapping("/admin/user/{id}")
    public String updateUserRole(@PathVariable Long id){
        usuarioService.updateUserRole(id);
        return "Rol de usuario actualizado";
    }


}
