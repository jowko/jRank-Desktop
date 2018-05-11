package pl.jowko.jrank.desktop.feature.properties;

import pl.jowko.jrank.desktop.feature.settings.Labels;
import pl.jowko.jrank.desktop.service.LanguageService;

/**
 * Created by Piotr on 2018-05-07.
 */
class PropertiesTranslatorHelper {
	
	private PropertiesController controller;
	private LanguageService labels;
	
	PropertiesTranslatorHelper(PropertiesController controller) {
		labels = LanguageService.getInstance();
		this.controller = controller;
	}
	
	void translateFields() {
		translateFilesFields();
		translateDataFields();
		translateParametersFields();
		translateButtons();
	}
	
	private void translateFilesFields() {
		controller.filesPane.setText(getText(Labels.PROP_FILES_PANEL));
		controller.learningDataFileLabel.setText(getText(Labels.LEARNING_FILE));
		controller.testDataFileLabel.setText(getText(Labels.TEST_FILE));
		controller.pctFileLabel.setText(getText(Labels.PCT_FILE));
		controller.pctApxFileLabel.setText(getText(Labels.PCT_APX_FILE));
		controller.pctRulesFileLabel.setText(getText(Labels.PCT_RULES_FILE));
		controller.graphFileLabel.setText(getText(Labels.GRAPH_FILE));
		controller.rankingFileLabel.setText(getText(Labels.RANKING_FILE));
	}
	
	private void translateDataFields() {
		controller.dataPanelLabel.setText(getText(Labels.PROP_DATA_PANEL));
		controller.referenceRankingLabel.setText(getText(Labels.REFERENCE_RANKING));
		controller.pairsLabel.setText(getText(Labels.PAIRS));
	}
	
	private void translateParametersFields() {
		controller.parametersPane.setText(getText(Labels.PROP_PARAMETERS_PANEL));
		controller.typeOfFamilyCriteriaLabel.setText(getText(Labels.TYPE_OF_FAMILY_CRITERIA));
		controller.typeOfRulesLabel.setText(getText(Labels.TYPE_OF_RULES));
		controller.consideredSetOfRulesLabel.setText(getText(Labels.CONSIDERED_SET_OF_RULES));
		
		controller.consistencyMeasureLabel.setText(getText(Labels.CONSISTENCY_MEASURE));
		controller.consistencyMeasureThresholdLabel.setText(getText(Labels.CONSISTENCY_MEASURE_THRESHOLD));
		controller.precisionLabel.setText(getText(Labels.PRECISION));
		
		controller.rankingProcedureLabel.setText(getText(Labels.RANKING_PROCEDURE));
		controller.dominanceLabel.setText(getText(Labels.DOMINANCE));
		controller.dominanceForPairsLabel.setText(getText(Labels.DOMINANCE_FOR_PAIRS));
		
		controller.satisfactionDegreesInGraphLabel.setText(getText(Labels.SATISFACTION_DEGREES));
		controller.fuzzyCalculationMethodLabel.setText(getText(Labels.FUZZY_CALCULATION_METHOD));
		
		controller.negativeExamplesTreatmentLabel.setText(getText(Labels.NEGATIVE_EXAMPLE_TREATMENT));
		controller.optimizeRuleConsistencyLabel.setText(getText(Labels.OPTIMIZE_RULE_CONSISTENCY));
		controller.ruleConditionsSelectionMethodLabel.setText(getText(Labels.RULE_CONDITIONS_SELECTION_METHOD));
		
		controller.allowEmptyRulesLabel.setText(getText(Labels.ALLOW_EMPTY_RULES));
		controller.useEdgeRegionsLabel.setText(getText(Labels.USE_EDGE_REGIONS));
		
		controller.additionalInfoPane.setText(getText(Labels.PROP_WRITE_PANEL));
		controller.writeDominationInformationLabel.setText(getText(Labels.WRITE_DOMINATION_INFO));
		controller.writeRulesStatisticsLabel.setText(getText(Labels.WRITE_RULES_STAT));
		controller.writeLearningPositiveExamplesLabel.setText(getText(Labels.WRITE_LEARNING_EXAMPLES));
	}
	
	private void translateButtons() {
		controller.saveButton.setText(getText(Labels.PROP_SAVE_BUTTON));
		controller.cancelButton.setText(getText(Labels.PROP_CANCEL_BUTTON));
		controller.setDefaultsButton.setText(getText(Labels.SET_DEFAULTS_BUTTON));
		controller.clearButton.setText(getText(Labels.PROP_CLEAR_BUTTON));
		controller.restoreValuesButton.setText(getText(Labels.RESTORE_VALUES_BUTTON));
		controller.validateFormButton.setText(getText(Labels.PROP_VALIDATE_FORM));
		controller.validateFormDefaults.setText(getText(Labels.VALIDATE_FORM_DEFAULTS));
	}
	
	private String getText(String labelCode) {
		return labels.get(labelCode);
	}
	
}
