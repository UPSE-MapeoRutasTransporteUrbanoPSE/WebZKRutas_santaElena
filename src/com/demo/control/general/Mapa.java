package com.demo.control.general;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.zkoss.zul.Textbox;





public class Mapa {
	// Clave de Google Maps:
		private String key = "AIzaSyDlzw2oyl1QeNM8fOEIsDB3X_73Z18PnOw";
		private Textbox coordenadas; 
		public String getMapaHtml(Textbox coordenadas) {
			StringBuilder contenido = new StringBuilder(); 
			
			if (coordenadas.getValue().isEmpty()) {
				coordenadas.setValue("lat:-2.232,lng:-80.879");
			}
			
			this.coordenadas = coordenadas; 
			
			LeerArchivo le = new LeerArchivo();
			System.out.println("holaaaaafgdfgf ");
			
			List<Punto> punto=new  ArrayList<>();
			
			
			Punto p = new Punto();
			 String ruta = "C:/Transcisa7.gpx";
			  //Y generamos el objeto respecto a la ruta del archivo
			  File archivo = new File(ruta);
			 
			   String info = "";
		        List<String> gpxList = decodeGPX(archivo);
		        for(int i = 0; i < gpxList.size(); i++){
		            info = gpxList.get(i).toString();
		           // System.out.println("info: "+info);
		            String[] latlong = info.split(":");
		            	            
		            double latitude = Double.parseDouble(latlong[0]);
		            double longitude = Double.parseDouble(latlong[1]);
		            
		            Punto q = new Punto(latitude,longitude);
		            punto.add(q);
		            
		            
		        }
		        //String []puntoss=new String [punto.size()];
		        //int[] numeros = new int[punto.size()];
		        String puntosPaJS = "[";
		        
		       for(int i=0;i<punto.size();i++){
		        	System.out.println("puntosOriginales: "+punto.get(i).toString());
		        	//puntoss[i]=punto.get(i).toString();
		        	if(i<punto.size()-1)
		        	{	
		        		puntosPaJS = puntosPaJS + punto.get(i).toString() + ",";
		        		}
		        	else{
		        		puntosPaJS = puntosPaJS + punto.get(i).toString() + "}";
		        	}
		        	
		        	
		        }
		        
		        // List<Punto> distancias=douglasPeucker(punto,0.000000025);
		        System.out.println("NUMERO DE PUNTOS ORIGINALES : "+punto.size());
			
			
			//-2.226381, -80.858324 0, 180
			
			contenido.append("<script src='https://maps.googleapis.com/maps/api/js?key=" + this.key + "&callback=initMap' async defer></script>");
			contenido.append("<head><style> #map {height: 100%; width:100%; } </style></head>");
			contenido.append("<body><div id='map'></div><script>");
			contenido.append("function initMap() {var map = new google.maps.Map(document.getElementById('map'), {zoom: 11,center: {lat: -2.226381, lng: -80.858324},mapTypeId: 'terrain'});");	
			//contenido.append("var flightPlanCoordinates = [{lat: -2.2316804, lng: -80.878799},{lat: -2.2231879, lng: -80.8591483},{lat: -2.220931, lng: -80.8626877},{lat: -2.2314787, lng: -80.8789409}];");
			String puntosADibujar = "var flightPath = new google.maps.Polyline({path:"+punto+",geodesic: true,strokeColor: '#FF0000',strokeOpacity: 1.0,strokeWeight: 3});";
			System.out.println(puntosADibujar);
			contenido.append(puntosADibujar);
			//contenido.append("var marker, poly, i;"+ for (int i = 0; i < punto.size(); i++)+" { marker = new google.maps.Marker({ position: new google.maps.LatLng("+punto.get(i).getLatitud()+", "+ punto.get(i).getLongitud()+"),map: map}); poly = new google.maps.Polyline({ path: new google.maps.LatLng("+punto.get(i).getLatitud()+", "+ punto.get(i).getLongitud()+"),strokeColor: '#000000',strokeOpacity: 1.0,strokeWeight: 3});})(marker,"+i+"));}}");
			
			
			contenido.append("flightPath.setMap(map);");
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
		
		
		
		private static List<String> decodeGPX(File is){
	        List<String> list = new ArrayList<>();

	        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
	        try {
	            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
	          //  FileInputStream fileInputStream = new FileInputStream(is);
	          Document document = documentBuilder.parse(is);
	           Element elementRoot = document.getDocumentElement();

	            NodeList nodelist_trkpt = document.getElementsByTagName("trkpt");

	            for(int i = 0; i < nodelist_trkpt.getLength(); i++){

	                Node node = nodelist_trkpt.item(i);
	                NamedNodeMap attributes = node.getAttributes();

	                String newLatitude = attributes.getNamedItem("lat").getTextContent();
	                Double newLatitude_double = Double.parseDouble(newLatitude);

	                String newLongitude = attributes.getNamedItem("lon").getTextContent();
	                Double newLongitude_double = Double.parseDouble(newLongitude);

	                String newLocationName = newLatitude + ":" + newLongitude;

	                list.add(newLocationName);

	            }

	  //          is.close();

	        } catch (ParserConfigurationException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        } catch (FileNotFoundException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        } catch (SAXException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        return list;
	    }
		
}
