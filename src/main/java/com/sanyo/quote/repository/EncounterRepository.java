package com.sanyo.quote.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.sanyo.quote.domain.Encounter;
import com.sanyo.quote.domain.Region;


public interface EncounterRepository extends PagingAndSortingRepository<Encounter, Integer> {
    @Query("SELECT e FROM Encounter e WHERE e.region = :region order by e.order asc")
    public List<Encounter> getEncountersByRegion(@Param("region") Region region);
    
    public List<Encounter> findByRegion(Region region);

}
