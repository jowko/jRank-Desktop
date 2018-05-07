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
		
		createPolishPropertiesLabels(language);
		createPolishPropertiesTooltips(language);
		
		return language;
	}
	
	private void createPolishPropertiesLabels(Map<String, String> language) {
		//TODO translate this text
		language.put(Labels.PROP_FILES_PANEL, "Input and output files");
		language.put(Labels.LEARNING_FILE, "Learning data file:");
		language.put(Labels.TEST_FILE, "Test data file:");
		language.put(Labels.PCT_FILE, "PCT file:");
		language.put(Labels.PCT_APX_FILE, "PCT APX file:");
		language.put(Labels.PCT_RULES_FILE, "PCT rules file:");
		language.put(Labels.GRAPH_FILE, "Preference graph file:");
		language.put(Labels.RANKING_FILE, "Ranking file:");
		
		language.put(Labels.PROP_DATA_PANEL, "Some text");
		language.put(Labels.REFERENCE_RANKING, "Reference Ranking:");
		language.put(Labels.PAIRS, "Pairs:");
		
		language.put(Labels.PROP_PARAMETERS_PANEL, "Parameters");
		language.put(Labels.TYPE_OF_FAMILY_CRITERIA, "Type of family criteria");
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
		
		language.put(Labels.ALLOW_EMPTY_RULES, "Allow empty rules in VCDomLEM");
		language.put(Labels.USE_EDGE_REGIONS, "Use edge regions in VCDomLEM");
		language.put(Labels.PROP_WRITE_PANEL, "Write:");
		language.put(Labels.WRITE_DOMINATION_INFO, "Domination information");
		language.put(Labels.WRITE_RULES_STAT, "Rule statistics");
		language.put(Labels.WRITE_LEARNING_EXAMPLES, "Learning positive examples");
		
		language.put(Labels.PROP_SAVE_BUTTON, "Zapisz");
		language.put(Labels.PROP_CANCEL_BUTTON, "Anuluj");
		language.put(Labels.SET_DEFAULTS_BUTTON, "Ustaw domyślne");
		language.put(Labels.PROP_CLEAR_BUTTON, "Wyczyść formularz");
		language.put(Labels.RESTORE_VALUES_BUTTON, "Przywróć początkowe wartości");
		language.put(Labels.PROP_VALIDATE_FORM, "Validate form");
		language.put(Labels.VALIDATE_FORM_DEFAULTS, "Validate form with defaults");
	}
	
	private void createPolishPropertiesTooltips(Map<String, String> language) {
		//TODO translate this text
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
		
		language.put(Labels.SET_DEFAULTS_BUTTON_TOOLTIP, "Zastąp wartości z formularza wartościami domyślnymi z pliku default.properties");
		language.put(Labels.RESTORE_VALUES_BUTTON_TOOLTIP, "Przywróć oryginalne wartości z pliku");
		language.put(Labels.VALIDATE_FORM_DEFAULTS_TOOLTIP, "Zastąp niewybrane opcje wartościami domyślnymi i dokonaj walidacji");
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
		
		createEnglishPropertiesLabels(language);
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
		
		language.put(Labels.PROP_DATA_PANEL, "Some text");
		language.put(Labels.REFERENCE_RANKING, "Reference Ranking:");
		language.put(Labels.PAIRS, "Pairs:");
		
		language.put(Labels.PROP_PARAMETERS_PANEL, "Parameters");
		language.put(Labels.TYPE_OF_FAMILY_CRITERIA, "Type of family criteria");
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
		
		language.put(Labels.ALLOW_EMPTY_RULES, "Allow empty rules in VCDomLEM");
		language.put(Labels.USE_EDGE_REGIONS, "Use edge regions in VCDomLEM");
		language.put(Labels.PROP_WRITE_PANEL, "Write:");
		language.put(Labels.WRITE_DOMINATION_INFO, "Domination information");
		language.put(Labels.WRITE_RULES_STAT, "Rule statistics");
		language.put(Labels.WRITE_LEARNING_EXAMPLES, "Learning positive examples");
		
		language.put(Labels.PROP_SAVE_BUTTON, "Save");
		language.put(Labels.PROP_CANCEL_BUTTON, "Cancel");
		language.put(Labels.SET_DEFAULTS_BUTTON, "Set defaults");
		language.put(Labels.PROP_CLEAR_BUTTON, "Clear form");
		language.put(Labels.RESTORE_VALUES_BUTTON, "Restore original values");
		language.put(Labels.PROP_VALIDATE_FORM, "Validate form");
		language.put(Labels.VALIDATE_FORM_DEFAULTS, "Validate form with defaults");
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
		
		language.put(Labels.SET_DEFAULTS_BUTTON_TOOLTIP, "Replace form values with default values from default.properties file");
		language.put(Labels.RESTORE_VALUES_BUTTON_TOOLTIP, "Restore original values from file");
		language.put(Labels.VALIDATE_FORM_DEFAULTS_TOOLTIP, "Replace empty options with defaults and validate result");
	}
	
}
