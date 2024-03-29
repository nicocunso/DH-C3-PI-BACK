package com.carbook.backend.controllers;

import com.carbook.backend.dtos.ReservaRequest;
import com.carbook.backend.dtos.ReservaResponse;
import com.carbook.backend.entities.Auto;
import com.carbook.backend.entities.Reserva;
import com.carbook.backend.services.AutoService;
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

    @PostMapping("/registrar")
    public ResponseEntity<ReservaResponse> guardarReserva(@RequestBody ReservaRequest reserva){
        return ResponseEntity.ok(reservaService.save(reserva));
    }


    @GetMapping("/fechas/{id}")
    public List<LocalDate> diasReservados(@PathVariable Long id){
        return reservaService.getDatesById(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reserva> getById(@PathVariable Long id) {
        Optional<Reserva> result = reservaService.getById(id);
        System.out.println(result);
        if (result.isPresent()) {
            return ResponseEntity.ok(result.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
