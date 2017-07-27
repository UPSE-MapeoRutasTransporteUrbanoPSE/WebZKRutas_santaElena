package com.demo.control.mvvm;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Window;

import com.demo.modelo.Ruta;
import com.demo.modelo.RutaDAO;

public class RutasControl {
	private RutaDAO rutaDao = new RutaDAO();
	private Ruta ruta;
	
	public RutasControl(){
		ruta = (Ruta)Executions.getCurrent().getArg().get("Ruta");
	}
	/**
	 * Graba la informacion. Para poder cerrar la ventana, pasa como parametro
	 * desde el formulario la ventana: onClick="@command('grabar', ventana=winPersonaEditar)"
	 */
	@Command
	public void grabar(@BindingParam("ventana")  Window ventana){
		try {
			rutaDao.getEntityManager().getTransaction().begin();
			
			if (ruta.getId() == null) {
				
				rutaDao.getEntityManager().persist(ruta);
			}else{
				ruta =(Ruta) rutaDao.getEntityManager().merge(ruta);
			}
			
			rutaDao.getEntityManager().getTransaction().commit();
			Clients.showNotification("Proceso Ejecutado con exito.");
			
			// Actualiza la lista
			BindUtils.postGlobalCommand(null, null, "RutaLista.buscar", null);
			salir(ventana);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			rutaDao.getEntityManager().getTransaction().rollback();
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
	public Ruta getRuta() {
		return ruta;
	}
	public void setRuta(Ruta ruta) {
		this.ruta = ruta;
	}
	

}
