
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	//Buscar si existe usuario
	@Query("select a From User a where a.name=?1")
	User getExisteUsuario(String name);

}
