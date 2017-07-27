package com.demo.control.mvvm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Window;

import com.demo.modelo.Visita;
import com.demo.modelo.VisitaDAO;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class VisitaLista {

	
	//Atributos consumidos en el formulario
			public String textoBuscar;

			// Instancia el objeto para acceso a datos.
			private VisitaDAO visitaDao = new VisitaDAO();

			//Objetos que contienen la persona y agenda con la que se esta trabajando
			private Visita visitaSeleccionada;
			private List<Visita> visita;
			
			
			//@GlobalCommand("SitiosIntereLista.buscar")
			@Command
			@NotifyChange({"visita"})
			public void buscar(){
				if (visita != null) {
					visita = null; 
				}
				visita = visitaDao.getVisitas(textoBuscar);

				// Limpia os objetos de trabajo
				visitaSeleccionada = null;
				
			}
			
			
			
			
			/**
		     * Crea un sitio
		     */
		    @Command
		    public void nuevo(){
		        Map<String, Object> params = new HashMap<String, Object>();
		        params.put("Visita", new Visita());
		        Window ventanaCargar = (Window) Executions.createComponents("/mvvm/miruta.zul", null, params);
		        ventanaCargar.doModal();
		    }

		    /**
		     * Edita una persona
		     */
		    @Command
		    public void editar(){
		        if (visitaSeleccionada == null) {
		            Clients.showNotification("Debe seleccionar un sitio.");
		            return; 
		        }

		        // Actualiza la instancia antes de enviarla a editar.
		        visitaDao.getEntityManager().refresh(visitaSeleccionada);

		        Map<String, Object> params = new HashMap<String, Object>();
		        params.put("Visita", visitaSeleccionada);
		        Window ventanaCargar = (Window) Executions.createComponents("/mvvm/miruta.zul", null, params);
		        ventanaCargar.doModal();

		    }




			public String getTextoBuscar() {
				return textoBuscar;
			}




			public void setTextoBuscar(String textoBuscar) {
				this.textoBuscar = textoBuscar;
			}




			public VisitaDAO getVisitaDao() {
				return visitaDao;
			}




			public void setVisitaDao(VisitaDAO visitaDao) {
				this.visitaDao = visitaDao;
			}




			public Visita getVisitaSeleccionada() {
				return visitaSeleccionada;
			}




			public void setVisitaSeleccionada(Visita visitaSeleccionada) {
				this.visitaSeleccionada = visitaSeleccionada;
			}




			public List<Visita> getVisita() {
				return visita;
			}




			public void setVisita(List<Visita> visita) {
				this.visita = visita;
			}
		    
		    
		    

	
}
