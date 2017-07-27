package com.demo.control.mvvm;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Window;

import com.demo.modelo.Lugare;
import com.demo.modelo.LugareDAO;



public class LugareControl {

	private LugareDAO lugareDao = new LugareDAO();
	
	
	private Lugare lugares;
	
	
	
	
	
	
	
	/**
     * Metodo que emula al metodo doAfterComposer de MVC
     */
    @AfterCompose
    public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {

        // Permite enlazar los componentes que se asocian con la anotacion @Wire
        Selectors.wireComponents(view, this, false);

        // Recupera el objeto pasado como parametro. 
        lugares = (Lugare) Executions.getCurrent().getArg().get("Lugare");
               
    }
    
    
    
    
    /**
     * Constructor para obtener el parametro enviado.
     */
    public LugareControl() {
        
        // Recupera el objeto pasado como parametro. 
    	lugares = (Lugare) Executions.getCurrent().getArg().get("Lugares");
                
    }
    
    
    
    
    
    /**
     * Graba la informacion. Para poder cerrar la ventana, pasa como parametro
     * desde el formulario la ventana: onClick="@command('grabar', ventana=winPersonaEditar)"
     */
    @Command
    public void grabar(@BindingParam("ventana")  Window ventana){
        
        try {
            // Inicia la transaccion
        	lugareDao.getEntityManager().getTransaction().begin();
        	
            
            // Almacena los datos.
            // Si es nuevo sa el metodo "persist" de lo contrario usa el metodo "merge"
            if (lugares.getId() == null) {
            	lugareDao.getEntityManager().persist(lugares);
            }else{
            	lugares = (Lugare) lugareDao.getEntityManager().merge(lugares);
            }
            
            // Cierra la transaccion.
            lugareDao.getEntityManager().getTransaction().commit();
            
            Clients.showNotification("Proceso Ejecutado con exito.");
            
            // Actualiza la lista
            BindUtils.postGlobalCommand(null, null, "LugareLista.buscar", null);
            
            // Cierra la ventana
            salir(ventana);
            
        } catch (Exception e) {
            e.printStackTrace();
            
            // Si hay error, reversa la transaccion.
            lugareDao.getEntityManager().getTransaction().rollback();
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




	public LugareDAO getLugareDao() {
		return lugareDao;
	}




	public void setLugareDao(LugareDAO lugareDao) {
		this.lugareDao = lugareDao;
	}




	public Lugare getLugares() {
		return lugares;
	}




	public void setLugares(Lugare lugares) {
		this.lugares = lugares;
	}
    
    
    
	
}
