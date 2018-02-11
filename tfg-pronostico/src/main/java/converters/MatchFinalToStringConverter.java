
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.MatchFinal;

@Component
@Transactional
public class MatchFinalToStringConverter implements Converter<MatchFinal, String> {

	@Override
	public String convert(MatchFinal ar) {
		String res;
		if (ar == null) {
			res = null;
		} else {
			res = String.valueOf(ar.getId());
		}
		return res;
	}

}
