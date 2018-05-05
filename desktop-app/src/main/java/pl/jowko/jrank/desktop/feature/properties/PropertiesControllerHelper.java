package pl.jowko.jrank.desktop.feature.properties;

import static java.util.Objects.isNull;
import static pl.jowko.jrank.desktop.utils.StringUtils.isNotNullOrEmpty;

/**
 * Created by Piotr on 2018-05-04.
 */
class PropertiesControllerHelper {
	
	private JRankParametersService parametersService;
	private PropertiesController controller;
	private JRankProperties editableProperties;
	
	PropertiesControllerHelper(PropertiesController controller) {
		parametersService = JRankParametersService.getInstance();
		this.controller = controller;
		editableProperties = controller.editableProperties;
	}
	
	public void setEditableProperties(JRankProperties editableProperties) {
		this.editableProperties = editableProperties;
	}
	
	void fillComboBoxes() {
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
	}
	
	void clearForm() {
		JRankParameter emptyParameter = parametersService.getEmptyParameter();
		
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
		
		editableProperties.setAllowEmptyRulesInVCDomLEM(null);
		editableProperties.setUseEdgeRegionsInVCDomLEM(null);
		
		editableProperties.setWriteDominationInformation(null);
		editableProperties.setWriteRulesStatistics(null);
		editableProperties.setWriteLearningPositiveExamples(null);
		
		fillFieldsValues();
	}
	
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
		
		controller.allowEmptyRules.setSelected(getBoolean(editableProperties.getAllowEmptyRulesInVCDomLEM()));
		controller.useEdgeRegions.setSelected(getBoolean(editableProperties.getUseEdgeRegionsInVCDomLEM()));
		
		controller.writeDominationInformation.setSelected(getBoolean(editableProperties.getWriteDominationInformation()));
		controller.writeRulesStatistics.setSelected(getBoolean(editableProperties.getWriteRulesStatistics()));
		controller.writeLearningPositiveExamples.setSelected(getBoolean(editableProperties.getWriteLearningPositiveExamples()));
	}
	
	JRankProperties getPropertiesFromForm() {
		JRankProperties jRankProperties = new JRankProperties();
		
		jRankProperties.setLearningDataFile(controller.learningDataFile.getText());
		jRankProperties.setTestDataFile(controller.testDataFile.getText());
		jRankProperties.setPctFile(controller.pctFile.getText());
		jRankProperties.setPctApxFile(controller.pctApxFile.getText());
		jRankProperties.setPctRulesFile(controller.pctRulesFile.getText());
		jRankProperties.setPreferenceGraphFile(controller.graphFile.getText());
		jRankProperties.setRankingFile(controller.rankingFile.getText());
		
		jRankProperties.setReferenceRanking(controller.referenceRanking.getText());
		jRankProperties.setPairs(controller.pairs.getText());
		
		jRankProperties.setTypeOfFamilyOfCriteria(controller.typeOfFamilyCriteria.getValue());
		jRankProperties.setTypeOfRules(controller.typeOfRules.getValue());
		jRankProperties.setConsideredSetOfRules(controller.consideredSetOfRules.getValue());
		
		jRankProperties.setConsistencyMeasure(controller.consistencyMeasure.getValue());
		jRankProperties.setConsistencyMeasureThreshold(getDoubleOrNull(controller.consistencyMeasureThreshold.getText()));
		jRankProperties.setPrecision(getIntegerOrNull(controller.precision.getText()));
		
		jRankProperties.setRankingProcedure(controller.rankingProcedure.getValue());
		jRankProperties.setDominance(controller.dominance.getValue());
		jRankProperties.setDominanceForPairsOfOrdinalValues(controller.dominanceForPairs.getValue());
		
		jRankProperties.setSatisfactionDegreesInPreferenceGraph(controller.satisfactionDegreesInGraph.getValue());
		jRankProperties.setFuzzySatisfactionDegreeCalculationMethod(controller.fuzzyCalculationMethod.getValue());
		
		jRankProperties.setNegativeExamplesTreatmentForVCDRSA(controller.negativeExamplesTreatment.getValue());
		jRankProperties.setOptimizeRuleConsistencyInVCDomLEMWrt(controller.optimizeRuleConsistency.getValue());
		jRankProperties.setRuleConditionsSelectionMethodInVCDomLEM(controller.ruleConditionsSelectionMethod.getValue());
		
		jRankProperties.setAllowEmptyRulesInVCDomLEM(controller.allowEmptyRules.isSelected());
		jRankProperties.setUseEdgeRegionsInVCDomLEM(controller.useEdgeRegions.isSelected());
		
		jRankProperties.setWriteDominationInformation(controller.writeDominationInformation.isSelected());
		jRankProperties.setWriteRulesStatistics(controller.writeRulesStatistics.isSelected());
		jRankProperties.setWriteLearningPositiveExamples(controller.writeLearningPositiveExamples.isSelected());
		
		return jRankProperties;
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
	
	private boolean getBoolean(Boolean value) {
		if(isNull(value)) {
			return false;
		}
		return value;
	}
	
}
