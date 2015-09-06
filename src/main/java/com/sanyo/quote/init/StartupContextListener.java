package com.sanyo.quote.init;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContextEvent;

import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoaderListener;

import com.sanyo.quote.domain.Group;
import com.sanyo.quote.domain.User;
import com.sanyo.quote.service.GroupService;
import com.sanyo.quote.service.UserService;

@Component
public class StartupContextListener extends ContextLoaderListener{
	
	@Override
	public void contextInitialized(ServletContextEvent event){
		super.contextInitialized(event);
		System.out.println("=========================== application is on start up");
		addSampleData();
	}
	private void addSampleData(){
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		ctx.load("classpath:jpa-app-context.xml");
		ctx.refresh();
		
		System.out.println("App context initialized successfully");
		
		UserService userService = ctx.getBean("userService", UserService.class);
		User admin = userService.findByUserName("admin");
		if(admin == null){
			
			
		
			
			User user = new User();
			user.setUsername("admin");
			user.setUsercode("01234");
			user.setPassword("ee10c315eba2c75b403ea99136f5b48d"); // admin in reserve,
			user.setActive(true);
			
			
			userService.save(user);
			
			GroupService groupService = ctx.getBean("groupService", GroupService.class);
			Group group = new Group();
			group.setGroupName("ROLE_USER");
			groupService.save(group);
			List<Group> groupList = new ArrayList<Group>();
			groupList.add(group);
			user.setGrouplist(groupList);
			
			userService.save(user);
			
		}
		ctx.destroy();
	}
}
