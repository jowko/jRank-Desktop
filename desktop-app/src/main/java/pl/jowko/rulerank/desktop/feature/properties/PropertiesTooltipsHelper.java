package pl.jowko.rulerank.desktop.feature.properties;

import javafx.scene.control.Tooltip;
import javafx.util.Duration;
import pl.jowko.rulerank.desktop.feature.internationalization.Labels;
import pl.jowko.rulerank.desktop.feature.internationalization.LanguageService;
import pl.jowko.rulerank.desktop.feature.settings.UserSettingsService;

import static pl.jowko.rulerank.desktop.utils.BooleanUtils.not;

/**
 * Created by Piotr on 2018-05-07.
 * This class creates tooltips for properties form and its fields.
 * If tooltips are disabled in user settings, they will not be added to form.
 * @see PropertiesController
 */
class PropertiesTooltipsHelper {
	
	private PropertiesController controller;
	private LanguageService labels;
	
	private Duration showDuration;
	private Duration delayDuration;
	
	/**
	 * Create instance of this class and initialize variables.
	 * @param controller from properties form
	 */
	PropertiesTooltipsHelper(PropertiesController controller) {
		labels = LanguageService.getInstance();
		this.controller = controller;
		showDuration = new Duration(10_000);
		delayDuration = new Duration(1500);
	}
	
	/**
	 * Add tooltips for fields in properties form.
	 * If user disabled showing tooltips in user settings, tooltips will not be created.
	 */
	void initializeTooltips() {
		boolean isTooltipsEnabled = UserSettingsService.getInstance().getUserSettings().isTooltipsEnabled();
		if(not(isTooltipsEnabled))
			return;
		
		initializeTooltipsForFilesFields();
		initializeTooltipsForRanking();
		initializeTooltipsForParameters();
		initializeTooltipsForBooleanParameters();
		initializeTooltipsForButtons();
		initializeTooltipsForPanes();
	}
	
	private void initializeTooltipsForFilesFields() {
		controller.learningDataFile.setTooltip(getTooltip(Labels.LEARNING_FILE_TOOLTIP));
		controller.testDataFile.setTooltip(getTooltip(Labels.TEST_FILE_TOOLTIP));
		controller.pctFile.setTooltip(getTooltip(Labels.PCT_FILE_TOOLTIP));
		controller.pctApxFile.setTooltip(getTooltip(Labels.PCT_APX_FILE_TOOLTIP));
		controller.pctRulesFile.setTooltip(getTooltip(Labels.PCT_RULES_FILE_TOOLTIP));
		controller.graphFile.setTooltip(getTooltip(Labels.GRAPH_FILE_TOOLTIP));
		controller.rankingFile.setTooltip(getTooltip(Labels.RANKING_FILE_TOOLTIP));
	}
	
	private void initializeTooltipsForRanking() {
		controller.referenceRanking.setTooltip(getTooltip(Labels.REFERENCE_RANKING_TOOLTIP));
		controller.pairs.setTooltip(getTooltip(Labels.PAIRS_TOOLTIP));
	}
	
	private void initializeTooltipsForParameters() {
		controller.typeOfFamilyCriteria.setTooltip(getTooltip(Labels.TYPE_OF_FAMILY_CRITERIA_TOOLTIP));
		controller.typeOfRules.setTooltip(getTooltip(Labels.TYPE_OF_RULES_TOOLTIP));
		controller.consideredSetOfRules.setTooltip(getTooltip(Labels.CONSIDERED_SET_OF_RULES_TOOLTIP));
		
		controller.consistencyMeasure.setTooltip(getTooltip(Labels.CONSISTENCY_MEASURE_TOOLTIP));
		controller.consistencyMeasureThreshold.setTooltip(getTooltip(Labels.CONSISTENCY_MEASURE_THRESHOLD_TOOLTIP));
		controller.precision.setTooltip(getTooltip(Labels.PRECISION_TOOLTIP));
		
		controller.rankingProcedure.setTooltip(getTooltip(Labels.RANKING_PROCEDURE_TOOLTIP));
		controller.dominance.setTooltip(getTooltip(Labels.DOMINANCE_TOOLTIP));
		controller.dominanceForPairs.setTooltip(getTooltip(Labels.DOMINANCE_FOR_PAIRS_TOOLTIP));
		
		controller.satisfactionDegreesInGraph.setTooltip(getTooltip(Labels.SATISFACTION_DEGREES_TOOLTIP));
		controller.fuzzyCalculationMethod.setTooltip(getTooltip(Labels.FUZZY_CALCULATION_METHOD_TOOLTIP));
		
		controller.negativeExamplesTreatment.setTooltip(getTooltip(Labels.NEGATIVE_EXAMPLE_TREATMENT_TOOLTIP));
		controller.optimizeRuleConsistency.setTooltip(getTooltip(Labels.OPTIMIZE_RULE_CONSISTENCY_TOOLTIP));
		controller.ruleConditionsSelectionMethod.setTooltip(getTooltip(Labels.RULE_CONDITIONS_SELECTION_METHOD_TOOLTIP));
	}
	
	private void initializeTooltipsForBooleanParameters() {
		controller.allowEmptyRules.setTooltip(getTooltip(Labels.ALLOW_EMPTY_RULES_TOOLTIP));
		controller.useEdgeRegions.setTooltip(getTooltip(Labels.USE_EDGE_REGIONS_TOOLTIP));
		
		controller.writeDominationInformation.setTooltip(getTooltip(Labels.WRITE_DOMINATION_INFO_TOOLTIP));
		controller.writeRulesStatistics.setTooltip(getTooltip(Labels.WRITE_RULES_STAT_TOOLTIP));
		controller.writeLearningPositiveExamples.setTooltip(getTooltip(Labels.WRITE_LEARNING_EXAMPLES_TOOLTIP));
	}
	
	private void initializeTooltipsForButtons() {
		controller.restoreValuesButton.setTooltip(getTooltip(Labels.RESTORE_VALUES_BUTTON_TOOLTIP));
		controller.validateFormDefaults.setTooltip(getTooltip(Labels.VALIDATE_FORM_DEFAULTS_TOOLTIP));
	}
	
	private void initializeTooltipsForPanes() {
		controller.parametersPane.setTooltip(getTooltip(Labels.PARAMETERS_TOOLTIP));
		controller.additionalInfoPane.setTooltip(getTooltip(Labels.WRITE_INFO_TOOLTIP));
		controller.filesPane.setTooltip(getTooltip(Labels.FILE_PANE_TOOLTIP));
	}
	
	/**
	 * Create tooltip with provided label.
	 * It is initialized with default settings
	 * @param labelCode with is used to find translation
	 * @return tooltip containing text translated with labelCode
	 */
	private Tooltip getTooltip(String labelCode) {
		Tooltip tooltip = new Tooltip(labels.get(labelCode));
		tooltip.setShowDuration(showDuration);
		tooltip.setShowDelay(delayDuration);
		return tooltip;
	}
	
}
