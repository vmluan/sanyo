package com.sanyo.quote.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sanyo.quote.domain.ProjectRevision;

public interface ProjectRevisionService {

	List<ProjectRevision> findAll();
	
	ProjectRevision findById(Integer id);
	
	ProjectRevision save(ProjectRevision revision);
	
	Page<ProjectRevision> findAllByPage(Pageable pageable);
}
