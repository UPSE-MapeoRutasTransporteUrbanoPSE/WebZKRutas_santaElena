package com.demo.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the buses database table.
 * 
 */
@Entity
@Table(name="buses")
@NamedQueries({
	@NamedQuery(name="Bus.findAll", query="SELECT b FROM Bus b"),
	@NamedQuery(name="Buss.buscarPorPatron",
	query="SELECT b FROM Bus b "
			+ "WHERE LOWER(b.placa) LIKE LOWER(:patron)")
	
	
})

public class Bus implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String capacidad;

	private String numero;

	private String placa;

	//bi-directional many-to-one association to Cooperativa
	@ManyToOne
	@JoinColumn(name="id_cooperativa")
	private Cooperativa cooperativa;

	//bi-directional many-to-one association to Busesruta
	@OneToMany(mappedBy="bus")
	private List<Busesruta> busesrutas;

	public Bus() {
	}
	
	

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCapacidad() {
		return this.capacidad;
	}

	public void setCapacidad(String capacidad) {
		this.capacidad = capacidad;
	}

	public String getNumero() {
		return this.numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getPlaca() {
		return this.placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public Cooperativa getCooperativa() {
		return this.cooperativa;
	}

	public void setCooperativa(Cooperativa cooperativa) {
		this.cooperativa = cooperativa;
	}

	public List<Busesruta> getBusesrutas() {
		return this.busesrutas;
	}

	public void setBusesrutas(List<Busesruta> busesrutas) {
		this.busesrutas = busesrutas;
	}

	public Busesruta addBusesruta(Busesruta busesruta) {
		getBusesrutas().add(busesruta);
		busesruta.setBus(this);

		return busesruta;
	}

	public Busesruta removeBusesruta(Busesruta busesruta) {
		getBusesrutas().remove(busesruta);
		busesruta.setBus(null);

		return busesruta;
	}

}