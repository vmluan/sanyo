package com.sanyo.quote.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "project_category", catalog = "sanyo")
@AssociationOverrides({
		@AssociationOverride(name = "pk.project", 
			joinColumns = @JoinColumn(name = "PROJECT_ID")),
		@AssociationOverride(name = "pk.category", 
			joinColumns = @JoinColumn(name = "CATEGORY_ID")) })
public class ProjectCategory implements java.io.Serializable {

	private Integer id;
	private ProjectCategoryId pk = new ProjectCategoryId();
	private Date createdDate;
	private String createdBy;


	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@EmbeddedId
	public ProjectCategoryId getPk() {
		return pk;
	}

	public void setPk(ProjectCategoryId pk) {
		this.pk = pk;
	}

	@Transient
	public Project getProject() {
		return getPk().getProject();
	}

	public void setProject(Project project) {
		getPk().setProject(project);
	}

	@Transient
	public Category getCategory() {
		return getPk().getCategory();
	}

	public void setCategory(Category category) {
		getPk().setCategory(category);
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATED_DATE", nullable = false, length = 10)
	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "CREATED_BY", nullable = false, length = 10)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		ProjectCategory that = (ProjectCategory) o;

		if (getPk() != null ? !getPk().equals(that.getPk())
				: that.getPk() != null)
			return false;

		return true;
	}

	public int hashCode() {
		return (getPk() != null ? getPk().hashCode() : 0);
	}
}
