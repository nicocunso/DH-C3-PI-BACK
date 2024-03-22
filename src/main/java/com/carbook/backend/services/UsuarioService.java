package com.carbook.backend.services;

import com.carbook.backend.config.JWTService;
import com.carbook.backend.dtos.AuthResponse;
import com.carbook.backend.dtos.CrearUsuarioDto;
import com.carbook.backend.dtos.IdentificarUsuarioDto;
import com.carbook.backend.entities.Usuario;
import com.carbook.backend.entities.RolUsuario;
import com.carbook.backend.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService implements UserDetailsService {
    @Autowired
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    public List<Usuario> find() {
        return usuarioRepository.findAll();
    }

    public AuthResponse create(CrearUsuarioDto crearUsuario) {
        Usuario usuario = Usuario.builder()
                .nombre(crearUsuario.getNombre())
                .apellido(crearUsuario.getApellido())
                .email(crearUsuario.getEmail())
                .contrasena(passwordEncoder.encode(crearUsuario.getContrasena()))
                .rol(RolUsuario.ROLE_USER)
                .build();
        usuarioRepository.save(usuario);
        String jwtToken = jwtService.generateToken(usuario);
        return AuthResponse.builder().token(jwtToken).build();
    }

    public AuthResponse login(IdentificarUsuarioDto identificarUsuario) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        identificarUsuario.getEmail(),
                        identificarUsuario.getContrasena()
                )
        );
        Usuario usuario = usuarioRepository.findByEmail(identificarUsuario.getEmail()).orElseThrow();
        if (usuario.getRol() != identificarUsuario.getRol()) {
            throw new AuthenticationException("Usuario no autorizado") {
            };
        }
        String jwtToken = jwtService.generateToken(usuario);
        AuthResponse authResponse = AuthResponse.builder()
                .token(jwtToken)
                .rol(usuario.getRol())
                .build();
        return authResponse;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> usuarioBuscado = usuarioRepository.findByEmail(username);
        if (usuarioBuscado.isPresent()){
            return usuarioBuscado.get();
        }else{
            throw new UsernameNotFoundException("No se encontro un usuario con ese correo");
        }
    }


    public void updateRole(Long id){
        Optional<Usuario> usuarioBuscado = usuarioRepository.findById(id);
        if (usuarioBuscado.isPresent()){
            Usuario usuario = usuarioBuscado.get();
            if(usuario.getRol() == RolUsuario.ROLE_USER){
                usuario.setRol(RolUsuario.ROLE_ADMIN);
                usuarioRepository.save(usuario);
            } else {
                usuario.setRol(RolUsuario.ROLE_USER);
                usuarioRepository.save(usuario);
            }
        }
    }
}
