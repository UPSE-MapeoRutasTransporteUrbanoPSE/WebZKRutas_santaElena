package com.demo.control.mvvm;

import java.util.List;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Window;

import com.demo.modelo.Bus;
import com.demo.modelo.BusDAO;
import com.demo.modelo.Busesruta;
import com.demo.modelo.BusesrutaDAO;
import com.demo.modelo.Ruta;
import com.demo.modelo.RutaDAO;

public class BusesrutaControl {
	
	private BusesrutaDAO busesrutaDao = new BusesrutaDAO();
	private BusDAO busDao = new BusDAO();
	private RutaDAO rutaDao = new RutaDAO();
	private Busesruta busrutas;
	
	public BusesrutaControl(){
		busrutas = (Busesruta) Executions.getCurrent().getArg().get("Busesruta");
		
		
	}
	
	@Command
	public void grabar(@BindingParam("ventana")  Window ventana){
		try {
			busesrutaDao.getEntityManager().getTransaction().begin();
			if (busrutas.getId()== null) {
				busesrutaDao.getEntityManager().persist(busrutas);
			}else{
				busrutas=(Busesruta) busesrutaDao.getEntityManager().merge(busrutas);
			}
			
			busesrutaDao.getEntityManager().getTransaction().commit();
			Clients.showNotification("Proceso Ejecutado con exito.");
			
			BindUtils.postGlobalCommand(null, null, "BusesrutaLista", null);
			salir(ventana);
		} catch (Exception e) {
			
			e.printStackTrace();
			busesrutaDao.getEntityManager().getTransaction().rollback();
			// TODO: handle exception
		}
		
	}
	@Command
	public void salir(@BindingParam("ventana")  Window ventana){
		ventana.detach();
	}
	
	public List<Bus> getBus(){
		return busDao.getBus();
	}
	
	public List<Ruta> getRuta(){
		return rutaDao.getRuta();
	}

	public Busesruta getBusrutas() {
		return busrutas;
	}

	public void setBusrutas(Busesruta busrutas) {
		this.busrutas = busrutas;
	}
	

	
}
