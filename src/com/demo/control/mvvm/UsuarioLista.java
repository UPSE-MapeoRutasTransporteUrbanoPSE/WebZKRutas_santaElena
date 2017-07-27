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

import com.demo.modelo.Usuario;
import com.demo.modelo.UsuarioDAO;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class UsuarioLista {
	public String textoBuscar;
	private UsuarioDAO usuarioDao = new UsuarioDAO();
	private Usuario usuarioSeleccionada;
	private List<Usuario> usuarios;
	
	@Command
	@NotifyChange({ "usuarios" })
	public void buscar() {
		if (usuarios != null) {
			usuarios = null;
		}
		usuarios = usuarioDao.getUsuarios(textoBuscar);
		usuarioSeleccionada = null;
	}
	
	@Command
	public void nuevo() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("Usuario", new Usuario());
		Window ventanaCargar = (Window) Executions.createComponents("/mvvm/usuario.zul", null, params);
		ventanaCargar.doModal();
	}
	
	@Command
	public void editar() {
		if (usuarioSeleccionada == null) {
			Clients.showNotification("Debe seleccionar una usuario.");
			return;
		}
		usuarioDao.getEntityManager().refresh(usuarioSeleccionada);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("Usuario", usuarioSeleccionada);
		Window ventanaCargar = (Window) Executions.createComponents("/mvvm/usuario.zul", null, params);
		ventanaCargar.doModal();
	}
	
	@Command
	public void eliminar() {
		if (usuarioSeleccionada == null) {
			Clients.showNotification("Debe seleccionar un usuario.");
			return;
		}
		Messagebox.show("Desea eliminar el registro seleccionado?", "Confirmación de borrado",
				Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new EventListener() {
					@Override
					public void onEvent(Event event) throws Exception {
						if (event.getName().equals("onYes")) {
							try {
								usuarioDao.getEntityManager().getTransaction().begin();
								usuarioSeleccionada.setEstado("X");
								usuarioSeleccionada = usuarioDao.getEntityManager().merge(usuarioSeleccionada);
								usuarioDao.getEntityManager().getTransaction().commit();
								;
								Clients.showNotification("Transaccion ejecutada con exito.");
								BindUtils.postGlobalCommand(null, null, "UsuarioLista.buscar", null);
							} catch (Exception e) {
								e.printStackTrace();
								usuarioDao.getEntityManager().getTransaction().rollback();
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

	public Usuario getUsuarioSeleccionada() {
		return usuarioSeleccionada;
	}

	public void setUsuarioSeleccionada(Usuario usuarioSeleccionada) {
		this.usuarioSeleccionada = usuarioSeleccionada;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
}
