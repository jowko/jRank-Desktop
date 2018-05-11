package pl.jowko.jrank.generator;

import pl.jowko.jrank.desktop.feature.settings.Labels;
import pl.jowko.jrank.desktop.feature.settings.UserSettings;
import pl.jowko.jrank.desktop.feature.settings.UserSettingsBuilder;

import java.util.HashMap;
import java.util.Map;

import static pl.jowko.jrank.desktop.feature.settings.JRankConst.MSG;

/**
 * Created by Piotr on 2018-03-16.
 * This class serves as container to different settings with will be saved to file.
 */
class StubSettings {
	
	private Map<String, Map<String, String>> labels;
	private Map<String, String> languages;
	private UserSettings settings;
	
	StubSettings() {
		createStubSettings();
		
		labels = new HashMap<>();
		labels.put("POL", createPolishLabels());
		labels.put("ENG", createEnglishLabels());
		
		languages = new HashMap<>();
		languages.put("POL", "Polski");
		languages.put("ENG", "English");
		
	}
	
	Map<String, Map<String, String>> getLabels() {
		return labels;
	}
	
	Map<String, String> getLanguages() {
		return languages;
	}
	
	UserSettings getSettings() {
		return settings;
	}
	
	private void createStubSettings() {
		settings = new UserSettingsBuilder()
				.setLanguage("ENG")
				.setWorkspacePath("\\workspace")
				.setTooltipsEnabled(true)
				.setAdvancedPropertiesEnabled(false)
				.createUserSettings();
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
		language.put(Labels.ADVANCED_PROPERTIES_ENABLED, "Advanced properties enabled");
		language.put(Labels.ADVANCED_PROPERTIES_ENABLED_TOOLTIP, "When advanced properties are enabled, all fields on properties form are visible by default. If this option is not enabled, most fields are hidden and you need to expand panels to edit them.");
		language.put(Labels.US_INFO, "Ustawienia zostaną zastosowane po restarcie aplikacji.");
		language.put(Labels.LANGUAGE_ERROR, "Kod języka jest nieprawidłowy. Wybierz inny język.\n");
		language.put(Labels.WORKSPACE_ERROR, "Podana ścieżka katalogu roboczego: " + MSG + " nie jest prawidłowa\n");
		language.put(Labels.ERROR_DIALOG_TITLE, "Niespodziewany błąd");
		language.put(Labels.VALIDATION_DIALOG_TITLE, "Błąd walidacji");
		language.put(Labels.US_ERROR_DIALOG_HEADER, "Błąd przy zapisie opcji użytkownika: ");
		language.put(Labels.VALIDATION_DIALOG_HEADER, "Wykryto błędy na formularzu: ");
		
		// Dialogs
		language.put(Labels.CONFIRM_DIALOG_TITLE, "Potwierdź akcję");
		language.put(Labels.CONFIRM_DIALOG_YES, "Tak");
		language.put(Labels.CONFIRM_DIALOG_NO, "Nie");
		
		// About dialog
		language.put(Labels.ABOUT_TITLE, "O JRank Ultimate Desktop Edition");
		language.put(Labels.ABOUT_APP_INFO, "Informacje o aplikacji:");
		language.put(Labels.ABOUT_AUTHOR, "Autor:");
		language.put(Labels.ABOUT_VERSION, "Wersja:");
		language.put(Labels.ABOUT_RELEASE_DATE, "Data wydania:");
		language.put(Labels.ABOUT_BOTTOM_INFO, "JRank Ultimate Desktop Edition został stworzony w ramach pracy magisterkiej.");
		
		// Lower tabs
		language.put(Labels.LOGS_TAB, "Logi");
		
		//TODO translate or remove polish language support
		createEnglishPropertiesLabels(language);
		createEnglishPropertiesMessages(language);
		createEnglishPropertiesValidationMessages(language);
		createEnglishPropertiesTooltips(language);
		
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
		language.put(Labels.ADVANCED_PROPERTIES_ENABLED, "Advanced properties enabled");
		language.put(Labels.ADVANCED_PROPERTIES_ENABLED_TOOLTIP, "When advanced properties are enabled, all fields on properties form are visible by default. If this option is not enabled, most fields are hidden and you need to expand panels to edit them.");
		language.put(Labels.US_INFO, "Settings will be allied after application restart.");
		language.put(Labels.LANGUAGE_ERROR, "Language code is not valid. Choose another language.\n");
		language.put(Labels.WORKSPACE_ERROR, "Provided workspace folder path: " + MSG + " is not correct\n");
		language.put(Labels.ERROR_DIALOG_TITLE, "Unexpected error");
		language.put(Labels.VALIDATION_DIALOG_TITLE, "Validation Fail");
		language.put(Labels.US_ERROR_DIALOG_HEADER, "Error when saving user options: ");
		language.put(Labels.VALIDATION_DIALOG_HEADER, "There were validation errors: ");
		
		// Dialogs
		language.put(Labels.CONFIRM_DIALOG_TITLE, "Confirm action");
		language.put(Labels.CONFIRM_DIALOG_YES, "Yes");
		language.put(Labels.CONFIRM_DIALOG_NO, "No");
		
		// About dialog
		language.put(Labels.ABOUT_TITLE, "About JRank Ultimate Desktop Edition");
		language.put(Labels.ABOUT_APP_INFO, "Application information:");
		language.put(Labels.ABOUT_AUTHOR, "Author:");
		language.put(Labels.ABOUT_VERSION, "Version:");
		language.put(Labels.ABOUT_RELEASE_DATE, "Release Date:");
		language.put(Labels.ABOUT_BOTTOM_INFO, "JRank Ultimate Desktop Edition was developed as part of master thesis.");
		
		// Lower tabs
		language.put(Labels.LOGS_TAB, "Logs");
		
		createEnglishPropertiesLabels(language);
		createEnglishPropertiesMessages(language);
		createEnglishPropertiesValidationMessages(language);
		createEnglishPropertiesTooltips(language);
		
		return language;
	}
	
