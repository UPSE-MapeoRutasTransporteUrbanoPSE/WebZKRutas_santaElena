package com.demo.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the lugares database table.
 * 
 */
@Entity
@Table(name="lugares")
@NamedQueries({
	@NamedQuery(name="Lugare.findAll", query="SELECT s FROM Lugare s"),
	@NamedQuery(name="Lugare.buscarPorPatron", 
	            query="SELECT s FROM Lugare s "
	            		+ "WHERE LOWER(s.descripcion) LIKE LOWER(:patron)")
})
public class Lugare implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String descripcion;

	//bi-directional many-to-one association to SitiosIntere
	@OneToMany(mappedBy="lugare")
	private List<SitiosIntere> sitiosInteres;

	public Lugare() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<SitiosIntere> getSitiosInteres() {
		return this.sitiosInteres;
	}

	public void setSitiosInteres(List<SitiosIntere> sitiosInteres) {
		this.sitiosInteres = sitiosInteres;
	}

	public SitiosIntere addSitiosIntere(SitiosIntere sitiosIntere) {
		getSitiosInteres().add(sitiosIntere);
		sitiosIntere.setLugare(this);

		return sitiosIntere;
	}

	public SitiosIntere removeSitiosIntere(SitiosIntere sitiosIntere) {
		getSitiosInteres().remove(sitiosIntere);
		sitiosIntere.setLugare(null);

		return sitiosIntere;
	}

}