package com.sanyo.quote.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.sanyo.quote.domain.MakerProject;
import com.sanyo.quote.domain.Project;


public interface MakerProjectRepository extends PagingAndSortingRepository<MakerProject, Integer> {
//	@Query("SELECT m FROM Maker m JOIN FETCH m.productGroupMakers WHERE m.id = :id")
//	public Maker findByIdAndFetchProductGroupMakerEagerly(@Param("id") Integer id);
//	
//	public Maker findByName(String name);
	public List<MakerProject> findByProject(Project project);

}
