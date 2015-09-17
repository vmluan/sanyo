
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
import com.sanyo.quote.repository.CategoryRepository;
import com.sanyo.quote.service.CategoryService;

@Service("categoryService")
@Repository
@Transactional
public class DefaultCategoryService implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Transactional(readOnly=true)
	public List<Category> findAll() {
		return Lists.newArrayList(categoryRepository.findAll());
	}

	@Transactional(readOnly=true)
	public Category findById(Integer id) {
		return categoryRepository.findOne(id);
	}

	public Category save(Category category) {
		return categoryRepository.save(category);
	}

	@Transactional(readOnly=true)
	public Page<Category> findAllByPage(Pageable pageable) {
		return categoryRepository.findAll(pageable);
	}

	@Override
	public List<Category> findByIds(List<Integer> ids) {
		return categoryRepository.findByIds(ids);
	}
	
}