	private void createEnglishPropertiesLabels(Map<String, String> language) {
		language.put(Labels.PROP_FILES_PANEL, "Input and output files");
		language.put(Labels.LEARNING_FILE, "Learning data file:");
		language.put(Labels.TEST_FILE, "Test data file:");
		language.put(Labels.PCT_FILE, "PCT file:");
		language.put(Labels.PCT_APX_FILE, "PCT APX file:");
		language.put(Labels.PCT_RULES_FILE, "PCT rules file:");
		language.put(Labels.GRAPH_FILE, "Preference graph file:");
		language.put(Labels.RANKING_FILE, "Ranking file:");
		
		language.put(Labels.PROP_DATA_PANEL, "Learning information");
		language.put(Labels.REFERENCE_RANKING, "Reference Ranking:");
		language.put(Labels.PAIRS, "Pairs:");
		
		language.put(Labels.PROP_PARAMETERS_PANEL, "Parameters");
		language.put(Labels.TYPE_OF_FAMILY_CRITERIA, "Type of family criteria:");
		language.put(Labels.TYPE_OF_RULES, "Type of rules:");
		language.put(Labels.CONSIDERED_SET_OF_RULES, "Considered set of rules:");
		language.put(Labels.CONSISTENCY_MEASURE, "Consistency measure:");
		language.put(Labels.CONSISTENCY_MEASURE_THRESHOLD, "Consistency measure threshold:");
		language.put(Labels.PRECISION, "Precision:");
		language.put(Labels.RANKING_PROCEDURE, "Ranking procedure:");
		language.put(Labels.DOMINANCE, "Dominance:");
		language.put(Labels.DOMINANCE_FOR_PAIRS, "Dominance for pairs of ordinal values:");
		language.put(Labels.SATISFACTION_DEGREES, "Satisfaction degrees in preference graph:");
		language.put(Labels.FUZZY_CALCULATION_METHOD, "Fuzzy satisfaction degree calculation method:");
		language.put(Labels.NEGATIVE_EXAMPLE_TREATMENT, "Negative examples treatment for VCDRSA:");
		language.put(Labels.OPTIMIZE_RULE_CONSISTENCY, "Optimize rule consistency in VCDomLEMWrt:");
		language.put(Labels.RULE_CONDITIONS_SELECTION_METHOD, "Rule conditions selection method in VCDomLEM:");
		
		language.put(Labels.ALLOW_EMPTY_RULES, "Allow empty rules in VCDomLEM:");
		language.put(Labels.USE_EDGE_REGIONS, "Use edge regions in VCDomLEM:");
		language.put(Labels.PROP_WRITE_PANEL, "Write additional information to files");
		language.put(Labels.WRITE_DOMINATION_INFO, "Write domination information:");
		language.put(Labels.WRITE_RULES_STAT, "Write rule statistics:");
		language.put(Labels.WRITE_LEARNING_EXAMPLES, "Write learning positive examples:");
		
		language.put(Labels.PROP_SAVE_BUTTON, "Save");
		language.put(Labels.PROP_CANCEL_BUTTON, "Cancel");
		language.put(Labels.PROP_CLEAR_BUTTON, "Clear form");
		language.put(Labels.RESTORE_VALUES_BUTTON, "Restore original values");
		language.put(Labels.PROP_VALIDATE_FORM, "Validate form");
		language.put(Labels.VALIDATE_FORM_DEFAULTS, "Validate form with defaults");
	}
	
