package com.demo.control.general;

import org.zkoss.zul.Textbox;

//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;

/**
 * Clase utilitaria para gestión de google Maps

 */
//@NoArgsConstructor
public class GMaps {
	
	// Clave de Google Maps:
	private String key = "AIzaSyDlzw2oyl1QeNM8fOEIsDB3X_73Z18PnOw";

	// Destino donde se mostrarÃ¡n las coordenadas
	private Textbox coordenadas; 

	public String getMapaHtml(Textbox coordenadas) {
		StringBuilder contenido = new StringBuilder(); 
		
		// Valor por defecto de las coordenadas 
		if (coordenadas.getValue().isEmpty()) {
			coordenadas.setValue("lat:-2.232,lng:-80.879");
		}
		
		this.coordenadas = coordenadas; 
		
		// Clave de Google
		contenido.append("<script src='https://maps.googleapis.com/maps/api/js?key=" + this.key + "&callback=initMap' async defer></script>");
		// Estilo.
		contenido.append("<head><style> #map {height: 100%; width:100%; } </style></head>");
		// Inicio del Script 
		contenido.append("<body><div id='map'></div><script>");
		// Inicializacion del Mapa
		contenido.append("function initMap() {var map = new google.maps.Map(document.getElementById('map'), {center: {" + coordenadas.getValue() + "}, zoom: 14});");
		// Marcador
		contenido.append("var marker = new google.maps.Marker({position: {" + coordenadas.getValue() + "}, draggable: true, map: map, title: '<<Titulo>>' });");
		//polilinea
		contenido.append("var flightPlanCoordinates = [{lat: 37.772, lng: -122.214},{lat: 21.291, lng: -157.821},{lat: -18.142, lng: 178.431},{lat: -27.467, lng: 153.027}];");
		contenido.append("var flightPath = new google.maps.Polyline({path: flightPlanCoordinates,geodesic: true,strokeColor: '#FF0000',strokeOpacity: 1.0,strokeWeight: 2});");
		contenido.append("flightPath.setMap(map);");
		// Listener para el movimiento
		//contenido.append("google.maps.event.addListener(marker, 'dragend', function(evt){ var txt = zk.Widget.$(jq('$" + coordenadas.getId() + "')); var val = 'lat:' + evt.latLng.lat().toFixed(6) + ', lng:' + evt.latLng.lng().toFixed(6); txt.setValue(val); txt.smartUpdate('value', val); });");
		// Finaliza el script
		contenido.append("}</script></body>"); 
		
		return contenido.toString();
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Textbox getCoordenadas() {
		return coordenadas;
	}

	public void setCoordenadas(Textbox coordenadas) {
		this.coordenadas = coordenadas;
	}
	
	
	
}
