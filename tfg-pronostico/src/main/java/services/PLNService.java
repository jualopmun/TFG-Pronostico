
package services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.annolab.tt4j.TreeTaggerException;

import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
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

	public static void postaggin(String comentario) {

		Properties props = new Properties();
		props.put("annotators", "tokenize, ssplit, pos, lemma, ner");
		props.setProperty("tokenize.language", "es");
		props.setProperty("pos.model", "edu/stanford/nlp/models/pos-tagger/spanish/spanish-distsim.tagger");
		props.setProperty("ner.model", "edu/stanford/nlp/models/ner/spanish.ancora.distsim.s512.crf.ser.gz");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		Annotation document = new Annotation(comentario);

		pipeline.annotate(document);
		List<CoreMap> sentences = document.get(SentencesAnnotation.class);
		for (CoreMap sentence : sentences) {
			for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
				String word = token.get(TextAnnotation.class);
				String pos = token.get(PartOfSpeechAnnotation.class);

				String ne = token.get(NamedEntityTagAnnotation.class);

				System.out.println("Lugar: " + word + " pos: " + pos + " ne:" + ne);
			}

		}
	}

	//lematizacion
	public static void lematizar(String comentario) throws IOException, TreeTaggerException {

		Properties props = new Properties();
		props.put("tokenize.language", "es");
		props.put("annotators", "tokenize, ssplit, pos, lemma, ner, parse");
		props.setProperty("pos.model", "edu/stanford/nlp/models/pos-tagger/spanish/spanish-distsim.tagger");

		props.put("ner.model", "edu/stanford/nlp/models/ner/spanish.ancora.distsim.s512.crf.ser.gz");
		props.put("lemma.model", "/edu/stanford/nlp/models/srparser/spanishSR.beam.ser.gz");

		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		Annotation document = new Annotation(comentario);

		pipeline.annotate(document);
		List<CoreMap> sentences = document.get(SentencesAnnotation.class);
		for (CoreMap sentence : sentences) {
			for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
				String word = token.get(TextAnnotation.class);
				String pos = token.get(LemmaAnnotation.class);

				String lemma = token.get(LemmaAnnotation.class);

				System.out.println("Lugar: " + word + " pos: " + pos + " ne:" + lemma);
			}

		}
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

	public static void main(String[] args) throws FileNotFoundException, IOException, TreeTaggerException {
		String comentario = "vine vi vencir";
		//eliminarStopWords(comentario);
		//postaggin(comentario);
		lematizar(comentario);
	}

}
