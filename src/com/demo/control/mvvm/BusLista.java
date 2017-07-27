package com.demo.control.mvvm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.demo.control.general.PrintUtil;
import com.demo.modelo.Bus;
import com.demo.modelo.BusDAO;
import com.demo.modelo.CooperativaDAO;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class BusLista {
	public String textoBuscar;
	
	private BusDAO busDao = new BusDAO();
	
	private Bus busSeleccionada;
	private List<Bus> bus;
	
	@Command
	@NotifyChange({"bus"})
	public void buscar(){
		if (bus !=null) {
			bus = null;
		}
		
		bus = busDao.getBus(textoBuscar);
		busSeleccionada = null;
		
	}
	@Command
	public void nuevo() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("Bus", new Bus());
		
		Window ventanaCargar = (Window) Executions.createComponents("/mvvm/buses.zul", null, params);
		ventanaCargar.doModal();
		
	}
	@Command
	public void editar() {
		if (busSeleccionada == null) {
			
			Clients.showNotification("Debe seleccionar un bus.");
			return;
			
		}
		
		busDao.getEntityManager().refresh(busSeleccionada);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("Bus", busSeleccionada);
		Window ventanaCargar = (Window) Executions.createComponents("/mvvm/buses.zul", null, params);
		ventanaCargar.doModal();
	}
	
	
	
	public String getTextoBuscar() {
		return textoBuscar;
	}
	public void setTextoBuscar(String textoBuscar) {
		this.textoBuscar = textoBuscar;
	}
	public Bus getBusSeleccionada() {
		return busSeleccionada;
	}
	public void setBusSeleccionada(Bus busSeleccionada) {
		this.busSeleccionada = busSeleccionada;
	}
	public List<Bus> getBus() {
		return bus;
	}
	public void setBus(List<Bus> bus) {
		this.bus = bus;
	}
	

}