	private void createEnglishPropertiesMessages(Map<String, String> language) {
		language.put(Labels.PROP_ERROR_SAVE, "Error when saving properties:");
		language.put(Labels.PROP_VALIDATE_DIALOG_TITLE, "Validation");
		language.put(Labels.PROP_VALIDATE_DIALOG_CONTENT, "Form does not contain any errors.");
		language.put(Labels.PROP_VD_DIALOG_TITLE, "Validation with defaults");
		language.put(Labels.PROP_VD_DIALOG_CONTENT, "Form does not contain any errors.");
		language.put(Labels.PROP_SAVE_ERROR_CONFIRM, "Do you want to save form? There are validation errors:");
		language.put(Labels.PROP_ABANDON_CHANGES, "Do you want to abandon changes in form?");
	}
	
	private void createEnglishPropertiesValidationMessages(Map<String, String> language) {
		language.put(Labels.LEARNING_DATA_FILE_EMPTY, "Learning data file is empty.\n");
		language.put(Labels.CONSISTENCY_MEASURE_LESS_THAN_ZERO, "Consistency measure threshold is smaller than zero.\n");
		language.put(Labels.FUZZY_EXHAUSTIVE_SET, "Incorrect fuzzy satisfaction degrees calculation method for exhaustive set of decision rules.\n");
		language.put(Labels.INCORRECT_CONSISTENCY_MEASURE, "Incorrect consistency measure threshold. Value has to be not greater than 1.0.\n");
		language.put(Labels.INCORRECT_CERTAIN_RULES, "In VC-DRSA only certain rules may be induced.\n");
		language.put(Labels.EXHAUSTIVE_SET_CERTAIN_RULES, "In VC-DRSA, exhaustive set of certain rules may be used only for monotonic consistency measures: epsilon, epsilon*, and epsilon', not for rough membership.\n");
		language.put(Labels.FUZZY_EXHAUSTIVE_POSSIBLE_ROUGH_MEMBERSHIP, "Fuzzy satisfaction degrees cannot be used in DRSA for exhaustive set of possible rules with rough membership measure. Assuming crisp satisfaction degrees in preference graph.\n");
		language.put(Labels.OPTIMIZE_POSSIBLE_RULES, "In VC-DomLEM, consistency of possible rules cannot be optimized w.r.t. set, only w.r.t. upper approximation of that set. Assuming optimization of rule consistency measure w.r.t. upper approximation.\n");
	}
	
