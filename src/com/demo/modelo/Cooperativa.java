package com.demo.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the cooperativa database table.
 * 
 */
@Entity
@NamedQueries({ @NamedQuery(name = "Cooperativa.findAll",query = "SELECT c FROM Cooperativa c"),
				@NamedQuery(name = "Cooperativas.buscaPorPatron",
				query = "SELECT c FROM Cooperativa c " + "WHERE LOWER(c.nombre) LIKE LOWER(:patron)") })

public class Cooperativa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String direccion;

	private String nombre;

	private String telefono;

	// bi-directional many-to-one association to Bus
	@OneToMany(mappedBy = "cooperativa")
	private List<Bus> buses;

	public Cooperativa() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public List<Bus> getBuses() {
		return this.buses;
	}

	public void setBuses(List<Bus> buses) {
		this.buses = buses;
	}

	public Bus addBus(Bus bus) {
		getBuses().add(bus);
		bus.setCooperativa(this);

		return bus;
	}

	public Bus removeBus(Bus bus) {
		getBuses().remove(bus);
		bus.setCooperativa(null);

		return bus;
	}

}