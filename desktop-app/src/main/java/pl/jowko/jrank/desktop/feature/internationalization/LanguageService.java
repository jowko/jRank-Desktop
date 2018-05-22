package pl.jowko.jrank.desktop.feature.internationalization;

import pl.jowko.jrank.desktop.feature.settings.UserSettingsService;
import pl.jowko.jrank.desktop.service.FileManager;
import pl.jowko.jrank.logger.JRankLogger;

import java.util.Map;
import java.util.Objects;

import static java.util.Objects.isNull;

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
		String lang = UserSettingsService.getInstance().getLanguage();
		Map<String, String> langCodes = labels.get(lang);
		if(Objects.isNull(langCodes)) {
			JRankLogger.warn("User language: [" + lang + "] is not recognized.");
			return '[' + lang + ":" + code + ']';
		}
		
		String label = langCodes.get(code);
		if(Objects.isNull(label)) {
			JRankLogger.warn("Label: [" + code + "] for language: [" + lang + "] is not found.");
			return '[' + lang + ":" + code + ']';
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