package com.secrets.dao.modelo.entitys;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity()
@Table(name = "secretos")
public class EntitySecretos implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "El secreto no puede estar vacio")
	@Size(max = 500, message = "El secreto no puede tener más de 500 caracteres")
	private String secreto;
	
	@NotEmpty(message = "La categoria no puede estar vacia")
	private String categoria;
	

	@Column(name = "fecha_creacion")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date fCreacion;
	
	
	//** Methods
	
	@PrePersist
	private void prePersist() {
		fCreacion=new Date();
	}
	
	public EntitySecretos() {}
	
	


	//** Getters and Setters
	private static final long serialVersionUID = 1L;

	public Long getId() {return id;	}
	public void setId(Long id) {this.id = id;	}
	public String getSecreto() {return secreto;	}
	public void setSecreto(String secreto) {this.secreto = secreto;	}
	public String getCategoria() {return categoria;	}
	public void setCategoria(String categoria) {this.categoria = categoria;	}
	public Date getfCreacion() {return fCreacion;	}
	public void setfCreacion(Date fCreacion) {this.fCreacion = fCreacion;	}
	
	

}
