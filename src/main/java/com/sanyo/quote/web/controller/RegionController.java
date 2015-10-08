package com.sanyo.quote.web.controller;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanyo.quote.domain.Region;
import com.sanyo.quote.domain.UserRegionRole;
import com.sanyo.quote.helper.Utilities;
import com.sanyo.quote.service.RegionService;

@Controller
@RequestMapping(value = "/regions")
public class RegionController {
	final Logger logger = LoggerFactory.getLogger(RegionController.class);
	
	@Autowired
	RegionService regionService;
	
	@RequestMapping(value = "/getAssginedUsersJson", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getAssginedUsersJson(@RequestParam(value="regionId", required=true) String regionId,
			@RequestParam(value="filterscount", required=false) String filterscount
			, @RequestParam(value="groupscount", required=false) String groupscount
			, @RequestParam(value="pagenum", required=false) Integer pagenum
			, @RequestParam(value="pagesize", required=false) Integer pagesize
			, @RequestParam(value="recordstartindex", required=false) Integer recordstartindex
			, @RequestParam(value="recordendindex", required=false) Integer recordendindex
			, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
		
		Region region = regionService.findByIdAndFetchUserRegionRolesEagerly(Integer.valueOf(regionId));
		Set<UserRegionRole> userRegionRoles = region.getUserRegionRoles();
		String result = Utilities.jSonSerialization(userRegionRoles);
		return result;
	}

}
