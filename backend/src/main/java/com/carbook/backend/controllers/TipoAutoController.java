package com.carbook.backend.controllers;

import com.carbook.backend.entities.TipoAuto;
import com.carbook.backend.services.TipoAutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("categorias")
public class TipoAutoController {

    @Autowired
    TipoAutoService tipoAutoService;

    @GetMapping
    public ResponseEntity<List<TipoAuto>> listarTiposDeAutos(){
        return ResponseEntity.ok(tipoAutoService.listarTipos());
    }

    @PostMapping
    public ResponseEntity<TipoAuto> agregarTipoAuto(TipoAuto tipoAuto){
        return ResponseEntity.ok(tipoAutoService.guardarTipoAuto(tipoAuto));
    }
}
