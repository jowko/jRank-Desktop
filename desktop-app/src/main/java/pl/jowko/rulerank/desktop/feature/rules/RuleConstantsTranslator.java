package pl.jowko.rulerank.desktop.feature.rules;

import pl.jowko.rulerank.desktop.feature.internationalization.Labels;
import pl.jowko.rulerank.desktop.feature.internationalization.LanguageService;
import pl.poznan.put.cs.idss.jrs.rules.Rule;

/**
 * This class translates Rule constants to string values. <br>
 *  <br>
 * Created by Piotr on 2018-05-21.
 */
class RuleConstantsTranslator {
	
	private static LanguageService labels = LanguageService.getInstance();
	
	/**
	 * Translates rule types to string values.
	 * @param type of rule
	 * @return string value for provided type
	 */
	static String getRuleType(int type) {
		switch (type) {
			case Rule.CERTAIN:
				return labels.get(Labels.RULES_CERTAIN);
			case Rule.POSSIBLE:
				return labels.get(Labels.RULES_POSSIBLE);
			default:
				return labels.get(Labels.RULES_APPROXIMATE);
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
				return labels.get(Labels.RULES_AT_LEAST);
			case Rule.AT_MOST:
				return labels.get(Labels.RULES_AT_MOST);
			default:
				return labels.get(Labels.RULES_EQUAL);
		}
	}
	
}
