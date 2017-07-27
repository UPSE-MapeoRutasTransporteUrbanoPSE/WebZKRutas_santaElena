package com.demo.control.mvvm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Window;

import com.demo.control.general.PrintUtil;
import com.demo.modelo.Busesruta;
import com.demo.modelo.BusesrutaDAO;

import com.demo.modelo.RutaDAO;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class BusesRutaLista {
	
	public String textoBuscar;
	private BusesrutaDAO busesrutaDao = new BusesrutaDAO();
	private Busesruta recorridoSeleccionada;
	private List<Busesruta> recorrido;
	
	private RutaDAO rutaDao = new RutaDAO();
	

	@Command
	@NotifyChange({"recorrido"})
	public void buscar(){
		if (recorrido !=null) {
			recorrido = null;
		}
		
		recorrido = busesrutaDao.getBusesruta(textoBuscar);
		recorridoSeleccionada = null;
		
	}
	
	@Command
	public void nuevo() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("Busesruta", new Busesruta());
		
		Window ventanaCargar = (Window) Executions.createComponents("/mvvm/recorridos.zul", null, params);
		ventanaCargar.doModal();
	}
	@Command
	public void editar() {
		if (recorridoSeleccionada == null) {
			
			Clients.showNotification("Debe seleccionar un recorrido.");
			return;
		}
		
		busesrutaDao.getEntityManager().refresh(recorridoSeleccionada);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("Busesruta", recorridoSeleccionada);
		
		Window ventanaCargar = (Window) Executions.createComponents("/mvvm/recorridos.zul", null, params);
		ventanaCargar.doModal();
		
		
	}
	
	@Command
	public void imprimir() {
		
		// Crea un arreglo de parametros.
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		// Coloca el parametro a pasar al reporte.
		//parametros.put("P_ID_BUSESRUTA", recorridoSeleccionada.getId());
		
		// Ejecuta el reporte.
		PrintUtil.ejecutaReporte(rutaDao, 
				                 "/reportes/grafica.jasper", 
				                 parametros, 
				                 PrintUtil.FORMATO_PDF);
	}
	@Command
	public void imprimirreporte() {
		
		// Crea un arreglo de parametros.
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		// Coloca el parametro a pasar al reporte.
		//parametros.put("P_ID_BUSESRUTA", recorridoSeleccionada.getId());
		
		// Ejecuta el reporte.
		PrintUtil.ejecutaReporte(rutaDao, 
				                 "/reportes/recorridos.jasper", 
				                 parametros, 
				                 PrintUtil.FORMATO_PDF);
	}
    

	public String getTextoBuscar() {
		return textoBuscar;
	}

	public void setTextoBuscar(String textoBuscar) {
		this.textoBuscar = textoBuscar;
	}

	public Busesruta getRecorridoSeleccionada() {
		return recorridoSeleccionada;
	}

	public void setRecorridoSeleccionada(Busesruta recorridoSeleccionada) {
		this.recorridoSeleccionada = recorridoSeleccionada;
	}

	public List<Busesruta> getRecorrido() {
		return recorrido;
	}

	public void setRecorrido(List<Busesruta> recorrido) {
		this.recorrido = recorrido;
	}
	
	
	
}
