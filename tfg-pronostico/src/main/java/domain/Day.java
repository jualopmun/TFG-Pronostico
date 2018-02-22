
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
public class Day extends DomainEntity {

	private Integer						num;
	private Collection<MatchForecast>	matchesForecast;
	private Collection<MatchFinal>		matchesFinal;


	@Range(min = 0, max = 38)
	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	@NotNull
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<MatchForecast> getMatchesForecast() {
		return matchesForecast;
	}

	public void setMatchesForecast(Collection<MatchForecast> matchesForecast) {
		this.matchesForecast = matchesForecast;
	}
	@NotNull
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public Collection<MatchFinal> getMatchesFinal() {
		return matchesFinal;
	}

	public void setMatchesFinal(Collection<MatchFinal> matchesFinal) {
		this.matchesFinal = matchesFinal;
	}

}
