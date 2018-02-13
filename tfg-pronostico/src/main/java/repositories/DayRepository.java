
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Day;

public interface DayRepository extends JpaRepository<Day, Integer> {

	//Buscamos la ultima jornada para la busqueda de partidos
	@Query("select distinct a from Day a where a.num=(select max(b.num) from Day b)")
	Day ultimaJornada();

}
