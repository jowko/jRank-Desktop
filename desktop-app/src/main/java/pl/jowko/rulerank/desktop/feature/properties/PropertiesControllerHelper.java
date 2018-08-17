package pl.jowko.rulerank.desktop.feature.properties;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextInputControl;
import pl.jowko.rulerank.desktop.feature.internationalization.Labels;
import pl.jowko.rulerank.desktop.feature.internationalization.LanguageService;
import pl.jowko.rulerank.desktop.feature.settings.UserSettingsService;
import pl.jowko.rulerank.feature.customfx.DecimalField;

import java.util.List;

import static java.util.Objects.isNull;
import static pl.jowko.rulerank.desktop.feature.settings.RuleRankConst.MSG;
import static pl.jowko.rulerank.desktop.utils.StringUtils.isNotNullOrEmpty;

/**
 * Created by Piotr on 2018-05-04.
 * This class contains useful methods for PropertiesController
 * @see PropertiesController
 */
class PropertiesControllerHelper {
	
	private RuleRankParametersService service;
	private PropertiesController ctrl;
	private LanguageService labels;
	
	private RuleRankProperties editableProp;
	private RuleRankProperties defaultProp;
	private RuleRankParameter emptyValue;
	
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
		emptyValue = service.getDefaultParameter();
		labels = LanguageService.getInstance();
		
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
		
		fillComboBoxValue(ctrl.typeOfFamilyCriteria, editableProp.getTypeOfFamilyOfCriteria());
		fillComboBoxValue(ctrl.typeOfRules, editableProp.getTypeOfRules());
		fillComboBoxValue(ctrl.consideredSetOfRules, editableProp.getConsideredSetOfRules());
		
		fillComboBoxValue(ctrl.consistencyMeasure, editableProp.getConsistencyMeasure());
		fillTextValue(ctrl.consistencyMeasureThreshold, getStringOrNull(editableProp.getConsistencyMeasureThreshold()), getStringOrNull(defaultProp.getConsistencyMeasureThreshold()));
		fillTextValue(ctrl.precision, getStringOrNull(editableProp.getPrecision()), getStringOrNull(defaultProp.getPrecision()));
		
		fillComboBoxValue(ctrl.rankingProcedure, editableProp.getRankingProcedure());
		fillComboBoxValue(ctrl.dominance, editableProp.getDominance());
		fillComboBoxValue(ctrl.dominanceForPairs, editableProp.getDominanceForPairsOfOrdinalValues());
		
		fillComboBoxValue(ctrl.satisfactionDegreesInGraph, editableProp.getSatisfactionDegreesInPreferenceGraph());
		fillComboBoxValue(ctrl.fuzzyCalculationMethod, editableProp.getFuzzySatisfactionDegreeCalculationMethod());
		
		fillComboBoxValue(ctrl.negativeExamplesTreatment, editableProp.getNegativeExamplesTreatmentForVCDRSA());
		fillComboBoxValue(ctrl.optimizeRuleConsistency, editableProp.getOptimizeRuleConsistencyInVCDomLEMWrt());
		fillComboBoxValue(ctrl.ruleConditionsSelectionMethod, editableProp.getRuleConditionsSelectionMethodInVCDomLEM());
		
		fillComboBoxValue(ctrl.allowEmptyRules, editableProp.getAllowEmptyRulesInVCDomLEM());
		fillComboBoxValue(ctrl.useEdgeRegions, editableProp.getUseEdgeRegionsInVCDomLEM());
		
		fillComboBoxValue(ctrl.writeDominationInformation, editableProp.getWriteDominationInformation());
		fillComboBoxValue(ctrl.writeRulesStatistics, editableProp.getWriteRulesStatistics());
		fillComboBoxValue(ctrl.writeLearningPositiveExamples, editableProp.getWriteLearningPositiveExamples());
	}
	
	private void fillTextValue(TextInputControl textField, String textValue, String defaultValue) {
		textField.setPromptText(defaultValue);
		textField.setText(textValue);
	}
	
	private void fillComboBoxValue(ComboBox<RuleRankParameter> comboBox, RuleRankParameter parameter) {
		if(emptyValue.equals(parameter)) {
			comboBox.getSelectionModel().select(0);
		} else {
			comboBox.getSelectionModel().select(parameter);
		}
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
		fillComboBox(ctrl.typeOfFamilyCriteria, service.getTypeOfFamilyOfCriteria(), defaultProp.getTypeOfFamilyOfCriteria());
		fillComboBox(ctrl.typeOfRules, service.getTypeOfRules(), defaultProp.getTypeOfRules());
		fillComboBox(ctrl.consideredSetOfRules, service.getConsideredSetOfRules(), defaultProp.getConsideredSetOfRules());
		
		fillComboBox(ctrl.consistencyMeasure, service.getConsistencyMeasure(), defaultProp.getConsistencyMeasure());
		
		fillComboBox(ctrl.rankingProcedure, service.getRankingProcedure(), defaultProp.getRankingProcedure());
		fillComboBox(ctrl.dominance, service.getDominance(), defaultProp.getDominance());
		fillComboBox(ctrl.dominanceForPairs, service.getDominanceForPairsOfOrdinalValues(), defaultProp.getDominanceForPairsOfOrdinalValues());
		
		fillComboBox(ctrl.satisfactionDegreesInGraph, service.getSatisfactionDegreesInPreferenceGraph(), defaultProp.getSatisfactionDegreesInPreferenceGraph());
		fillComboBox(ctrl.fuzzyCalculationMethod, service.getFuzzySatisfactionDegreeCalculationMethod(), defaultProp.getFuzzySatisfactionDegreeCalculationMethod());
		
		fillComboBox(ctrl.negativeExamplesTreatment, service.getNegativeExamplesTreatmentForVCDRSA(), defaultProp.getNegativeExamplesTreatmentForVCDRSA());
		fillComboBox(ctrl.optimizeRuleConsistency, service.getOptimizeRuleConsistencyInVCDomLEMWrt(), defaultProp.getOptimizeRuleConsistencyInVCDomLEMWrt());
		fillComboBox(ctrl.ruleConditionsSelectionMethod, service.getRuleConditionsSelectionMethodInVCDomLEM(), defaultProp.getRuleConditionsSelectionMethodInVCDomLEM());
		
		fillComboBox(ctrl.allowEmptyRules, service.getAllowEmptyRules(), defaultProp.getAllowEmptyRulesInVCDomLEM());
		fillComboBox(ctrl.useEdgeRegions, service.getUseEdgeRegions(), defaultProp.getUseEdgeRegionsInVCDomLEM());
		
		fillComboBox(ctrl.writeDominationInformation, service.getWriteDominationInfo(), defaultProp.getWriteDominationInformation());
		fillComboBox(ctrl.writeRulesStatistics, service.getWriteRulesStatistics(), defaultProp.getWriteRulesStatistics());
		fillComboBox(ctrl.writeLearningPositiveExamples, service.getWritePositiveExamples(), defaultProp.getWriteLearningPositiveExamples());
	}
	
	private void fillComboBox(ComboBox<RuleRankParameter> comboBox, List<RuleRankParameter> list, RuleRankParameter defaultParameter) {
		RuleRankParameter defaultValue = service.getDefaultParameter();
		String label = labels.get(Labels.PROP_DEFAULT_VALUE).replace(MSG, defaultParameter.getLabel());
		defaultValue.setLabel(label);
		list.set(0, defaultValue);
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
