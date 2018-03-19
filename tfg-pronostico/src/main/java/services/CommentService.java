
package services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Comment;
import domain.MatchForecast;
import domain.User;
import repositories.CommentRepository;

@Transactional
@Service
public class CommentService {

	@Autowired
	private CommentRepository		commentRepository;
	@Autowired
	private MatchForecastService	matchForecastService;
	@Autowired
	private DayService				dayService;
	@Autowired
	private UserService				userService;


	public CommentService() {
		super();
	}

	public Comment save(Comment comment) {

		return commentRepository.save(comment);

	}

	public List<Comment> findAll() {
		return commentRepository.findAll();
	}

	public Comment findOne(Integer id) {
		Assert.notNull(id);
		return commentRepository.findOne(id);
	}

	public List<Comment> save(List<Comment> comments) {
		return commentRepository.save(comments);
	}

	public boolean exists(Integer id) {
		Assert.notNull(id);
		return commentRepository.exists(id);
	}

	public Comment getExisteComentario(String comment) {
		return commentRepository.getExisteComentario(comment);
	}

	public void guardarComentarios() {
		int partido = -1;
		try {
			//Metodos para comprobaciones
			List<Comment> commentsAux = commentRepository.findAll();
			List<User> usersAux = userService.findAll();
			List<MatchForecast> matches = matchForecastService.findAll();
			List<String> commentcomp = new ArrayList<String>();
			List<String> usercomp = new ArrayList<String>();

			for (Comment a : commentsAux) {
				commentcomp.add(a.getComment());
			}
			for (User a : usersAux) {
				usercomp.add(a.getName());
			}
			for (String foros : PruebaJsoup.listaDeForos(dayService.ultimaJornada().getNum())) {

				Document d = Jsoup.connect(foros).timeout(600000).get();
				Elements elem = d.select("div.parrafo");
				Elements elem2 = d.select("div.nombre_usuario");
				partido++;
				for (int i = 0; i < elem.size(); i++) {

					String usuario = elem2.get(i).text().replaceAll("Un pronÛstico de: ", "");
					String usuarioFinal = usuario.replaceAll("EstadÌsticas del pronosticador AÒadir pronosticador a favoritos", "");
					String comentario = elem.get(i).text();
					comentario = remove1(comentario);

					if (!commentcomp.contains(comentario)) {
						User user = new User();
						for (User a : usersAux) {
							if (a.getName().equals(usuarioFinal)) {
								user = a;
								break;
							}
						}

						user.setComments(new ArrayList<Comment>());
						Comment comment = new Comment();

						Document j = Jsoup.connect("http://www.apuestasdeportivas.com/tipster/" + usuarioFinal).timeout(600000).get();
						Elements elem3 = j.select("span.cell_02.impar_amarillo_B.bold");

						String convertir = elem3.get(0).text().replaceAll(" %", "");
						Double puntuacion = Double.parseDouble(convertir);
						user.setName(usuarioFinal);
						user.setSucess(puntuacion);

						comment.setComment(comentario);
						comment.setUser(user);
						user.getComments().add(comment);
						//comment.setUser(user);
						//commentRepository.save(comment);

						matches.get(partido).getComments().add(comment);
						matches.get(partido).setActualization(new Date());
						matchForecastService.save(matches.get(partido));
						commentcomp.add(comentario);
						usersAux.add(user);
					}
					//user.setComments(comments);
					//userService.save(user);

				}

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

	public Comment saveAndFlush(Comment entity) {
		return commentRepository.saveAndFlush(entity);
	}

}
