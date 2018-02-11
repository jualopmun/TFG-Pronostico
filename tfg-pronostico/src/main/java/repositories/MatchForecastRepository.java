
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.MatchForecast;

public interface MatchForecastRepository extends JpaRepository<MatchForecast, Integer> {

	//Buscar si existe partido
	@Query("select a From MatchForecast a where a.local=?1")
	MatchForecast getExistePartido(String local);

}
