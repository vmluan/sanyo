package com.sanyo.quote.domain;

import java.util.Date;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "project_category", catalog = "sanyo")
@AssociationOverrides({
	@AssociationOverride(name = "pk.project", 
		joinColumns = @JoinColumn(name = "projectid")),
	@AssociationOverride(name = "pk.category", 
		joinColumns = @JoinColumn(name = "categoryid")) })
public class ProjectCategory implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2598033029498774040L;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "CREATED_DATE", nullable = false, length = 10)
	private Date createdDate;
	
	@Column(name = "CREATED_BY", nullable = false, length = 10)
	private String createdBy;
	private ProjectCategoryId pk = new ProjectCategoryId();
	
	public ProjectCategory(){
		
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@EmbeddedId
	public ProjectCategoryId getPk() {
		return pk;
	}

	public void setPk(ProjectCategoryId pk) {
		this.pk = pk;
	}
	
	@Transient
	public Project getProject(){
		return pk.getProject();
	}
	public void setProject(Project project){
		pk.setProject(project);;
	}
	@Transient
	public Category getCategory(){
		return pk.getCategory();
	}
	public void setCategory(Category category){
		pk.setCategory(category);
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
