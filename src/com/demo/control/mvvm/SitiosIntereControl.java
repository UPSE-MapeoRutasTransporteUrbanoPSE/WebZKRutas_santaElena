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
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.image.AImage;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Window;

import com.demo.control.general.FileUtil;
import com.demo.modelo.Lugare;
import com.demo.modelo.LugareDAO;
import com.demo.modelo.SitiosIntere;
import com.demo.modelo.SitiosIntereDAO;

public class SitiosIntereControl {
	
	private SitiosIntereDAO sitiosintereDao = new SitiosIntereDAO();
	private LugareDAO lugareDao = new LugareDAO();
	
	private SitiosIntere sitiosintere;
	
	
	
	
	
	
	
	/**
     * Metodo que emula al metodo doAfterComposer de MVC
     */
    @AfterCompose
    public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {

        // Permite enlazar los componentes que se asocian con la anotacion @Wire
        Selectors.wireComponents(view, this, false);

        // Recupera el objeto pasado como parametro. 
        sitiosintere = (SitiosIntere) Executions.getCurrent().getArg().get("SitiosIntere");
               
    }
    
    
    
    
    /**
     * Constructor para obtener el parametro enviado.
     */
    public SitiosIntereControl() {
        
        // Recupera el objeto pasado como parametro. 
    	sitiosintere = (SitiosIntere) Executions.getCurrent().getArg().get("SitiosIntere");
                
    }
    
    
    
    
    
    /**
     * Graba la informacion. Para poder cerrar la ventana, pasa como parametro
     * desde el formulario la ventana: onClick="@command('grabar', ventana=winPersonaEditar)"
     */
    @Command
    public void grabar(@BindingParam("ventana")  Window ventana){
        
        try {
            // Inicia la transaccion
        	sitiosintereDao.getEntityManager().getTransaction().begin();
        	
            
            // Almacena los datos.
            // Si es nuevo sa el metodo "persist" de lo contrario usa el metodo "merge"
            if (sitiosintere.getId() == null) {
            	sitiosintereDao.getEntityManager().persist(sitiosintere);
            }else{
            	sitiosintere = (SitiosIntere) sitiosintereDao.getEntityManager().merge(sitiosintere);
            }
            
            // Cierra la transaccion.
            sitiosintereDao.getEntityManager().getTransaction().commit();
            
            Clients.showNotification("Proceso Ejecutado con exito.");
            
            // Actualiza la lista
            BindUtils.postGlobalCommand(null, null, "SitiosIntereLista.buscar", null);
            
            // Cierra la ventana
            salir(ventana);
            
        } catch (Exception e) {
            e.printStackTrace();
            
            // Si hay error, reversa la transaccion.
            sitiosintereDao.getEntityManager().getTransaction().rollback();
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
    
    
    /**
     * Sube el archivo seleccionado.
     * El eventp de carga viene en el parametro "ctx"
     */
    @Command
    @NotifyChange("imagenPersona")
    public void subir(@ContextParam(ContextType.BIND_CONTEXT) BindContext contexto) {
        
        String pathRetornado; 
        
        // Extrae el evento de carga
        UploadEvent eventoCarga = (UploadEvent) contexto.getTriggerEvent();
        
        // Pasa el archivo a cargar al metodo "cargaArchivo" de la clase "FileUtil"
        pathRetornado = FileUtil.cargaArchivo(eventoCarga.getMedia());
        
        // Almacena el path retornado en el objeto.
        sitiosintere.setFotos(pathRetornado);
        
    }
    
    /**
     * Descarga el archivo de imagen
     */
    @Command
    public void descargar() {
        if (sitiosintere.getFotos() != null) {
            FileUtil.descargaArchivo(sitiosintere.getFotos());
        }
    }
    
    /**
     * Retorna un objeto de tipo imagen con la imagen de la persona.
     * @return
     */
    public AImage getImagenPersona() {
        AImage retorno = null;
        if (sitiosintere.getFotos() != null) {
            try {
                retorno = FileUtil.getImagenTamanoFijo(new AImage(sitiosintere.getFotos()), 100, -1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return retorno; 
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

    
    
    
    
}
