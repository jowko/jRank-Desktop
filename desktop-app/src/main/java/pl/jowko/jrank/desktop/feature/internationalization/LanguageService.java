package pl.jowko.jrank.desktop.feature.internationalization;

import pl.jowko.jrank.desktop.feature.settings.UserSettingsService;
import pl.jowko.jrank.desktop.service.ConfigFileManager;
import pl.jowko.jrank.logger.JRankLogger;

import java.util.Map;
import java.util.Objects;

import static java.util.Objects.isNull;

/**
 * Created by Piotr on 2018-03-16.
 * This class serves as label translator for application.
 * It translates label codes to text containing proper translation.
 * Current language is extracted from user settings.
 * Labels are stored in labels.json file in data directory.
 * @see Labels
 */
public class LanguageService {
	
	private static LanguageService instance;
	private  Map<String, Map<String, String>> labels;
	private  Map<String, String> languages;
	
	
	private LanguageService() {
		languages = ConfigFileManager.getInstance().readLanguages();
		labels = ConfigFileManager.getInstance().readLabels();
	}
	
	/**
	 * Gets instance of this class and creates new if it doesn't exits.
	 * language.json and labels.json files are read in private constructor.
	 * @return instance of this class
	 */
	public static LanguageService getInstance() {
		if(isNull(instance)) {
			instance = new LanguageService();
		}
		return instance;
	}
	
	/**
	 * Gets translation for provided label code.
	 * It uses user settings to get current active language.
	 * When language or label code is not found, this situation is logged into WARN log and special label is returned to show this on UI.
	 * @see Labels
	 * @see UserSettingsService
	 * @param code from Labels class
	 * @return translated text extracted from labels map
	 */
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
	
	/**
	 * Get labels map, where key is language code.
	 * Nested map contains translation, where key is label code from Labels class.
	 * @see Labels
	 * @return map of map containing translation for all available languages
	 */
	public Map<String, Map<String, String>> getLabels() {
		return labels;
	}
	
	/**
	 * Gets language map, where key is language code and value is language description.
	 * Example:
	 * key - ENG, value - English
	 * @return map of languages
	 */
	public Map<String, String> getLanguages() {
		return languages;
	}
	
}
