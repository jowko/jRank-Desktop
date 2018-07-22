package pl.jowko.rulerank.desktop.feature.internationalization;

/**
 * Created by Piotr on 2018-03-16.
 * This class contains constants values for internationalization.
 * They are used in application to display labels using LanguageService
 * This codes are saved in labels.json file and later read into map of labels.
 * Config data are saved in file-generator project.
 * @see LanguageService
 */
public class Labels {
	
	// General
	public static final String APP_TITLE = "APP_TITLE";
	public static final String US_SETTINGS_ERROR = "US_SETTINGS_ERROR";
	
	// menu items
	public static final String MENU_FILE = "MENU_FILE";
	public static final String MENU_QUIT = "MENU_QUIT";
	public static final String MENU_SETTINGS = "MENU_SETTINGS";
	public static final String MENU_USER_SETTINGS = "MENU_USER_SETTINGS";
	public static final String MENU_HELP = "MENU_HELP";
	public static final String MENU_ITEM_HELP = "MENU_ITEM_HELP";
	public static final String MENU_ABOUT = "MENU_ABOUT";
	
	
	// Toolbar
	public static final String TOOL_REFRESH_BUTTON = "TOOL_REFRESH_BUTTON";
	public static final String TOOL_CREATE_BUTTON = "TOOL_CREATE_BUTTON";
	public static final String TOOL_CREATE_DIALOG_TITLE = "TOOL_CREATE_DIALOG_TITLE";
	public static final String TOOL_CREATE_OVERRIDE_CONFIRM = "TOOL_CREATE_OVERRIDE_CONFIRM";
	
	
	// Workspace
	public static final String WORK_MENU_DELETE = "WORK_MENU_DELETE";
	public static final String WORK_MENU_DEL_CONFIRM_DIR = "WORK_MENU_DEL_CONFIRM_DIR";
	public static final String WORK_MENU_DEL_CONFIRM_FILE = "WORK_MENU_DEL_CONFIRM_FILE";
	public static final String WORK_MENU_COPY = "WORK_MENU_COPY";
	public static final String WORK_MENU_PASTE = "WORK_MENU_PASTE";
	public static final String WORK_MENU_CUT = "WORK_MENU_CUT";
	public static final String WORK_MENU_ADD_PROPERTIES = "WORK_MENU_ADD_PROPERTIES";
	public static final String WORK_MENU_ADD_PROPERTIES_PROMPT = "WORK_MENU_ADD_PROPERTIES_PROMPT";
	public static final String WORK_MENU_ADD_ISF = "WORK_MENU_ADD_ISF";
	public static final String WORK_MENU_ADD_ISF_PROMPT = "WORK_MENU_ADD_ISF_PROMPT";
	public static final String WORK_MENU_TEXT_DIALOG_TITLE = "WORK_MENU_TEXT_DIALOG_TITLE";
	public static final String WORK_MENU_TEXT_DIALOG_TEXT = "WORK_MENU_TEXT_DIALOG_TEXT";
	public static final String WORK_MENU_RENAME = "WORK_MENU_RENAME";
	public static final String WORK_MENU_RENAME_PROMPT = "WORK_MENU_RENAME_PROMPT";
	
	
	// General buttons
	public static final String BUTTON_SAVE = "BUTTON_SAVE";
	public static final String BUTTON_CANCEL = "BUTTON_CANCEL";
	
	// Dialogs
	public static final String ERROR_DIALOG_TITLE = "ERROR_DIALOG_TITLE";
	public static final String VALIDATION_DIALOG_TITLE = "VALIDATION_DIALOG_TITLE";
	public static final String DIALOG_ACTION_FAIL = "DIALOG_ACTION_FAIL";
	public static final String US_ERROR_DIALOG_HEADER = "US_ERROR_DIALOG_HEADER";
	public static final String VALIDATION_DIALOG_HEADER = "VALIDATION_DIALOG_HEADER";
	public static final String CONFIRM_DIALOG_TITLE = "CONFIRM_DIALOG_TITLE";
	public static final String CONFIRM_DIALOG_YES = "CONFIRM_DIALOG_YES";
	public static final String CONFIRM_DIALOG_NO = "CONFIRM_DIALOG_NO";
	
