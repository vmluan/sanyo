package com.sanyo.quote.web.controller;

import com.sanyo.quote.domain.EncounterJson;
import com.sanyo.quote.domain.Group;
import com.sanyo.quote.domain.Region;
import com.sanyo.quote.domain.UserRegionRole;
import com.sanyo.quote.helper.Utilities;
import com.sanyo.quote.service.*;
import com.sanyo.quote.web.util.MyRequestWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by luan on 3/20/16.
 */
public class PrivilegeFilter implements Filter{
    UserService userService;
    UserRegionRoleService userRegionRoleService;
    RegionService regionService;

    private static final Logger logger = LoggerFactory.getLogger(CommonInterceptor.class);
    private static final String ADD_QUOTATION= "/addquotation";
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
        ctx.load("classpath:jpa-app-context.xml");
        ctx.refresh();

        System.out.println("App context initialized successfully");
        userService = ctx.getBean("userService", UserService.class);
        userRegionRoleService = ctx.getBean("userRegionRoleService", UserRegionRoleService.class);
        regionService = ctx.getBean("regionService", RegionService.class);
        ctx.close();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // check privilege here
        MyRequestWrapper myRequestWrapper = null;
        boolean hasPrivilege = true;
        try {
            myRequestWrapper = new MyRequestWrapper((HttpServletRequest) servletRequest);
            hasPrivilege = checkPrivilege((HttpServletRequest)servletRequest,(HttpServletResponse)servletResponse,myRequestWrapper);
            //check here

        } catch (IOException e) {
            e.printStackTrace();
        }
        if(hasPrivilege && myRequestWrapper != null){
            filterChain.doFilter(myRequestWrapper,servletResponse);
        }else{
            return;
        }
    }

    @Override
    public void destroy() {

    }
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
    private boolean checkPrivilege(HttpServletRequest request,
                                   HttpServletResponse response, MyRequestWrapper myRequestWrapper){
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
            EncounterJson encounterJson = (EncounterJson) Utilities.jSonDeserialization(body, EncounterJson.class);
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
}
