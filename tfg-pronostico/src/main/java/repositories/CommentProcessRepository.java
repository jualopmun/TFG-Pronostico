
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.CommentProcess;

public interface CommentProcessRepository extends JpaRepository<CommentProcess, Integer> {

	//Buscar comentarios procesados

	@Query("select c.commentProcess From MatchForecast a join a.comments b join b.commentProcess c join b.user d where a.local=?1 order by d.sucess ASC")
	List<String> comentariosProcesados(String equipo);

}
