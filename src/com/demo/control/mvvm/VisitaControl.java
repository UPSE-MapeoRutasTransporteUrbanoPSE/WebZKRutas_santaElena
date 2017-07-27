package com.demo.control.mvvm;

import java.io.IOException;
import java.util.List;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Html;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.demo.control.general.GMaps;
import com.demo.modelo.Visita;
import com.demo.modelo.VisitaDAO;


public class VisitaControl {

	@Wire Html mapa;
	@Wire Textbox txtCoordenadasDomicilio;
	GMaps gmap =new GMaps();
	
	 private VisitaDAO visitaDao = new VisitaDAO();
	private Visita visita;
    
	
	/**
     * Metodo que emula al metodo doAfterComposer de MVC
     */
    @AfterCompose
    public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {

        // Permite enlazar los componentes que se asocian con la anotacion @Wire
        Selectors.wireComponents(view, this, false);
        
     // Recupera el objeto pasado como parametro. 
        visita = (Visita) Executions.getCurrent().getArg().get("Visita");

              
        txtCoordenadasDomicilio.setValue(visita.getCoordenadas());
        mapa.setContent(gmap.getMapaHtml(txtCoordenadasDomicilio));

    }
	
	
    /**
     * Constructor para obtener el parametro enviado.
     */
    public VisitaControl() {
        
        // Recupera el objeto pasado como parametro. 
    	visita = (Visita) Executions.getCurrent().getArg().get("Visita");
                
    }
    
    @Command
    public void grabar(@BindingParam("ventana")  Window ventana){
        
        try {
            // Inicia la transaccion
            visitaDao.getEntityManager().getTransaction().begin();
            visita.setCoordenadas(txtCoordenadasDomicilio.getValue());
            
            // Almacena los datos.
            // Si es nuevo sa el metodo "persist" de lo contrario usa el metodo "merge"
            if (visita.getId() == null) {
                visitaDao.getEntityManager().persist(visita);
            }else{
                visita = (Visita) visitaDao.getEntityManager().merge(visita);
            }
            
            // Cierra la transaccion.
            visitaDao.getEntityManager().getTransaction().commit();
            
            Clients.showNotification("Proceso Ejecutado con exito.");
            
            // Actualiza la lista
            BindUtils.postGlobalCommand(null, null, "visitaLista.buscar", null);
            
            // Cierra la ventana
            salir(ventana);
            
        } catch (Exception e) {
            e.printStackTrace();
            
            // Si hay error, reversa la transaccion.
            visitaDao.getEntityManager().getTransaction().rollback();
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







	public Html getMapa() {
		return mapa;
	}







	public void setMapa(Html mapa) {
		this.mapa = mapa;
	}







	public Textbox getTxtCoordenadasDomicilio() {
		return txtCoordenadasDomicilio;
	}







	public void setTxtCoordenadasDomicilio(Textbox txtCoordenadasDomicilio) {
		this.txtCoordenadasDomicilio = txtCoordenadasDomicilio;
	}







	public GMaps getGmap() {
		return gmap;
	}







	public void setGmap(GMaps gmap) {
		this.gmap = gmap;
	}







	public Visita getVisita() {
		return visita;
	}







	public void setVisita(Visita visita) {
		this.visita = visita;
	}
    
    
    
	
}
