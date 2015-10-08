package com.sanyo.quote.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.sanyo.quote.domain.ProjectRevision;


public interface ProjectRevisionRepository extends PagingAndSortingRepository<ProjectRevision, Integer> {
}
