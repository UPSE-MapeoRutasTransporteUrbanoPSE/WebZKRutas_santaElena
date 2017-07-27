package com.demo.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the visita database table.
 * 
 */
@Entity
@Table(name="visita")
@NamedQueries({
	@NamedQuery(name="Visita.findAll", query="SELECT s FROM Visita s"),
	@NamedQuery(name="Visitas.buscarPorPatron", 
	            query="SELECT s FROM Visita s "
	            		+ "WHERE LOWER(s.coordenadas) LIKE LOWER(:patron)")
})
public class Visita implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String coordenadas;

	@Temporal(TemporalType.DATE)
	private Date fecha;

	private String valoracion;

	//bi-directional many-to-one association to SitiosIntere
	@ManyToOne
	@JoinColumn(name="id_sitios_interes")
	private SitiosIntere sitiosIntere;

	public Visita() {
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

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getValoracion() {
		return this.valoracion;
	}

	public void setValoracion(String valoracion) {
		this.valoracion = valoracion;
	}

	public SitiosIntere getSitiosIntere() {
		return this.sitiosIntere;
	}

	public void setSitiosIntere(SitiosIntere sitiosIntere) {
		this.sitiosIntere = sitiosIntere;
	}

}