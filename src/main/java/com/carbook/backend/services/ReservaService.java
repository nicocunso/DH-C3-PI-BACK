package com.carbook.backend.services;

import com.carbook.backend.dtos.ReservaRequest;
import com.carbook.backend.dtos.ReservaResponse;
import com.carbook.backend.dtos.ReservaUsuarioResponse;
import com.carbook.backend.dtos.UsuarioReservasDto;
import com.carbook.backend.entities.Auto;
import com.carbook.backend.entities.Imagen;
import com.carbook.backend.entities.Reserva;
import com.carbook.backend.entities.Usuario;
import com.carbook.backend.repository.ReservaRepository;
import com.carbook.backend.repository.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.yaml.snakeyaml.util.ArrayUtils;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AutoService autoService;

    @Autowired
    private UsuarioService usuarioService;


    public ReservaResponse getById(long id){
        Optional<Reserva> reserva = reservaRepository.findById(id);
        if (reserva.isPresent()){
            return reservaAReservaResponse(reserva.get());
        } else {
            return null;
        }
    }

    public List<LocalDate> listarFechasReservasPorAuto(Long autoId) {
        List<Reserva> reservas = reservaRepository.findAllByAutoId(autoId);
        List<LocalDate> fechasReservadas = new ArrayList<>();

        for (Reserva reserva : reservas) {
            fechasReservadas.add(reserva.getFechaInicio());
            do {
                fechasReservadas.add(fechasReservadas.get(fechasReservadas.size() - 1).plus(1,ChronoUnit.DAYS));
            } while (!fechasReservadas.get(fechasReservadas.size() - 1).isEqual(reserva.getFechaDevolucion()));
        }

        return fechasReservadas;
    }

    public List<LocalDate> getDatesById(Long id) {
        Reserva reserva = reservaRepository.getById(id);
        List<LocalDate> fechasReservadas = new ArrayList<>();
        fechasReservadas.add(reserva.getFechaInicio());
        LocalDate nuevaFecha = reserva.getFechaInicio();
        do {
            nuevaFecha = nuevaFecha.plus(1, ChronoUnit.DAYS);
            fechasReservadas.add(nuevaFecha);
        } while (!(nuevaFecha.isEqual(reserva.getFechaDevolucion())));
        return fechasReservadas;
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

    @Transactional
    public ReservaResponse save(ReservaRequest reserva) {
        // se convierte la reservaRequest a reserva
        Reserva nuevaReserva = reservaRequestAReserva(reserva);
        // se obtienen las fechas reservadas del auto
        List<LocalDate> diasReservados = new ArrayList<>();
        Optional<Auto> auto = autoService.getById(reserva.getIdAuto());
        if(auto.isPresent()){
            diasReservados = auto.get().getDiasReservados();

        }
        // se agregan las fechas nuevas a los dias reservados del auto
        List<LocalDate> nuevosDiasReservados = getDates(nuevaReserva);
        diasReservados.addAll(nuevosDiasReservados);

        // se settea la nueva lista de fechas
        nuevaReserva.getAuto().setDiasReservados(diasReservados);
        // se guarda la reserva
        reservaRepository.save(nuevaReserva);
        return reservaAReservaResponse(nuevaReserva);
    }

    @Transactional
    private Reserva reservaRequestAReserva (ReservaRequest reservaRequest){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // conversion manual
        Reserva reserva=new Reserva();
        System.out.println(authentication);
        Usuario usuario = usuarioService.currentUser(authentication);
        Auto auto = autoService.getById(reservaRequest.getIdAuto()).orElseThrow();
        reserva.setFechaInicio(reservaRequest.getFechaInicio());
        reserva.setFechaDevolucion(reservaRequest.getFechaDevolucion());
        reserva.setUsuario(usuario);
        reserva.setAuto(auto);

        return reserva;

    }

    @Transactional
    private ReservaResponse reservaAReservaResponse (Reserva reserva){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // conversion manual
        ReservaResponse reservaResponse=new ReservaResponse();
        Usuario usuario = usuarioService.currentUser(authentication);
        reservaResponse.setId(reserva.getId());
        reservaResponse.setUsuario(usuarioService.usuarioARespuestaUsuario(usuario));
        reservaResponse.setIdAutos(Set.of(reserva.getAuto().getId()));
        reservaResponse.setFechaInicio(reserva.getFechaInicio());
        reservaResponse.setFechaDevolucion(reserva.getFechaDevolucion());

        return reservaResponse;
    }
}