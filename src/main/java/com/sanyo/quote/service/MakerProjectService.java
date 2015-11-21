package com.sanyo.quote.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sanyo.quote.domain.MakerProject;
import com.sanyo.quote.domain.Project;

public interface MakerProjectService {

	List<MakerProject> findAll();
	
	MakerProject findById(Integer id);
	
	MakerProject save(MakerProject maker);
	
	Page<MakerProject> findAllByPage(Pageable pageable);
	
	List<MakerProject> findByProject(Project project);
	
}