	// User Settings
	public static final String US_TITLE = "US_TITLE";
	public static final String LANGUAGE = "LANGUAGE";
	public static final String WORKSPACE = "WORKSPACE";
	public static final String CSV_SEPARATOR = "CSV_SEPARATOR";
	public static final String TOOLTIPS_ENABLED = "TOOLTIPS_ENABLED";
	public static final String ADVANCED_PROPERTIES_ENABLED = "ADVANCED_PROPERTIES_ENABLED";
	public static final String ADVANCED_PROPERTIES_ENABLED_TOOLTIP = "ADVANCED_PROPERTIES_ENABLED_TOOLTIP";
	public static final String MANUAL_EDITION_ENABLED = "MANUAL_EDITION_ENABLED";
	public static final String MANUAL_EDITION_ENABLED_TOOLTIP = "MANUAL_EDITION_ENABLED_TOOLTIP";
	public static final String START_MAXIMIZED = "START_MAXIMIZED";
	public static final String US_INFO = "US_INFO";
	public static final String LANGUAGE_ERROR = "LANGUAGE_ERROR";
	public static final String WORKSPACE_ERROR = "WORKSPACE_ERROR";
	
	// About dialog
	public static final String ABOUT_TITLE = "ABOUT_TITLE";
	public static final String ABOUT_APP_INFO = "ABOUT_APP_INFO";
	public static final String ABOUT_AUTHOR = "ABOUT_AUTHOR";
	public static final String ABOUT_VERSION = "ABOUT_VERSION";
	public static final String ABOUT_RELEASE_DATE = "ABOUT_RELEASE_DATE";
	public static final String ABOUT_BOTTOM_INFO = "ABOUT_BOTTOM_INFO";
	
	
	// tabs
	public static final String TABS_CLOSE_THIS = "TABS_CLOSE_THIS";
	public static final String TABS_CLOSE_THIS_CONFIRM = "TABS_CLOSE_THIS_CONFIRM";
	public static final String TABS_CLOSE_ALL = "TABS_CLOSE_ALL";
	public static final String TABS_CLOSE_ALL_CONFIRM = "TABS_CLOSE_ALL_CONFIRM";
	public static final String TABS_CLOSE_OTHERS = "TABS_CLOSE_OTHERS";
	public static final String TABS_CLOSE_OTHERS_CONFIRM = "TABS_CLOSE_OTHERS_CONFIRM";
	
	
	// Lower tabs
	public static final String LOGS_TAB = "LOGS_TAB";
	
	// Properties form field labels
	public static final String PROP_FILES_PANEL = "PROP_FILES_PANEL";
	public static final String LEARNING_FILE = "LEARNING_FILE";
	public static final String TEST_FILE = "TEST_FILE";
	public static final String PCT_FILE = "PCT_FILE";
	public static final String PCT_APX_FILE = "PCT_APX_FILE";
	public static final String PCT_RULES_FILE = "PCT_RULES_FILE";
	public static final String GRAPH_FILE = "GRAPH_FILE";
	public static final String RANKING_FILE = "RANKING_FILE";
	
	public static final String PROP_DATA_PANEL = "PROP_DATA_PANEL";
	public static final String REFERENCE_RANKING = "REFERENCE_RANKING";
	public static final String EDIT_RANKING_BUTTON = "EDIT_RANKING_BUTTON";
	public static final String PAIRS = "PAIRS";
	public static final String EDIT_PAIRS_BUTTON = "EDIT_PAIRS_BUTTON";
	
	public static final String PROP_PARAMETERS_PANEL = "PROP_PARAMETERS_PANEL";
	public static final String TYPE_OF_FAMILY_CRITERIA = "TYPE_OF_FAMILY_CRITERIA";
	public static final String TYPE_OF_RULES = "TYPE_OF_RULES";
	public static final String CONSIDERED_SET_OF_RULES = "CONSIDERED_SET_OF_RULES";
	public static final String CONSISTENCY_MEASURE = "CONSISTENCY_MEASURE";
	public static final String CONSISTENCY_MEASURE_THRESHOLD = "CONSISTENCY_MEASURE_THRESHOLD";
	public static final String PRECISION = "PRECISION";
	public static final String RANKING_PROCEDURE = "RANKING_PROCEDURE";
	public static final String DOMINANCE = "DOMINANCE";
	public static final String DOMINANCE_FOR_PAIRS = "DOMINANCE_FOR_PAIRS";
	public static final String SATISFACTION_DEGREES = "SATISFACTION_DEGREES";
	public static final String FUZZY_CALCULATION_METHOD = "FUZZY_CALCULATION_METHOD";
	public static final String NEGATIVE_EXAMPLE_TREATMENT = "NEGATIVE_EXAMPLE_TREATMENT";
	public static final String OPTIMIZE_RULE_CONSISTENCY = "OPTIMIZE_RULE_CONSISTENCY";
	public static final String RULE_CONDITIONS_SELECTION_METHOD = "RULE_CONDITIONS_SELECTION_METHOD";
	