	private void createEnglishPropertiesTooltips(Map<String, String> language) {
		language.put(Labels.LEARNING_FILE_TOOLTIP, "Absolute or relative path to the learning ISF data file; folders must be separated by / or \\\\");
		language.put(Labels.TEST_FILE_TOOLTIP, "Absolute or relative path to the test ISF data file; folders must be separated by / or \\\\; if not given, then assumed to be equal to learningDataFile");
		language.put(Labels.PCT_FILE_TOOLTIP, "Absolute or relative path to the ISF file where PCT created from preference information will be saved; folders must be separated by / or \\\\; if not given, then set automatically using value of learningDataFile parameter");
		language.put(Labels.PCT_APX_FILE_TOOLTIP, "Absolute or relative path to the file where approximations for PCT created from preference information will be saved; folders must be separated by / or \\\\; if not given, then set automatically using value of learningDataFile parameter");
		language.put(Labels.PCT_RULES_FILE_TOOLTIP, "Absolute or relative path to the file where rules for PCT created from preference information will be saved; folders must be separated by / or \\\\; if not given, then set automatically using value of learningDataFile parameter");
		language.put(Labels.GRAPH_FILE_TOOLTIP, "Absolute or relative path to the file where preference graph resulting from application of rules to all pairs of objects will be saved; folders must be separated by / or \\\\; if not given, then set automatically using value of testDataFile parameter");
		language.put(Labels.RANKING_FILE_TOOLTIP, "Absolute or relative path to the file where ranking of all objects will be saved; folders must be separated by / or \\\\; if not given, then set automatically using value of testDataFile parameter");
		
		language.put(Labels.REFERENCE_RANKING_TOOLTIP, "Comma-separated list of places; at each place there may be just one or many objects' numbers, separated by white spaces (objects' numbers start from 1)");
		language.put(Labels.PAIRS_TOOLTIP, "Comma-separated list of compared pairs; each entry consists of a pair of examples (comma-separated, in {}) and S or Sc flag indicating the result of comparison (e.g., {1, 2} S, {2, 4} Sc)");
		
		language.put(Labels.TYPE_OF_FAMILY_CRITERIA_TOOLTIP, "Considered type of family of criteria");
		language.put(Labels.TYPE_OF_RULES_TOOLTIP, "Possible rules may only be used for such value of consistency measure threshold, which for used consistency measure implies classical DRSA");
		language.put(Labels.CONSIDERED_SET_OF_RULES_TOOLTIP, "Used set of decision rules - either minimal (rules induced by VC-DomLEM algorithm) or exhaustive (virtual set of robust all rules)");
		language.put(Labels.CONSISTENCY_MEASURE_TOOLTIP, "Used consistency measure");
		language.put(Labels.CONSISTENCY_MEASURE_THRESHOLD_TOOLTIP, "Floating-point value from interval [0,1] for measures: epsilon, epsilon*, and rough membership, or from interval [0,X] for measure epsilon', where X can be greater than 1");
		language.put(Labels.PRECISION_TOOLTIP, "Denotes precision of floating-point numbers; set -1 in order to print floating-point numbers as they are, without rounding");
		language.put(Labels.RANKING_PROCEDURE_TOOLTIP, "Ranking procedure is used for exploitation of the preference graph resulting from application of decision rules to all pairs of objects from the test data set");
		language.put(Labels.DOMINANCE_TOOLTIP, "Pareto or Lorenz dominance relation(for Lorenz dominance data must meet special requirements)");
		language.put(Labels.DOMINANCE_FOR_PAIRS_TOOLTIP, "Use classic or strict definition of dominance for ordinal criteria present in PCT?");
		language.put(Labels.SATISFACTION_DEGREES_TOOLTIP, "Use fuzzy (i.e., from interval [0,1]) or crisp (i.e., from set {0,1}) satisfaction degrees in preference graph?; fuzzy satisfaction degrees cannot be used for DRSA + exhaustive set of possible rules + rough membership");
		language.put(Labels.FUZZY_CALCULATION_METHOD_TOOLTIP, "Method of calculating fuzzy satisfaction degrees in preference graphs, if such satisfaction degrees are used; for given pair of objects, can be maximum of credibility over covering rules (for minimal or exhaustive set of rules) or maximum product of credibility and coverage factor over covering rules (for minimal set of rules only)");
		language.put(Labels.NEGATIVE_EXAMPLE_TREATMENT_TOOLTIP, "Strategy of covering negative examples by rules in case of VC-DRSA");
		language.put(Labels.OPTIMIZE_RULE_CONSISTENCY_TOOLTIP, "Set of pairs of objects with respect to which value of rule consistency measure optimized in VC-DomLEM algorithm is calculated; can be lower (or upper) approximation of the preference relation for which a certain (or possible, respectively) rule is generated or entire preference relation (for certain rules only)");
		language.put(Labels.RULE_CONDITIONS_SELECTION_METHOD_TOOLTIP, "Strategy of rule conditions selection in VC-DomLEM algorithm; mix conditions from different pairs of objects or take all rule conditions from the same base pair of objects?");
		
		language.put(Labels.ALLOW_EMPTY_RULES_TOOLTIP, "Allow VC-DomLEM algorithm to induce rules with empty condition part if their consistency is good enough");
		language.put(Labels.USE_EDGE_REGIONS_TOOLTIP, "Use pairs of objects from EDGE region only or all (sufficiently consistent) pairs of objects from considered lower/upper approximation when creating rule's conditions in VC-DomLEM algorithm?");
		language.put(Labels.WRITE_DOMINATION_INFO_TOOLTIP, "Insert sections [P-dominating sets] and [P-dominated sets] into *.apx file?");
		language.put(Labels.WRITE_RULES_STAT_TOOLTIP, "Insert rules' statistics into *.rules file?");
		language.put(Labels.WRITE_LEARNING_EXAMPLES_TOOLTIP, "List learning positive examples of each rule (write LearningPositiveExamples tag) inside [RULESINFO] section of *.rules file? (works only when writeRulesStatistics is true)");
		
		language.put(Labels.RESTORE_VALUES_BUTTON_TOOLTIP, "Restore original values from file");
		language.put(Labels.VALIDATE_FORM_DEFAULTS_TOOLTIP, "Replace empty options with defaults and validate result");
		
		language.put(Labels.PARAMETERS_TOOLTIP, "This panel contains all parameters related with used algorithms in jRank");
		language.put(Labels.WRITE_INFO_TOOLTIP, "This panel contains all options related with saving results to files");
		language.put(Labels.FILE_PANE_TOOLTIP, "This panel contains file names to with results will be saved. All options can be derived from 'Learning data file' field.");
	}
	
}
