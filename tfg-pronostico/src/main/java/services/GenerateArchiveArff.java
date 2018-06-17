
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


	public void generarArchivoWeka() throws Exception {
		String ruta = "C:/Users/karli/Desktop/Informatica/TFG/Repositorio/TFG-Pronostico/Archivo weka/weka.arff";
		File archivo = new File(ruta);
		BufferedWriter bw = new BufferedWriter(new FileWriter(archivo));

		try {

			//Entra a crear el archivo
			bw.write("@RELATION PREDICCION \n\n");
			bw.write("@ATTRIBUTE partidos {");
			
			//for (MatchForecast matches : matchForecastService.findAll()) {
			for(int i=0;i<matchForecastService.findAll().size(); i++) {
				if (i==matchForecastService.findAll().size()-1) {
					bw.write(matchForecastService.findAll().get(i).getLocal() + "-vs-" + matchForecastService.findAll().get(i).getVisit() + "} \n");
				} else {
					bw.write(matchForecastService.findAll().get(i).getLocal() + "-vs-" + matchForecastService.findAll().get(i).getVisit() + ",");
				}


			}

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
						bw.write(matches.getLocal() + "-vs-" + matches.getVisit() + "," + lematizar.get(0) + "," + lematizar.get(1) + "," + lematizar.get(2) + "," + resultado + "\n");
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
