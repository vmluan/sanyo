package com.sanyo.quote.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mysql.jdbc.Blob;
import com.sanyo.quote.domain.User;

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
	public Blob saveImagesWithBlobType(HttpServletRequest httpServletRequest, String destFolder){
		
        MultiValueMap<String, MultipartFile> fileMap = ((MultipartHttpServletRequest)httpServletRequest).getMultiFileMap();
        Iterator<String> fileNameIterator = fileMap.keySet().iterator();
        String fileName = "";
        Blob blob = null;
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
//				file.transferTo(multipartFile); //may not need this code. will test and cofirm later.
				FileInputStream fileInputStream = new FileInputStream(multipartFile);
				blob = (Blob) Hibernate.getLobCreator((Session) httpServletRequest.getSession()).createBlob(fileInputStream, multipartFile.length());
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		
		return blob;
	}
//	   public void readImage(User user, String photoFilePath) throws IOException, SQLException {
//	        Blob blob = user.getAvatarBlob();
//	        byte[] blobBytes = blob.getBytes(1, (int) blob.length());
//	        saveBytesToFile(photoFilePath, blobBytes);
//	        blob.free();
//	    }
//	     
//	    public void saveBytesToFile(String filePath, byte[] fileBytes) throws IOException {
//	        FileOutputStream outputStream = new FileOutputStream(filePath);
//	        outputStream.write(fileBytes);
//	        outputStream.close();
//	    }
	     
	

}
