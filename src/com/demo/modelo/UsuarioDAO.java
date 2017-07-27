package com.demo.modelo;

import java.util.List;
import javax.persistence.Query;

public class UsuarioDAO extends ClaseDAO {

	public Usuario getUsuario(String nombreUsuario) {
		Usuario usuario; 
		Query consulta;
		consulta = getEntityManager().createNamedQuery("Usuario.buscaUsuario");
		consulta.setParameter("nombreUsuario", nombreUsuario);
		usuario = (Usuario) consulta.getSingleResult();
		return usuario;
	}
	
	public Usuario getUsuarioPorId(int id){
		Usuario usuario;
		usuario = (Usuario) getEntityManager().find(Usuario.class, id);
		return usuario;
	}
	
	public List<Usuario> getUsuarios(String value){
		List<Usuario> resultado;
		String patron = value;
		
		if (value == null || value.length() == 0) {
			patron = "%";
		}else{
			patron = "%" + patron.toLowerCase() + "%";
		}

		Query query = getEntityManager().createNamedQuery("Usuarios.buscarPorPatron");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setParameter("patron", patron);
		resultado =(List<Usuario>) query.getResultList();
		return resultado;
	}
	
}