	public static final String ALLOW_EMPTY_RULES = "ALLOW_EMPTY_RULES";
	public static final String USE_EDGE_REGIONS = "USE_EDGE_REGIONS";
	public static final String PROP_WRITE_PANEL = "PROP_WRITE_PANEL";
	public static final String WRITE_DOMINATION_INFO = "WRITE_DOMINATION_INFO";
	public static final String WRITE_RULES_STAT = "WRITE_RULES_STAT";
	public static final String WRITE_LEARNING_EXAMPLES = "WRITE_LEARNING_EXAMPLES";
	
	public static final String PROP_SAVE_BUTTON = "PROP_SAVE_BUTTON";
	public static final String PROP_CANCEL_BUTTON = "PROP_CANCEL_BUTTON";
	public static final String PROP_CLEAR_BUTTON = "PROP_CLEAR_BUTTON";
	public static final String RESTORE_VALUES_BUTTON = "RESTORE_VALUES_BUTTON";
	public static final String PROP_VALIDATE_FORM = "PROP_VALIDATE_FORM";
	public static final String VALIDATE_FORM_DEFAULTS = "VALIDATE_FORM_DEFAULTS";
	
	// Properties form messages
	public static final String PROP_ERROR_SAVE = "PROP_ERROR_SAVE";
	public static final String PROP_VALIDATE_DIALOG_TITLE = "PROP_VALIDATE_DIALOG_TITLE";
	public static final String PROP_VALIDATE_DIALOG_CONTENT = "PROP_VALIDATE_DIALOG_CONTENT";
	public static final String PROP_VD_DIALOG_TITLE = "PROP_VD_DIALOG_TITLE";
	public static final String PROP_VD_DIALOG_CONTENT = "PROP_VD_DIALOG_CONTENT";
	public static final String PROP_SAVE_ERROR_CONFIRM = "PROP_SAVE_ERROR_CONFIRM";
	public static final String PROP_ABANDON_CHANGES = "PROP_ABANDON_CHANGES";
	
	// Properties validation messages
	public static final String LEARNING_DATA_FILE_EMPTY = "LEARNING_DATA_FILE_EMPTY";
	public static final String CONSISTENCY_MEASURE_LESS_THAN_ZERO = "CONSISTENCY_MEASURE_LESS_THAN_ZERO";
	public static final String FUZZY_EXHAUSTIVE_SET = "FUZZY_EXHAUSTIVE_SET";
	public static final String INCORRECT_CONSISTENCY_MEASURE = "INCORRECT_CONSISTENCY_MEASURE";
	public static final String INCORRECT_CERTAIN_RULES = "INCORRECT_CERTAIN_RULES";
	public static final String EXHAUSTIVE_SET_CERTAIN_RULES = "EXHAUSTIVE_SET_CERTAIN_RULES";
	public static final String FUZZY_EXHAUSTIVE_POSSIBLE_ROUGH_MEMBERSHIP = "FUZZY_EXHAUSTIVE_POSSIBLE_ROUGH_MEMBERSHIP";
	public static final String OPTIMIZE_POSSIBLE_RULES = "OPTIMIZE_POSSIBLE_RULES";
	
	public static final String PROP_EXT_LEARNING = "PROP_EXT_LEARNING";
	public static final String PROP_EXT_TEST = "PROP_EXT_TEST";
	public static final String PROP_EXT_PCT_ISF = "PROP_EXT_PCT_ISF";
	public static final String PROP_EXT_APX = "PROP_EXT_APX";
	public static final String PROP_EXT_RULES = "PROP_EXT_RULES";
	public static final String PROP_EXT_GRAPH = "PROP_EXT_GRAPH";
	public static final String PROP_EXT_RANKING = "PROP_EXT_RANKING";
	
	public static final String PROP_EMPTY_PAIRS_RANKING = "PROP_EMPTY_PAIRS_RANKING";
	public static final String PROP_EMPTY_CONSISTENCY_THRESHOLD = "PROP_EMPTY_CONSISTENCY_THRESHOLD";
	public static final String PROP_EMPTY_CONSISTENCY = "PROP_EMPTY_CONSISTENCY";
	public static final String PROP_EMPTY_PRECISION = "PROP_EMPTY_PRECISION";
	public static final String PROP_EMPTY_TYPE_CRITERIA = "PROP_EMPTY_TYPE_CRITERIA";
	public static final String PROP_EMPTY_TYPE_RULES = "PROP_EMPTY_TYPE_RULES";
	public static final String PROP_EMPTY_CONSIDERED_RULES = "PROP_EMPTY_CONSIDERED_RULES";
	public static final String PROP_EMPTY_SAT_DEGREES = "PROP_EMPTY_SAT_DEGREES";
	public static final String PROP_EMPTY_FUZZY_METHOD = "PROP_EMPTY_FUZZY_METHOD";
	public static final String PROP_EMPTY_RANKING_PROCEDURE = "PROP_EMPTY_RANKING_PROCEDURE";
	public static final String PROP_EMPTY_DOMINANCE = "PROP_EMPTY_DOMINANCE";
	public static final String PROP_EMPTY_DOMINANCE_PAIRS = "PROP_EMPTY_DOMINANCE_PAIRS";
	public static final String PROP_EMPTY_NEGATIVE_EXAMPLES = "PROP_EMPTY_NEGATIVE_EXAMPLES";
	public static final String PROP_EMPTY_RULE_CONDITIONS = "PROP_EMPTY_RULE_CONDITIONS";
	public static final String PROP_EMPTY_OPTIMIZE_RULES = "PROP_EMPTY_OPTIMIZE_RULES";
	
