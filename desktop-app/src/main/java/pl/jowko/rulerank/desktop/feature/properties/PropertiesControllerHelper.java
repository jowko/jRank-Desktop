package pl.jowko.rulerank.desktop.feature.properties;

import pl.jowko.rulerank.desktop.feature.settings.UserSettingsService;
import pl.jowko.rulerank.feature.customfx.DecimalField;

import static java.util.Objects.isNull;
import static pl.jowko.rulerank.desktop.utils.StringUtils.isNotNullOrEmpty;

/**
 * Created by Piotr on 2018-05-04.
 * This class contains useful methods for PropertiesController
 * @see PropertiesController
 */
class PropertiesControllerHelper {
	
	private RuleRankParametersService parametersService;
	private PropertiesController controller;
	private RuleRankProperties editableProperties;
	
	/**
	 * Initializes instance of this class.
	 * This class will manipulate controller fields.
	 * @param controller from properties form
	 */
	PropertiesControllerHelper(PropertiesController controller) {
		parametersService = RuleRankParametersService.getInstance();
		this.controller = controller;
		editableProperties = controller.editableProperties;
		
		initTitledPanes();
		fillComboBoxes();
		fillFieldsValues();
		
		controller.consistencyMeasureThreshold.setPattern(DecimalField.POSITIVE_DECIMAL_PATTERN);
		controller.precision.setCharLimit(6);
		controller.consistencyMeasureThreshold.setCharLimit(50);
		
		boolean isManualEditEnabled = UserSettingsService.getInstance().getUserSettings().isManualInfoEditionEnabled();
		controller.referenceRanking.setEditable(isManualEditEnabled);
		controller.pairs.setEditable(isManualEditEnabled);
	}
	
	/**
	 * Sets properties with are used to fill fields in properties form.
	 * Used when restoring default values in form.
	 * @param editableProperties with will replace current form values
	 */
	void setEditableProperties(RuleRankProperties editableProperties) {
		this.editableProperties = editableProperties;
	}
	
	/**
	 * Clear all fields to empty values.
	 */
	void clearForm() {
		RuleRankParameter emptyParameter = parametersService.getEmptyParameter();
		
		editableProperties.setLearningDataFile(null);
		editableProperties.setTestDataFile(null);
		editableProperties.setPctFile(null);
		editableProperties.setPctApxFile(null);
		editableProperties.setPctRulesFile(null);
		editableProperties.setPreferenceGraphFile(null);
		editableProperties.setRankingFile(null);
		
		editableProperties.setReferenceRanking(null);
		editableProperties.setPairs(null);
		
		editableProperties.setTypeOfFamilyOfCriteria(emptyParameter);
		editableProperties.setTypeOfRules(emptyParameter);
		editableProperties.setConsideredSetOfRules(emptyParameter);
		
		editableProperties.setConsistencyMeasure(emptyParameter);
		editableProperties.setConsistencyMeasureThreshold(null);
		editableProperties.setPrecision(null);
		
		editableProperties.setRankingProcedure(emptyParameter);
		editableProperties.setDominance(emptyParameter);
		editableProperties.setDominanceForPairsOfOrdinalValues(emptyParameter);
		
		editableProperties.setSatisfactionDegreesInPreferenceGraph(emptyParameter);
		editableProperties.setFuzzySatisfactionDegreeCalculationMethod(emptyParameter);
		
		editableProperties.setNegativeExamplesTreatmentForVCDRSA(emptyParameter);
		editableProperties.setOptimizeRuleConsistencyInVCDomLEMWrt(emptyParameter);
		editableProperties.setRuleConditionsSelectionMethodInVCDomLEM(emptyParameter);
		
		editableProperties.setAllowEmptyRulesInVCDomLEM(emptyParameter);
		editableProperties.setUseEdgeRegionsInVCDomLEM(emptyParameter);
		
		editableProperties.setWriteDominationInformation(emptyParameter);
		editableProperties.setWriteRulesStatistics(emptyParameter);
		editableProperties.setWriteLearningPositiveExamples(emptyParameter);
		
		fillFieldsValues();
	}
	
