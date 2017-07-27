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
import com.demo.modelo.Cooperativa;
import com.demo.modelo.CooperativaDAO;

public class BusControl {

	private BusDAO busDao = new BusDAO();
	private CooperativaDAO cooperativaDao = new CooperativaDAO();
	private Bus bus;
	
	public BusControl(){
		bus =(Bus)Executions.getCurrent().getArg().get("Bus");
	}
	/**
	 * Graba la informacion. Para poder cerrar la ventana, pasa como parametro
	 * desde el formulario la ventana: onClick="@command('grabar', ventana=winPersonaEditar)"
	 */
	@Command
	public void grabar(@BindingParam("ventana")  Window ventana){
		try {
			busDao.getEntityManager().getTransaction().begin();
			if (bus.getId() == null) {
				busDao.getEntityManager().persist(bus);
				
			} else {
				
				bus =(Bus) busDao.getEntityManager().merge(bus);
			}
			
			busDao.getEntityManager().getTransaction().commit();
			Clients.showNotification("Proceso Ejecutado con exito.");
			
			BindUtils.postGlobalCommand(null, null, "BusLista.buscar", null);
			salir(ventana);
		} catch (Exception e) {
			e.printStackTrace();
			busDao.getEntityManager().getTransaction().rollback();
		}
	}
	/**
	 * Cierra el formulario, para ello pasa un parametro desde el formulario con
	 * la ventana a cerrar: onClick="@command('salir', ventana=winPersonaEditar)"
	 */
	@Command
	public void salir(@BindingParam("ventana")  Window ventana){
		ventana.detach();
	}
	
	public List<Cooperativa> getCooperativa(){
		return cooperativaDao.getCooperativa();
	}
	
	public Bus getBus() {
		return bus;
	}
	public void setBus(Bus bus) {
		this.bus = bus;
	}
	
}
