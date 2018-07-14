
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
	
	
	public void generarArchivoWekaPalabrasImportante() throws Exception {
		String ruta = "C:\\Users\\karli\\Desktop\\Informatica\\TFG\\Repositorio\\TFG-Pronostico\\Archivo weka\\WekaPalabras\\wekapalabras"+dayService.findAll().iterator().next().getNum()+".arff";
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
			bw.write("@ATTRIBUTE gan real \n");
			bw.write("@ATTRIBUTE handicap real \n");
			bw.write("@ATTRIBUTE trepid real \n");
			bw.write("@ATTRIBUTE gol real \n");
			bw.write("@ATTRIBUTE victori real \n");
			bw.write("@ATTRIBUTE local real \n");
			bw.write("@ATTRIBUTE resultado {1,x,2} \n\n");

			bw.write("@data \n");
			int gan=0;
			int handicap=0;
			int trepid=0;
			int gol=0;
			int victori=0;
			int local=0;

			String resultado = null;
			List<Float> lematizar = new ArrayList<Float>();
			String lematizar2=null;
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
				for (String a : commentProccesService.comentariosProcesados(matches.getLocal())) {
					suma=suma+" "+a;
					
				}
				lematizar = PLNService.postaggin(suma);
				lematizar2= PLNService.lematizar(suma);
				
				String [] separarLem= lematizar2.split(" ");
				for(String a :separarLem) {
					
					if(a.equals("gan")) {
						gan++;
					}
					if(a.equals("handicap")) {
						handicap++;
					}
					if(a.equals("trepid")) {
						trepid++;
					}
					if(a.equals("gol")) {
						gol++;
					}
					if(a.equals("victori")) {
						victori++;
					}
					if(a.equals("local")) {
						local++;
					}
				}
					if (!lematizar.isEmpty())
						bw.write(matches.getLocal() + "," + matches.getVisit() + "," + lematizar.get(0) + "," + lematizar.get(1) + "," + lematizar.get(2) + "," +gan+","+handicap+","+trepid+","+gol+","+victori+","+local+","+ resultado + "\n");
					lematizar = new ArrayList<Float>();
					gan=0;
					handicap=0;
					trepid=0;
					gol=0;
					victori=0;
					local=0;
				

				commentProccesService.flush();
				
			}
		} catch (

		Exception e) {
			e.printStackTrace();
		}
		bw.close();
	}

}
