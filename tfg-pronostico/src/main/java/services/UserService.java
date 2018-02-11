
package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.User;
import repositories.UserRepository;

@Transactional
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;


	public UserService() {
		super();
	}

	public User save(User user) {
		return userRepository.save(user);
	}

	public List<User> findAll() {
		return userRepository.findAll();
	}

	public User findOne(Integer id) {
		Assert.notNull(id);
		return userRepository.findOne(id);
	}

	public boolean exists(Integer id) {
		Assert.notNull(id);
		return userRepository.exists(id);
	}

	public User getExisteUsuario(String name) {
		return userRepository.getExisteUsuario(name);
	}

	public User saveAndFlush(User entity) {
		return userRepository.saveAndFlush(entity);
	}

}
