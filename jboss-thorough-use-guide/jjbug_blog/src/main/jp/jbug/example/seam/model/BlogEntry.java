package jp.jbug.example.seam.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.validator.Length;
import org.jboss.seam.annotations.Name;

@Entity
@Name("blogEntry")
public class BlogEntry implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long blogEntryId;

	@Column(name = "BLOGDATE", nullable = false)
	private Date blogDate;

	@Length(min = 1, max = 20, message = "カテゴリは20文字以内で入力してください。")
	@Column(nullable = false, length = 20)
	private String category;

	@Length(min = 1, max = 255, message = "ブログは255文字以内で入力してください。")
	@Column(nullable = false)
	private String blogEntry;

	@OneToMany(mappedBy = "blogEntry", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Comment> comments = new ArrayList<Comment>(0);

	public Date getBlogDate() {
		return blogDate;
	}

	public String getBlogEntry() {
		return blogEntry;
	}

	public Long getBlogEntryId() {
		return blogEntryId;
	}

	public String getCategory() {
		return category;
	}

	public void setBlogDate(Date blogDate) {
		this.blogDate = blogDate;
	}

	public void setBlogEntry(String blogEntry) {
		this.blogEntry = blogEntry;
	}

	public void setBlogEntryId(Long blogEntryId) {
		this.blogEntryId = blogEntryId;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

}
