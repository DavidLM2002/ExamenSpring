package com.example.demo.e2e;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import com.example.demo.entity.Habilidad;
import com.example.demo.entity.Naufrago;
import com.example.demo.repository.HabilidadRepository;
import com.example.demo.repository.NaufragoRepository;

@DataJpaTest
class Integracion {

    @Autowired
    private NaufragoRepository naufragoRepository;

    @Autowired
    private HabilidadRepository habilidadRepository;

    @Test
    void testIntegracion() {
        // 1. Definimos el nuevo naufrago
        Naufrago nuevoNaufrago = new Naufrago("Pepe", 12, "Hombre", "Isla del Tesoro", "Española", LocalDate.of(2025, 1, 15));
        Naufrago nuevoNaufrago2 = new Naufrago("Laura", 14, "Mujer", "Isla del Tesoro", "Española", LocalDate.of(2025, 1, 15));
       
        // 2. Enviamos el POST para crear el naufrago
        naufragoRepository.save(nuevoNaufrago);
        naufragoRepository.save(nuevoNaufrago2);

        // 3. Creamos una habilidad para el alumno creado
        Habilidad p1 = new Habilidad();
        p1.setNombre("Hola mundo");
        p1.setDescripcion("Descripción de la habilidad");
        p1.setDificultad("Media");
        p1.setExperiencia("10 años");
        p1.setCategoria("Supervivencia");
        p1.setNaufrago(nuevoNaufrago);
        
        Habilidad p2 = new Habilidad();
        p2.setNombre("Hola mundo2");
        p2.setDescripcion("Descripción de la habilidad2");
        p2.setDificultad("Alta");
        p2.setExperiencia("5 años");
        p2.setCategoria("Prueba");
        p2.setNaufrago(nuevoNaufrago2);
        
        habilidadRepository.save(p1);
        habilidadRepository.save(p2);
        
        // 4. Verificación y Conteo (USANDO FOR EN LUGAR DE STREAMS)
        List<Habilidad> todos = habilidadRepository.findAll();
        
        // 5 Con un for recorro todoas las habilidades y veo que estan asociadas a Pepe
		long asociadosAPepe = 0;
		for (Habilidad h : todos) {
			if (h.getNaufrago() != null && h.getNaufrago().getNombre().equals("Pepe")) {
				asociadosAPepe++;
			}
		}

		System.out.println("RESULTADOS EN H2:");
		System.out.println(" -> Total habilidades de Pepe: " + asociadosAPepe);

		// VALIDACIONES
		assertEquals(1, asociadosAPepe, "Pepe debería tener 1 habilidades asociadas");
		
        // 5 Con un for recorro todoas las habilidades y veo que estan asociadas a Laura
		long asociadosALaura = 0;
		for (Habilidad h : todos) {
			if (h.getNaufrago() != null && h.getNaufrago().getNombre().equals("Laura")) {
				asociadosALaura++;
			}
		}
		
		System.out.println("RESULTADOS EN H2:");
		System.out.println(" -> Total habilidades de Laura: " + asociadosALaura);
		
		// VALIDACIONES
		assertEquals(1, asociadosALaura, "Laura debería tener habilidades asociadas");

    }

}