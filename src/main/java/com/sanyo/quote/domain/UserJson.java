package com.sanyo.quote.domain;

import java.io.Serializable;

/*
 * it is used to convert json data to java object.
 */
public class UserJson implements Serializable{
private String userName;
private String userId;
public String getUserName() {
	return userName;
}
public void setUserName(String userName) {
	this.userName = userName;
}
public String getUserId() {
	return userId;
}
public void setUserId(String userId) {
	this.userId = userId;
}

}
