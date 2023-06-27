package com.secrets.dao.modelo.entitys;

import java.io.Serializable;
import java.time.LocalDate;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;

@Entity()
@Table(name = "secretos")
public class EntitySecreto implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_secreto")
	private Long id;
	
	@NotEmpty(message = "El secreto no puede estar vacio")
	@Size(max = 500, message = "El secreto no puede tener más de 500 caracteres")
	private String secreto;
	
	
	@Column(name = "fecha_creacion")
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private LocalDate fCreacion;
	

	@NotNull(message =  "La categoria no puede estar vacia")
	@ManyToOne
	@JoinColumn(name="id_categoria")
	private EntityCategoria entityCategoria;
	
	
	//--------------------------------------------------------------------------
	
	//-- Methods
	@PrePersist
	private void prePersist() {
			fCreacion= LocalDate.now();
		
	}
	
	public EntitySecreto() {}
	
	

	//--------------------------------------------------------------------------
	public Long getId() {return id;	}
	public void setId(Long id) {this.id = id;	}
	public String getSecreto() {return secreto;	}
	public void setSecreto(String secreto) {this.secreto = secreto;	}

	public EntityCategoria getEntityCategoria() {
		return entityCategoria;
	}

	public void setEntityCategoria(EntityCategoria entityCategoria) {
		this.entityCategoria = entityCategoria;
	}

	public LocalDate getfCreacion() {return fCreacion;	}
	public void setfCreacion(LocalDate fCreacion) {this.fCreacion = fCreacion;	}
	
	private static final long serialVersionUID = 1L;
	
	

}
