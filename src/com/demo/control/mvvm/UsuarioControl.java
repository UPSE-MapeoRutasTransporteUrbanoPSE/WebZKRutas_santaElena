package com.demo.control.mvvm;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Window;

import com.demo.modelo.Usuario;
import com.demo.modelo.UsuarioDAO;

public class UsuarioControl {
	private UsuarioDAO usuarioDao = new UsuarioDAO();
	private Usuario usuario;
	
	public UsuarioControl(){
		usuario = (Usuario)Executions.getCurrent().getArg().get("Usuario");
	}
	
	@Command
	public void grabar(@BindingParam("ventana")  Window ventana){
		try {
			usuarioDao.getEntityManager().getTransaction().begin();
			if (usuario.getId() == null) {
				usuarioDao.getEntityManager().persist(usuario);
			}else{
				usuario =(Usuario) usuarioDao.getEntityManager().merge(usuario);
			}
			usuarioDao.getEntityManager().getTransaction().commit();
			Clients.showNotification("Proceso Ejecutado con exito.");
			BindUtils.postGlobalCommand(null, null, "UsuarioLista.buscar", null);
			salir(ventana);
		} catch (Exception e) {
			e.printStackTrace();
			usuarioDao.getEntityManager().getTransaction().rollback();
		}
	}
	
	@Command
	public void salir(@BindingParam("ventana")  Window ventana){
		ventana.detach();
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}
