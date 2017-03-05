package org.billing.geb60.test;

import java.io.File;
import java.net.URL;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;

public class MainTestCase {
	
	public void initTest(Log log) {
		log.info("Starting test '" + Thread.currentThread().getStackTrace()[2].getMethodName() + "'!");
	}
	
	public void closeTest(Log log) {
		log.info("Finished test '" + Thread.currentThread().getStackTrace()[2].getMethodName() + "'!");
	}

	public URL getResource(String fileName) {
		String folderName = StringUtils.substringAfterLast(this.getClass().getName(), ".");
		ClassLoader classLoader = getClass().getClassLoader();
		URL result = classLoader.getResource(folderName + File.separator + fileName);
		
		return result;
	}
}
