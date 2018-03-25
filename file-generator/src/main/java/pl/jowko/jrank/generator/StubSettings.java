package pl.jowko.jrank.generator;

import pl.jowko.jrank.desktop.settings.Labels;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Piotr on 2018-03-16.
 * This class serves as container to different settings with will be saved to file.
 */
class StubSettings {
	
	private Map<String, Map<String, String>> labels;
	private Map<String, String> languages;
	
	StubSettings() {
		labels = new HashMap<>();
		labels.put("POL", createPolishLabels());
		labels.put("ENG", createEnglishLabels());
		
		languages = new HashMap<>();
		languages.put("POL", "Polski");
		languages.put("ENG", "English");
		
	}
	
	public Map<String, Map<String, String>> getLabels() {
		return labels;
	}
	
	public Map<String, String> getLanguages() {
		return languages;
	}
	
	private Map<String, String> createPolishLabels() {
		Map<String, String> language = new HashMap<>();
		
		language.put(Labels.TITLE, "jRank Desktop Application");
		
		// menu items
		language.put(Labels.FILE, "Plik");
		language.put(Labels.SETTINGS, "Ustawienia");
		language.put(Labels.HELP, "Pomoc");
		language.put(Labels.ABOUT, "O programie");
		
		// General buttons
		language.put(Labels.SAVE, "Zapisz");
		language.put(Labels.CANCEL, "Anuluj");
		
		// User Settings
		language.put(Labels.US_TITLE, "Ustawienia użytkownika");
		language.put(Labels.LANGUAGE, "Język");
		
		return language;
	}
	
	private Map<String, String> createEnglishLabels() {
		Map<String, String> language = new HashMap<>();
		
		language.put(Labels.TITLE, "jRank Desktop Application");
		
		// menu items
		language.put(Labels.FILE, "File");
		language.put(Labels.SETTINGS, "Settings");
		language.put(Labels.HELP, "Help");
		language.put(Labels.ABOUT, "About");
		
		// General buttons
		language.put(Labels.SAVE, "Save");
		language.put(Labels.CANCEL, "Cancel");
		
		// User Settings
		language.put(Labels.US_TITLE, "User settings");
		language.put(Labels.LANGUAGE, "Language");
		
		return language;
	}
	
}
