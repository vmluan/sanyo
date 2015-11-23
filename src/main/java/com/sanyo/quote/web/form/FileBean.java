package com.sanyo.quote.web.form;

import org.springframework.web.multipart.MultipartFile;

public class FileBean {

	  private MultipartFile file;

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
	}
