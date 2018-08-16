package pl.jowko.rulerank.desktop.feature.properties;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextInputControl;
import pl.jowko.rulerank.desktop.feature.settings.UserSettingsService;
import pl.jowko.rulerank.feature.customfx.DecimalField;

import java.util.List;

import static java.util.Objects.isNull;
import static pl.jowko.rulerank.desktop.utils.StringUtils.isNotNullOrEmpty;

/**
 * Created by Piotr on 2018-05-04.
 * This class contains useful methods for PropertiesController
 * @see PropertiesController
 */
class PropertiesControllerHelper {
	
	private RuleRankParametersService service;
	private PropertiesController ctrl;
	private RuleRankProperties editableProp;
	private RuleRankProperties defaultProp;
	
	/**
	 * Initializes instance of this class.
	 * This class will manipulate controller fields.
	 * @param controller from properties form
	 */
	PropertiesControllerHelper(PropertiesController controller) {
		service = RuleRankParametersService.getInstance();
		this.ctrl = controller;
		editableProp = controller.editableProperties;
		defaultProp = controller.defaultProperties;
		
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
		this.editableProp = editableProperties;
	}
	
	/**
	 * Clear all fields to empty values.
	 */
	void clearForm() {
		RuleRankParameter emptyParameter = service.getDefaultParameter();
		
		editableProp.setLearningDataFile(null);
		editableProp.setTestDataFile(null);
		editableProp.setPctFile(null);
		editableProp.setPctApxFile(null);
		editableProp.setPctRulesFile(null);
		editableProp.setPreferenceGraphFile(null);
		editableProp.setRankingFile(null);
		
		editableProp.setReferenceRanking(null);
		editableProp.setPairs(null);
		
		editableProp.setTypeOfFamilyOfCriteria(emptyParameter);
		editableProp.setTypeOfRules(emptyParameter);
		editableProp.setConsideredSetOfRules(emptyParameter);
		
		editableProp.setConsistencyMeasure(emptyParameter);
		editableProp.setConsistencyMeasureThreshold(null);
		editableProp.setPrecision(null);
		
		editableProp.setRankingProcedure(emptyParameter);
		editableProp.setDominance(emptyParameter);
		editableProp.setDominanceForPairsOfOrdinalValues(emptyParameter);
		
		editableProp.setSatisfactionDegreesInPreferenceGraph(emptyParameter);
		editableProp.setFuzzySatisfactionDegreeCalculationMethod(emptyParameter);
		
		editableProp.setNegativeExamplesTreatmentForVCDRSA(emptyParameter);
		editableProp.setOptimizeRuleConsistencyInVCDomLEMWrt(emptyParameter);
		editableProp.setRuleConditionsSelectionMethodInVCDomLEM(emptyParameter);
		
		editableProp.setAllowEmptyRulesInVCDomLEM(emptyParameter);
		editableProp.setUseEdgeRegionsInVCDomLEM(emptyParameter);
		
		editableProp.setWriteDominationInformation(emptyParameter);
		editableProp.setWriteRulesStatistics(emptyParameter);
		editableProp.setWriteLearningPositiveExamples(emptyParameter);
		
		fillFieldsValues();
	}
	
