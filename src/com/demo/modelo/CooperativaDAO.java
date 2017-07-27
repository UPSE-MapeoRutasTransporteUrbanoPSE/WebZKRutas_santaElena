package com.demo.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

public class CooperativaDAO extends ClaseDAO {

	public Cooperativa getPorId(int id){
		Cooperativa cooperativa;
		cooperativa =(Cooperativa)getEntityManager().find(Cooperativa.class, id);
		return cooperativa;
	}
	
	public List<Cooperativa> getCooperativa(){
		List<Cooperativa> retorno = new ArrayList<Cooperativa>();
		Query query = getEntityManager().createNamedQuery("Cooperativa.findAll");
		retorno = (List<Cooperativa>)query.getResultList();
		
		return retorno;
	}
	
	/**
	 * Busca cooperativas con un patron de busqueda
	 */
	
	
	public List<Cooperativa>getCooperativas(String value){
		List<Cooperativa> resultado;
		String patron= value;
		
		// Adapta el patron de busqueda.
		if (value == null || value.length() == 0) {
			patron = "%";
		}else{
			patron = "%" + patron.toLowerCase() + "%";
		}
		
		Query query = getEntityManager().createNamedQuery("Cooperativas.buscaPorPatron");
		
		// Para asegurar que la consulta trae lo ultimo de la base.
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");

		// Asigna el patron de busqueda
		query.setParameter("patron", patron);
		
		resultado = (List<Cooperativa>)query.getResultList();
		return resultado;
		
	}
	
}
