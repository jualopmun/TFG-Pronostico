
package services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Comment;
import domain.Day;
import domain.League;
import domain.MatchFinal;
import domain.MatchForecast;
import repositories.MatchForecastRepository;

@Transactional
@Service
public class MatchForecastService {

	@Autowired
	private MatchForecastRepository	matchForecastRepository;

	@Autowired
	private DayService				dayService;
	@Autowired
	private LeagueService			leagueService;
	@Autowired
	private MatchFinalService		matchFinalService;


	public MatchForecastService() {
		super();
	}

	public MatchForecast save(MatchForecast matchForecast) {

		return matchForecastRepository.save(matchForecast);
	}

	public List<MatchForecast> findAll() {
		return matchForecastRepository.findAll();
	}

	public MatchForecast findOne(Integer id) {
		Assert.notNull(id);
		return matchForecastRepository.findOne(id);
	}

	public boolean exists(Integer id) {
		Assert.notNull(id);
		return matchForecastRepository.exists(id);
	}

	public MatchForecast getExistePartido(String local) {
		return matchForecastRepository.getExistePartido(local);
	}

	public void guardarPartidos() {
		Day day = dayService.ultimaJornada();
		List<MatchForecast> matchs = matchForecastRepository.findAll();
		List<String> matchsaux = new ArrayList<String>();
		for (MatchForecast m : matchs) {
			matchsaux.add(m.getLocal());
		}

		while (!dayService.existePartido()) {
			matchFinalService.guardarResultadoFinal(dayService.ultimaJornada().getNum());
			day.setNum(dayService.ultimaJornada().getNum() + 1);
			day.setMatchesFinal(new ArrayList<MatchFinal>());
			day.setMatchesForecast(new ArrayList<MatchForecast>());
			dayService.save(day);
			League league = leagueService.findAll().iterator().next();
			league.getDays().add(day);
			leagueService.save(league);

		}

		List<String> partidos = PruebaJsoup.getPartidos(day.getNum());
		for (String partido : partidos) {

			String[] separar = partido.split("-vs-");
			if (!matchsaux.contains(separar[0])) {
				MatchForecast matchForecast = new MatchForecast();
				matchForecast.setLocal(separar[0]);
				matchForecast.setVisit(separar[1]);
				matchForecast.setResultLocal(0);
				matchForecast.setResultVisit(0);
				matchForecast.setActualization(new Date());
				matchForecast.setComments(new ArrayList<Comment>());

				day.getMatchesForecast().add(matchForecast);
				dayService.save(day);
				matchsaux.add(separar[0]);

			}
		}
	}

	public MatchForecast saveAndFlush(MatchForecast entity) {
		return matchForecastRepository.saveAndFlush(entity);
	}

}