	// Properties parameters
	public static final String ANY_FAMILY_OF_CRITERIA = "ANY_FAMILY_OF_CRITERIA";
	public static final String CONSISTENT_FAMILY_OF_CRITERIA = "CONSISTENT_FAMILY_OF_CRITERIA";
	public static final String EPSILON = "EPSILON";
	public static final String EPSILON_STAR = "EPSILON_STAR";
	public static final String EPSILON_PRIM = "EPSILON_PRIM";
	public static final String ROUGH_MEMBERSHIP = "ROUGH_MEMBERSHIP";
	public static final String RULE_CERTAIN = "RULE_CERTAIN";
	public static final String RULE_POSSIBLE = "RULE_POSSIBLE";
	public static final String MINIMAL_SET_OF_RULES = "MINIMAL_SET_OF_RULES";
	public static final String EXHAUSTIVE_SET_OF_RULES = "EXHAUSTIVE_SET_OF_RULES";
	public static final String SATISFACTION_FUZZY = "SATISFACTION_FUZZY";
	public static final String SATISFACTION_CRISP = "SATISFACTION_CRISP";
	public static final String MAX_CREDIBILITY = "MAX_CREDIBILITY";
	public static final String MAX_CREDIBILITY_X_FACTOR = "MAX_CREDIBILITY_X_FACTOR";
	public static final String NFS = "NFS";
	public static final String REPEATED_NFS = "REPEATED_NFS";
	public static final String NFS_CLOSURE = "NFS_CLOSURE";
	public static final String REPEATED_NFS_CLOSURE = "REPEATED_NFS_CLOSURE";
	public static final String NFS_P_CLOSURE = "NFS_P_CLOSURE";
	public static final String REPEATED_NFS_P_CLOSURE = "REPEATED_NFS_P_CLOSURE";
	public static final String PARETO = "PARETO";
	public static final String LORENZ = "LORENZ";
	public static final String STRICT_ORDINAL_DOMINANCE_CHECK_METHOD = "STRICT_ORDINAL_DOMINANCE_CHECK_METHOD";
	public static final String CLASSIC_ORDINAL_DOMINANCE_CHECK_METHOD = "CLASSIC_ORDINAL_DOMINANCE_CHECK_METHOD";
	public static final String ONLY_INCONSISTENT_NEGATIVE_EXAMPLES = "ONLY_INCONSISTENT_NEGATIVE_EXAMPLES";
	public static final String ONLY_INCONSISTENT_AND_BOUNDARY_NEGATIVE_EXAMPLES = "ONLY_INCONSISTENT_AND_BOUNDARY_NEGATIVE_EXAMPLES";
	public static final String ANY_NEGATIVE_EXAMPLES = "ANY_NEGATIVE_EXAMPLES";
	public static final String CHOOSE_CONDITIONS_FROM_ONE_OBJECT = "CHOOSE_CONDITIONS_FROM_ONE_OBJECT";
	public static final String MIX_CONDITIONS_FROM_DIFFERENT_OBJECTS = "MIX_CONDITIONS_FROM_DIFFERENT_OBJECTS";
	public static final String CONSISTENCY_APPROXIMATION = "CONSISTENCY_APPROXIMATION";
	public static final String CONSISTENCY_SET = "CONSISTENCY_SET";
	public static final String BOOLEAN_YES = "BOOLEAN_YES";
	public static final String BOOLEAN_NO = "BOOLEAN_NO";
	
	// Properties form tooltips
	public static final String LEARNING_FILE_TOOLTIP = "LEARNING_FILE_TOOLTIP";
	public static final String TEST_FILE_TOOLTIP = "TEST_FILE_TOOLTIP";
	public static final String PCT_FILE_TOOLTIP = "PCT_FILE_TOOLTIP";
	public static final String PCT_APX_FILE_TOOLTIP = "PCT_APX_FILE_TOOLTIP";
	public static final String PCT_RULES_FILE_TOOLTIP = "PCT_RULES_FILE_TOOLTIP";
	public static final String GRAPH_FILE_TOOLTIP = "GRAPH_FILE_TOOLTIP";
	public static final String RANKING_FILE_TOOLTIP = "RANKING_FILE_TOOLTIP";
	
