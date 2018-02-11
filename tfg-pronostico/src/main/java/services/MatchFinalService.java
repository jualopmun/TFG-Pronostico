
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

	public void guardarResultadoFinal(Integer num) {
		try {
			Document d = Jsoup.connect("http://www.marcadoresonline.com/futbol/espa%C3%B1a/primeradivision/jornada" + num).timeout(600000).get();
			Elements elem = d.select("div.contPartido.pdt_fin");
			List<String> partidosAux = new ArrayList<>();
			Day day = dayService.ultimaJornada();
			for (Element e : elem) {
				MatchFinal matchFinal = new MatchFinal();
				String partido = e.select("strong.equipo").text();
				String resultado = e.select("span[id].numeroGoles").text();
				String[] separarPartido = partido.split(" ");
				String[] separarResultado = resultado.split(" ");
				matchFinal.setLocal(separarPartido[0]);

				matchFinal.setVisit(separarPartido[1]);
				matchFinal.setResultLocal(Integer.parseInt(separarResultado[0]));
				matchFinal.setResultVisit(Integer.parseInt(separarResultado[1]));
				day.getMatchesFinal().add(matchFinal);
				if (!partidosAux.contains(partidosAux))
					dayService.save(day);
				partidosAux.add(separarPartido[0]);

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

}
