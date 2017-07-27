package com.demo.modelo;

import java.io.Serializable;
import javax.persistence.*;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.List;

@Table(name="usuario")
@Entity
@NamedQueries({
	@NamedQuery(name="Usuario.buscaUsuario", 
	query="SELECT u "
	+ "FROM Usuario u "
	+ "WHERE u.usuario = :nombreUsuario"),
	@NamedQuery(name="Usuarios.buscarPorPatron",
	query="SELECT u FROM Usuario u "
    		+ "WHERE LOWER(u.usuario) LIKE LOWER(:patron)")
})

public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String clave;

	private String estado;

	private String nombre;

	private String usuario;

	@OneToMany(mappedBy="usuario")
	private List<UsuarioPrivilegio> usuarioPrivilegios;

	public Usuario() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getClave() {
		return this.clave;
	}

	public void setClave(String clave) {
		String texto = clave;
		String textoEC = DigestUtils.md5Hex(texto);
		this.clave = clave;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getUsuario() {
		return this.usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public List<UsuarioPrivilegio> getUsuarioPrivilegios() {
		return this.usuarioPrivilegios;
	}

	public void setUsuarioPrivilegios(List<UsuarioPrivilegio> usuarioPrivilegios) {
		this.usuarioPrivilegios = usuarioPrivilegios;
	}

	public UsuarioPrivilegio addUsuarioPrivilegio(UsuarioPrivilegio usuarioPrivilegio) {
		getUsuarioPrivilegios().add(usuarioPrivilegio);
		usuarioPrivilegio.setUsuario(this);
		return usuarioPrivilegio;
	}

	public UsuarioPrivilegio removeUsuarioPrivilegio(UsuarioPrivilegio usuarioPrivilegio) {
		getUsuarioPrivilegios().remove(usuarioPrivilegio);
		usuarioPrivilegio.setUsuario(null);
		return usuarioPrivilegio;
	}

}