	public static final String REFERENCE_RANKING_TOOLTIP = "REFERENCE_RANKING_TOOLTIP";
	public static final String PAIRS_TOOLTIP = "PAIRS_TOOLTIP";
	
	public static final String TYPE_OF_FAMILY_CRITERIA_TOOLTIP = "TYPE_OF_FAMILY_CRITERIA_TOOLTIP";
	public static final String TYPE_OF_RULES_TOOLTIP = "TYPE_OF_RULES_TOOLTIP";
	public static final String CONSIDERED_SET_OF_RULES_TOOLTIP = "CONSIDERED_SET_OF_RULES_TOOLTIP";
	public static final String CONSISTENCY_MEASURE_TOOLTIP = "CONSISTENCY_MEASURE_TOOLTIP";
	public static final String CONSISTENCY_MEASURE_THRESHOLD_TOOLTIP = "CONSISTENCY_MEASURE_THRESHOLD_TOOLTIP";
	public static final String PRECISION_TOOLTIP = "PRECISION_TOOLTIP";
	public static final String RANKING_PROCEDURE_TOOLTIP = "RANKING_PROCEDURE_TOOLTIP";
	public static final String DOMINANCE_TOOLTIP = "DOMINANCE_TOOLTIP";
	public static final String DOMINANCE_FOR_PAIRS_TOOLTIP = "DOMINANCE_FOR_PAIRS_TOOLTIP";
	public static final String SATISFACTION_DEGREES_TOOLTIP = "SATISFACTION_DEGREES_TOOLTIP";
	public static final String FUZZY_CALCULATION_METHOD_TOOLTIP = "FUZZY_CALCULATION_METHOD_TOOLTIP";
	public static final String NEGATIVE_EXAMPLE_TREATMENT_TOOLTIP = "NEGATIVE_EXAMPLE_TREATMENT_TOOLTIP";
	public static final String OPTIMIZE_RULE_CONSISTENCY_TOOLTIP = "OPTIMIZE_RULE_CONSISTENCY_TOOLTIP";
	public static final String RULE_CONDITIONS_SELECTION_METHOD_TOOLTIP = "RULE_CONDITIONS_SELECTION_METHOD_TOOLTIP";
	
	public static final String ALLOW_EMPTY_RULES_TOOLTIP = "ALLOW_EMPTY_RULES_TOOLTIP";
	public static final String USE_EDGE_REGIONS_TOOLTIP = "USE_EDGE_REGIONS_TOOLTIP";
	public static final String WRITE_DOMINATION_INFO_TOOLTIP = "WRITE_DOMINATION_INFO_TOOLTIP";
	public static final String WRITE_RULES_STAT_TOOLTIP = "WRITE_RULES_STAT_TOOLTIP";
	public static final String WRITE_LEARNING_EXAMPLES_TOOLTIP = "WRITE_LEARNING_EXAMPLES_TOOLTIP";
	
	public static final String RESTORE_VALUES_BUTTON_TOOLTIP = "RESTORE_VALUES_BUTTON_TOOLTIP";
	public static final String VALIDATE_FORM_DEFAULTS_TOOLTIP = "VALIDATE_FORM_DEFAULTS_TOOLTIP";
	
	public static final String PARAMETERS_TOOLTIP = "PARAMETERS_TOOLTIP";
	public static final String WRITE_INFO_TOOLTIP = "WRITE_INFO_TOOLTIP";
	public static final String FILE_PANE_TOOLTIP = "FILE_PANE_TOOLTIP";
	
	
	// Properties dialog labels
	public static final String PROP_RANKING_TITLE = "PROP_RANKING_TITLE";
	public static final String PROP_PAIRS_TITLE = "PROP_PAIRS_TITLE";
	public static final String PROP_INFO_ID = "PROP_INFO_ID";
	public static final String PROP_INFO_S = "PROP_INFO_S";
	public static final String PROP_INFO_SC = "PROP_INFO_SC";
	public static final String PROP_INFO_PAIRS_PARSE_EXCEPTION = "PROP_INFO_PAIRS_PARSE_EXCEPTION";
	
