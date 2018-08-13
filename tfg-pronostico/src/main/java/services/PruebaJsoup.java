
package services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class PruebaJsoup {

	//Coger todos los foros de apuestas 
	public static List<String> listaDeForos(int num) {
		List<String> foros = new ArrayList<String>();

		Integer pagina = 0;
		//Entramos en la pagina de foros de la liga
		String lista = "http://www.apuestasdeportivas.com/foro/pronosticos-de-futbol-espanol/";

		try {
			
			//Buscaremos los hilos de los partidos que hemos capturado en el método getJornadas
			while (pagina < 120) {
				Document d = Jsoup.connect(lista).timeout(600000).get();
				//Apuntaremos donde se encuentra el link del hilo
				Elements elem = d.select("td.lastpost.windowbg2");
				
				//Guardaremos el link en la lista de foros. Comprobaremos si no está guardado el link o el partido
				for (Element e : elem) {
					for (String partidos : getJornadas(num)) {
						String pag = e.select("a[href]").attr("href");
						if (pag.contains(partidos) && !foros.contains(pag)) {
							foros.add(pag);
						}
					}

				}
				pagina += 25;
				lista = lista + pagina;
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return foros;

	}

	public static List<String> getPartidos(int num) {
		List<String> foros = new ArrayList<String>();

		try {

			Document d = Jsoup.connect("http://www.apuestasdeportivas.com/foro/pronosticos-de-futbol-espanol/").timeout(600000).get();
			Elements elem = d.select("td.lastpost.windowbg2");

			for (Element e : elem) {
				for (String partidos : getJornadas(num)) {

					String pag = e.select("a[href]").attr("href");

					if (pag.contains(partidos)) {

						foros.add(partidos);

					}
				}

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return foros;

	}
	//Metodo para recoger los comentarios
	public static Map<String, String> getComentarios(List<String> foros) {

		Map<String, String> comentarios = new HashMap<String, String>();
		try {
			for (String f : foros) {

				Document d = Jsoup.connect(f).timeout(600000).get();
				Elements elem = d.select("div.d.d-5");
				Elements elem2 = d.select("div.usuario_pro.clearfix");

				for (int i = 0; i < elem.size(); i++) {
					String usuario = elem2.get(i).text().replaceAll("Un pronóstico de: ", "");
					String usuarioFinal = usuario.replaceAll(" Estadísticas del pronosticador Añadir pronosticador a favoritos", "");
					String comentario = elem.get(i).text();
					comentarios.put(usuarioFinal, comentario);

				}

			}

			for (String comentario : comentarios.values()) {
				System.out.println(comentario);

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return comentarios;

	}

	//Metodo para recoger a los usuarios y su puntuacion
	public static Map<String, Double> getUsuarios(Map<String, String> comentarios) {
		Map<String, Double> usuarios = new HashMap<String, Double>();
		try {
			for (String usu : comentarios.keySet()) {

				Document d = Jsoup.connect("http://www.apuestasdeportivas.com/tipster/" + usu).timeout(600000).get();
				Elements elem = d.select("span.cell_02.impar_amarillo_B.bold");

				String convertir = elem.get(0).text().replaceAll(" %", "");
				Double puntuacion = Double.parseDouble(convertir);

				usuarios.put(usu, puntuacion);
				System.out.println("usuario: " + usu + "puntuacion: " + puntuacion);

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return usuarios;

	}

	//Metodo para recoger partidos

	public static List<String> getJornadas(int num) {
		List<String> jornadas = new ArrayList<String>();
		List<String> result = new ArrayList<String>();
		try {
			//Nos conectamos a la página donde viene recogida la jornada y los partidos de esa jornada
			Document d = Jsoup.connect("http://www.marcadoresonline.com/futbol/españa/primeradivision/jornada" + num).timeout(600000).get();
			
			//Apuntamos a los nombres de los equipos (Durante la jornada, esta clasificado partido pendiente, partido finalizado y partido en juego)
			Elements elem = d.select("div.contPartido.pdt_pend");
			Elements elem2 = d.select("div.contPartido.pdt_fin");
			Elements elem3 = d.select("div.contPartido.pdt_enjuego");
			
			//Capturamos los nombres de los equipos de los partidos
			for (Element e : elem) {

				jornadas.add(e.select("a[href]").text());

			}
			for (Element e : elem2) {

				jornadas.add(e.select("a[href]").text());

			}
			for (Element e : elem3) {

				jornadas.add(e.select("a[href]").text());

			}
			
			//Parcheamos los nombres para que todos se llamen un nombre 
			for (String f : jornadas) {
				f = f.toLowerCase();
				if (f.contains("athletic bilbao")) {
					f = f.replaceAll("athletic bilbao", "athletic-bilbao");
				}
				if (f.contains("depor la coruña")) {
					f = f.replaceAll("depor la coruña", "deportivo-la-coruna");
				}
				if (f.contains("real madrid")) {
					f = f.replaceAll("real madrid", "real-madrid");
				}
				if (f.contains("levante u.d.")) {
					f = f.replaceAll("levante u.d.", "levante");
				}
				if (f.contains("atletico madrid")) {
					f = f.replaceAll("atletico madrid", "atletico-madrid");
				}
				if (f.contains("celta vigo")) {
					f = f.replaceAll("celta vigo", "celta-vigo");
				}
				if (f.contains("las palmas")) {
					f = f.replaceAll("las palmas", "las-palmas");
				}
				if (f.contains("athletic bilbao")) {
					f = f.replaceAll("athletic bilbao", "athletic-bilbao");
				}
				if (f.contains("depor la coruña")) {
					f = f.replaceAll("depor la coruña", "deportivo-la-coruna");
				}
				if (f.contains("real madrid")) {
					f = f.replaceAll("real madrid", "real-madrid");
				}
				if (f.contains("betis")) {
					f = f.replaceAll("betis", "real-betis");
				}
				if (f.contains("real sociedad")) {
					f = f.replaceAll("real sociedad", "real-sociedad");
				}
				if (f.contains("rayo vallecano")) {
					f = f.replaceAll("rayo vallecano", "rayo-vallecano");
				}
				f = f.replaceAll(" ", "-vs-");
				result.add(f);

			}
			

		} catch (

		IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	//Recoger los resultados de la jornada
	public static List<String> getResultados(int num) {
		
		List<String> res= new ArrayList<String>();
		try {
			Document d = Jsoup.connect("http://www.marcadoresonline.com/futbol/espa%C3%B1a/primeradivision/jornada" + num).timeout(600000).get();
			Elements elem = d.select("div.contPartido.pdt_fin");
			for (Element e : elem) {
				String a=e.select("span[id].numeroGoles").text();
				System.out.println(e.select("strong.equipo").text());
				System.out.println(e.select("span[id].numeroGoles").text());
				res.add(a);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;

	}

	public static Boolean comprobarPartido(Integer num) {
		Boolean hay = true;

		try {
			Document d = Jsoup.connect("http://www.marcadoresonline.com/futbol/espa%C3%B1a/primeradivision/jornada" + num).timeout(600000).get();
			Elements elem = d.select("div.contPartido.pdt_pend");

			if (elem.isEmpty()) {
				hay = false;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return hay;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int num = 19;
		System.out.println("------------------jornada: " + num + "------------");
		
		for(String a: getJornadas(num)) {
			System.out.println(a + "\n");
		}

	}

}
