package com.demo.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the sitios_interes database table.
 * 
 */
@Entity
@Table(name="sitios_interes")
@NamedQueries({
	@NamedQuery(name="SitiosIntere.findAll", query="SELECT s FROM SitiosIntere s"),
	@NamedQuery(name="SitiosInteres.buscarPorPatron", 
	            query="SELECT s FROM SitiosIntere s "
	            		+ "WHERE LOWER(s.descripcion) LIKE LOWER(:patron)")
})

public class SitiosIntere implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String coordenadas;

	private String descripcion;

	private String fotos;

	//bi-directional many-to-one association to Lugare
	@ManyToOne
	@JoinColumn(name="id_lugares")
	private Lugare lugare;
	
	
	//bi-directional many-to-one association to Visita
	@OneToMany(mappedBy="sitiosIntere")
	private List<Visita> visitas;

	public SitiosIntere() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCoordenadas() {
		return this.coordenadas;
	}

	public void setCoordenadas(String coordenadas) {
		this.coordenadas = coordenadas;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getFotos() {
		return this.fotos;
	}

	public void setFotos(String fotos) {
		this.fotos = fotos;
	}

	public Lugare getLugare() {
		return this.lugare;
	}

	public void setLugare(Lugare lugare) {
		this.lugare = lugare;
	}

	public List<Visita> getVisitas() {
		return this.visitas;
	}

	public void setVisitas(List<Visita> visitas) {
		this.visitas = visitas;
	}

	public Visita addVisita(Visita visita) {
		getVisitas().add(visita);
		visita.setSitiosIntere(this);

		return visita;
	}

	public Visita removeVisita(Visita visita) {
		getVisitas().remove(visita);
		visita.setSitiosIntere(null);

		return visita;
	}

	
	
	

}