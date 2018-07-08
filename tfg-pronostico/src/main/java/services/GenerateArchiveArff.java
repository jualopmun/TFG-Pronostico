
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
	private MatchForecastService	matchForecastService;

	@Autowired
	private CommentProcessService	commentProccesService;

	@Autowired
	private MatchFinalService		matchFinalService;
	@Autowired
	private DayService dayService;

	
	public void generarArchivoWekaComentarios() throws Exception {
		String ruta = "C:/Users/karli/Desktop/Informatica/TFG/Repositorio/TFG-Pronostico/Archivo weka/wekaComent"+dayService.findAll().iterator().next().getNum()+".arff";
		File archivo = new File(ruta);
		BufferedWriter bw = new BufferedWriter(new FileWriter(archivo));

		try {

			//Entra a crear el archivo
			bw.write("@RELATION PREDICCION \n\n");
			bw.write("@ATTRIBUTE local {real-madrid,barcelona,sevilla,levante,deportivo-la-coruna,celta-vigo,leganes,valencia,villarreal,alaves,getafe,real-sociedad,malaga,girona,atletico-madrid,espanyol,las-palmas,eibar,athletic-bilbao,real-betis}\r\n" + 
					 "@ATTRIBUTE visitante{real-madrid,barcelona,sevilla,levante,deportivo-la-coruna,leganes,celta-vigo,valencia,alaves,villarreal,getafe,real-sociedad,malaga,girona,atletico-madrid,espanyol,las-palmas,eibar,athletic-bilbao,real-betis} \n");

			bw.write("@ATTRIBUTE NN real \n");
			bw.write("@ATTRIBUTE VB real \n");
			bw.write("@ATTRIBUTE ADJ real \n");
			bw.write("@ATTRIBUTE resultado {1,x,2} \n\n");

			bw.write("@data \n");

			String resultado = null;
			List<Float> lematizar = new ArrayList<Float>();
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
				String suma="";
				for (String a : commentProccesService.comentariosProcesados2(matches.getLocal())) {
					suma=suma+" "+a;
					
				}
				lematizar = PLNService.postaggin(suma);
					if (!lematizar.isEmpty())
						bw.write(matches.getLocal() + "," + matches.getVisit() + "," + lematizar.get(0) + "," + lematizar.get(1) + "," + lematizar.get(2) + "," + resultado + "\n");
					lematizar = new ArrayList<Float>();

				

				commentProccesService.flush();
				
			}

		} catch (

		Exception e) {
			e.printStackTrace();
		}
		bw.close();

	}
	
	
	public void generarArchivoWekaLos3MejoresComentarios() throws Exception {
		String ruta = "C:/Users/karli/Desktop/Informatica/TFG/Repositorio/TFG-Pronostico/Archivo weka/weka3Mejores"+dayService.findAll().iterator().next().getNum()+".arff";
		File archivo = new File(ruta);
		BufferedWriter bw = new BufferedWriter(new FileWriter(archivo));

		try {

			//Entra a crear el archivo
			bw.write("@RELATION PREDICCION \n\n");
			bw.write("@ATTRIBUTE local {real-madrid,barcelona,sevilla,levante,deportivo-la-coruna,celta-vigo,leganes,valencia,villarreal,alaves,getafe,real-sociedad,malaga,girona,atletico-madrid,espanyol,las-palmas,eibar,athletic-bilbao,real-betis}\r\n" + 
					 "@ATTRIBUTE visitante{real-madrid,barcelona,sevilla,levante,deportivo-la-coruna,leganes,celta-vigo,valencia,alaves,villarreal,getafe,real-sociedad,malaga,girona,atletico-madrid,espanyol,las-palmas,eibar,athletic-bilbao,real-betis} \n");
			
			bw.write("@ATTRIBUTE NN real \n");
			bw.write("@ATTRIBUTE VB real \n");
			bw.write("@ATTRIBUTE ADJ real \n");
			bw.write("@ATTRIBUTE resultado {1,x,2} \n\n");

			bw.write("@data \n");

			String resultado = null;
			List<Float> lematizar = new ArrayList<Float>();
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

					lematizar = PLNService.postaggin(a);
					if (!lematizar.isEmpty())
						bw.write(matches.getLocal() + "," + matches.getVisit() + "," + lematizar.get(0) + "," + lematizar.get(1) + "," + lematizar.get(2) + "," + resultado + "\n");
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

}
