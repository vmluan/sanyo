package com.sanyo.quote.helper;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/*
 * class contains utility methods for processing image.
 */
public class ImageHelper {
	private static ImageHelper imageHelper;
	
//	private ImageHelper(){
//		//do nothing
//	}
	public static ImageHelper getInstances(){
		if(imageHelper == null)
			imageHelper = new ImageHelper();
		return imageHelper;
	}
	//function to upload image to application server.
	public String saveImages(HttpServletRequest httpServletRequest, String destFolder){
		
        MultiValueMap<String, MultipartFile> fileMap = ((MultipartHttpServletRequest)httpServletRequest).getMultiFileMap();
        Iterator<String> fileNameIterator = fileMap.keySet().iterator();
        String fileName = "";
        while(fileNameIterator.hasNext()) {
        	fileName = fileNameIterator.next();
        }
        System.out.println("======== file name is " + fileName);
		MultipartFile file =  ((MultipartHttpServletRequest)httpServletRequest).getFile(fileName);
		
		String mime_type = file.getContentType();
		
		System.out.println("======================= file type is " + mime_type);
		if (mime_type.substring(0, 5).equalsIgnoreCase("image")){
			fileName = file.getOriginalFilename();
			String filePath = httpServletRequest.getSession().getServletContext().getRealPath("/");
			String resultFile = filePath +  destFolder + fileName;
			
			File multipartFile = new File(resultFile);
			fileName = resultFile;
			try {
				file.transferTo(multipartFile);
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else
			fileName = "";

		return fileName;
	}

}
