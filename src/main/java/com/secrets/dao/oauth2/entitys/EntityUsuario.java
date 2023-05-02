package com.secrets.dao.oauth2.entitys;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;



@Entity
@Table(name = "usuarios")
public class EntityUsuario implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotEmpty(message = "El username no puede estar vacio")
	@Column(unique = true)
	private String username;
	
	@NotEmpty(message = "El Password no puede estar vacia")
	@Column(length = 60)
	private String password;
	
	private Boolean enabled;
	
	@Column(name = "descripcion_perfil")
	
	private String descripcionPerfil;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "usuarios_roles",
	joinColumns = @JoinColumn(name="usuario_id"),
	inverseJoinColumns = @JoinColumn(name="rol_id"),
	uniqueConstraints = {@UniqueConstraint(columnNames = {"usuario_id","rol_id"})})
	private List<EntityRol> roles;
	
	
	//-- Valores por defecto
	@PrePersist
	private void datosPorDefecto() {
		this.enabled=true;
		this.descripcionPerfil="Sin descripción aún";
	}

	
	//-- Getters and setters
	public Integer getId() {return id;	}
	public void setId(Integer id) {	this.id = id;}
	public String getUsername() {	return username;}
	public void setUsername(String username) {	this.username = username;}
	public String getPassword() {	return password;}
	public void setPassword(String password) {	this.password = password;}
	public Boolean getEnabled() {	return enabled;	}
	public void setEnabled(Boolean enabled) {	this.enabled = enabled;	}
	public String getDescripcionPerfil() {return descripcionPerfil;	}
	public void setDescripcionPerfil(String descripcionPerfil) {this.descripcionPerfil = descripcionPerfil;	}
	public List<EntityRol> getRoles() {	return roles;}
	public void setRoles(List<EntityRol> roles) {	this.roles = roles;	}

	
	//-- Constructores
	public EntityUsuario(Integer id, @NotEmpty(message = "El username no puede estar vacio") String username,
			@NotEmpty(message = "El Password no puede estar vacia") String password, Boolean enabled,
			String descripcionPerfil, List<EntityRol> roles) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.descripcionPerfil = descripcionPerfil;
		this.roles = roles;
	}

	public EntityUsuario() {
	}
	
	
	

	
	
	

	
}
