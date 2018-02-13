
package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.League;
import repositories.LeagueRepository;

@Transactional
@Service
public class LeagueService {

	@Autowired
	private LeagueRepository leagueRepository;


	public LeagueService() {
		super();
	}

	public League save(League league) {
		return leagueRepository.save(league);
	}

	public List<League> findAll() {
		return leagueRepository.findAll();
	}

	public League findOne(Integer id) {
		Assert.notNull(id);
		return leagueRepository.findOne(id);
	}

	public boolean exists(Integer id) {
		Assert.notNull(id);
		return leagueRepository.exists(id);
	}

	public League getLeaguePrimeraDivision() {
		return leagueRepository.getLeaguePrimeraDivision();
	}

}
