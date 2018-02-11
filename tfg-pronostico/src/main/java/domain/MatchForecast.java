
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class MatchForecast extends DomainEntity {

	private String				local;
	private String				visit;
	private Integer				resultLocal;
	private Integer				resultVisit;
	private Collection<Comment>	comments;
	private Date				actualization;


	@NotBlank
	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	@NotBlank
	public String getVisit() {
		return visit;
	}

	public void setVisit(String visit) {
		this.visit = visit;
	}

	@Range(min = 0)
	public Integer getResultLocal() {
		return resultLocal;
	}

	public void setResultLocal(Integer resultLocal) {
		this.resultLocal = resultLocal;
	}

	@Range(min = 0)
	public Integer getResultVisit() {
		return resultVisit;
	}

	public void setResultVisit(Integer resultVisit) {
		this.resultVisit = resultVisit;
	}

	@NotNull
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@NotFound(action = NotFoundAction.IGNORE)
	public Collection<Comment> getComments() {
		return comments;
	}

	public void setComments(Collection<Comment> comments) {
		this.comments = comments;
	}

	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getActualization() {
		return actualization;
	}

	public void setActualization(Date actualization) {
		this.actualization = actualization;
	}

}
