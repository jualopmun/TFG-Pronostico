
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class CommentProcess extends DomainEntity {

	private String	commentProcess;
	private Comment	comment;


	@NotBlank
	@Lob
	@Column(length = 100000)
	@Type(type = "org.hibernate.type.StringClobType")
	public String getCommentProcess() {
		return commentProcess;
	}

	public void setCommentProcess(String commentProcess) {
		this.commentProcess = commentProcess;
	}

	@NotNull
	@OneToOne
	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}

}