	public static final String PROP_INFO_LABEL_LABEL = "PROP_INFO_LABEL_LABEL";
	public static final String PROP_INFO_ADD_S_BUTTON = "PROP_INFO_ADD_S_BUTTON";
	public static final String PROP_INFO_ADD_SC_BUTTON = "PROP_INFO_ADD_SC_BUTTON";
	public static final String PROP_INFO_REMOVE_BUTTON = "PROP_INFO_REMOVE_BUTTON";
	public static final String PROP_INFO_SAVE_BUTTON = "PROP_INFO_SAVE_BUTTON";
	public static final String PROP_INFO_CANCEL_BUTTON = "PROP_INFO_CANCEL_BUTTON";
	
	public static final String PROP_INFO_RANKING = "PROP_INFO_RANKING";
	public static final String PROP_INFO_RANK = "PROP_INFO_RANK";
	public static final String PROP_INFO_REMOVE = "PROP_INFO_REMOVE";
	public static final String PROP_INFO_ADD_BEGIN = "PROP_INFO_ADD_BEGIN";
	public static final String PROP_INFO_ADD_END = "PROP_INFO_ADD_END";
	public static final String PROP_INFO_RANKING_PARSE_EXCEPTION = "PROP_INFO_RANKING_PARSE_EXCEPTION";
	public static final String PROP_INFO_DISPLAY_LABEL = "PROP_INFO_DISPLAY_LABEL";
	public static final String PROP_INFO_R_SAVE_BUTTON = "PROP_INFO_R_SAVE_BUTTON";
	public static final String PROP_INFO_R_CANCEL_BUTTON = "PROP_INFO_R_CANCEL_BUTTON";
	
	
	// Experiment running
	public static final String RUN_PROP_READ_ERROR = "RUN_PROP_READ_ERROR";
	public static final String RUN_SOURCE_TITLE = "RUN_SOURCE_TITLE";
	public static final String RUN_SOURCE_CONTENT = "RUN_SOURCE_CONTENT";
	public static final String RUN_SOURCE_RANKING = "RUN_SOURCE_RANKING";
	public static final String RUN_SOURCE_PAIRS = "RUN_SOURCE_PAIRS";
	public static final String RUN_SOURCE_DECISION_ATTRIBUTE = "RUN_SOURCE_DECISION_ATTRIBUTE";
	public static final String RUN_LEARNING_FILE = "RUN_LEARNING_FILE";
	public static final String RUN_TEST_FILE = "RUN_TEST_FILE";
	public static final String RUN_OVERRIDE_FILES = "RUN_OVERRIDE_FILES";
	public static final String RUN_FILE_IS_EMPTY = "RUN_FILE_IS_EMPTY";
	public static final String RUN_ISF_ERRORS = "RUN_ISF_ERRORS";
	public static final String RUN_TEXT_PARSE_ERROR = "RUN_TEXT_PARSE_ERROR";
	
	
	// Learning table attribute dialog
	public static final String ATT_DIALOG_NAME = "ATT_DIALOG_NAME";
	public static final String ATT_DIALOG_ACTIVE = "ATT_DIALOG_ACTIVE";
	public static final String ATT_DIALOG_TYPE = "ATT_DIALOG_TYPE";
	public static final String ATT_DIALOG_KIND = "ATT_DIALOG_KIND";
	public static final String ATT_DIALOG_PREFERENCE = "ATT_DIALOG_PREFERENCE";
	public static final String ATT_DIALOG_ENUMS = "ATT_DIALOG_ENUMS";
	public static final String ATT_DIALOG_SAVE_BUTTON = "ATT_DIALOG_SAVE_BUTTON";
	public static final String ATT_DIALOG_CANCEL_BUTTON = "ATT_DIALOG_CANCEL_BUTTON";
	public static final String ATT_DIALOG_CLEAR_BUTTON = "ATT_DIALOG_CLEAR_BUTTON";
	
	public static final String ATT_DIALOG_CONDITION = "ATT_DIALOG_CONDITION";
	public static final String ATT_DIALOG_DECISION = "ATT_DIALOG_DECISION";
	public static final String ATT_DIALOG_DESCRIPTION = "ATT_DIALOG_DESCRIPTION";
	public static final String ATT_DIALOG_NONE = "ATT_DIALOG_NONE";
	public static final String ATT_DIALOG_GAIN = "ATT_DIALOG_GAIN";
	public static final String ATT_DIALOG_COST = "ATT_DIALOG_COST";
	
