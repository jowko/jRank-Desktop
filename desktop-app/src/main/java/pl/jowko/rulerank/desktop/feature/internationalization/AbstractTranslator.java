package pl.jowko.rulerank.desktop.feature.internationalization;

/**
 * This is abstract implementation of Translator interface. <br>
 * It provides LanguageService and get method to its children. <br>
 * To use it, simply extend it. <br>
 *  <br>
 * Created by Piotr on 2018-05-20.
 */
public abstract class AbstractTranslator implements Translator {
	
	protected LanguageService labels;
	
	public AbstractTranslator() {
		labels = LanguageService.getInstance();
	}
	
	/**
	 * This methods accepts labelCode from Labels class and return corresponding translation.
	 * @param labelCode from Labels class
	 * @return translation for that code and current language
	 */
	protected String get(String labelCode) {
		return labels.get(labelCode);
	}
	
}
