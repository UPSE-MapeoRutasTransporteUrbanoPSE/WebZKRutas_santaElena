package com.demo.control.mvvm;



import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;

import com.demo.modelo.Opcion;
import com.demo.modelo.OpcionDAO;

public class Menu extends SelectorComposer<Component>{
	@Wire
	Grid grMenu;
	
	OpcionDAO opcionDao=new OpcionDAO();
	
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		
		Rows filas=grMenu.getRows();
		
		for(Opcion opcion : opcionDao.getOpciones()){
			//generando cada fila y estas van en filas
			Row fila=getFilaMenu(opcion.getTitulo(),opcion.getImagen(),
									opcion.getUrl());
			
			//llenar la fila en las filas
			filas.appendChild(fila);
			
		}
	}

	private Row getFilaMenu(String titulo,String imagen,String url){
		Row fila=new Row();
		
		Image img=new Image(imagen);
		Label etiqueta=new Label(titulo);
		
		fila.appendChild(img);
		fila.appendChild(etiqueta);
		
		//listener que escucha evento
		EventListener <Event> listener=new SerializableEventListener<Event>(){

			@Override
			public void onEvent(Event event) throws Exception {
				Executions.getCurrent().sendRedirect(url);
				
			}
			
		};
		fila.addEventListener(Events.ON_CLICK, listener);
		return fila;
	}
}
