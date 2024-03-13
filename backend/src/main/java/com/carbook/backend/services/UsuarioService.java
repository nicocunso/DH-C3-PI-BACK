package com.carbook.backend.services;

import com.carbook.backend.dtos.UserResponse;
import com.carbook.backend.entities.Usuario;
import com.carbook.backend.entities.UsuarioRole;
import com.carbook.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService implements UserDetailsService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> usuarioBuscado = usuarioRepository.findByEmail(username);
        if (usuarioBuscado.isPresent()){
            return usuarioBuscado.get();
        }else{
            throw new UsernameNotFoundException("No se encontro un usuario con ese correo");
        }
    }


    public void updateUserRole(Long id){
        Optional<Usuario> usuarioBuscado = usuarioRepository.findById(id);
        if (usuarioBuscado.isPresent()){
            Usuario usuario = usuarioBuscado.get();
            if(usuario.getUsuarioRole() == UsuarioRole.ROLE_USER){
                usuario.setUsuarioRole(UsuarioRole.ROLE_ADMIN);
                usuarioRepository.save(usuario);
            } else {
                usuario.setUsuarioRole(UsuarioRole.ROLE_USER);
                usuarioRepository.save(usuario);
            }
        }
    }

    public List<UserResponse> getAllUsers(){

        List<Usuario> Usuarios = usuarioRepository.findAll();
        List<UserResponse> listaDeUsuarios = new ArrayList<>();
        for (Usuario usuario: Usuarios){
            UserResponse usuarioDto = UserResponse.builder()
                    .id(usuario.getId())
                    .nombre(usuario.getNombre())
                    .apellido(usuario.getApellido())
                    .email(usuario.getEmail())
                    .build();
            listaDeUsuarios.add(usuarioDto);
        }
        return listaDeUsuarios;
    }
}
