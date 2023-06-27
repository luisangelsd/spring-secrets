package com.secrets.dao.modelo.entitys;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "categorias")
public class EntityCategoria implements Serializable {



	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_categoria")
	private Long id;
	
	@Column(name = "nombre", unique = true)
	@NotEmpty(message = "El nombre no puedes estar vacio")
	private String nombre;
	
	
	
	
	//-------------------------------------------------------
	EntityCategoria(){ }
	
	//-------------------------------------------------------
	private static final long serialVersionUID = 1L;

	public Long getId() {return id;	}
	public void setId(Long id) {	this.id = id;	}
	public String getNombre() {		return nombre;	}
	public void setNombre(String nombre) {	this.nombre = nombre;	}
	
	
}