	/**
	 * Fills all fields with data extracted from editableProp variable.
	 */
	void fillFieldsValues() {
		fillTextValue(ctrl.learningDataFile, editableProp.getLearningDataFile(), defaultProp.getLearningDataFile());
		fillTextValue(ctrl.testDataFile, editableProp.getTestDataFile(), defaultProp.getTestDataFile());
		fillTextValue(ctrl.pctFile, editableProp.getPctFile(), defaultProp.getPctFile());
		fillTextValue(ctrl.pctApxFile, editableProp.getPctApxFile(), defaultProp.getPctApxFile());
		fillTextValue(ctrl.pctRulesFile, editableProp.getPctRulesFile(), defaultProp.getPctRulesFile());
		fillTextValue(ctrl.graphFile, editableProp.getPreferenceGraphFile(), defaultProp.getPreferenceGraphFile());
		fillTextValue(ctrl.rankingFile, editableProp.getRankingFile(), defaultProp.getRankingFile());
		
		fillTextValue(ctrl.referenceRanking, editableProp.getReferenceRanking(), defaultProp.getReferenceRanking());
		fillTextValue(ctrl.pairs, editableProp.getPairs(), defaultProp.getPairs());
		
		fillComboBoxValue(ctrl.typeOfFamilyCriteria, editableProp.getTypeOfFamilyOfCriteria(), defaultProp.getTypeOfFamilyOfCriteria());
		fillComboBoxValue(ctrl.typeOfRules, editableProp.getTypeOfRules(), defaultProp.getTypeOfRules());
		fillComboBoxValue(ctrl.consideredSetOfRules, editableProp.getConsideredSetOfRules(), defaultProp.getConsideredSetOfRules());
		
		fillComboBoxValue(ctrl.consistencyMeasure, editableProp.getConsistencyMeasure(), defaultProp.getConsistencyMeasure());
		fillTextValue(ctrl.consistencyMeasureThreshold, getStringOrNull(editableProp.getConsistencyMeasureThreshold()), getStringOrNull(defaultProp.getConsistencyMeasureThreshold()));
		fillTextValue(ctrl.precision, getStringOrNull(editableProp.getPrecision()), getStringOrNull(defaultProp.getPrecision()));
		
		fillComboBoxValue(ctrl.rankingProcedure, editableProp.getRankingProcedure(), defaultProp.getRankingProcedure());
		fillComboBoxValue(ctrl.dominance, editableProp.getDominance(), defaultProp.getDominance());
		fillComboBoxValue(ctrl.dominanceForPairs, editableProp.getDominanceForPairsOfOrdinalValues(), defaultProp.getDominanceForPairsOfOrdinalValues());
		
		fillComboBoxValue(ctrl.satisfactionDegreesInGraph, editableProp.getSatisfactionDegreesInPreferenceGraph(), defaultProp.getSatisfactionDegreesInPreferenceGraph());
		fillComboBoxValue(ctrl.fuzzyCalculationMethod, editableProp.getFuzzySatisfactionDegreeCalculationMethod(), defaultProp.getFuzzySatisfactionDegreeCalculationMethod());
		
		fillComboBoxValue(ctrl.negativeExamplesTreatment, editableProp.getNegativeExamplesTreatmentForVCDRSA(), defaultProp.getNegativeExamplesTreatmentForVCDRSA());
		fillComboBoxValue(ctrl.optimizeRuleConsistency, editableProp.getOptimizeRuleConsistencyInVCDomLEMWrt(), defaultProp.getOptimizeRuleConsistencyInVCDomLEMWrt());
		fillComboBoxValue(ctrl.ruleConditionsSelectionMethod, editableProp.getRuleConditionsSelectionMethodInVCDomLEM(), defaultProp.getRuleConditionsSelectionMethodInVCDomLEM());
		
		fillComboBoxValue(ctrl.allowEmptyRules, editableProp.getAllowEmptyRulesInVCDomLEM(), defaultProp.getAllowEmptyRulesInVCDomLEM());
		fillComboBoxValue(ctrl.useEdgeRegions, editableProp.getUseEdgeRegionsInVCDomLEM(), defaultProp.getUseEdgeRegionsInVCDomLEM());
		
		fillComboBoxValue(ctrl.writeDominationInformation, editableProp.getWriteDominationInformation(), defaultProp.getWriteDominationInformation());
		fillComboBoxValue(ctrl.writeRulesStatistics, editableProp.getWriteRulesStatistics(), defaultProp.getWriteRulesStatistics());
		fillComboBoxValue(ctrl.writeLearningPositiveExamples, editableProp.getWriteLearningPositiveExamples(), defaultProp.getWriteLearningPositiveExamples());
	}
	
	private void fillTextValue(TextInputControl textField, String textValue, String defaultValue) {
		textField.setText(textValue);
	}
	
	private void fillComboBoxValue(ComboBox<RuleRankParameter> comboBox, RuleRankParameter parameter, RuleRankParameter defaultParameter) {
		comboBox.getSelectionModel().select(parameter);
	}
	
