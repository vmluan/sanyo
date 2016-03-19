package com.sanyo.quote.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.sanyo.quote.service.UserService;

public class CommonInterceptor extends HandlerInterceptorAdapter{
	@Autowired
	UserService userService;
	
	private static final Logger logger = LoggerFactory.getLogger(CommonInterceptor.class);

	//before the actual handler will be executed
	public boolean preHandle(HttpServletRequest request, 
		HttpServletResponse response, Object handler)
	    throws Exception {
		return true;
	}
	//after the handler is executed
	public void postHandle(
		HttpServletRequest request, HttpServletResponse response, 
		Object handler, ModelAndView modelAndView)
		throws Exception {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication != null && authentication.isAuthenticated()){
			if(!authentication.getPrincipal().toString().equalsIgnoreCase("anonymousUser")){
				org.springframework.security.core.userdetails.User user = (User) authentication.getPrincipal();
				
				logger.info("========= postHandle. username = "+ user.getUsername());
				
				com.sanyo.quote.domain.User userSanyo = userService.findByUserName(user.getUsername());
				modelAndView.addObject("user",userSanyo);
			}
		}
		
		
	}
}