
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.CommentProcess;

public interface CommentProcessRepository extends JpaRepository<CommentProcess, Integer> {

}
