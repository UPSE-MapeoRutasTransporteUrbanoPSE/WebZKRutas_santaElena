package com.demo.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

public class RutaDAO extends ClaseDAO {

	public Ruta getRutaPorId(int id) {
		Ruta ruta;
		ruta = (Ruta) getEntityManager().find(Ruta.class, id);
		return ruta;
	}

	@SuppressWarnings("unchecked")
	public List<Ruta> getRuta() {
		List<Ruta> retorno = new ArrayList<Ruta>();
		Query query = getEntityManager().createNamedQuery("Ruta.findAll");
		retorno = (List<Ruta>) query.getResultList();
		return retorno;
	}

	/**
	 * Busca rutas en base a un patron de busqueda.
	 * 
	 * @param value
	 * @return
	 */

	public List<Ruta> getRutas(String value) {
		List<Ruta> resultado;
		String patron = value;

		// Adapta el patron de busqueda.
		if (value == null || value.length() == 0) {
			patron = "%";
		} else {
			patron = "%" + patron.toLowerCase() + "%";
		}

		// Crea la consulta.
		Query query = getEntityManager().createNamedQuery("Rutas.buscarPorPatron");

		// Para asegurar que la consulta trae lo ultimo de la base.
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");

		// Asigna el patron de busqueda
		query.setParameter("patron", patron);

		resultado = (List<Ruta>) query.getResultList();
		return resultado;

	}
}
