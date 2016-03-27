package com.sanyo.quote.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import com.sanyo.quote.domain.Project;
import com.sanyo.quote.domain.ProjectRevision;

public interface ProjectRevisionService {

	List<ProjectRevision> findAll();
	
	ProjectRevision findById(Integer id);
	
	ProjectRevision save(ProjectRevision revision);
	
	Page<ProjectRevision> findAllByPage(Pageable pageable);
	
	void delete(Integer id);
	void delete(ProjectRevision projectRevision);
	List<ProjectRevision> findRevisions(Project project);
	List<ProjectRevision> findByProjectOrderByDateAsc(Project project);
	ProjectRevision findLatestRevision(Project project);
}
