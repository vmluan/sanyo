package com.sanyo.quote.web.controller;

import javax.servlet.ServletRequestWrapper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sanyo.quote.domain.EncounterJson;
import com.sanyo.quote.domain.Group;
import com.sanyo.quote.domain.Region;
import com.sanyo.quote.domain.UserRegionRole;
import com.sanyo.quote.helper.Utilities;
import com.sanyo.quote.service.RegionService;
import com.sanyo.quote.service.UserRegionRoleService;
import com.sanyo.quote.web.util.MyRequestWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.sanyo.quote.service.UserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class CommonInterceptor extends HandlerInterceptorAdapter{
	@Autowired
	UserService userService;
	@Autowired
	UserRegionRoleService userRegionRoleService;
	@Autowired
	RegionService regionService;

	private static final Logger logger = LoggerFactory.getLogger(CommonInterceptor.class);
	private static final String ADD_QUOTATION= "/addquotation";

	private com.sanyo.quote.domain.User getLoggedInUser(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication != null && authentication.isAuthenticated()){
			if(!authentication.getPrincipal().toString().equalsIgnoreCase("anonymousUser")){
				org.springframework.security.core.userdetails.User user = (User) authentication.getPrincipal();
				logger.info("========= preHandle. username = " + user.getUsername());
				com.sanyo.quote.domain.User userSanyo = userService.findByUserName(user.getUsername());
				return userSanyo;
			}
		}
		return null;
	}
	private String getRequestBody(HttpServletRequest request){
		ServletRequestWrapper wrapper = new ServletRequestWrapper(request);

		// Read from request
		BufferedReader reader = null;
		try {
			StringBuilder buffer = new StringBuilder();
			reader = wrapper.getReader();
			String line;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			String data = buffer.toString();
			return data;

		}catch (Exception e){

		}finally {
			if(reader != null){
				try{
					reader.close();
				}catch (IOException ex){
					ex.printStackTrace();
				}
			}
		}
		return null;
	}
	private boolean checkPrivilege(HttpServletRequest request,
								   HttpServletResponse response, Object handler, MyRequestWrapper myRequestWrapper){
		String uri = request.getRequestURI();
		String url = request.getRequestURL().toString();
		//only check special uris.
		if(uri.contains(ADD_QUOTATION)){


			com.sanyo.quote.domain.User userSanyo = getLoggedInUser();
			List<Group> rolesList = userSanyo.getGrouplist();
			for(Group role : rolesList){
				if("ROLE_ADMIN".equalsIgnoreCase(role.getGroupName())){
					return true;
				}
			}
			// convert json to object

			//String body = getRequestBody(request);
			String body = "";
			body = myRequestWrapper.getBody();
			EncounterJson encounterJson = (EncounterJson) Utilities.jSonDeserialization(body,EncounterJson.class);
			if(encounterJson != null && userSanyo != null){
				// get region id, then get userRegionRole, and get its Name and compare with Edit or View
				String regionId = encounterJson.getRegionId();
				Region region = regionService.findById(Integer.valueOf(regionId));
				List<UserRegionRole> userRegionRoleList = userRegionRoleService.findByRegionAndUser(region,userSanyo);
				if(userRegionRoleList != null && !userRegionRoleList.isEmpty()){
					for(UserRegionRole userRegionRole : userRegionRoleList){
						String roleName = userRegionRole.getRoleName();
						if(roleName == null || roleName.equalsIgnoreCase("VIEW"))
							return false;
						else if(roleName.equalsIgnoreCase("EDIT")){
							return true;
						}else{
							return false;
						}
					}
				}else{
					return false;
				}

			}

		}else{
			// do nothing
			return true;
		}
		return true;
	}
	//before the actual handler will be executed
	public boolean preHandle(HttpServletRequest request, 
		HttpServletResponse response, Object handler)  throws Exception {
/*		MyRequestWrapper myRequestWrapper = null;
		try {
			myRequestWrapper = new MyRequestWrapper((HttpServletRequest) request);

		} catch (IOException e) {
			e.printStackTrace();
		}*/
		//boolean result = checkPrivilege(request,response,handler, myRequestWrapper);
		return true;
	}
	//after the handler is executed
	public void postHandle(
		HttpServletRequest request, HttpServletResponse response, 
		Object handler, ModelAndView modelAndView)
		throws Exception {
		// call common function
		org.springframework.security.core.userdetails.User user = Utilities.getCurrentUser();
		if(user == null)
			return;
		request.setAttribute("userName", user.getUsername());
		com.sanyo.quote.domain.User userSanyo = userService.findByUserName(user.getUsername());
		request.setAttribute("logginUser", userSanyo);
		boolean isAdminrole = Utilities.hasAdminRole();
		request.setAttribute("isAdminrole",isAdminrole);
	}
}