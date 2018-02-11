
package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.CommentProcess;
import repositories.CommentProcessRepository;

@Transactional
@Service
public class CommentProcessService {

	@Autowired
	private CommentProcessRepository commentProcessRepository;


	public CommentProcessService() {
		super();
	}

	public CommentProcess save(CommentProcess commentProcess) {
		return commentProcessRepository.save(commentProcess);
	}

	public List<CommentProcess> findAll() {
		return commentProcessRepository.findAll();
	}

	public CommentProcess findOne(Integer id) {
		Assert.notNull(id);
		return commentProcessRepository.findOne(id);
	}

	public List<CommentProcess> save(List<CommentProcess> commentProcesse) {
		return commentProcessRepository.save(commentProcesse);
	}

}
