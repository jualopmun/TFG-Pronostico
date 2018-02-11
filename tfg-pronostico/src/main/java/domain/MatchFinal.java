
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
public class MatchFinal extends DomainEntity {

	private String	local;
	private String	visit;
	private Integer	resultLocal;
	private Integer	resultVisit;


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

}
