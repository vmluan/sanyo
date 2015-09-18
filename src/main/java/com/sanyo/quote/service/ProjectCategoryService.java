package com.sanyo.quote.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sanyo.quote.domain.Category;
import com.sanyo.quote.domain.ProjectCategory;

public interface ProjectCategoryService {

	List<ProjectCategory> findAll();
	
	ProjectCategory findById(Integer id);
	
	ProjectCategory save(ProjectCategory category);
	
	Page<ProjectCategory> findAllByPage(Pageable pageable);
	List<ProjectCategory> findByIds(List<Integer > ids);
	
}
