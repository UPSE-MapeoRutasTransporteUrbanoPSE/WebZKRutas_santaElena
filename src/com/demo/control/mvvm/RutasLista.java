package com.demo.control.mvvm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.demo.modelo.Ruta;
import com.demo.modelo.RutaDAO;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class RutasLista {
	public String textoBuscar;

	private RutaDAO rutaDao = new RutaDAO();

	private Ruta rutaSeleccionada;
	private List<Ruta> rutas;

	@Command
	@NotifyChange({ "rutas" })
	public void buscar() {
		if (rutas != null) {
			rutas = null;
		}

		rutas = rutaDao.getRutas(textoBuscar);
		rutaSeleccionada = null;

	}

	@Command
	public void nuevo() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("Ruta", new Ruta());

		Window ventanaCargar = (Window) Executions.createComponents("/mvvm/ruta.zul", null, params);
		ventanaCargar.doModal();
	}

	@Command
	public void editar() {
		if (rutaSeleccionada == null) {
			Clients.showNotification("Debe seleccionar una ruta.");
			return;
		}

		rutaDao.getEntityManager().refresh(rutaSeleccionada);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("Ruta", rutaSeleccionada);
		Window ventanaCargar = (Window) Executions.createComponents("/mvvm/ruta.zul", null, params);
		ventanaCargar.doModal();
	}

	@Command
	public void eliminar() {
		if (rutaSeleccionada == null) {
			Clients.showNotification("Debe seleccionar una ruta.");
			return;
		}
		Messagebox.show("Desea eliminar el registro seleccionado?", "Confirmación de borrado",
				Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new EventListener() {
					@Override
					public void onEvent(Event event) throws Exception {
						if (event.getName().equals("onYes")) {
							try {
								rutaDao.getEntityManager().getTransaction().begin();
								rutaSeleccionada.setEstado("X");
								rutaSeleccionada = rutaDao.getEntityManager().merge(rutaSeleccionada);
								rutaDao.getEntityManager().getTransaction().commit();
								;
								Clients.showNotification("Transaccion ejecutada con exito.");
								BindUtils.postGlobalCommand(null, null, "RutaLista.buscar", null);
							} catch (Exception e) {
								// TODO: handle exception
								e.printStackTrace();
								rutaDao.getEntityManager().getTransaction().rollback();
							}
						}
					}
				});

	}
	

	public String getTextoBuscar() {
		return textoBuscar;
	}

	public void setTextoBuscar(String textoBuscar) {
		this.textoBuscar = textoBuscar;
	}

	public Ruta getRutaSeleccionada() {
		return rutaSeleccionada;
	}

	public void setRutaSeleccionada(Ruta rutaSeleccionada) {
		this.rutaSeleccionada = rutaSeleccionada;
	}

	public List<Ruta> getRutas() {
		return rutas;
	}

	public void setRutas(List<Ruta> rutas) {
		this.rutas = rutas;
	}
	

}
