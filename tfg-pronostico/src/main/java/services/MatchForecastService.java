
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
	
	//Metodo para guardar los partidos de la jornada
	public void guardarPartidos() {
		//Apunta al dia que esta guardado en la base de datos
		Day day = dayService.findAll().iterator().next();
		//Sacaremos todos los partidos guardados en esta jornada para comprobar que no esta guardado de antes
		List<MatchForecast> matchs = matchForecastRepository.findAll();
		List<String> matchsaux = new ArrayList<String>();
		for (MatchForecast m : matchs) {
			matchsaux.add(m.getLocal());
		}

		//Empezaremos sacar los partidos a través del metodo getJornadas
		List<String> partidos = PruebaJsoup.getJornadas(day.getNum());
	

		int cont = 0;
		//Empezamos a guardar los partidos uno por uno
		for (String partido : partidos) {

			String[] separar = partido.split("-vs-");
			
			if (!matchsaux.contains(separar[0])) {
				//String resultado= PruebaJsoup.getResultados(day.getNum()).get(cont);
				//String [] separar_resultado=resultado.split(" ");
				MatchForecast matchForecast = new MatchForecast();
				matchForecast.setLocal(separar[0]);
				matchForecast.setVisit(separar[1]);
				matchForecast.setResultLocal(0);
				matchForecast.setResultVisit(0);
				matchForecast.setActualization(new Date());
				matchForecast.setComments(new ArrayList<Comment>());

				day.getMatchesForecast().add(matchForecast);
				dayService.save(day);

			}
			cont++;
			matchsaux.add(separar[0]);

		}


	}

	public MatchForecast saveAndFlush(MatchForecast entity) {
		return matchForecastRepository.saveAndFlush(entity);
	}

	public void flush() {
		matchForecastRepository.flush();
	}

}
