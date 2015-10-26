package com.sanyo.quote.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.sanyo.quote.domain.Region;


public interface RegionRepository extends PagingAndSortingRepository<Region, Integer> {
    @Query("SELECT r FROM Region r JOIN FETCH r.users WHERE r.regionId = :id")
    public Region findByIdAndFetchUsersEagerly(@Param("id") Integer id);
    
    @Query("SELECT r FROM Region r JOIN FETCH r.userRegionRoles WHERE r.regionId = :id")
    public Region findByIdAndFetchUserRegionRolesEagerly(@Param("id") Integer id);
    
    @Query("SELECT r FROM Region r JOIN FETCH r.encounters WHERE r.regionId = :id")
    public Region findByIdAndFetchEncountersEagerly(@Param("id") Integer id);
}
