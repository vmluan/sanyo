package com.sanyo.quote.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.sanyo.quote.domain.Project;
import com.sanyo.quote.domain.Summary;

public interface SummaryRepository extends PagingAndSortingRepository<Summary, Integer>{
	
	@Query("SELECT c FROM Summary c WHERE c.project = :project")
    public Summary findByProject (@Param("project") Project project);

}
