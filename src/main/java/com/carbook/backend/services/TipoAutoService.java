package com.carbook.backend.services;

import com.carbook.backend.entities.TipoAuto;
import com.carbook.backend.entities.Auto;
import com.carbook.backend.repository.TipoAutoRepository;
import com.carbook.backend.repository.AutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoAutoService {

    @Autowired
    TipoAutoRepository tipoAutoRepository;
    AutoRepository autoRepository;

    public TipoAuto guardarTipoAuto(TipoAuto tipoAuto) {
        return tipoAutoRepository.save(tipoAuto);
    }

    public List<TipoAuto> listarTipos(){
        return tipoAutoRepository.findAll();
    }
}
