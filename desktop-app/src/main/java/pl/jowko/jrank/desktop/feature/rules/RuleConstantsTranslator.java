package pl.jowko.jrank.desktop.feature.rules;

import pl.poznan.put.cs.idss.jrs.rules.Rule;

/**
 * Created by Piotr on 2018-05-21.
 * This class translates Rule constants to string values.
 */
class RuleConstantsTranslator {
	
	/**
	 * Translates rule types to string values.
	 * @param type of rule
	 * @return string value for provided type
	 */
	static String getRuleType(int type) {
		switch (type) {
			case Rule.CERTAIN:
				return "CERTAIN";
			case Rule.POSSIBLE:
				return "POSSIBLE";
			default:
				return "APPROXIMATE";
		}
	}
	
	/**
	 * Translates usage types to string values
	 * @param type of usage
	 * @return string value for provided type
	 */
	static String getUsageType(int type) {
		switch (type) {
			case Rule.AT_LEAST:
				return "AT LEAST";
			case Rule.AT_MOST:
				return "AT MOST";
			default:
				return "EQUAL";
		}
	}
	
}
