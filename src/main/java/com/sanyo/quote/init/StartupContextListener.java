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
		GroupService groupService = ctx.getBean("groupService", GroupService.class);
		for(int i=0; i < 100; i++){
			String userName = "admin" + i;
			if(i ==0)
				userName = "admin";
			User admin = userService.findByUserName(userName);
			if(admin == null){
				
				
			
				
				User user = new User();
				user.setUsername(userName);
				user.setUsercode("01234");
				user.setPassword("ee10c315eba2c75b403ea99136f5b48d"); // admin in reserve,
				user.setActive(true);
				user.setEmail(userName + "@gmail.com"); 
				
				
				userService.save(user);
				Group userGroup = groupService.findByGroupName("ROLE_USER");
				if(userGroup == null){
					userGroup = new Group();
					userGroup.setGroupName("ROLE_USER");
					groupService.save(userGroup);
					
					List<Group> groupList = new ArrayList<Group>();
					groupList.add(userGroup);
					if(userName.equals("admin")){
						Group roleAdmin = new Group();
						roleAdmin.setGroupName("ROLE_ADMIN");
						groupList.add(roleAdmin);
					}
					user.setGrouplist(groupList);
				}else{
					List<Group> groupList = user.getGrouplist();
					if(groupList == null)
						groupList = new ArrayList<Group>();
					groupList.add(userGroup);
					user.setGrouplist(groupList);
				}
				userService.save(user);
				
			}else if(admin != null && userName.equals("admin")){
				Group adminRole = groupService.findByGroupName("ROLE_ADMIN");
				if(adminRole == null){
					adminRole = new Group();
					adminRole.setGroupName("ROLE_ADMIN");
				}
				List<Group> groupList = admin.getGrouplist();
				if(groupList == null )
					groupList = new ArrayList<Group>();
				groupList.add(adminRole);
				userService.save(admin);
			}
			
		}

		ctx.destroy();
	}
}
