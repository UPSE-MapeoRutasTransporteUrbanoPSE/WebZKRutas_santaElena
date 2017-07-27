package com.demo.modelo;

import java.util.Date;
import java.util.List;
import java.util.List;

import javax.persistence.Query;

public class BusesrutaDAO extends ClaseDAO {
	
	public Busesruta getBusesrutaPorId(int id){
		
		Busesruta busesruta;
		busesruta = (Busesruta)getEntityManager().find(Busesruta.class, id);
	
		return busesruta;
	}
	public List<Busesruta> getBusesruta(String value){
	List<Busesruta> resultado;
	String patron = value;
	if (value == null || value.length() == 0) {
		patron = "%";
	}else{
		patron = "%" + patron.toLowerCase() + "%";
	}
	Query query = getEntityManager().createNamedQuery("Busesrutas.buscarPorPatron");
	
	query.setHint("javax.persistence.cache.storeMode", "REFRESH");

	// Asigna el patron de busqueda
	query.setParameter("patron", patron);
	
	resultado =(List<Busesruta>) query.getResultList();
	return resultado;
	
	}
	
	//consulta de busqueda por fecha y ruta

		public List<Busesruta> getBusesrecorridos(String ruta, Date fecha) {
			List<Busesruta> resultados;
			//query de consulta se encuentra en clases Busesruta
			Query query = getEntityManager().createNamedQuery("Busesrutas.buscarfechayruta");

			query.setHint("javax.persistence.cache.storeMode", "REFRESH");

			// Asigna el patron de busqueda
			query.setParameter("ruta", ruta);
			query.setParameter("fecha", fecha);

			resultados = (List<Busesruta>) query.getResultList();
			return resultados;

		}

	

}
