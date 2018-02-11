
package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.CommentProcess;
import repositories.CommentProcessRepository;

@Component
@Transactional
public class StringToCommentProcessConverter implements Converter<String, CommentProcess> {

	@Autowired
	private CommentProcessRepository arRepository;


	@Override
	public CommentProcess convert(String text) {
		CommentProcess result;
		int id;
		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.arRepository.findOne(id);
			}
		} catch (final Exception oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}