	public static final String ATT_DIALOG_CUSTOMIZE_TITLE = "ATT_DIALOG_CUSTOMIZE_TITLE";
	public static final String ATT_DIALOG_EDIT_TITLE = "ATT_DIALOG_EDIT_TITLE";
	public static final String ATT_DIALOG_NAME_EMPTY = "ATT_DIALOG_NAME_EMPTY";
	public static final String ATT_DIALOG_FIELD_EMPTY = "ATT_DIALOG_FIELD_EMPTY";
	public static final String ATT_DIALOG_PREFERENCE_EMPTY = "ATT_DIALOG_PREFERENCE_EMPTY";
	public static final String ATT_DIALOG_ENUMS_EMPTY = "ATT_DIALOG_ENUMS_EMPTY";
	public static final String ATT_DIALOG_VALIDATION_FAIL = "ATT_DIALOG_VALIDATION_FAIL";
	public static final String ATT_DIALOG_ENUMS_TOOLTIP = "ATT_DIALOG_ENUMS_TOOLTIP";
	
	
	// Learning table
	public static final String LEARN_TABLE_NO_PREFERENCE = "LEARN_TABLE_NO_PREFERENCE";
	public static final String LEARN_TABLE_GAIN = "LEARN_TABLE_GAIN";
	public static final String LEARN_TABLE_COST = "LEARN_TABLE_COST";
	public static final String LEARN_TABLE_DECISION = "LEARN_TABLE_DECISION";
	public static final String LEARN_TABLE_DESCRIPTION = "LEARN_TABLE_DESCRIPTION";
	public static final String LEARN_TABLE_ACTIVE = "LEARN_TABLE_ACTIVE";
	public static final String LEARN_TABLE_INACTIVE = "LEARN_TABLE_INACTIVE";
	
	public static final String LEARN_TABLE_DECISION_VALIDATION = "LEARN_TABLE_DECISION_VALIDATION";
	public static final String LEARN_TABLE_ATTRIBUTE_NAMES_NOT_UNIQUE = "LEARN_TABLE_ATTRIBUTE_NAMES_NOT_UNIQUE";
	public static final String LEARN_TABLE_UNKNOWN_FIELDS = "LEARN_TABLE_UNKNOWN_FIELDS";
	public static final String LEARN_TABLE_NO_CONDITION_FIELDS = "LEARN_TABLE_NO_CONDITION_FIELDS";
	
	public static final String LEARN_TABLE_ADD_EXAMPLE = "LEARN_TABLE_ADD_EXAMPLE";
	public static final String LEARN_TABLE_ADD_EXAMPLE_FAIL = "LEARN_TABLE_ADD_EXAMPLE_FAIL";
	public static final String LEARN_TABLE_REMOVE_EXAMPLES = "LEARN_TABLE_REMOVE_EXAMPLES";
	public static final String LEARN_TABLE_REMOVE_EXAMPLES_FAIL = "LEARN_TABLE_REMOVE_EXAMPLES_FAIL";
	public static final String LEARN_TABLE_CUSTOMIZE_ATTRIBUTES = "LEARN_TABLE_CUSTOMIZE_ATTRIBUTES";
	public static final String LEARN_TABLE_CUSTOMIZE_ATTRIBUTES_FAIL = "LEARN_TABLE_CUSTOMIZE_ATTRIBUTES_FAIL";
	public static final String LEARN_TABLE_ADD_ATTRIBUTE = "LEARN_TABLE_ADD_ATTRIBUTE";
	public static final String LEARN_TABLE_COPY_ROWS = "LEARN_TABLE_COPY_ROWS";
	
	public static final String LEARN_TABLE_REMOVE_ALL_HEADER = "LEARN_TABLE_REMOVE_ALL_HEADER";
	public static final String LEARN_TABLE_ABANDON_CHANGES = "LEARN_TABLE_ABANDON_CHANGES";
	public static final String LEARN_TABLE_ERRORS = "LEARN_TABLE_ERRORS";
	public static final String LEARN_TABLE_SAVE_CONFIRM = "LEARN_TABLE_SAVE_CONFIRM";
	
	public static final String LEARN_TABLE_ID= "LEARN_TABLE_ID";
	public static final String LEARN_TABLE_EMPTY = "LEARN_TABLE_EMPTY";
	public static final String LEARN_TABLE_SELECT_ATTRIBUTE = "LEARN_TABLE_SELECT_ATTRIBUTE";
	public static final String LEARN_TABLE_REMOVE_ATTRIBUTE = "LEARN_TABLE_REMOVE_ATTRIBUTE";
	public static final String LEARN_TABLE_REMOVE_ATTRIBUTE_FAIL = "LEARN_TABLE_REMOVE_ATTRIBUTE_FAIL";
	public static final String LEARN_TABLE_REMOVE_ALL_EXAMPLES = "LEARN_TABLE_REMOVE_ALL_EXAMPLES";
	public static final String LEARN_TABLE_SAVE_BUTTON = "LEARN_TABLE_SAVE_BUTTON";
	public static final String LEARN_TABLE_CANCEL_BUTTON = "LEARN_TABLE_CANCEL_BUTTON";
	
	
	// PCT isf table
	public static final String PCT_ID = "PCT_ID";
	public static final String PCT_COPY_ROWS = "PCT_COPY_ROWS";
	public static final String PCT_S = "PCT_S";
	public static final String PCT_SC = "PCT_SC";
	
