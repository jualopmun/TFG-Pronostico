
package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.League;
import repositories.LeagueRepository;

@Component
@Transactional
public class StringToLeagueConverter implements Converter<String, League> {

	@Autowired
	private LeagueRepository arRepository;


	@Override
	public League convert(String text) {
		League result;
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
