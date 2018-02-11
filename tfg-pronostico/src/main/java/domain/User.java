
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
public class User extends DomainEntity {

	private String				name;
	private Double				sucess;
	private Collection<Comment>	comments;


	@NotBlank
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Range(min = 0)
	public Double getSucess() {
		return sucess;
	}

	public void setSucess(Double sucess) {
		this.sucess = sucess;
	}

	@NotNull
	//@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, targetEntity = Comment.class)
	@OneToMany
	public Collection<Comment> getComments() {
		return comments;
	}

	public void setComments(Collection<Comment> comments) {
		this.comments = comments;
	}

}