	/**
	 * Fills all fields with data extracted from editableProperties variable.
	 */
	void fillFieldsValues() {
		controller.learningDataFile.setText(editableProperties.getLearningDataFile());
		controller.testDataFile.setText(editableProperties.getTestDataFile());
		controller.pctFile.setText(editableProperties.getPctFile());
		controller.pctApxFile.setText(editableProperties.getPctApxFile());
		controller.pctRulesFile.setText(editableProperties.getPctRulesFile());
		controller.graphFile.setText(editableProperties.getPreferenceGraphFile());
		controller.rankingFile.setText(editableProperties.getRankingFile());
		
		controller.referenceRanking.setText(editableProperties.getReferenceRanking());
		controller.pairs.setText(editableProperties.getPairs());
		
		controller.typeOfFamilyCriteria.getSelectionModel().select(editableProperties.getTypeOfFamilyOfCriteria());
		controller.typeOfRules.getSelectionModel().select(editableProperties.getTypeOfRules());
		controller.consideredSetOfRules.getSelectionModel().select(editableProperties.getConsideredSetOfRules());
		
		controller.consistencyMeasure.getSelectionModel().select(editableProperties.getConsistencyMeasure());
		controller.consistencyMeasureThreshold.setText(getStringOrNull(editableProperties.getConsistencyMeasureThreshold()));
		controller.precision.setText(getStringOrNull(editableProperties.getPrecision()));
		
		controller.rankingProcedure.getSelectionModel().select(editableProperties.getRankingProcedure());
		controller.dominance.getSelectionModel().select(editableProperties.getDominance());
		controller.dominanceForPairs.getSelectionModel().select(editableProperties.getDominanceForPairsOfOrdinalValues());
		
		controller.satisfactionDegreesInGraph.getSelectionModel().select(editableProperties.getSatisfactionDegreesInPreferenceGraph());
		controller.fuzzyCalculationMethod.getSelectionModel().select(editableProperties.getFuzzySatisfactionDegreeCalculationMethod());
		
		controller.negativeExamplesTreatment.getSelectionModel().select(editableProperties.getNegativeExamplesTreatmentForVCDRSA());
		controller.optimizeRuleConsistency.getSelectionModel().select(editableProperties.getOptimizeRuleConsistencyInVCDomLEMWrt());
		controller.ruleConditionsSelectionMethod.getSelectionModel().select(editableProperties.getRuleConditionsSelectionMethodInVCDomLEM());
		
		controller.allowEmptyRules.getSelectionModel().select(editableProperties.getAllowEmptyRulesInVCDomLEM());
		controller.useEdgeRegions.getSelectionModel().select(editableProperties.getUseEdgeRegionsInVCDomLEM());
		
		controller.writeDominationInformation.getSelectionModel().select(editableProperties.getWriteDominationInformation());
		controller.writeRulesStatistics.getSelectionModel().select(editableProperties.getWriteRulesStatistics());
		controller.writeLearningPositiveExamples.getSelectionModel().select(editableProperties.getWriteLearningPositiveExamples());
	}
	
	/**
	 * Extract all values from properties form and assembles new JrankProperties from them.
	 * @return RuleRankProperties with values from properties form.
	 */
	RuleRankProperties getPropertiesFromForm() {
		RuleRankProperties ruleRankProperties = new RuleRankProperties();
		
		ruleRankProperties.setLearningDataFile(controller.learningDataFile.getText());
		ruleRankProperties.setTestDataFile(controller.testDataFile.getText());
		ruleRankProperties.setPctFile(controller.pctFile.getText());
		ruleRankProperties.setPctApxFile(controller.pctApxFile.getText());
		ruleRankProperties.setPctRulesFile(controller.pctRulesFile.getText());
		ruleRankProperties.setPreferenceGraphFile(controller.graphFile.getText());
		ruleRankProperties.setRankingFile(controller.rankingFile.getText());
		
		ruleRankProperties.setReferenceRanking(controller.referenceRanking.getText());
		ruleRankProperties.setPairs(controller.pairs.getText());
		
		ruleRankProperties.setTypeOfFamilyOfCriteria(controller.typeOfFamilyCriteria.getValue());
		ruleRankProperties.setTypeOfRules(controller.typeOfRules.getValue());
		ruleRankProperties.setConsideredSetOfRules(controller.consideredSetOfRules.getValue());
		
		ruleRankProperties.setConsistencyMeasure(controller.consistencyMeasure.getValue());
		ruleRankProperties.setConsistencyMeasureThreshold(getDoubleOrNull(controller.consistencyMeasureThreshold.getText()));
		ruleRankProperties.setPrecision(getIntegerOrNull(controller.precision.getText()));
		
		ruleRankProperties.setRankingProcedure(controller.rankingProcedure.getValue());
		ruleRankProperties.setDominance(controller.dominance.getValue());
		ruleRankProperties.setDominanceForPairsOfOrdinalValues(controller.dominanceForPairs.getValue());
		
		ruleRankProperties.setSatisfactionDegreesInPreferenceGraph(controller.satisfactionDegreesInGraph.getValue());
		ruleRankProperties.setFuzzySatisfactionDegreeCalculationMethod(controller.fuzzyCalculationMethod.getValue());
		
		ruleRankProperties.setNegativeExamplesTreatmentForVCDRSA(controller.negativeExamplesTreatment.getValue());
		ruleRankProperties.setOptimizeRuleConsistencyInVCDomLEMWrt(controller.optimizeRuleConsistency.getValue());
		ruleRankProperties.setRuleConditionsSelectionMethodInVCDomLEM(controller.ruleConditionsSelectionMethod.getValue());
		
		ruleRankProperties.setAllowEmptyRulesInVCDomLEM(controller.allowEmptyRules.getValue());
		ruleRankProperties.setUseEdgeRegionsInVCDomLEM(controller.useEdgeRegions.getValue());
		
		ruleRankProperties.setWriteDominationInformation(controller.writeDominationInformation.getValue());
		ruleRankProperties.setWriteRulesStatistics(controller.writeRulesStatistics.getValue());
		ruleRankProperties.setWriteLearningPositiveExamples(controller.writeLearningPositiveExamples.getValue());
		
		return ruleRankProperties;
	}
	
