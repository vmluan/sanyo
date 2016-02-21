package com.sanyo.quote.service;

import java.util.List;

import com.sanyo.quote.domain.Project;
import com.sanyo.quote.domain.Summary;

public interface SummaryService {
	List<Summary> findAll();
	Summary save (Summary sum);
	Summary findByProject (Project project);
}
