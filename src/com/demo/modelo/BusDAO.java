package com.demo.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

public class BusDAO extends ClaseDAO {
	
	public Bus getBusPorId(int id) {
		Bus bus;
		bus = (Bus) getEntityManager().find(Bus.class, id);
		return bus;
	}

	@SuppressWarnings("unchecked")
	public List<Bus> getBus() {
		List<Bus> retorno = new ArrayList<Bus>();
		Query query = getEntityManager().createNamedQuery("Bus.findAll");
		retorno = (List<Bus>) query.getResultList();
		return retorno;
	}

	/**
	 * Busca buses en base a un patron de busqueda.
	 * 
	 * @param value
	 * @return
	 */

	public List<Bus> getBus(String value) {
		List<Bus> resultado;

		String patron = value;

		if (value == null || value.length() == 0) {
			patron = "%";
		} else {
			patron = "%" + patron.toLowerCase() + "%";
		}

		Query query = getEntityManager().createNamedQuery("Buss.buscarPorPatron");
		// Para asegurar que la consulta trae lo ultimo de la base.
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");

		// Asigna el patron de busqueda
		query.setParameter("patron", patron);

		resultado = (List<Bus>) query.getResultList();
		return resultado;
	}

}