	/**
	 * Extract all values from properties form and assembles new RuleRankProperties from them.
	 * @return RuleRankProperties with values from properties form.
	 */
	RuleRankProperties getPropertiesFromForm() {
		RuleRankProperties ruleRankProperties = new RuleRankProperties();
		
		ruleRankProperties.setLearningDataFile(ctrl.learningDataFile.getText());
		ruleRankProperties.setTestDataFile(ctrl.testDataFile.getText());
		ruleRankProperties.setPctFile(ctrl.pctFile.getText());
		ruleRankProperties.setPctApxFile(ctrl.pctApxFile.getText());
		ruleRankProperties.setPctRulesFile(ctrl.pctRulesFile.getText());
		ruleRankProperties.setPreferenceGraphFile(ctrl.graphFile.getText());
		ruleRankProperties.setRankingFile(ctrl.rankingFile.getText());
		
		ruleRankProperties.setReferenceRanking(ctrl.referenceRanking.getText());
		ruleRankProperties.setPairs(ctrl.pairs.getText());
		
		ruleRankProperties.setTypeOfFamilyOfCriteria(ctrl.typeOfFamilyCriteria.getValue());
		ruleRankProperties.setTypeOfRules(ctrl.typeOfRules.getValue());
		ruleRankProperties.setConsideredSetOfRules(ctrl.consideredSetOfRules.getValue());
		
		ruleRankProperties.setConsistencyMeasure(ctrl.consistencyMeasure.getValue());
		ruleRankProperties.setConsistencyMeasureThreshold(getDoubleOrNull(ctrl.consistencyMeasureThreshold.getText()));
		ruleRankProperties.setPrecision(getIntegerOrNull(ctrl.precision.getText()));
		
		ruleRankProperties.setRankingProcedure(ctrl.rankingProcedure.getValue());
		ruleRankProperties.setDominance(ctrl.dominance.getValue());
		ruleRankProperties.setDominanceForPairsOfOrdinalValues(ctrl.dominanceForPairs.getValue());
		
		ruleRankProperties.setSatisfactionDegreesInPreferenceGraph(ctrl.satisfactionDegreesInGraph.getValue());
		ruleRankProperties.setFuzzySatisfactionDegreeCalculationMethod(ctrl.fuzzyCalculationMethod.getValue());
		
		ruleRankProperties.setNegativeExamplesTreatmentForVCDRSA(ctrl.negativeExamplesTreatment.getValue());
		ruleRankProperties.setOptimizeRuleConsistencyInVCDomLEMWrt(ctrl.optimizeRuleConsistency.getValue());
		ruleRankProperties.setRuleConditionsSelectionMethodInVCDomLEM(ctrl.ruleConditionsSelectionMethod.getValue());
		
		ruleRankProperties.setAllowEmptyRulesInVCDomLEM(ctrl.allowEmptyRules.getValue());
		ruleRankProperties.setUseEdgeRegionsInVCDomLEM(ctrl.useEdgeRegions.getValue());
		
		ruleRankProperties.setWriteDominationInformation(ctrl.writeDominationInformation.getValue());
		ruleRankProperties.setWriteRulesStatistics(ctrl.writeRulesStatistics.getValue());
		ruleRankProperties.setWriteLearningPositiveExamples(ctrl.writeLearningPositiveExamples.getValue());
		
		return ruleRankProperties;
	}
	
	/**
	 * Sets tiled panes as expanded or collapsed according to user settings.
	 */
	private void initTitledPanes() {
		boolean isAdvancedPropertiesEnabled = UserSettingsService.getInstance().getUserSettings().isAdvancedPropertiesEnabled();
		ctrl.filesPane.setExpanded(isAdvancedPropertiesEnabled);
		ctrl.parametersPane.setExpanded(isAdvancedPropertiesEnabled);
		ctrl.additionalInfoPane.setExpanded(isAdvancedPropertiesEnabled);
	}
	
	/**
	 * Fills all ComboBoxes with options to choose.
	 * @see RuleRankParametersService
	 */
	private void fillComboBoxes() {
		fillComboBox(ctrl.typeOfFamilyCriteria, service.getTypeOfFamilyOfCriteria());
		fillComboBox(ctrl.typeOfRules, service.getTypeOfRules());
		fillComboBox(ctrl.consideredSetOfRules, service.getConsideredSetOfRules());
		
		fillComboBox(ctrl.consistencyMeasure, service.getConsistencyMeasure());
		
		fillComboBox(ctrl.rankingProcedure, service.getRankingProcedure());
		fillComboBox(ctrl.dominance, service.getDominance());
		fillComboBox(ctrl.dominanceForPairs, service.getDominanceForPairsOfOrdinalValues());
		
		fillComboBox(ctrl.satisfactionDegreesInGraph, service.getSatisfactionDegreesInPreferenceGraph());
		fillComboBox(ctrl.fuzzyCalculationMethod, service.getFuzzySatisfactionDegreeCalculationMethod());
		
		fillComboBox(ctrl.negativeExamplesTreatment, service.getNegativeExamplesTreatmentForVCDRSA());
		fillComboBox(ctrl.optimizeRuleConsistency, service.getOptimizeRuleConsistencyInVCDomLEMWrt());
		fillComboBox(ctrl.ruleConditionsSelectionMethod, service.getRuleConditionsSelectionMethodInVCDomLEM());
		
		fillComboBox(ctrl.allowEmptyRules, service.getBooleanParameter());
		fillComboBox(ctrl.useEdgeRegions, service.getBooleanParameter());
		
		fillComboBox(ctrl.writeDominationInformation, service.getBooleanParameter());
		fillComboBox(ctrl.writeRulesStatistics, service.getBooleanParameter());
		fillComboBox(ctrl.writeLearningPositiveExamples, service.getBooleanParameter());
	}
	
	private void fillComboBox(ComboBox<RuleRankParameter> comboBox, List<RuleRankParameter> list) {
		comboBox.getItems().addAll(list);
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
