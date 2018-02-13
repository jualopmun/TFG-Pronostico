
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

		try {
			Document d = Jsoup.connect("http://www.apuestasdeportivas.com/foro/pronosticos-de-futbol-espanol/").timeout(600000).get();
			Elements elem = d.select("td.lastpost.windowbg2");

			for (Element e : elem) {
				for (String partidos : getJornadas(num)) {
					String pag = e.select("a[href]").attr("href");
					if (pag.contains(partidos)) {

						foros.add(pag);
					}
				}

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
		List<String> partidos = new ArrayList<String>();
		List<String> fecha = new ArrayList<String>();
		try {
			Document d = Jsoup.connect("http://www.marcadoresonline.com/futbol/espa%C3%B1a/primeradivision/jornada" + num).timeout(600000).get();

			Elements elem = d.select("div.contPartido.pdt_pend");
			for (Element e : elem) {

				jornadas.add(e.select("a[href]").attr("href"));

			}

			Elements elem2 = d.select("span.fechaSeparador");
			for (Element e : elem2) {

				fecha.add(e.text());

			}

			for (String f : jornadas) {

				for (String fec : fecha) {
					//Primera pasada 
					if (f.contains(fec)) {
						String trans = f.replaceAll("/futbol/" + fec + "/", "");
						String transf2 = trans.replaceAll("-", "-vs-");
						partidos.add(transf2);

					}
				}

			}

			for (int i = 0; i < partidos.size(); i++) {

				if (partidos.get(i).contains("athleticbilbao")) {

					String g = partidos.get(i).replaceAll("athleticbilbao", "athletic-bilbao");
					partidos.remove(i);
					partidos.add(g);

				}

				if (partidos.get(i).contains("deportivocoruna")) {

					String g = partidos.get(i).replace("deportivocoruna", "deportivo-coruna");
					partidos.remove(i);
					partidos.add(g);

				}
				if (partidos.get(i).contains("realmadrid")) {

					String g = partidos.get(i).replace("realmadrid", "real-madrid");
					partidos.remove(i);
					partidos.add(g);
				}
				if (partidos.get(i).contains("realsociedad")) {

					String g = partidos.get(i).replace("realsociedad", "real-sociedad");
					partidos.remove(i);
					partidos.add(g);
				}
				if (partidos.get(i).contains("atleticomadrid")) {

					String g = partidos.get(i).replace("atleticomadrid", "atletico-madrid");
					partidos.remove(i);
					partidos.add(g);

				}
				if (partidos.get(i).contains("laspalmas")) {

					String g = partidos.get(i).replace("laspalmas", "las-palmas");
					partidos.remove(i);
					partidos.add(g);
				}
				if (partidos.get(i).contains("celtavigo")) {

					String g = partidos.get(i).replace("celtavigo", "celta-vigo");
					partidos.remove(i);
					partidos.add(g);
				}

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return partidos;
	}

	//Recoger los resultados de la jornada
	public static void getResultados(int num) {

		try {
			Document d = Jsoup.connect("http://www.marcadoresonline.com/futbol/espa%C3%B1a/primeradivision/jornada" + num).timeout(600000).get();
			Elements elem = d.select("div.contPartido.pdt_fin");
			for (Element e : elem) {

				System.out.println(e.select("strong.equipo").text());
				System.out.println(e.select("span[id].numeroGoles").text());

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
		int num = 23;
		System.out.println("------------------jornada: " + num + "------------");
		//getComentarios(listaDeForos(num));
		//getComentarios(listaDeForos(num));
		//getUsuarios(getComentarios(listaDeForos(num)));
		getResultados(num);

	}

}
