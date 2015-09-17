package com.sanyo.quote.domain;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class ProjectCategoryId implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	private Project project;
	
	@ManyToOne
	private Category category;
	
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}


	
	public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProjectCategoryId that = (ProjectCategoryId) o;

        if (project != null ? !project.equals(that.project) : that.project != null) return false;
        if (category != null ? !category.equals(that.category) : that.category != null)
            return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = (project != null ? project.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        return result;
    }

}
