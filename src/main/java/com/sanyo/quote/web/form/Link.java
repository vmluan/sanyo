package com.sanyo.quote.web.form;

public class Link {
	private String linkUrl;
	private String linkName;
	private boolean isHomeLink;
	
	
	
	public String getLinkUrl() {
		return linkUrl;
	}
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
	public String getLinkName() {
		return linkName;
	}
	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}
	public boolean isHomeLink() {
		return isHomeLink;
	}
	public void setHomeLink(boolean isHomeLink) {
		this.isHomeLink = isHomeLink;
	}
	
}
