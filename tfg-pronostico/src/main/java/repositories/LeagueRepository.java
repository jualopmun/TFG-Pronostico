
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.League;

public interface LeagueRepository extends JpaRepository<League, Integer> {

	@Query("select a from League a where a.leagueName='Primera Division'")
	League getLeaguePrimeraDivision();

}
