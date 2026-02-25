package com.example.demo.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Naufrago;
import com.example.demo.repository.NaufragoRepository;


@Service
public class NaufragoService {

    @Autowired
    private NaufragoRepository naufragoRepository;

    public List<Naufrago> listarTodos() {
        return naufragoRepository.findAll();
    }

    public Naufrago guardar(Naufrago naufrago) {
        return naufragoRepository.save(naufrago);
    }

    public Naufrago buscarPorId(Long id) {
        return naufragoRepository.findById(id).orElse(null);
    }

    public void eliminar(Long id) {
        naufragoRepository.deleteById(id);
    }
}

