package pl.jowko.jrank.generator;

import pl.jowko.jrank.desktop.settings.Labels;

import java.util.HashMap;
import java.util.Map;

import static pl.jowko.jrank.desktop.settings.JRankConst.MSG;

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
		
		language.put(Labels.APP_TITLE, "JRank Ultimate Desktop Edition");
		language.put(Labels.US_SETTINGS_ERROR, "Ustawienia użytkownika nie zostały poprawnie skonfigurowane: ");
		
		// menu items
		language.put(Labels.MENU_FILE, "Plik");
		language.put(Labels.MENU_QUIT, "Wyjście");
		language.put(Labels.MENU_SETTINGS, "Ustawienia");
		language.put(Labels.MENU_USER_SETTINGS, "Ustawienia użytkownika");
		language.put(Labels.MENU_HELP, "Pomoc");
		language.put(Labels.MENU_ITEM_HELP, "Pomoc");
		language.put(Labels.MENU_ABOUT, "O programie");
		
		// General buttons
		language.put(Labels.BUTTON_SAVE, "Zapisz");
		language.put(Labels.BUTTON_CANCEL, "Anuluj");
		
		// User Settings
		language.put(Labels.US_TITLE, "Ustawienia użytkownika");
		language.put(Labels.LANGUAGE, "Język: ");
		language.put(Labels.WORKSPACE, "Scieżka robocza: ");
		language.put(Labels.TOOLTIPS_ENABLED, "Podpowiedzi aktywne");
		language.put(Labels.US_INFO, "Ustawienia zostaną zastosowane po restarcie aplikacji.");
		language.put(Labels.LANGUAGE_ERROR, "Kod języka jest nieprawidłowy. Wybierz inny język.\n");
		language.put(Labels.WORKSPACE_ERROR, "Podana ścieżka katalogu roboczego: " + MSG + " nie jest prawidłowa\n");
		language.put(Labels.ERROR_DIALOG_TITLE, "Niespodziewany błąd");
		language.put(Labels.VALIDATION_DIALOG_TITLE, "Błąd walidacji");
		language.put(Labels.US_ERROR_DIALOG_HEADER, "Błąd przy zapisie opcji użytkownika: ");
		language.put(Labels.VALIDATION_DIALOG_HEADER, "Wykryto błędy na formularzu: ");
		
		// About dialog
		language.put(Labels.ABOUT_TITLE, "O JRank Ultimate Desktop Edition");
		language.put(Labels.ABOUT_APP_INFO, "Informacje o aplikacji:");
		language.put(Labels.ABOUT_AUTHOR, "Autor:");
		language.put(Labels.ABOUT_VERSION, "Wersja:");
		language.put(Labels.ABOUT_RELEASE_DATE, "Data wydania:");
		language.put(Labels.ABOUT_BOTTOM_INFO, "JRank Ultimate Desktop Edition został stworzony w ramach pracy magisterkiej.");
		
		// Lower tabs
		language.put(Labels.LOGS_TAB, "Logi");
		
		return language;
	}
	
	private Map<String, String> createEnglishLabels() {
		Map<String, String> language = new HashMap<>();
		
		language.put(Labels.APP_TITLE, "JRank Ultimate Desktop Edition");
		language.put(Labels.US_SETTINGS_ERROR, "User settings are not correctly configured: ");
		
		// menu items
		language.put(Labels.MENU_FILE, "File");
		language.put(Labels.MENU_QUIT, "Quit");
		language.put(Labels.MENU_SETTINGS, "Settings");
		language.put(Labels.MENU_USER_SETTINGS, "User settings");
		language.put(Labels.MENU_HELP, "Help");
		language.put(Labels.MENU_ITEM_HELP, "Help");
		language.put(Labels.MENU_ABOUT, "About");
		
		// General buttons
		language.put(Labels.BUTTON_SAVE, "Save");
		language.put(Labels.BUTTON_CANCEL, "Cancel");
		
		// User Settings
		language.put(Labels.US_TITLE, "User settings");
		language.put(Labels.LANGUAGE, "Language: ");
		language.put(Labels.WORKSPACE, "Workspace path: ");
		language.put(Labels.TOOLTIPS_ENABLED, "Tooltips enabled");
		language.put(Labels.US_INFO, "Settings will be allied after application restart.");
		language.put(Labels.LANGUAGE_ERROR, "Language code is not valid. Choose another language.\n");
		language.put(Labels.WORKSPACE_ERROR, "Provided workspace folder path: " + MSG + " is not correct\n");
		language.put(Labels.ERROR_DIALOG_TITLE, "Unexpected error");
		language.put(Labels.VALIDATION_DIALOG_TITLE, "Validation Fail");
		language.put(Labels.US_ERROR_DIALOG_HEADER, "Error when saving user options: ");
		language.put(Labels.VALIDATION_DIALOG_HEADER, "There were validation errors: ");
		
		// About dialog
		language.put(Labels.ABOUT_TITLE, "About JRank Ultimate Desktop Edition");
		language.put(Labels.ABOUT_APP_INFO, "Application information:");
		language.put(Labels.ABOUT_AUTHOR, "Author:");
		language.put(Labels.ABOUT_VERSION, "Version:");
		language.put(Labels.ABOUT_RELEASE_DATE, "Release Date:");
		language.put(Labels.ABOUT_BOTTOM_INFO, "JRank Ultimate Desktop Edition was developed as part of master thesis.");
		
		// Lower tabs
		language.put(Labels.LOGS_TAB, "Logs");
		
		return language;
	}
	
}
