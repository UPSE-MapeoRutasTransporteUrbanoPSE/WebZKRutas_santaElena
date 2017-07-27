package com.demo.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

public class SitiosIntereDAO extends ClaseDAO {
	
	public SitiosIntere getSitiosInterePorId(int id){
		SitiosIntere sitiosintere;
		sitiosintere = (SitiosIntere) getEntityManager().find(SitiosIntere.class, id);

		return sitiosintere;
	} 
	
	
	public List<SitiosIntere> getSitiosIntere() {
		List<SitiosIntere> retorno = new ArrayList<SitiosIntere>();

		Query query = getEntityManager().createNamedQuery("SitiosIntere.findAll");
		retorno = (List<SitiosIntere>) query.getResultList();
		
		return retorno;
	}
	
	
	/**
	 * Busca sitios en base a un patron de busqueda.
	 * @param value
	 * @return
	 */

	public List<SitiosIntere> getSitiosIntere(String value) {
		List<SitiosIntere> resultado;
		String patron = value;

		// Adapta el patron de busqueda.
		if (value == null || value.length() == 0) {
			patron = "%";
		}else{
			patron = "%" + patron.toLowerCase() + "%";
		}

		// Crea la consulta.
		Query query = getEntityManager().createNamedQuery("SitiosInteres.buscarPorPatron");

		// Para asegurar que la consulta trae lo ultimo de la base.
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");

		// Asigna el patron de busqueda
		query.setParameter("patron", patron);

		// Recupera los resultados.
		resultado = (List<SitiosIntere>) query.getResultList();

		return resultado;
	}


}
