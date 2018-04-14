package pl.jowko.jrank.desktop.service;

import java.util.Map;

import static java.util.Objects.isNull;

/**
 * Created by Piotr on 2018-03-17.
 */
public class LanguageService {
	
	private static LanguageService instance;
	private Map<String, String> languages;
	
	private LanguageService() {}
	
	public static LanguageService getInstance() {
		if(isNull(instance)) {
			instance = new LanguageService(); //TODO read from file
		}
		return instance;
	}
	
	public Map<String, String> getLanguages() {
		return this.languages;
	}
}
