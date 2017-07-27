package com.demo.modelo;

import java.util.List;

import javax.persistence.Query;

public class VisitaDAO extends ClaseDAO {

	public Visita getVisitaPorId(int id){
		Visita visita;
		visita = (Visita) getEntityManager().find(Visita.class, id);

		return visita;
	} 
	
	/**
	 * Busca personas en base a un patron de busqueda.
	 * @param value
	 * @return
	 */

	public List<Visita> getVisitas(String value) {
		List<Visita> resultado;
		String patron = value;

		// Adapta el patron de busqueda.
		if (value == null || value.length() == 0) {
			patron = "%";
		}else{
			patron = "%" + patron.toLowerCase() + "%";
		}

		// Crea la consulta.
		Query query = getEntityManager().createNamedQuery("Visitas.buscarPorPatron");

		// Para asegurar que la consulta trae lo ultimo de la base.
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");

		// Asigna el patron de busqueda
		query.setParameter("patron", patron);

		// Recupera los resultados.
		resultado = (List<Visita>) query.getResultList();

		return resultado;
	}
	
	
	
}
