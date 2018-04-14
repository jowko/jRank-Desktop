package pl.jowko.jrank.desktop.service;

import java.util.Map;

import static java.util.Objects.isNull;

/**
 * Created by Piotr on 2018-03-16.
 */
public class LabelTranslator {
	
	private static LabelTranslator instance;
	private static Map<String, Map<String, String>> labels;
	private static Map<String, String> languages;
	
	private LabelTranslator() {}
	
	public static LabelTranslator getInstance() {
		if(isNull(instance)) {
			instance = new LabelTranslator();
		}
		return instance;
	}
	
	public static String get(String code) {
		return labels.get(SettingsService.getLanguage()).get(code);
	}
	
	public static Map<String, Map<String, String>> getLabels() {
		return LabelTranslator.labels;
	}
	
	public static Map<String, String> getLanguages() {
		return LabelTranslator.languages;
	}
}