	/**
	 * Sets tiled panes as expanded or collapsed according to user settings.
	 */
	private void initTitledPanes() {
		boolean isAdvancedPropertiesEnabled = UserSettingsService.getInstance().getUserSettings().isAdvancedPropertiesEnabled();
		controller.filesPane.setExpanded(isAdvancedPropertiesEnabled);
		controller.parametersPane.setExpanded(isAdvancedPropertiesEnabled);
		controller.additionalInfoPane.setExpanded(isAdvancedPropertiesEnabled);
	}
	
	/**
	 * Fills all ComboBoxes with options to choose.
	 * @see RuleRankParametersService
	 */
	private void fillComboBoxes() {
		controller.typeOfFamilyCriteria.getItems().addAll(parametersService.getTypeOfFamilyOfCriteria());
		controller.typeOfRules.getItems().addAll(parametersService.getTypeOfRules());
		controller.consideredSetOfRules.getItems().addAll(parametersService.getConsideredSetOfRules());
		
		controller.consistencyMeasure.getItems().addAll(parametersService.getConsistencyMeasure());
		
		controller.rankingProcedure.getItems().addAll(parametersService.getRankingProcedure());
		controller.dominance.getItems().addAll(parametersService.getDominance());
		controller.dominanceForPairs.getItems().addAll(parametersService.getDominanceForPairsOfOrdinalValues());
		
		controller.satisfactionDegreesInGraph.getItems().addAll(parametersService.getSatisfactionDegreesInPreferenceGraph());
		controller.fuzzyCalculationMethod.getItems().addAll(parametersService.getFuzzySatisfactionDegreeCalculationMethod());
		
		controller.negativeExamplesTreatment.getItems().addAll(parametersService.getNegativeExamplesTreatmentForVCDRSA());
		controller.optimizeRuleConsistency.getItems().addAll(parametersService.getOptimizeRuleConsistencyInVCDomLEMWrt());
		controller.ruleConditionsSelectionMethod.getItems().addAll(parametersService.getRuleConditionsSelectionMethodInVCDomLEM());
		
		controller.allowEmptyRules.getItems().addAll(parametersService.getBooleanParameter());
		controller.useEdgeRegions.getItems().addAll(parametersService.getBooleanParameter());
		
		controller.writeDominationInformation.getItems().addAll(parametersService.getBooleanParameter());
		controller.writeRulesStatistics.getItems().addAll(parametersService.getBooleanParameter());
		controller.writeLearningPositiveExamples.getItems().addAll(parametersService.getBooleanParameter());
	}
	
	private String getStringOrNull(Object value) {
		if(isNull(value)) {
			return null;
		}
		return value.toString();
	}
	
	private Double getDoubleOrNull(String value) {
		if(isNotNullOrEmpty(value)) {
			return Double.valueOf(value);
		}
		return null;
	}
	
	private Integer getIntegerOrNull(String value) {
		if(isNotNullOrEmpty(value)) {
			return Integer.valueOf(value);
		}
		return null;
	}
	
}
