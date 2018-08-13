
package services;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.MatchForecast;

@Service
public class GenerateArchiveArff {

	@Autowired
	private MatchForecastService matchForecastService;

	@Autowired
	private CommentProcessService commentProccesService;

	@Autowired
	private MatchFinalService matchFinalService;
	@Autowired
	private DayService dayService;

	public void generarArchivoWekaComentarios(String ruta) throws Exception {
		// Implementamos la ruta donde estara alojado el fichero y generamos el archivo
		// .arff
		String rutaFinal = ruta + "\\ComentariosGeneralJornada" + dayService.findAll().iterator().next().getNum()
				+ ".arff";
		File archivo = new File(rutaFinal);
		if (!archivo.exists()) {
			archivo.createNewFile();
		}
		BufferedWriter bw = new BufferedWriter(new FileWriter(archivo));

		try {

			// Generamos la cabecera del archivo
			bw.write("@RELATION PREDICCION \n\n");
			bw.write(
					"@ATTRIBUTE local {real-madrid,barcelona,sevilla,levante,deportivo-la-coruna,celta-vigo,leganes,valencia,villarreal,alaves,getafe,real-sociedad,malaga,girona,atletico-madrid,espanyol,las-palmas,eibar,athletic-bilbao,real-betis}\r\n"
							+ "@ATTRIBUTE visitante{real-madrid,barcelona,sevilla,levante,deportivo-la-coruna,leganes,celta-vigo,valencia,alaves,villarreal,getafe,real-sociedad,malaga,girona,atletico-madrid,espanyol,las-palmas,eibar,athletic-bilbao,real-betis} \n");

			bw.write("@ATTRIBUTE NN real \n");
			bw.write("@ATTRIBUTE VB real \n");
			bw.write("@ATTRIBUTE ADJ real \n");
			bw.write("@ATTRIBUTE resultado {1,x,2} \n\n");

			bw.write("@data \n");

			// Empezamos a generar los datos
			String resultado = null;
			List<Float> lematizar = new ArrayList<Float>();
			// Calculamos el resultados por [1,x,2] en cada partido
			for (MatchForecast matches : matchForecastService.findAll()) {

				if (matches.getResultLocal() > matches.getResultVisit()) {
					resultado = "1";
				}
				if (matches.getResultLocal() == matches.getResultVisit()) {
					resultado = "x";
				}

				if (matches.getResultLocal() < matches.getResultVisit()) {
					resultado = "2";
				}
				// Jutamos todas los comentarios en uno solo
				String suma = "";
				for (String a : commentProccesService.comentariosProcesados2(matches.getLocal())) {
					suma = suma + " " + a;

				}
				// Lematizamos
				lematizar = PLNService.postaggin(suma);
				// Generamos el resultado final
				if (!lematizar.isEmpty())
					bw.write(matches.getLocal() + "," + matches.getVisit() + "," + lematizar.get(0) + ","
							+ lematizar.get(1) + "," + lematizar.get(2) + "," + resultado + "\n");
				lematizar = new ArrayList<Float>();

				commentProccesService.flush();

			}

		} catch (

		Exception e) {
			e.printStackTrace();
		}
		bw.close();

	}

	public void generarArchivoWekaLos3MejoresComentarios(String ruta) throws Exception {
		// Implementamos la ruta donde estara alojado el fichero y generamos el archivo
		// .arff

		String rutaFinal = ruta + "\\mejores3comentariosJornada" + dayService.findAll().iterator().next().getNum()
				+ ".arff";
		File archivo = new File(rutaFinal);
		if (!archivo.exists()) {
			archivo.createNewFile();
		}
		BufferedWriter bw = new BufferedWriter(new FileWriter(archivo));

		try {

			// Generamos la cabecera del archivo
			bw.write("@RELATION PREDICCION \n\n");
			bw.write(
					"@ATTRIBUTE local {real-madrid,barcelona,sevilla,levante,deportivo-la-coruna,celta-vigo,leganes,valencia,villarreal,alaves,getafe,real-sociedad,malaga,girona,atletico-madrid,espanyol,las-palmas,eibar,athletic-bilbao,real-betis}\r\n"
							+ "@ATTRIBUTE visitante{real-madrid,barcelona,sevilla,levante,deportivo-la-coruna,leganes,celta-vigo,valencia,alaves,villarreal,getafe,real-sociedad,malaga,girona,atletico-madrid,espanyol,las-palmas,eibar,athletic-bilbao,real-betis} \n");

			bw.write("@ATTRIBUTE NN real \n");
			bw.write("@ATTRIBUTE VB real \n");
			bw.write("@ATTRIBUTE ADJ real \n");
			bw.write("@ATTRIBUTE resultado {1,x,2} \n\n");

			bw.write("@data \n");
			// Empezamos a generar los datos
			String resultado = null;
			List<Float> lematizar = new ArrayList<Float>();
			// Calculamos el resultados por [1,x,2] en cada partido
			for (MatchForecast matches : matchForecastService.findAll()) {

				if (matches.getResultLocal() > matches.getResultVisit()) {
					resultado = "1";
				}
				if (matches.getResultLocal() == matches.getResultVisit()) {
					resultado = "x";
				}

				if (matches.getResultLocal() < matches.getResultVisit()) {
					resultado = "2";
				}

				for (String a : commentProccesService.comentariosProcesados(matches.getLocal())) {
					// Lematizamos
					lematizar = PLNService.postaggin(a);
					// Generamos el resultado final
					if (!lematizar.isEmpty())
						bw.write(matches.getLocal() + "," + matches.getVisit() + "," + lematizar.get(0) + ","
								+ lematizar.get(1) + "," + lematizar.get(2) + "," + resultado + "\n");
					lematizar = new ArrayList<Float>();

				}

				commentProccesService.flush();

			}

		} catch (

		Exception e) {
			e.printStackTrace();
		}
		bw.close();

	}

