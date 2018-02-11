
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.League;

public interface LeagueRepository extends JpaRepository<League, Integer> {

}
