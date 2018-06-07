
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.MatchFinal;

public interface MatchFinalRepository extends JpaRepository<MatchFinal, Integer> {

	@Query("select a From MatchFinal a where a.visit=?1 or a.local=?1")
	MatchFinal partidoFinal(String partido);

}
