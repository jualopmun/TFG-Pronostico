
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.MatchForecast;

@Component
@Transactional
public class MatchForecastToStringConverter implements Converter<MatchForecast, String> {

	@Override
	public String convert(MatchForecast ar) {
		String res;
		if (ar == null) {
			res = null;
		} else {
			res = String.valueOf(ar.getId());
		}
		return res;
	}

}
