package com.demo.control.mvvm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Window;

import com.demo.modelo.SitiosIntere;
import com.demo.modelo.SitiosIntereDAO;



@SuppressWarnings({ "unchecked", "rawtypes" })
public class SitiosIntereLista {
	
	
	//Atributos consumidos en el formulario
		public String textoBuscar;

		// Instancia el objeto para acceso a datos.
		private SitiosIntereDAO sitiosintereDao = new SitiosIntereDAO();

		//Objetos que contienen la persona y agenda con la que se esta trabajando
		private SitiosIntere sitiosintereSeleccionada;
		private List<SitiosIntere> sitiosInteres;
		
		
		//@GlobalCommand("SitiosIntereLista.buscar")
		@Command
		@NotifyChange({"sitiosInteres"})
		public void buscar(){
			if (sitiosInteres != null) {
				sitiosInteres = null; 
			}
			sitiosInteres = sitiosintereDao.getSitiosIntere(textoBuscar);

			// Limpia os objetos de trabajo
			sitiosintereSeleccionada = null;
			
		}
		
		
		
		
		/**
	     * Crea un sitio
	     */
	    @Command
	    public void nuevo(){
	        Map<String, Object> params = new HashMap<String, Object>();
	        params.put("SitiosIntere", new SitiosIntere());
	        Window ventanaCargar = (Window) Executions.createComponents("/mvvm/sitiosintere.zul", null, params);
	        ventanaCargar.doModal();
	    }

	    /**
	     * Edita una persona
	     */
	    @Command
	    public void editar(){
	        if (sitiosintereSeleccionada == null) {
	            Clients.showNotification("Debe seleccionar un sitio.");
	            return; 
	        }

	        // Actualiza la instancia antes de enviarla a editar.
	        sitiosintereDao.getEntityManager().refresh(sitiosintereSeleccionada);

	        Map<String, Object> params = new HashMap<String, Object>();
	        params.put("SitiosIntere", sitiosintereSeleccionada);
	        Window ventanaCargar = (Window) Executions.createComponents("/mvvm/sitiosintere.zul", null, params);
	        ventanaCargar.doModal();

	    }




		public String getTextoBuscar() {
			return textoBuscar;
		}




		public void setTextoBuscar(String textoBuscar) {
			this.textoBuscar = textoBuscar;
		}




		public SitiosIntereDAO getSitiosintereDao() {
			return sitiosintereDao;
		}




		public void setSitiosintereDao(SitiosIntereDAO sitiosintereDao) {
			this.sitiosintereDao = sitiosintereDao;
		}




		public SitiosIntere getSitiosintereSeleccionada() {
			return sitiosintereSeleccionada;
		}




		public void setSitiosintereSeleccionada(SitiosIntere sitiosintereSeleccionada) {
			this.sitiosintereSeleccionada = sitiosintereSeleccionada;
		}




		public List<SitiosIntere> getSitiosInteres() {
			return sitiosInteres;
		}




		public void setSitiosInteres(List<SitiosIntere> sitiosInteres) {
			this.sitiosInteres = sitiosInteres;
		}
	    
	    
	    
	    
	    
	    

}
