
package services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Day;
import domain.MatchFinal;
import repositories.MatchFinalRepository;

@Transactional
@Service
public class MatchFinalService {

	@Autowired
	private MatchFinalRepository	matchFinalRepository;
	@Autowired
	private DayService				dayService;


	public MatchFinalService() {
		super();
	}

	public void guardarResultadoFinal() {
		Integer num = dayService.ultimaJornada().getNum();
		try {
			Document d = Jsoup.connect("http://www.marcadoresonline.com/futbol/espa%C3%B1a/primeradivision/jornada" + num).timeout(600000).get();
			Elements elem = d.select("div.contPartido.pdt_fin");
			List<String> partidosAux = new ArrayList<>();
			List<MatchFinal> partidosGuardados = matchFinalRepository.findAll();
			for (MatchFinal a : partidosGuardados) {
				partidosAux.add(a.getLocal());
			}
			Day day = dayService.ultimaJornada();

			for (Element e : elem) {
				MatchFinal matchFinal = new MatchFinal();
				String partido = e.select("strong.equipo").text();
				String resultado = e.select("span[id].numeroGoles").text();

				if (partido.contains("Depor")) {
					partido = partido.replaceAll("Depor La Coruña", "Depor");
				}

				String[] separarPartido = partido.split(" ");
				String[] separarResultado = resultado.split(" ");
				matchFinal.setLocal(separarPartido[0]);

				if (separarPartido[0].contains("Real") || separarPartido[0].contains("Athletic") || separarPartido[0].contains("Sociedad") || separarPartido[0].contains("Celta") || separarPartido[0].contains("Las")
					|| separarPartido[0].contains("Levante")) {
					matchFinal.setLocal(separarPartido[0] + " " + separarPartido[1]);
					if (separarPartido[2].contains("Real") || separarPartido[2].contains("Levante") || separarPartido[2].contains("Athletic") || separarPartido[2].contains("Sociedad") || separarPartido[2].contains("Celta")
						|| separarPartido[2].contains("Las")) {
						matchFinal.setVisit(separarPartido[2] + " " + separarPartido[3]);
					} else {
						matchFinal.setVisit(separarPartido[2]);
					}

				} else if (separarPartido[1].contains("Real") || separarPartido[1].contains("Levante") || separarPartido[1].contains("Athletic") || separarPartido[1].contains("Sociedad") || separarPartido[1].contains("Celta")
					|| separarPartido[1].contains("Las")) {
					matchFinal.setLocal(separarPartido[0]);
					matchFinal.setVisit(separarPartido[1] + separarPartido[2]);
				} else {
					matchFinal.setLocal(separarPartido[0]);
					matchFinal.setVisit(separarPartido[1]);
				}

				matchFinal.setResultLocal(Integer.parseInt(separarResultado[0]));
				matchFinal.setResultVisit(Integer.parseInt(separarResultado[1]));
				if (!partidosAux.contains(matchFinal.getLocal())) {
					day.getMatchesFinal().add(matchFinal);

					dayService.save(day);
				}

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public List<MatchFinal> findAll() {
		return matchFinalRepository.findAll();
	}

	public MatchFinal findOne(Integer id) {
		Assert.notNull(id);
		return matchFinalRepository.findOne(id);
	}

	public boolean exists(Integer id) {
		Assert.notNull(id);
		return matchFinalRepository.exists(id);
	}

	public MatchFinal partidoFinal(String partido) {
		return matchFinalRepository.partidoFinal(partido);
	}

}
