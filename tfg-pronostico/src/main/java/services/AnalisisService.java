
package services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.Comment;

@Service
public class AnalisisService {

	@Autowired
	private MatchForecastService	matchForecastService;

	@Autowired
	private CommentProcessService	commentProccesService;


	//Analisis de palabras comunes para analizar
	public List<String> palabrasComunes() {

		List<String> palabrasComunes = new ArrayList<String>();
		List<String> prueba = new ArrayList<String>();
		for (int i = 0; i < matchForecastService.findAll().size(); i++) {
			for (Comment a : matchForecastService.findAll().get(i).getComments()) {
				String[] palabras = a.getCommentProcess().getCommentProcess().split(" ");

				if (palabrasComunes.size() == 0) {
					for (String com : palabras) {

						palabrasComunes.add(com);

					}
					if (prueba.size() == 0) {
						break;
					}
				}

				for (String com : palabras) {

					prueba.add(com);

				}
				palabrasComunes.retainAll(prueba);

			}
			matchForecastService.flush();

		}
		for (String palabras : palabrasComunes) {
			System.out.println("palabras comunes-> " + palabras);
		}

		return palabrasComunes;
	}

}
