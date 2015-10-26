/**
 * Copyright (c) 2015  osgicse group.
 * Filename   : Utilities.java
 * Description: 
 * @author    : Luan Vo
 * Created    : Sep 3, 2013
 */

package com.sanyo.quote.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import com.ibm.icu.util.Calendar;

public class Utilities {
	
	public static final String datePattern = "dd/MM/yyyy";
	
	static final Logger logger = LoggerFactory.getLogger(Utilities.class);
	
	public static String getServerUploadPath(
			HttpServletRequest httpServletRequest) {
		return httpServletRequest.getRealPath(CommonProperties
				.ServerUploadPath());
	}

	public static String getUrlMediaPath(String contextPath) {
		return String.format("%s/%s", contextPath,
				CommonProperties.ServerUploadPath());
	}

	public static <T> String jSonSerialization(T object) {
		ObjectMapper objMapper = new ObjectMapper();
		String jsonString = "";

		try {
			jsonString = objMapper.writeValueAsString(object);
		} catch (Exception ex) {
			logger.error("Json Serialization : ", ex);
		}
		return jsonString;
	}
	
	public static <T> T jSonDeserialization(String jsonString) {
		ObjectMapper objMapper = new ObjectMapper();
		
		T object = null;
		/*try {
			object = objMapper.readValue(jsonString, new TypeReference<T>(){} );
		} catch (Exception ex) {
			logger.error("Json Serialization : ", ex);
		}*/
		return object;
	}
	public static Date parseDate(String dateString){
		SimpleDateFormat format = new SimpleDateFormat(datePattern);
		try {
			return format.parse(dateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	public static Date getLastTimeOfDate(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		Date tempDate = calendar.getTime();
		
		return tempDate;
	}
	public static User getCurrentUser(){
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return user;
	}
	public static boolean isValidInputNumber(String input){
		if(input != null && !input.trim().equalsIgnoreCase(""))
			return true;
		else
			return false;
			
	}

}
