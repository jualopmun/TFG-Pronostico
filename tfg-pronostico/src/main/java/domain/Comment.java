
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Comment extends DomainEntity {

	private String			comment;
	private User			user;
	private CommentProcess	commentProcess;


	@NotBlank
	@Lob
	@Column(length = 100000)
	@Type(type = "org.hibernate.type.StringClobType")
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@NotFound(action = NotFoundAction.IGNORE)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@OneToOne(mappedBy = "comment")
	public CommentProcess getCommentProcess() {
		return commentProcess;
	}

	public void setCommentProcess(CommentProcess commentProcess) {
		this.commentProcess = commentProcess;
	}

}
