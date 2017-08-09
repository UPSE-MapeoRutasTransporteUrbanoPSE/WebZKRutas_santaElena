package com.demo.control.mvvm;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.zkoss.bind.BindContext;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.io.Files;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.SelectEvent;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Html;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;


import org.zkoss.zk.ui.select.SelectorComposer;






import com.demo.control.general.FileUtil;
import com.demo.control.general.GMaps;
import com.demo.control.general.LeerArchivo;
import com.demo.control.general.Mapa;
import com.demo.control.general.Marcador;
import com.demo.modelo.Lugare;
import com.demo.modelo.LugareDAO;
import com.demo.modelo.SitiosIntere;
import com.demo.modelo.SitiosIntereDAO;
import com.demo.modelo.Visita;
import org.zkoss.util.media.Media;

import org.zkoss.zk.au.out.*;
import org.zkoss.zul.*;
import org.zkoss.util.media.*;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Messagebox;
import com.demo.control.general.Util;



@SuppressWarnings({ "unchecked", "rawtypes", "serial" })
public class MapaControl extends SelectorComposer{
	public static int a=3,b;
		
	@Wire Html mapa;
	Mapa gmap =new Mapa();
	Marcador gmar =new Marcador();
	GMaps gmaps =new GMaps();
	@Wire Textbox txtCoordenadasDomicilio;
	private Visita visita;
	 private Media media;
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

        
        System.out.println("entro con a valor :"+a);
        
        if(a==2){
    		mapa.setContent(gmar.getMapaHtml(txtCoordenadasDomicilio));
    		System.out.println("dibujo marcador con ruta");
    	}
        if(a==1){
    	
    	mapa.setContent(gmap.getMapaHtml(txtCoordenadasDomicilio));
    	System.out.println("aki");
    	
    	
    }
        
        if(a==3){
    	mapa.setContent(gmaps.getMapaHtml(txtCoordenadasDomicilio));
    	System.out.println("dibujo marcador solo");
    	System.out.println("a tiene avlro de "+ a);
    }
        
        	
    
    
        	
        
        	
    
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
   
   
  
     
   
   //subir archivos y cargarlos en el mapa
   
   @Command
   @NotifyChange("mapa")
   public void subir(@ContextParam(ContextType.BIND_CONTEXT) BindContext contexto) {
       
       String pathRetornado; 
       
       // Extrae el evento de carga
       UploadEvent eventoCarga = (UploadEvent) contexto.getTriggerEvent();
       
       // Pasa el archivo a cargar al metodo "cargaArchivo" de la clase "FileUtil"
       pathRetornado = FileUtil.cargaArchivo(eventoCarga.getMedia());
      
       a=1;
       System.out.println("se fue con valor : "+a);
       Clients.showNotification("Ruta Cargarada con exito!...Refresque la Pagina >P");
       Executions.sendRedirect("");
   }
   
   
   @Command
   @NotifyChange("mapa")
   public void mostrar(@ContextParam(ContextType.BIND_CONTEXT) BindContext contexto) {

       a=2;
       System.out.println("se fue con valor : "+a+"de marcador");
       Clients.showNotification("BUS SOBRE RUTA CARGADO");
       Executions.sendRedirect("");
   }
   
   @Command
   public void descargar() {
	   if( b==0 || a==0){
		   Clients.showNotification("NO SE HA SELEECIONADO UN ARCHIVO PARA EL PROCESO"); 
	   }else{
		   FileUtil.descargaArchivo("D:/Aplicaciones/Archivos/2017/7/7/archivo.gpx");
	   }
       
           
       
   }
   
   ///////*****************
   
  
@Command
public void reducir() throws IOException, ParserConfigurationException{
	
	 
	 
	 if( a==0){
		   Clients.showNotification("NO SE HA SELEECIONADO UN ARCHIVO PARA EL PROCESO"); 
	   }else{
		   LeerArchivo red =new LeerArchivo();
			red.reducir();
			 Clients.showNotification("Archivo Generado con exito!");
			 b=1;
	   }
	 
}

   	

   //-----------------------------
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
