package pl.jowko.rulerank.desktop.feature.properties;

import pl.jowko.rulerank.desktop.feature.internationalization.AbstractTranslator;
import pl.jowko.rulerank.desktop.feature.internationalization.Labels;
import pl.jowko.rulerank.desktop.feature.internationalization.LanguageService;

/**
 * This class translates fields from properties tab. <br>
 *  <br>
 * Created by Piotr on 2018-05-07.
 */
class PropertiesTranslatorHelper extends AbstractTranslator {
	
	private PropertiesController controller;
	
	PropertiesTranslatorHelper(PropertiesController controller) {
		labels = LanguageService.getInstance();
		this.controller = controller;
	}
	
	@Override
	public void translateFields() {
		translateFilesFields();
		translateDataFields();
		translateParametersFields();
		translateButtons();
	}
	
	private void translateFilesFields() {
		controller.filesPane.setText(get(Labels.PROP_FILES_PANEL));
		controller.learningDataFileLabel.setText(get(Labels.LEARNING_FILE));
		controller.testDataFileLabel.setText(get(Labels.TEST_FILE));
		controller.pctFileLabel.setText(get(Labels.PCT_FILE));
		controller.pctApxFileLabel.setText(get(Labels.PCT_APX_FILE));
		controller.pctRulesFileLabel.setText(get(Labels.PCT_RULES_FILE));
		controller.graphFileLabel.setText(get(Labels.GRAPH_FILE));
		controller.rankingFileLabel.setText(get(Labels.RANKING_FILE));
	}
	
	private void translateDataFields() {
		controller.dataPanelLabel.setText(get(Labels.PROP_DATA_PANEL));
		controller.referenceRankingLabel.setText(get(Labels.REFERENCE_RANKING));
		controller.editRankingButton.setText(get(Labels.EDIT_RANKING_BUTTON));
		controller.pairsLabel.setText(get(Labels.PAIRS));
		controller.editPairsButton.setText(get(Labels.EDIT_PAIRS_BUTTON));
	}
	
	private void translateParametersFields() {
		controller.parametersPane.setText(get(Labels.PROP_PARAMETERS_PANEL));
		controller.typeOfFamilyCriteriaLabel.setText(get(Labels.TYPE_OF_FAMILY_CRITERIA));
		controller.typeOfRulesLabel.setText(get(Labels.TYPE_OF_RULES));
		controller.consideredSetOfRulesLabel.setText(get(Labels.CONSIDERED_SET_OF_RULES));
		
		controller.consistencyMeasureLabel.setText(get(Labels.CONSISTENCY_MEASURE));
		controller.consistencyMeasureThresholdLabel.setText(get(Labels.CONSISTENCY_MEASURE_THRESHOLD));
		controller.precisionLabel.setText(get(Labels.PRECISION));
		
		controller.rankingProcedureLabel.setText(get(Labels.RANKING_PROCEDURE));
		controller.dominanceLabel.setText(get(Labels.DOMINANCE));
		controller.dominanceForPairsLabel.setText(get(Labels.DOMINANCE_FOR_PAIRS));
		
		controller.satisfactionDegreesInGraphLabel.setText(get(Labels.SATISFACTION_DEGREES));
		controller.fuzzyCalculationMethodLabel.setText(get(Labels.FUZZY_CALCULATION_METHOD));
		
		controller.negativeExamplesTreatmentLabel.setText(get(Labels.NEGATIVE_EXAMPLE_TREATMENT));
		controller.optimizeRuleConsistencyLabel.setText(get(Labels.OPTIMIZE_RULE_CONSISTENCY));
		controller.ruleConditionsSelectionMethodLabel.setText(get(Labels.RULE_CONDITIONS_SELECTION_METHOD));
		
		controller.allowEmptyRulesLabel.setText(get(Labels.ALLOW_EMPTY_RULES));
		controller.useEdgeRegionsLabel.setText(get(Labels.USE_EDGE_REGIONS));
		
		controller.additionalInfoPane.setText(get(Labels.PROP_WRITE_PANEL));
		controller.writeDominationInformationLabel.setText(get(Labels.WRITE_DOMINATION_INFO));
		controller.writeRulesStatisticsLabel.setText(get(Labels.WRITE_RULES_STAT));
		controller.writeLearningPositiveExamplesLabel.setText(get(Labels.WRITE_LEARNING_EXAMPLES));
	}
	
	private void translateButtons() {
		controller.saveButton.setText(get(Labels.PROP_SAVE_BUTTON));
		controller.cancelButton.setText(get(Labels.PROP_CANCEL_BUTTON));
		controller.clearButton.setText(get(Labels.PROP_CLEAR_BUTTON));
		controller.restoreValuesButton.setText(get(Labels.RESTORE_VALUES_BUTTON));
		controller.validateFormButton.setText(get(Labels.PROP_VALIDATE_FORM));
	}
	
}
