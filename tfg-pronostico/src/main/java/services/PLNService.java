
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
	public String transformaPalabras(String comentario) throws FileNotFoundException, IOException {
		//Leemos el archivo donde esta loacalizado la lista de palabras de apodos de equipos de f˙tbol
		String archivo = "ApodosEquiposFutbol.txt";
		InputStream file = Thread.currentThread().getContextClassLoader().getResourceAsStream(archivo);
		int numeroLineas = 10000;
		byte chars[] = new byte[numeroLineas];
		file.read(chars);
		String palabra = "";

		for (int i = 0; i < numeroLineas; i++) {

			palabra += (char) chars[i];

		}
		//Transformamos las palabras de los comentarios en minuscula
		String[] separar = palabra.split("\r\n");
		comentario = comentario.toLowerCase();
		//Eliminamos los caracteres especiales por ejemplo las tildes
		comentario = remove1(comentario);
		
		//Comparamos el comentario por uno a uno las palabras  y lo sustituimos por el nombre del equipo
		for (String b : separar) {
			String[] separacionFinal = b.split("->");

			for (int i = 0; i < separacionFinal.length; i++) {
				String[] equipos = separacionFinal[i].split(",");
				for (String equiposT : equipos) {
					comentario = comentario.replaceAll(equiposT, separacionFinal[0]);
				}
			}
		}

		return comentario;
	}

	//Nos interesa realizar una lista de contadores para procesarlo
	//lista[Nombres,Verbos,Adjetivos]
	public static List<Float> postaggin(String comentario) {
		
		//Usaremos la libreria de Stanford para abrir el idioma espaÒol y las anotaciones necesarias
		Properties props = new Properties();
		props.put("annotators", "tokenize, ssplit, pos, lemma, ner");
		props.setProperty("tokenize.language", "es");
		props.setProperty("pos.model", "edu/stanford/nlp/models/pos-tagger/spanish/spanish-distsim.tagger");
		List<Float> res = new ArrayList<Float>();
		//Estos contadores nos servira luego para generar los datasets
		float NN = 0;
		float VB = 0;
		float ADJ = 0;
		float contPal = 0;
		//Invocamos la libreria de Stanford
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		//Invocamos las anotaciones
		Annotation document = new Annotation(comentario);
		
		pipeline.annotate(document);
		List<CoreMap> sentences = document.get(SentencesAnnotation.class);
		//Leemos una por una las palabras del comentario. solo nos interesa si es un NN,VB o ADJ
		for (CoreMap sentence : sentences) {
			for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
				String pos = token.get(PartOfSpeechAnnotation.class);
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
		//Devolvemos los contadores
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
		//Invocamos la libreria snowball
		SpanishStemmer spanish = new SpanishStemmer();

		String[] tokens = palabra.split(" ");
		String result = "";
		//Comparamos palabras por palabras del comentario y lematizamos
		for (String string : tokens) {
			spanish.setCurrent(string);
			spanish.stem();
			String stemmed = spanish.getCurrent();
			result += stemmed + " ";

		}
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
		
		System.out.println(lematizar("partido de entre el espanol y el athletic club. el athletic viene de hacer buenos partidos desde que fue eliminado en copa. por eso me decanto por esta apuesta,aunque creo que puede ganar,el campo del espanol no es un buen escenario para el athletic que lleva sin ganar 20 anos en liga.por eso nos cubrimos con el x2 por que el espanol anda flojillo ultimamente puede dar un susto y terminar el partido en empate suerte a todos"));
	}

}
