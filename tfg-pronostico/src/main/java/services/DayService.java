
package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Day;
import repositories.DayRepository;

@Transactional
@Service
public class DayService {

	@Autowired
	private DayRepository dayRepository;


	public DayService() {
		super();
	}

	public Day save(Day day) {

		return dayRepository.save(day);
	}

	public List<Day> findAll() {
		return dayRepository.findAll();
	}

	public Day findOne(Integer id) {
		Assert.notNull(id);
		return dayRepository.findOne(id);
	}

	public boolean exists(Integer id) {
		Assert.notNull(id);
		return dayRepository.exists(id);
	}

	//Metodos de ayuda
	public Day ultimaJornada() {
		return dayRepository.ultimaJornada().get(0);
	}

	public boolean existePartido() {
		Boolean existe = true;
		Day day = dayRepository.ultimaJornada().get(0);
		List<String> partidos = PruebaJsoup.getJornadas(day.getNum());
		if (partidos.size() == 0) {
			existe = false;
		}
		return existe;
	}

}
