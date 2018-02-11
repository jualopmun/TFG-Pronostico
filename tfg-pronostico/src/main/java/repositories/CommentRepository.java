
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

	//Buscar si existe comentario
	@Query("select a From Comment a where a.comment=?1")
	Comment getExisteComentario(String comment);

}
