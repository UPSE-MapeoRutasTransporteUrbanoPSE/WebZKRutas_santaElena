package com.demo.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

public class OpcionDAO extends ClaseDAO{
	
	/**
	 * Retorna la lista de opciones disponibles.
	 */
		public List<Opcion> getOpciones(){
			
			// creo variable retorno tipo List
			List<Opcion> retorno = new ArrayList<Opcion>();
			//creo la consulta heredando getEntityManager
			Query consulta = getEntityManager().createNamedQuery("Opcion.findAll");
			
			//Ejecuta la consulta y la retorna en forma de lista
			//El cast es para darle la forma de List para meterla en el objeto retorno
			retorno = (List<Opcion>) consulta.getResultList();
			
			return retorno;
		}
		
		/**
		 * Retorna la lista de opciones disponibles filtradas por 
		 * los privilegios del usuario
		 * @return
		 */
		public List<Opcion> getOpcionesFiltradas() {
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			List<Opcion> opciones = new ArrayList<Opcion>();
			List<Opcion> retorno = new ArrayList<Opcion>();

			Query query = getEntityManager().createNamedQuery("Opcion.findAll");
			opciones = (List<Opcion>) query.getResultList();
			
			// Obtiene el usuario logoneado
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			// Busco el usuario en la base
			Usuario usuario = usuarioDAO.getUsuario(user.getUsername()); 
			
			// Filtra solo las opciones a las que tiene acceso el usuario.
			for (Opcion opcion : opciones) {
				// Por cada opcion, verifica si esta asignada a 
				// algun privilegio que tenga el usuario.
				boolean opcionInsertada = false; 
				for (OpcionPrivilegio opcionPrivilegio : opcion.getOpcionPrivilegios()) {
					for (UsuarioPrivilegio usuarioPrivilegio : usuario.getUsuarioPrivilegios()) {
						if (opcionPrivilegio.getPrivilegio().getId() == usuarioPrivilegio.getPrivilegio().getId()) {
							retorno.add(opcion);
							opcionInsertada = true; 
							break;
						}
					}
					if (opcionInsertada) {
						break;
					}
				}
			}
			
			return retorno;
		}
		
}
