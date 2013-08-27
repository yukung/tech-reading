package entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Nomikai implements Serializable {
	@Id
	@GeneratedValue
	private long nomikaiid;

	private Date date;

	private String detail;

	private String name;

	@OneToMany(mappedBy = "nomikaiid")
	private Set<Nomikaimember> nomikaimemberCollection;

	private static final long serialVersionUID = 1L;

	public Nomikai() {
		super();
	}

	public long getNomikaiid() {
		return this.nomikaiid;
	}

	public void setNomikaiid(long nomikaiid) {
		this.nomikaiid = nomikaiid;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDetail() {
		return this.detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Nomikaimember> getNomikaimemberCollection() {
		return this.nomikaimemberCollection;
	}

	public void setNomikaimemberCollection(
			Set<Nomikaimember> nomikaimemberCollection) {
		this.nomikaimemberCollection = nomikaimemberCollection;
	}

}
