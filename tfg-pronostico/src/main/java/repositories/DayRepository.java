
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Day;

public interface DayRepository extends JpaRepository<Day, Integer> {

	//Buscamos la ultima jornada para la busqueda de partidos
	@Query("select distinct a from Day a where a.num=27")
	List<Day> ultimaJornada();

}
