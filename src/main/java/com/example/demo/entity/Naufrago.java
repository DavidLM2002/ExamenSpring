package com.example.demo.entity;

import java.time.LocalDate;
import java.util.List;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "naufragos") 
public class Naufrago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private int edad; 

    private String sexo;
    
    private String isla;
    
    private String nacionalidad;
    
    private LocalDate fechaRescate;

    @OneToMany(mappedBy = "naufrago", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Habilidad> habilidads;
    
    // Constructores
    public Naufrago() {}
    
    public Naufrago(String nombre, int edad, String sexo, String isla, String nacionalidad, LocalDate fechaRescate) {
    	this.nombre = nombre;
    	this.edad = edad;
    	this.sexo = sexo;
    	this.isla = isla;
    	this.nacionalidad = nacionalidad;
    	this.fechaRescate = fechaRescate;
    }
    
    
    // --- GETTERS Y SETTERS ---

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getIsla() {
		return isla;
	}

	public void setIsla(String isla) {
		this.isla = isla;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public LocalDate getFechaRescate() {
		return fechaRescate;
	}

	public void setFechaRescate(LocalDate fechaRescate) {
		this.fechaRescate = fechaRescate;
	}

	public List<Habilidad> getHabilidads() {
		return habilidads;
	}

	public void setHabilidads(List<Habilidad> habilidads) {
		this.habilidads = habilidads;
	} 
    



	
}

