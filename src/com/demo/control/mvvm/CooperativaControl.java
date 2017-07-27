package com.demo.control.mvvm;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Window;

import com.demo.modelo.Cooperativa;
import com.demo.modelo.CooperativaDAO;


public class CooperativaControl {
	private CooperativaDAO cooperativaDao = new CooperativaDAO();
	private Cooperativa cooperativa;
	
	public CooperativaControl(){
		cooperativa = (Cooperativa)Executions.getCurrent().getArg().get("Cooperativa");
	}
	
	@Command
	public void grabar(@BindingParam("ventana")  Window ventana){
		try {
			cooperativaDao.getEntityManager().getTransaction().begin();
			if (cooperativa.getId()==null) {
				cooperativaDao.getEntityManager().persist(cooperativa);
			} else {
				cooperativa =(Cooperativa) cooperativaDao.getEntityManager().merge(cooperativa);
			}
			
			cooperativaDao.getEntityManager().getTransaction().commit();
			Clients.showNotification("Proceso Ejecutado con exito.");
			
			BindUtils.postGlobalCommand(null, null, "CooperativaLista", null);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			cooperativaDao.getEntityManager().getTransaction().rollback();
		}
	}
	
	@Command
	public void salir(@BindingParam("ventana")  Window ventana){
		ventana.detach();
	}

	public Cooperativa getCooperativa() {
		return cooperativa;
	}

	public void setCooperativa(Cooperativa cooperativa) {
		this.cooperativa = cooperativa;
	}
	

}
