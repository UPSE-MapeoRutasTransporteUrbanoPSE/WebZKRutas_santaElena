package com.demo.control.mvvm;


import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zul.Messagebox;

import com.demo.modelo.Busesruta;
import com.demo.modelo.BusesrutaDAO;
import com.demo.modelo.Ruta;
import com.demo.modelo.RutaDAO;

public class RecorridoLista {

	private Ruta rutas = new Ruta();
	private Date fecha;
	private BusesrutaDAO busesrutaDao = new BusesrutaDAO();
	private RutaDAO rutaDao = new RutaDAO();
	private List<Busesruta> recorrido;
	
//realiza busqueda enviando parametros fecha y ruta
	@Command
	@NotifyChange({"recorrido"})
	public void buscar() {
		recorrido = busesrutaDao.getBusesrecorridos(rutas.getRuta(), fecha);	
	}
	

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public Ruta getRutas() {
		return rutas;
	}

	public void setRutas(Ruta rutas) {
		this.rutas = rutas;
	}
	public List<Busesruta> getRecorrido() {
		return recorrido;
	}

	public void setRecorrido(List<Busesruta> recorrido) {
		this.recorrido = recorrido;
	}

	public List<Ruta> getRuta(){
		return rutaDao.getRuta();
	}

}
