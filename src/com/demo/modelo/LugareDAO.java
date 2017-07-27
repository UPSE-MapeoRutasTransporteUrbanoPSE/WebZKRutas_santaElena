package com.demo.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

public class LugareDAO extends ClaseDAO {
	
	public List<Lugare> getLugares() {
		List<Lugare> retorno = new ArrayList<Lugare>();

		Query query = getEntityManager().createNamedQuery("Lugare.findAll");
		retorno = (List<Lugare>) query.getResultList();
		
		return retorno;
	}

}
