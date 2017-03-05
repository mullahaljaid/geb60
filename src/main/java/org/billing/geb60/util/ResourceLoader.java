package org.billing.geb60.util;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.billing.geb60.bo.Question;
import org.ini4j.Config;
import org.ini4j.Ini;
import org.ini4j.InvalidFileFormatException;

public class ResourceLoader {

	public static final String DEFAULT_RESOURCE = "Questions.properties";
	
	private static ResourceLoader loader = null;
	
	private ResourceLoader() {}
	
	public static ResourceLoader getInstance() {
		if (loader == null) {
			loader = new ResourceLoader();
		}
		return loader;
	}
	
	public List<Question> getQuestions(String fileName) {
		URL resourceUrl = getClass().getResource(fileName);
		return getQuestions(resourceUrl);
	}
	
	public List<Question> getQuestions(URL fileUrl) {
		List<Question> res = new ArrayList<Question>();
		try {
			Ini resourceIni = new Ini();
			Config config = new Config();
			config.setEscape(false);
			config.setFileEncoding(Charset.forName("ISO-8859-1"));
			resourceIni.setConfig(config);
			resourceIni.load(fileUrl);
			for (String sectionName : resourceIni.keySet()) {
				res.add(QuestionParser.parseQuestion(resourceIni, sectionName));
			}
		} catch (InvalidFileFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
	}
}
