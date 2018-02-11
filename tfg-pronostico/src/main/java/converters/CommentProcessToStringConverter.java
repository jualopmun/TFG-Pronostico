
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.CommentProcess;

@Component
@Transactional
public class CommentProcessToStringConverter implements Converter<CommentProcess, String> {

	@Override
	public String convert(CommentProcess ar) {
		String res;
		if (ar == null) {
			res = null;
		} else {
			res = String.valueOf(ar.getId());
		}
		return res;
	}

}
