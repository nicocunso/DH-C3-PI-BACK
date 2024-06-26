package com.carbook.backend.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class WebSecurityConfig {

    private final JWTFilter jwtFilter;
    private final AuthenticationProvider authenticationProvider;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth.requestMatchers(publicEndpoints()).permitAll()
          //              .requestMatchers("/usuario/admin/**").hasRole("ADMIN") <-- Pendiente de implementar
                        .anyRequest().authenticated())
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    private RequestMatcher publicEndpoints(){
        return new OrRequestMatcher(  // <-- aqui van los endpoints publicos
                new AntPathRequestMatcher("/demo/publico"),
                new AntPathRequestMatcher("/auth/**"),
                new AntPathRequestMatcher("/autos/**"),
                new AntPathRequestMatcher("/autos/**/imagenes"),
                new AntPathRequestMatcher("/categorias"),
                new AntPathRequestMatcher("/categorias/**/autos"),
                new AntPathRequestMatcher("/usuario/admin/**"),
                new AntPathRequestMatcher("/usuarios"),
                new AntPathRequestMatcher("/usuarios/**"),
                new AntPathRequestMatcher("/usuarios/roles/**"),
                new AntPathRequestMatcher("/usuarios/identificar"),
                new AntPathRequestMatcher("/reservas"),
                new AntPathRequestMatcher("/reservas/autos/**"),
                new AntPathRequestMatcher("/reservas/registrar"),
                new AntPathRequestMatcher("/reservas/**/**")
        );
    }
}
