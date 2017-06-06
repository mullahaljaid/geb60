package org.billing.geb60.test.util;

import java.net.URL;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.billing.geb60.bo.Answer;
import org.billing.geb60.bo.Question;
import org.billing.geb60.test.MainTestCase;
import org.billing.geb60.util.ResourceLoader;
import org.junit.Assert;
import org.junit.Test;

public class ResourceLoaderTest extends MainTestCase {
	
	private final Log _log = LogFactory.getLog(getClass());
	
	@Test
	public void testGetQuestions() {
		initTest(_log);
		URL testUrl = getResource(ResourceLoader.DEFAULT_RESOURCE);
		List<Question> list = ResourceLoader.getInstance().getQuestions(testUrl);
		Assert.assertTrue("Should be 3 questions!", list.size() == 3);
		closeTest(_log);
	}
	
	@Test
	public void testGetQuestionOrder() {
		initTest(_log);
		URL testUrl = getResource(ResourceLoader.DEFAULT_RESOURCE);
		List<Question> list = ResourceLoader.getInstance().getQuestions(testUrl);
		Question q = null;
		for (Question q1 : list) {
			if (q1.isQuestion("Wie alt seid ihr?")) {
				q = q1;
			}
		}
		
		Assert.assertTrue("Question should be found!", q != null);
		Assert.assertTrue("Should have 4 answers!", q.getAnswers().size() == 4);
		Answer a1 = q.getAnswers().get(0);
		for (int i = 1; i < 4; i++) {
			Answer a2 = q.getAnswers().get(i);
			Assert.assertTrue("Descending order!", a2.getPoints() <= a1.getPoints());
			a1 = a2;
		}
		closeTest(_log);
	}
}
