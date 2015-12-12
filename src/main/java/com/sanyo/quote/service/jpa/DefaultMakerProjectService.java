
package com.sanyo.quote.service.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.sanyo.quote.domain.Category;
import com.sanyo.quote.domain.MakerProject;
import com.sanyo.quote.domain.Project;
import com.sanyo.quote.repository.MakerProjectRepository;
import com.sanyo.quote.service.MakerProjectService;

@Service("makerProjectService")
@Repository
@Transactional
public class DefaultMakerProjectService implements MakerProjectService {

	@Autowired
	private MakerProjectRepository makerProjectRepository;

	@Override
	public List<MakerProject> findAll() {
		return Lists.newArrayList(makerProjectRepository.findAll());
	}

	@Override
	public MakerProject findById(Integer id) {
		return makerProjectRepository.findOne(id);
	}

	@Override
	public MakerProject save(MakerProject makerProject) {
		return makerProjectRepository.save(makerProject);
	}

	@Override
	public Page<MakerProject> findAllByPage(Pageable pageable) {
		return makerProjectRepository.findAll(pageable);
	}

	@Override
	public List<MakerProject> findByProject(Project project) {
		return makerProjectRepository.findByProject(project);
	}

	@Override
	public List<MakerProject> findByProjectAndCategory(Project project, Category category) {
		return makerProjectRepository.findByProjectAndCategory(project, category);
	}

	@Override
	public void delete(Integer id) {
		makerProjectRepository.delete(id);
		
	}



}
