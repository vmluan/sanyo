package com.sanyo.quote.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.sanyo.quote.domain.ProjectCategory;

public interface ProjectCategoryRepository extends PagingAndSortingRepository<ProjectCategory, Integer> {
}
