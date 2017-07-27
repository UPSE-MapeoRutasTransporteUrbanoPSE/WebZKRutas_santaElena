package com.demo.modelo;

import java.util.List;

import javax.persistence.Query;

public class LugareeDAO extends ClaseDAO {
	public Lugare getLugarePorId(int id){
		Lugare lugare;
		lugare = (Lugare) getEntityManager().find(Lugare.class, id);

		return lugare;
	} 
	
	
	
	/**
	 * Busca sitios en base a un patron de busqueda.
	 * @param value
	 * @return
	 */

	public List<Lugare> getLugare(String value) {
		List<Lugare> resultado;
		String patron = value;

		// Adapta el patron de busqueda.
		if (value == null || value.length() == 0) {
			patron = "%";
		}else{
			patron = "%" + patron.toLowerCase() + "%";
		}

		// Crea la consulta.
		Query query = getEntityManager().createNamedQuery("Lugare.buscarPorPatron");

		// Para asegurar que la consulta trae lo ultimo de la base.
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");

		// Asigna el patron de busqueda
		query.setParameter("patron", patron);

		// Recupera los resultados.
		resultado = (List<Lugare>) query.getResultList();

		return resultado;
	}
}
