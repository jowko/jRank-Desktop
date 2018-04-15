package pl.jowko.jrank.desktop.service;

import java.util.Map;
import java.util.Objects;

import static java.util.Objects.isNull;
import static pl.jowko.jrank.desktop.settings.JRankConst.NO_LABEL;

/**
 * Created by Piotr on 2018-03-16.
 */
public class LanguageService {
	
	private static LanguageService instance;
	private  Map<String, Map<String, String>> labels;
	private  Map<String, String> languages;
	
	private LanguageService() {
		languages = FileManager.getInstance().readLanguages();
		labels = FileManager.getInstance().readLabels();
	}
	
	public static LanguageService getInstance() {
		if(isNull(instance)) {
			instance = new LanguageService();
		}
		return instance;
	}
	
	public String get(String code) {
		String label = labels.get(SettingsService.getInstance().getLanguage()).get(code);
		if(Objects.isNull(label)) {
			return NO_LABEL;
		}
		return label;
	}
	
	public Map<String, Map<String, String>> getLabels() {
		return labels;
	}
	
	public Map<String, String> getLanguages() {
		return languages;
	}
	
}
