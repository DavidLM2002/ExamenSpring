package com.example.demo.rest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import com.example.demo.entity.Naufrago;
import com.example.demo.service.NaufragoService;

@RestController
@RequestMapping(value = "/api/naufragos", produces = "application/json")
public class NaufragoRestController {

    @Autowired
    private NaufragoService naufragoService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Naufrago> listar() {
        return naufragoService.listarTodos();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Naufrago obtener(@PathVariable Long id) {
        return naufragoService.buscarPorId(id);
    }

    @PostMapping
    public Naufrago crear(@RequestBody Naufrago naufrago) {
        return naufragoService.guardar(naufrago);
    }

    // MÉTODO UPDATE (Actualizar)
    @PutMapping("/{id}")
    public Naufrago actualizar(@PathVariable Long id, @RequestBody Naufrago naufrago) {
        naufrago.setId(id); 
        return naufragoService.guardar(naufrago);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        naufragoService.eliminar(id);
    }
}

