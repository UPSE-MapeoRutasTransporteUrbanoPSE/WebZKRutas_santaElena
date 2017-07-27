package com.demo.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * The persistent class for the busesrutas database table.
 * 
 */
@Entity
@Table(name = "busesrutas")
@NamedQueries({ @NamedQuery(name = "Busesruta.findAll", query = "SELECT b FROM Busesruta b"),
		@NamedQuery(name = "Busesrutas.buscarPorPatron", query = "SELECT b FROM Busesruta b "
				+ "WHERE LOWER(b.bus.placa) LIKE LOWER(:patron)"),

//query de consulta de busqueda por fecha y ruta 
@NamedQuery(name = "Busesrutas.buscarfechayruta", query = "SELECT b FROM Busesruta b WHERE b.fecha=:fecha AND b.ruta.ruta=:ruta")

})
public class Busesruta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Temporal(TemporalType.DATE)
	private Date fecha;

	// bi-directional many-to-one association to Bus
	@ManyToOne
	@JoinColumn(name = "id_buses")
	private Bus bus;

	// bi-directional many-to-one association to Ruta
	@ManyToOne
	@JoinColumn(name = "id_rutas")
	private Ruta ruta;

	public Busesruta() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Bus getBus() {
		return this.bus;
	}

	public void setBus(Bus bus) {
		this.bus = bus;
	}

	public Ruta getRuta() {
		return this.ruta;
	}

	public void setRuta(Ruta ruta) {
		this.ruta = ruta;
	}
	

}