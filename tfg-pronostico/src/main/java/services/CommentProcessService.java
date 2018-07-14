
package services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Comment;
import domain.CommentProcess;
import repositories.CommentProcessRepository;

@Transactional
@Service
public class CommentProcessService {

	@Autowired
	private CommentProcessRepository	commentProcessRepository;
	@Autowired
	private CommentService				commentService;


	public CommentProcessService() {
		super();
	}

	public void guardarComentariosProcesados() throws FileNotFoundException, IOException {
		List<Comment> comentarios = commentService.findAll();
		List<CommentProcess> comentariosProcesados = commentProcessRepository.findAll();
		List<String> aux = new ArrayList<String>();
		for (CommentProcess cp : comentariosProcesados) {
			aux.add(cp.getCommentProcess());
		}

		for (Comment a : comentarios) {
			String comentario = a.getComment();
			String eliminar = eliminarStopWords(comentario);

			String transformar = transformaPalabras(eliminar);
		    //String lematizar = PLNService.lematizar(transformar);

			if (!aux.contains(eliminar)) {
				CommentProcess commentProcess = new CommentProcess();

				commentProcess.setCommentProcess(transformar);
				commentProcess.setComment(a);
				a.setCommentProcess(commentProcess);

				commentProcessRepository.save(commentProcess);

			}
		}

	}

	public CommentProcess save(CommentProcess commentProcess) {
		return commentProcessRepository.save(commentProcess);
	}

	public List<CommentProcess> findAll() {
		return commentProcessRepository.findAll();
	}

	public CommentProcess findOne(Integer id) {
		Assert.notNull(id);
		return commentProcessRepository.findOne(id);
	}

	public List<CommentProcess> save(List<CommentProcess> commentProcesse) {
		return commentProcessRepository.save(commentProcesse);
	}

	public String eliminarStopWords(String comentario) throws FileNotFoundException, IOException {
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
		String archivo = "ApodosEquiposFutbol.txt";
		InputStream file = Thread.currentThread().getContextClassLoader().getResourceAsStream(archivo);
		int numeroLineas = 10000;
		byte chars[] = new byte[numeroLineas];
		file.read(chars);
		String palabra = "";

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

		return comentario;
	}

	//codigo obtenido en:http://www.v3rgu1.com/blog/231/2010/programacion/eliminar-acentos-y-caracteres-especiales-en-java/
	public static String remove1(String input) {
		// Cadena de caracteres original a sustituir.
		String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ";
		// Cadena de caracteres ASCII que reemplazarán los originales.
		String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
		String output = input;
		for (int i = 0; i < original.length(); i++) {
			// Reemplazamos los caracteres especiales.
			output = output.replace(original.charAt(i), ascii.charAt(i));
		}//for i
		return output;
	}//remove1

	public List<String> comentariosProcesados(String equipo) {
		if (commentProcessRepository.comentariosProcesados(equipo).size() < 3) {
			return commentProcessRepository.comentariosProcesados(equipo);
		} else {
			return commentProcessRepository.comentariosProcesados(equipo).subList(0, 3);
		}

	}
	
	public List<String> comentariosProcesados2(String equipo) {
		
		return commentProcessRepository.comentariosProcesados(equipo);
	}

	public void flush() {
		commentProcessRepository.flush();
	}

}
