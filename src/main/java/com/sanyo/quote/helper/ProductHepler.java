package com.sanyo.quote.helper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.util.List;

import com.sanyo.quote.domain.Product;

public class ProductHepler {

	private static String productSamples;

	public void generateProductJson(List<Product> products, String location) {
		BufferedWriter writer = null;

		try {
			// create a js file
			String fileName = "products.js";
			File logFile = new File(location + fileName);
//			writer = new BufferedWriter(new FileWriter(logFile));
			writer = new BufferedWriter
				    (new OutputStreamWriter(new FileOutputStream(logFile),"UTF-8"));
			String productString = products.toString();

			productString = '{' + productString.substring(1,
					productString.length() - 1) + '}';

			String outputString = "var sampleProducts =" + productString;
			writer.write(outputString);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// Close the writer regardless of what happens...
				writer.close();
			} catch (Exception e) {
			}
		}

	}

	public static String getProductSamples() {
		return productSamples;
	}

	public static void setProductSamples(String productSamples) {
		ProductHepler.productSamples = productSamples;
	}

	public void generateProductSamples(List<Product> products) {
		String productString = products.toString();
		productString = '{' + productString.substring(1,
				productString.length() - 1) + '}';
		this.productSamples = "var sampleProducts =" + productString;
	}

}
