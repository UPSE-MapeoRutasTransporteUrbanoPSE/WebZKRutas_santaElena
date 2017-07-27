package com.demo.control.mvvm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.SelectEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Html;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.demo.control.general.GMaps;
import com.demo.control.general.Mapa;
import com.demo.modelo.Lugare;
import com.demo.modelo.LugareDAO;
import com.demo.modelo.SitiosIntere;
import com.demo.modelo.SitiosIntereDAO;
import com.demo.modelo.Visita;


public class MapaControl {
	
		
	@Wire Html mapa;
	Mapa gmap =new Mapa();
	@Wire Textbox txtCoordenadasDomicilio;
	private Visita visita;
	
	//elemntos de mi mapa.zul
	@Wire Combobox combopais;
	@Wire Listbox lstSitios;
	@Wire Window winMapa;
	
	
	private LugareDAO lugareDao = new LugareDAO();
	private SitiosIntere sitiosintere;
	
	private List<SitiosIntere> sitiosInteres;
	private SitiosIntereDAO sitiosintereDao = new SitiosIntereDAO();
	private SitiosIntere sitiosintereSeleccionada;
	private Lugare lugareSeleccionada;
	
	
	
		
		//Objeto que contiene la persona con que se esta trabajando
		private Lugare lugares;
		private SitiosIntere sitios;
		
		
	
	/** 
     * Metodo que emula al metodo doAfterComposer de MVC
     */
    @AfterCompose
    public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {

        // Permite enlazar los componentes que se asocian con la anotacion @Wire
        Selectors.wireComponents(view, this, false);

        
        mapa.setContent(gmap.getMapaHtml(txtCoordenadasDomicilio));
       
    }
   
    
  //@GlobalCommand("SitiosIntereLista.buscar")
  		@Command
  		@NotifyChange({"sitiosInteres"})
  		public void buscar(){
  			if (sitiosInteres != null) {
  				sitiosInteres = null; 
  			}
  			sitiosInteres = sitiosintereDao.getSitiosIntere(combopais.getValue().toString());

  			// Limpia os objetos de trabajo
  			sitiosintereSeleccionada = null;
  			
  		}
      
   
   @Command
   public void onSearch(){
	   try {
		   String item= combopais.getValue().toString();
		   System.out.println(item + " esete es el valor");
		   
	} catch (Exception e) {
		e.printStackTrace();
		e.getMessage();
	}
	   
	
	   
   }
   
   
   
   
   
   

   
   public Listbox getLstSitios() {
	return lstSitios;
}


public void setLstSitios(Listbox lstSitios) {
	this.lstSitios = lstSitios;
}


public Lugare getLugareSeleccionada() {
	return lugareSeleccionada;
}


public void setLugareSeleccionada(Lugare lugareSeleccionada) {
	this.lugareSeleccionada = lugareSeleccionada;
}


public SitiosIntere getSitiosintereSeleccionada() {
	return sitiosintereSeleccionada;
}


public void setSitiosintereSeleccionada(SitiosIntere sitiosintereSeleccionada) {
	this.sitiosintereSeleccionada = sitiosintereSeleccionada;
}


public SitiosIntereDAO getSitiosintereDao() {
	return sitiosintereDao;
}


public void setSitiosintereDao(SitiosIntereDAO sitiosintereDao) {
	this.sitiosintereDao = sitiosintereDao;
}





public List<Lugare> getLugares() {
       return lugareDao.getLugares();
   }





public SitiosIntere getSitiosintere() {
	return sitiosintere;
}





public void setSitiosintere(SitiosIntere sitiosintere) {
	this.sitiosintere = sitiosintere;
}

   
public List<SitiosIntere> getSitiosInteres() {
	return sitiosInteres;
}




public void setSitiosInteres(List<SitiosIntere> sitiosInteres) {
	this.sitiosInteres = sitiosInteres;
}
   
   
   
  
    
	
	
	
    
}