	public void generarArchivoWekaPalabrasImportante(String ruta) throws Exception {
		// Implementamos la ruta donde estara alojado el fichero y generamos el archivo
		// .arff
		String rutaFinal = ruta + "\\PalabrasImportantesJornada" + dayService.findAll().iterator().next().getNum()
				+ ".arff";
		File archivo = new File(rutaFinal);
		if (!archivo.exists()) {
			archivo.createNewFile();
		}
		BufferedWriter bw = new BufferedWriter(new FileWriter(archivo));

		try {

			// Generamos la cabezera del archivo
			bw.write("@RELATION PREDICCION \n\n");
			bw.write(
					"@ATTRIBUTE local {real-madrid,barcelona,sevilla,levante,deportivo-la-coruna,celta-vigo,leganes,valencia,villarreal,alaves,getafe,real-sociedad,malaga,girona,atletico-madrid,espanyol,las-palmas,eibar,athletic-bilbao,real-betis}\r\n"
							+ "@ATTRIBUTE visitante{real-madrid,barcelona,sevilla,levante,deportivo-la-coruna,leganes,celta-vigo,valencia,alaves,villarreal,getafe,real-sociedad,malaga,girona,atletico-madrid,espanyol,las-palmas,eibar,athletic-bilbao,real-betis} \n");

			bw.write("@ATTRIBUTE NN real \n");
			bw.write("@ATTRIBUTE VB real \n");
			bw.write("@ATTRIBUTE ADJ real \n");
			bw.write("@ATTRIBUTE gan real \n");
			bw.write("@ATTRIBUTE handicap real \n");
			bw.write("@ATTRIBUTE trepid real \n");
			bw.write("@ATTRIBUTE gol real \n");
			bw.write("@ATTRIBUTE victori real \n");
			bw.write("@ATTRIBUTE local real \n");
			bw.write("@ATTRIBUTE resultado {1,x,2} \n\n");

			bw.write("@data \n");
			// Inicializamos las palabras que queremos contar
			int gan = 0;
			int handicap = 0;
			int trepid = 0;
			int gol = 0;
			int victori = 0;
			int local = 0;

			String resultado = null;
			List<Float> postaggin = new ArrayList<Float>();
			String lematizar = null;
			// Calculamos el resultados por [1,x,2] en cada partido
			for (MatchForecast matches : matchForecastService.findAll()) {

				if (matches.getResultLocal() > matches.getResultVisit()) {
					resultado = "1";
				}
				if (matches.getResultLocal() == matches.getResultVisit()) {
					resultado = "x";
				}

				if (matches.getResultLocal() < matches.getResultVisit()) {
					resultado = "2";
				}
				String suma = "";
				for (String a : commentProccesService.comentariosProcesados(matches.getLocal())) {
					suma = suma + " " + a;

				}
				// Realizamos el postaggin
				postaggin = PLNService.postaggin(suma);
				// lematizamos
				lematizar = PLNService.lematizar(suma);

				String[] separarLem = lematizar.split(" ");
				// Contamos las palabras por cada uno de los comentarios
				for (String a : separarLem) {

					if (a.equals("gan")) {
						gan++;
					}
					if (a.equals("handicap")) {
						handicap++;
					}
					if (a.equals("trepid")) {
						trepid++;
					}
					if (a.equals("gol")) {
						gol++;
					}
					if (a.equals("victori")) {
						victori++;
					}
					if (a.equals("local")) {
						local++;
					}
				}
				// Generamos los resultados
				if (!postaggin.isEmpty())
					bw.write(matches.getLocal() + "," + matches.getVisit() + "," + postaggin.get(0) + ","
							+ postaggin.get(1) + "," + postaggin.get(2) + "," + gan + "," + handicap + "," + trepid
							+ "," + gol + "," + victori + "," + local + "," + resultado + "\n");
				postaggin = new ArrayList<Float>();
				gan = 0;
				handicap = 0;
				trepid = 0;
				gol = 0;
				victori = 0;
				local = 0;

				commentProccesService.flush();

			}
		} catch (

		Exception e) {
			e.printStackTrace();
		}
		bw.close();
	}

}