	// Rules
	public static final String RULES_NO_DATA = "RULES_NO_DATA";
	public static final String RULES_DECISION_PART = "RULES_DECISION_PART";
	public static final String RULES_CONDITION_PART = "RULES_CONDITION_PART";
	public static final String RULES_ID = "RULES_ID";
	public static final String RULES_DECISION_S = "RULES_DECISION_S";
	public static final String RULES_DECISION_SC = "RULES_DECISION_SC";
	public static final String RULES_CERTAIN = "RULES_CERTAIN";
	public static final String RULES_POSSIBLE = "RULES_POSSIBLE";
	public static final String RULES_APPROXIMATE = "RULES_APPROXIMATE";
	public static final String RULES_AT_LEAST = "RULES_AT_LEAST";
	public static final String RULES_AT_MOST = "RULES_AT_MOST";
	public static final String RULES_EQUAL = "RULES_EQUAL";
	public static final String RULES_COPY_ROWS = "RULES_COPY_ROWS";
	
	// Rules statistics
	public static final String STAT_TAB_HEADER = "STAT_TAB_HEADER";
	public static final String STAT_UNAVAILABLE = "STAT_UNAVAILABLE";
	public static final String STAT_RULE_TYPE = "STAT_RULE_TYPE";
	public static final String STAT_USAGE_TYPE = "STAT_USAGE_TYPE";
	public static final String STAT_CHARACTERISTIC_CLASS = "STAT_CHARACTERISTIC_CLASS";
	public static final String STAT_CHARACTERISTIC_S = "STAT_CHARACTERISTIC_S";
	public static final String STAT_CHARACTERISTIC_SC = "STAT_CHARACTERISTIC_SC";
	public static final String STAT_SUPPORT = "STAT_SUPPORT";
	public static final String STAT_SUPPORT_EXAMPLES = "STAT_SUPPORT_EXAMPLES";
	public static final String STAT_STRENGTH = "STAT_STRENGTH";
	public static final String STAT_CONFIDENCE = "STAT_CONFIDENCE";
	public static final String STAT_COVERAGE_FACTOR = "STAT_COVERAGE_FACTOR";
	public static final String STAT_COVERAGE = "STAT_COVERAGE";
	public static final String STAT_COVERED_EXAMPLES = "STAT_COVERED_EXAMPLES";
	public static final String STAT_NEGATIVE_COVERAGE = "STAT_NEGATIVE_COVERAGE";
	public static final String STAT_NEGATIVE_EXAMPLES = "STAT_NEGATIVE_EXAMPLES";
	public static final String STAT_INCONSISTENCY_MEASURE = "STAT_INCONSISTENCY_MEASURE";
	public static final String STAT_F_MEASURE = "STAT_F_MEASURE";
	public static final String STAT_A_MEASURE = "STAT_A_MEASURE";
	public static final String STAT_Z_MEASURE = "STAT_Z_MEASURE";
	public static final String STAT_L_MEASURE = "STAT_L_MEASURE";
	public static final String STAT_DOUBLE_INFINITY = "STAT_DOUBLE_INFINITY";
	
	
	// Ranking
	public static final String RANKING_POSITION = "RANKING_POSITION";
	public static final String RANKING_EVALUATION = "RANKING_EVALUATION";
	public static final String RANKING_UNKNOWN_FIELDS = "RANKING_UNKNOWN_FIELDS";
	public static final String RANKING_COPY_ROWS = "RANKING_COPY_ROWS";
	
	
	// Text file tab
	public static final String TXT_TAB_SAVE_BUTTON = "TXT_TAB_SAVE_BUTTON";
	public static final String TXT_TAB_CANCEL_BUTTON = "TXT_TAB_CANCEL_BUTTON";
	
	// Graph file tab
	public static final String ARCS_OUTGOING = "ARCS_OUTGOING";
	public static final String ARCS_OUTGOING_S = "ARCS_OUTGOING_S";
	public static final String ARCS_OUTGOING_SC = "ARCS_OUTGOING_SC";
	public static final String ARCS_INGOING = "ARCS_INGOING";
	public static final String ARCS_INGOING_S = "ARCS_INGOING_S";
	public static final String ARCS_INGOING_SC = "ARCS_INGOING_SC";
	public static final String ARCS_NONE = "ARCS_NONE";
	public static final String ARCS_TAB_TITLE = "ARCS_TAB_TITLE";
	
	private Labels() {}
	
}
