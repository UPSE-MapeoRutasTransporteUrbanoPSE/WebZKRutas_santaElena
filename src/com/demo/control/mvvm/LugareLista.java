package com.demo.control.mvvm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Window;

import com.demo.modelo.Lugare;
import com.demo.modelo.LugareeDAO;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class LugareLista {

	//Atributos consumidos en el formulario
			public String textoBuscar;

			// Instancia el objeto para acceso a datos.
			private LugareeDAO lugareeDao = new LugareeDAO();

			//Objetos que contienen la persona y agenda con la que se esta trabajando
			private Lugare lugareeSeleccionada;
			private List<Lugare> lugares;
			
			
			//@GlobalCommand("SitiosIntereLista.buscar")
			@Command
			@NotifyChange({"lugares"})
			public void buscar(){
				if (lugares != null) {
					lugares = null; 
				}
				lugares = lugareeDao.getLugare(textoBuscar);

				// Limpia os objetos de trabajo
				lugareeSeleccionada = null;
				
			}
			
			
			
			
			/**
		     * Crea un sitio
		     */
		    @Command
		    public void nuevo(){
		        Map<String, Object> params = new HashMap<String, Object>();
		        params.put("Lugare", new Lugare());
		        Window ventanaCargar = (Window) Executions.createComponents("/mvvm/lugares.zul", null, params);
		        ventanaCargar.doModal();
		    }

		    /**
		     * Edita una persona
		     */
		    @Command
		    public void editar(){
		        if (lugareeSeleccionada == null) {
		            Clients.showNotification("Debe seleccionar un sitio.");
		            return; 
		        }

		        // Actualiza la instancia antes de enviarla a editar.
		        lugareeDao.getEntityManager().refresh(lugareeSeleccionada);

		        Map<String, Object> params = new HashMap<String, Object>();
		        params.put("Lugare", lugareeSeleccionada);
		        Window ventanaCargar = (Window) Executions.createComponents("/mvvm/lugares.zul", null, params);
		        ventanaCargar.doModal();

		    }




			public String getTextoBuscar() {
				return textoBuscar;
			}




			public void setTextoBuscar(String textoBuscar) {
				this.textoBuscar = textoBuscar;
			}




			public LugareeDAO getLugareeDao() {
				return lugareeDao;
			}




			public void setLugareDao(LugareeDAO lugareDao) {
				this.lugareeDao = lugareeDao;
			}




			public Lugare getLugareeSeleccionada() {
				return lugareeSeleccionada;
			}




			public void setLugareeSeleccionada(Lugare lugareeSeleccionada) {
				this.lugareeSeleccionada = lugareeSeleccionada;
			}




			public List<Lugare> getLugares() {
				return lugares;
			}




			public void setLugares(List<Lugare> lugares) {
				this.lugares = lugares;
			}




			
		    
		    
	
}
