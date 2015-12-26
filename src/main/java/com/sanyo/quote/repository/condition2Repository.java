package com.sanyo.quote.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.sanyo.quote.domain.Condition2;
import com.sanyo.quote.domain.Project;

public interface condition2Repository extends PagingAndSortingRepository<Condition2, Integer>{
	/*@Query("update condition2 set checkboxs=:checkbox,contents=:content where PROJECT_ID  = :project_id")
	public Condition2 update(@Param("project_id")  Project PROJECT_ID,@Param("checkbox") String checkboxs,@Param("content") String contents);
	*/
	/*@Query("SELECT e FROM condition2 e WHERE e.PROJECT_ID = :project")
    public List<Condition2> findByProject (@Param("project") Project PROJECT_ID);
    
    */
	@Query("SELECT c FROM Condition2 c WHERE c.project = :project")
    public Condition2 findByProject (@Param("project") Project project);
}
