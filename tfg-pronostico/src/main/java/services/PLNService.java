
package services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.annolab.tt4j.TreeTaggerException;
import org.tartarus.snowball.ext.SpanishStemmer;

import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

public class PLNService {

	public static String eliminarStopWords(String comentario) throws FileNotFoundException, IOException {
		String archivo = "stopWords.txt";
		InputStream file = Thread.currentThread().getContextClassLoader().getResourceAsStream(archivo);
		int numeroLineas = 10000;
		byte chars[] = new byte[numeroLineas];
		file.read(chars);
		String palabra = "";
		String result = "";
		for (int i = 0; i < numeroLineas; i++) {

			palabra += (char) chars[i];

		}

		String[] separar = palabra.split("\r\n");
		comentario = comentario.toLowerCase();
		comentario = remove1(comentario);
		String[] separarPalabras = comentario.split("\\W+");
		for (String a : separarPalabras) {

			for (String b : separar) {
				if (a.compareTo(b) == 0) {
					a = a.replaceAll(a, "");
				}

			}
			result += " " + a;
		}

		return result;

	}

	public static String transformaPalabras(String comentario) throws FileNotFoundException, IOException {
		String archivo = "ApodosEquiposFutbol.txt";
		InputStream file = Thread.currentThread().getContextClassLoader().getResourceAsStream(archivo);
		int numeroLineas = 10000;
		byte chars[] = new byte[numeroLineas];
		file.read(chars);
		String palabra = "";
		String result = "";
		for (int i = 0; i < numeroLineas; i++) {

			palabra += (char) chars[i];

		}

		String[] separar = palabra.split("\r\n");
		comentario = comentario.toLowerCase();
		comentario = remove1(comentario);
		//String[] separarPalabras = comentario.split("\\W+");

		for (String b : separar) {
			String[] separacionFinal = b.split("->");

			for (int i = 0; i < separacionFinal.length; i++) {
				String[] equipos = separacionFinal[i].split(",");
				for (String equiposT : equipos) {
					comentario = comentario.replaceAll(equiposT, separacionFinal[0]);
				}
			}
		}

		return result;
	}

	//Nos interesa realizar una lista de contadores para procesarlo
	//lista[Nombres,Verbos,Adjetivos]
	public static List<Float> postaggin(String comentario) {

		Properties props = new Properties();
		props.put("annotators", "tokenize, ssplit, pos, lemma, ner");
		props.setProperty("tokenize.language", "es");
		props.setProperty("pos.model", "edu/stanford/nlp/models/pos-tagger/spanish/spanish-distsim.tagger");
		//props.setProperty("parse.model", "edu/stanford/nlp/models/lexparser/spanishPCFG.ser.gz");
		List<Float> res = new ArrayList<Float>();
		float NN = 0;
		float VB = 0;
		float ADJ = 0;
		float contPal = 0;
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		Annotation document = new Annotation(comentario);

		pipeline.annotate(document);
		List<CoreMap> sentences = document.get(SentencesAnnotation.class);
		for (CoreMap sentence : sentences) {
			for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
				String pos = token.get(PartOfSpeechAnnotation.class);
				String word = token.get(TextAnnotation.class);
				if (pos.contains("nc") || pos.contains("np0")) {
					NN++;
				}

				if (pos.contains("va") || pos.contains("vm") || pos.contains("vs")) {
					VB++;
				}
				if (pos.contains("ao") || pos.contains("aq")) {
					ADJ++;
				}
				contPal++;

			}

		}
		float fNN=(float) (NN/contPal);
		float fVB=(float) (VB/contPal);
		float fADJ=(float) (ADJ/contPal);
		System.out.println(ADJ);
		System.out.println(fNN);
		res.add(fNN);
		res.add(fVB);
		res.add(fADJ);

		return res;
	}

	public List<String> stem(List<String> input) {
		List<String> output = new ArrayList<>();
		SpanishStemmer snowballStemmer = new SpanishStemmer();
		for (String word : input) {
			snowballStemmer.setCurrent(word);
			snowballStemmer.stem();
			output.add(snowballStemmer.getCurrent());
			System.out.println(snowballStemmer.getCurrent());
		}
		return output;
	}

	public static String lematizar(String palabra) {

		SpanishStemmer spanish = new SpanishStemmer();

		String[] tokens = palabra.split(" ");
		String result = "";
		for (String string : tokens) {
			spanish.setCurrent(string);
			spanish.stem();
			String stemmed = spanish.getCurrent();
			result += stemmed + " ";

		}
		System.out.println(result);
		return result;
	}

	//codigo obtenido en:http://www.v3rgu1.com/blog/231/2010/programacion/eliminar-acentos-y-caracteres-especiales-en-java/
	public static String remove1(String input) {
		// Cadena de caracteres original a sustituir.
		String original = "·‡‰ÈËÎÌÏÔÛÚˆ˙˘uÒ¡¿ƒ…»ÀÕÃœ”“÷⁄Ÿ‹—Á«";
		// Cadena de caracteres ASCII que reemplazar·n los originales.
		String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
		String output = input;
		for (int i = 0; i < original.length(); i++) {
			// Reemplazamos los caracteres especiales.
			output = output.replace(original.charAt(i), ascii.charAt(i));
		}//for i
		return output;
	}//remove1

	//Probando algoritmo para sacar las palabras comunes en los comentarios

	public static void main(String[] args) throws FileNotFoundException, IOException, TreeTaggerException {

		lematizar("Voy con este pronostico arriesgado pero con posibilidades de salir, en primer lugar decir que el actual entrenador del Sevilla cuando de enfrentaba a equipos de abajo con el Milan como visitante les gano a todos por dos goles o mas de diferencia. -Por otro lado voy analizar primero al Alaves que Abelardo parece haberle lavado un poco la imagen, hasta la llegada de este ultimo habia perdido todos sus partidos como local menos uno y cuando ha jugado contra alguno de los 7 primeros como local ha perdido por dos goles o mas a excepcion de Real Madrid y Valencia que solo fue por uno. Tiene las bajas de dos jugadores que estaban siendo importantes para Abelardo en distintas lineas como son Alexis y Burgui y eso lo puede notar especialmente en ataque. -El Sevilla era muy irregular fuera de casa con su anterior entrenador, pero a los rivales de la zona baja de la tabla les ha ganado a todos pero lo malo es que en la mayoria de ellos solo ha sido por un gol, viene de perder por 3-5 ante el Betis, que esos partidos son los que duelen por su rivalidad, por lo que tienen que salir ha ganar y por mas de un gol para poder reencontrarse con su aficion en el siguiente partido. Ambos han jugado copa del rey pero no creo que sea transcendente para este partido porque estaban ya practicamente clasificados de la ida. A mi parecer el Sevilla lleva equipo para ganarle por dos goles o mas, porque el equipo ha sido confeccionado para esta en Champions mientras su rival para no descender. ");

	}

}
