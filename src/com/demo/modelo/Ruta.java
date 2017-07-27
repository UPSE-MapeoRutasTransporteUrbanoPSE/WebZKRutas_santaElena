package com.demo.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the rutas database table.
 * 
 */
@Entity
@Table(name="rutas")
@NamedQueries({
	@NamedQuery(name="Ruta.findAll", query="SELECT r FROM Ruta r"),
	@NamedQuery(name="Rutas.buscarPorPatron",
				query="SELECT r FROM Ruta r "
						+ "WHERE LOWER(r.ruta) LIKE LOWER(:patron)")
	
	
})

public class Ruta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String costo;

	private String estado;

	private String horario;

	private String ruta;

	private String tiempo;

	//bi-directional many-to-one association to Busesruta
	@OneToMany(mappedBy="ruta")
	private List<Busesruta> busesrutas;

	public Ruta() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCosto() {
		return this.costo;
	}

	public void setCosto(String costo) {
		this.costo = costo;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getHorario() {
		return this.horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	public String getRuta() {
		return this.ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	public String getTiempo() {
		return this.tiempo;
	}

	public void setTiempo(String tiempo) {
		this.tiempo = tiempo;
	}

	public List<Busesruta> getBusesrutas() {
		return this.busesrutas;
	}

	public void setBusesrutas(List<Busesruta> busesrutas) {
		this.busesrutas = busesrutas;
	}

	public Busesruta addBusesruta(Busesruta busesruta) {
		getBusesrutas().add(busesruta);
		busesruta.setRuta(this);

		return busesruta;
	}

	public Busesruta removeBusesruta(Busesruta busesruta) {
		getBusesrutas().remove(busesruta);
		busesruta.setRuta(null);

		return busesruta;
	}

}