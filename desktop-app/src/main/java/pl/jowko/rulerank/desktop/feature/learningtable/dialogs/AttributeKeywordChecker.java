package pl.jowko.rulerank.desktop.feature.learningtable.dialogs;

import java.util.List;

import static java.util.Arrays.asList;

/**
 * Checks if attribute name is reserved word in isf file. <br>
 * If attribute name is reserved word, ruleRank cannot read such isf file because of errors. <br>
 * Currently this words are reserved: <br>
 * - decision, description, none, cost, gain <br>
 * I didn't saw error with this keywords: <br>
 * - nominal, integer, continuous, numbercoded <br>
 * But it is validated just in case of some edge cases <br>
 * <br>
 * Created by Piotr on 2018-09-04
 */
class AttributeKeywordChecker {
	
	private static List<String> reservedWords;
	
	static {
		reservedWords = asList(
				"decision", "description", "none", "cost", "gain",
				"nominal", "integer", "continuous", "numbercoded"
		);
	}
	
	private AttributeKeywordChecker() {}
	
	/**
	 * Check if attribute name is equal to reserved word from isf file.
	 * @param attributeName from attribute form
	 * @return true if attribute name is reserved word, false otherwise
	 */
	static boolean isAttributeNameIsfKeyword(String attributeName) {
		return reservedWords.contains(attributeName);
	}
	
}
