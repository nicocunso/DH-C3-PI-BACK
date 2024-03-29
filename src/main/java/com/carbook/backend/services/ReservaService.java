package com.carbook.backend.services;

import com.carbook.backend.dtos.ReservaRequest;
import com.carbook.backend.dtos.ReservaResponse;
import com.carbook.backend.entities.Auto;
import com.carbook.backend.entities.Reserva;
import com.carbook.backend.entities.Usuario;
import com.carbook.backend.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;


    @Autowired
    private AutoService autoService;

    @Autowired
    private UsuarioService usuarioService;


    public Optional<Reserva> getById(long id){return reservaRepository.findById(id);}

    public List<LocalDate> getDatesById(Long id){
        Reserva reserva = reservaRepository.getById(id);
        List<LocalDate> fechasReservadas = new ArrayList<>();
        fechasReservadas.add(reserva.getFechaInicio());
        LocalDate nuevaFecha = reserva.getFechaInicio();
        do {
            nuevaFecha = nuevaFecha.plus(1,ChronoUnit.DAYS);
            fechasReservadas.add(nuevaFecha);
        } while (!(nuevaFecha.isEqual(reserva.getFechaDevolucion())));
        return  fechasReservadas;
    }

    public List<LocalDate> getDates(Reserva reserva){
        List<LocalDate> fechasReservadas = new ArrayList<>();
        fechasReservadas.add(reserva.getFechaInicio());
        LocalDate nuevaFecha = reserva.getFechaInicio();
        do {
            nuevaFecha = nuevaFecha.plus(1,ChronoUnit.DAYS);
            fechasReservadas.add(nuevaFecha);
        } while (!(nuevaFecha.isEqual(reserva.getFechaDevolucion())));
        return  fechasReservadas;
    }

//    @Transactional
    public ReservaResponse save(ReservaRequest reserva) {
        Reserva nuevaReserva = reservaRequestAReserva(reserva);
        List<LocalDate> diasReservados = getDates(nuevaReserva);
        nuevaReserva.getAutos().setDiasReservados(diasReservados);
        reservaRepository.save(nuevaReserva); //--> resolver
        return reservaAReservaResponse(nuevaReserva);
    }

    private Reserva reservaRequestAReserva (ReservaRequest reservaRequest){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // conversion manual
        Reserva reserva=new Reserva();
        Usuario usuario = usuarioService.currentUser(authentication);
        Auto auto = autoService.getById(reservaRequest.getIdAuto()).orElseThrow();
        reserva.setFechaInicio(reservaRequest.getFechaInicio());
        reserva.setFechaDevolucion(reservaRequest.getFechaDevolucion());
        reserva.setUsuarios(usuario);
        reserva.setAutos(auto);

        return reserva;

    }

    private ReservaResponse reservaAReservaResponse (Reserva reserva){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // conversion manual
        ReservaResponse reservaResponse=new ReservaResponse();
        Usuario usuario = usuarioService.currentUser(authentication);
        reservaResponse.setId(reserva.getId());
        reservaResponse.setUsuario(usuario);
        reservaResponse.setIdAutos(Set.of(reserva.getAutos().getId()));
        reservaResponse.setFechaInicio(reserva.getFechaInicio());
        reservaResponse.setFechaDevolucion(reserva.getFechaDevolucion());

        return reservaResponse;

    }





}
