
package com.sanyo.quote.service.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.sanyo.quote.domain.Project;
import com.sanyo.quote.domain.ProjectRevision;
import com.sanyo.quote.repository.ProjectRevisionRepository;
import com.sanyo.quote.service.ProjectRevisionService;

@Service("projectRevisionService")
@Repository
@Transactional
public class DefaultProjectRevisionService implements ProjectRevisionService {

	@Autowired
	private ProjectRevisionRepository ProjectRevisionRepository;

	@Override
	public List<ProjectRevision> findAll() {
		return Lists.newArrayList(ProjectRevisionRepository.findAll());
	}

	@Override
	public ProjectRevision findById(Integer id) {
		return ProjectRevisionRepository.findOne(id);
	}

	@Override
	public ProjectRevision save(ProjectRevision revision) {
		return ProjectRevisionRepository.save(revision);
	}

	@Override
	public Page<ProjectRevision> findAllByPage(Pageable pageable) {
		return ProjectRevisionRepository.findAll(pageable);
	}

	@Override
	public void delete(Integer id) {
		ProjectRevisionRepository.delete(id);
	}

	@Override
	public void delete(ProjectRevision projectRevision) {
		ProjectRevisionRepository.delete(projectRevision);
	}

	@Override
	public List<ProjectRevision> findRevisions(Project project) {
		return ProjectRevisionRepository.findRevisions(project);
	}

	@Override
	public ProjectRevision findLatestRevision(Project project) {
		List<ProjectRevision> revisions = this.findRevisions(project);
		return revisions.get(0);
	}

	
}
