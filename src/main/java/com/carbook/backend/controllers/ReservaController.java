package com.carbook.backend.controllers;

import com.carbook.backend.dtos.ReservaRequest;
import com.carbook.backend.dtos.ReservaResponse;
import com.carbook.backend.dtos.ReservaUsuarioResponse;
import com.carbook.backend.dtos.ReservasRequest;
import com.carbook.backend.entities.Auto;
import com.carbook.backend.entities.Reserva;
import com.carbook.backend.entities.Usuario;
import com.carbook.backend.services.AutoService;
import com.carbook.backend.entities.TipoAuto;
import com.carbook.backend.services.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;
    @Autowired
    private AutoService autoService;

    @GetMapping("/{fechaInicio}/{fechaDevolucion}")
    public List<Long> listarReservasPorFecha(@PathVariable LocalDate fechaInicio, @PathVariable LocalDate fechaDevolucion){
        ReservasRequest reservasRequest = new ReservasRequest(fechaInicio, fechaDevolucion);
        return reservaService.getAllByDate(reservasRequest);
    }

    @PostMapping("/registrar")
    public ResponseEntity<Reserva> guardarReserva(@RequestBody Reserva reserva) {
        return ResponseEntity.ok(reservaService.save(reserva));
    }

    @GetMapping("/fechas")
    public List<LocalDate> diasReservados(@RequestBody Reserva reserva) {
        return reservaService.getDates(reserva);
    }

    @GetMapping("/fechas/{id}")
    public List<LocalDate> diasReservados(@PathVariable Long id){
        return reservaService.getDatesById(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(reservaService.getById(id));
    }

    @GetMapping("/autos/{autoId}")
    public ResponseEntity<List<LocalDate>> listarReservasPorAuto(@PathVariable Long autoId) {
        return ResponseEntity.ok(reservaService.listarFechasReservasPorAuto(autoId));
    }

    @GetMapping("/usuarios")
    public ResponseEntity<List<Reserva>> findByUser() {
        return ResponseEntity.ok(reservaService.findByUser());
    }
}