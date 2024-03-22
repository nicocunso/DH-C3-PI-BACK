package com.carbook.backend.services;

import com.carbook.backend.entities.TipoAuto;
import com.carbook.backend.repository.TipoAutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoAutoService {

    @Autowired
    TipoAutoRepository tipoAutoRepository;

    public TipoAuto guardarTipoAuto(TipoAuto tipoAuto) {
        return tipoAutoRepository.save(tipoAuto);
    }

    public List<TipoAuto> listarTipos(){
        return tipoAutoRepository.findAll();
    }

}
