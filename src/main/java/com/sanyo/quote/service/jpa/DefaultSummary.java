package com.sanyo.quote.service.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.sanyo.quote.domain.Project;
import com.sanyo.quote.domain.Summary;
import com.sanyo.quote.repository.SummaryRepository;
import com.sanyo.quote.service.SummaryService;
@Service("SummaryService")
@Repository
@Transactional
public class DefaultSummary implements SummaryService{
	@Autowired
    SummaryRepository summR;
	@Transactional(readOnly = true)
    public List<Summary> findAll() {
        return Lists.newArrayList(summR.findAll());
    }
	@Override
    public Summary save (Summary sum) {
        return summR.save(sum);
    }
	@Transactional(readOnly = true)
    public Summary findByProject (Project project) {
		Summary sum = summR.findByProject(project);
        return sum;
    }
}
