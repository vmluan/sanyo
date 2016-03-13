package com.sanyo.quote.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.sanyo.quote.domain.Encounter;
import com.sanyo.quote.domain.Location;
import com.sanyo.quote.domain.Region;
import com.sanyo.quote.domain.UserRegionRole;


public interface RegionRepository extends PagingAndSortingRepository<Region, Integer> {
//    @Query("SELECT r FROM Region r JOIN FETCH r.users WHERE r.regionId = :id")
//    public Region findByIdAndFetchUsersEagerly(@Param("id") Integer id);
    
    @Query("SELECT r FROM Region r JOIN FETCH r.userRegionRoles WHERE r.regionId = :id")
    public Region findByIdAndFetchUserRegionRolesEagerly(@Param("id") Integer id);
    
    @Query("SELECT r FROM Region r JOIN FETCH r.encounters WHERE r.regionId = :id")
    public Region findByIdAndFetchEncountersEagerly(@Param("id") Integer id);
    
    @Query("SELECT distinct r.encounters FROM Region r JOIN  r.encounters WHERE r.regionId = :id")
    public List<Encounter> getEncounters(@Param("id") Integer id);
    
    @Query("SELECT distinct r.userRegionRoles FROM Region r JOIN  r.userRegionRoles WHERE r.regionId = :id")
    public List<UserRegionRole> getUserRegionRoles(@Param("id") Integer id);

    public List<Region> findByLocation(Location location);
}
