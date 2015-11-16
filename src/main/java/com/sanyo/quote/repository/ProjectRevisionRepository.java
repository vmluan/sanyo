package com.sanyo.quote.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.sanyo.quote.domain.Project;
import com.sanyo.quote.domain.ProjectRevision;


public interface ProjectRevisionRepository extends PagingAndSortingRepository<ProjectRevision, Integer> {
	@Query("SELECT pr FROM ProjectRevision pr WHERE pr.project = :project order by pr.date desc")
	public List<ProjectRevision> findRevisions(@Param("project") Project project);
}
