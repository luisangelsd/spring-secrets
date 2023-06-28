package com.secrets.dao.oauth2.services.entitys;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
public class EntityRol implements Serializable{

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(unique = true, length = 20)
	private String nombre;


	
	//-- Getters and Setters
	public Integer getId() {return id;	}
	public void setId(Integer id) {	this.id = id;	}
	public String getNombre() {	return nombre;	}
	public void setNombre(String nombre) {this.nombre = nombre;	}
	
	
	//-- Constructores
	public EntityRol(Integer id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}
	
	public EntityRol() {

	}
	
	
}
