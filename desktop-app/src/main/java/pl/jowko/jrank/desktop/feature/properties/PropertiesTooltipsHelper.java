package pl.jowko.jrank.desktop.feature.properties;

import javafx.scene.control.Tooltip;
import javafx.util.Duration;
import pl.jowko.jrank.desktop.feature.settings.Labels;
import pl.jowko.jrank.desktop.feature.settings.UserSettingsService;
import pl.jowko.jrank.desktop.service.LanguageService;

import static pl.jowko.jrank.desktop.utils.BooleanUtils.not;

/**
 * Created by Piotr on 2018-05-07.
 */
class PropertiesTooltipsHelper {
	
	private PropertiesController controller;
	private LanguageService labels;
	
	private Duration showDuration;
	private Duration delayDuration;
	
	PropertiesTooltipsHelper(PropertiesController controller) {
		labels = LanguageService.getInstance();
		this.controller = controller;
		showDuration = new Duration(10_000);
		delayDuration = new Duration(1500);
	}
	
	void initializeTooltips() {
		boolean isTooltipsEnabled = UserSettingsService.getInstance().getUserSettings().isTooltipsEnabled();
		if(not(isTooltipsEnabled))
			return;
		
		initializeTooltipsForFilesFields();
		initializeTooltipsForRanking();
		initializeTooltipsForParameters();
		initializeTooltipsForBooleanParameters();
		initializeTooltipsForButtons();
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
		controller.setDefaultsButton.setTooltip(getTooltip(Labels.SET_DEFAULTS_BUTTON_TOOLTIP));
		controller.restoreValuesButton.setTooltip(getTooltip(Labels.RESTORE_VALUES_BUTTON_TOOLTIP));
		controller.validateFormDefaults.setTooltip(getTooltip(Labels.VALIDATE_FORM_DEFAULTS_TOOLTIP));
	}
	
	private Tooltip getTooltip(String labelCode) {
		Tooltip tooltip = new Tooltip(labels.get(labelCode));
		tooltip.setShowDuration(showDuration);
		tooltip.setShowDelay(delayDuration);
		return tooltip;
	}
	
}
