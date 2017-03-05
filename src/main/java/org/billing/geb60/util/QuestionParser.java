package org.billing.geb60.util;

import org.apache.commons.lang3.StringUtils;
import org.billing.geb60.bo.Answer;
import org.billing.geb60.bo.Question;
import org.ini4j.Ini;
import org.ini4j.Profile.Section;

public class QuestionParser {

	public static Question parseQuestion(Ini ini, String sectionName) {
		String questionName = ini.get(sectionName, "question");
		Question question = new Question(questionName);
		Section section = ini.get(sectionName);
		for (String key : section.keySet()) {
			if (!StringUtils.equalsIgnoreCase(key, "question")) {
				Answer ans = new Answer(key, ini.get(sectionName, key, int.class));
				question.addAnswer(ans);
			}
		}
		return question;
	}
}
