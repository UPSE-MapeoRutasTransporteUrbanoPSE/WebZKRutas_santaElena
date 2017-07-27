package com.demo.control.mvvm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.demo.control.general.PrintUtil;
import com.demo.modelo.Cooperativa;
import com.demo.modelo.CooperativaDAO;
import com.demo.modelo.RutaDAO;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class CooperativaLista {
	
	public String textoBuscar;

	private CooperativaDAO cooperativaDao = new CooperativaDAO();
	private Cooperativa cooperativaSeleccionada;
	private List<Cooperativa> cooperativa;
	

	@Command
	@NotifyChange({"cooperativa"})
	
	public void buscar(){
		if (cooperativa!= null) {
			cooperativa = null;
		}
		cooperativa = cooperativaDao.getCooperativas(textoBuscar);
		cooperativaSeleccionada=null;
		
	}
	@Command
	public void nuevo() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("Cooperativa", new Cooperativa());
		
		Window ventanaCargar = (Window) Executions.createComponents("/mvvm/cooperativa.zul", null, params);
		ventanaCargar.doModal();
	}
	@Command
	public void editar() {
		if (cooperativaSeleccionada==null) {
			Clients.showNotification("Debe seleccionar una cooperativa.");
			return;
		}
		cooperativaDao.getEntityManager().refresh(cooperativaSeleccionada);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("Cooperativa", cooperativaSeleccionada);
		Window ventanaCargar = (Window) Executions.createComponents("/mvvm/cooperativa.zul", null, params);
		ventanaCargar.doModal();
		
	}
	@Command
	public void eliminar() {
		if (cooperativaSeleccionada == null) {
			Clients.showNotification("Debe seleccionar una cooperativa.");
			return;
			
		}
		Messagebox.show("Desea eliminar el registro seleccionado?", "Confirmación de borrado",
				Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new EventListener() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				if (event.getName().equals("onYes")) {
					try {
						cooperativaDao.getEntityManager().getTransaction().begin();
						cooperativaSeleccionada.setTelefono("X");
						cooperativaSeleccionada = cooperativaDao.getEntityManager().merge(cooperativaSeleccionada);
						cooperativaDao.getEntityManager().getTransaction().commit();;
						Clients.showNotification("Transaccion ejecutada con exito.");
						BindUtils.postGlobalCommand(null, null, "CooperativaLista.buscar", null);
						
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						cooperativaDao.getEntityManager().getTransaction().rollback();
					}
				}
			}
				
			});
									
		
	}
	@Command
	public void imprimir() {
		
		// Crea un arreglo de parametros.
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		// Coloca el parametro a pasar al reporte.
		//parametros.put("P_ID_BUSESRUTA", recorridoSeleccionada.getId());
		
		// Ejecuta el reporte.
		PrintUtil.ejecutaReporte(cooperativaDao, 
				                 "/reportes/totalbuses.jasper", 
				                 parametros, 
				                 PrintUtil.FORMATO_PDF);
	}
	
	public String getTextoBuscar() {
		return textoBuscar;
	}
	public Cooperativa getCooperativaSeleccionada() {
		return cooperativaSeleccionada;
	}
	public void setCooperativaSeleccionada(Cooperativa cooperativaSeleccionada) {
		this.cooperativaSeleccionada = cooperativaSeleccionada;
	}
	public List<Cooperativa> getCooperativa() {
		return cooperativa;
	}
	public void setCooperativa(List<Cooperativa> cooperativa) {
		this.cooperativa = cooperativa;
	}
	public void setTextoBuscar(String textoBuscar) {
		this.textoBuscar = textoBuscar;
	}

	
}
