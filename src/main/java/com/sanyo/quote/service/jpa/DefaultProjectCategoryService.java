
package com.sanyo.quote.service.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.sanyo.quote.domain.ProjectCategory;
import com.sanyo.quote.repository.ProjectCategoryRepository;
import com.sanyo.quote.service.ProjectCategoryService;

@Service("projectCategoryService")
@Repository
@Transactional
public class DefaultProjectCategoryService implements ProjectCategoryService {

	@Autowired
	private ProjectCategoryRepository projectCategoryRepository;

	@Transactional(readOnly=true)
	public List<ProjectCategory> findAll() {
		return Lists.newArrayList(projectCategoryRepository.findAll());
	}

	@Transactional(readOnly=true)
	public ProjectCategory findById(Integer id) {
		return projectCategoryRepository.findOne(id);
	}

	public ProjectCategory save(ProjectCategory projectCategory) {
		return projectCategoryRepository.save(projectCategory);
	}

	@Transactional(readOnly=true)
	public Page<ProjectCategory> findAllByPage(Pageable pageable) {
		return projectCategoryRepository.findAll(pageable);
	}

	@Override
	public List<ProjectCategory> findByIds(List<Integer> ids) {
		return null;
	}
	
}
