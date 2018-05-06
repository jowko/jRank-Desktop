package pl.jowko.jrank.desktop.feature.properties;

import pl.jowko.jrank.desktop.utils.Cloner;

import static java.util.Objects.isNull;
import static pl.jowko.jrank.desktop.utils.StringUtils.isNotNullOrEmpty;
import static pl.jowko.jrank.desktop.utils.StringUtils.isNullOrEmpty;

/**
 * Created by Piotr on 2018-05-06.
 * This class merges properties from experiment/form with default properties.
 * Result is used for running jRank algorithm and in form validation.
 */
public class RunnerPropertiesProvider {
	
	private JRankParametersService parametersService;
	private JRankProperties properties;
	private JRankProperties defaults;
	
	public RunnerPropertiesProvider(JRankProperties experimentProperties, JRankProperties defaults) {
		parametersService = JRankParametersService.getInstance();
		this.properties = (JRankProperties) Cloner.deepClone(experimentProperties);
		this.defaults = defaults;
	}
	
	public JRankProperties getPropertiesWithDefaults() {
		setDefaultPropertiesForFiles();
		setDefaultPropertiesForRankingInformation();
		setDefaultPropertiesForParameters();
		setDefaultBooleanProperties();
		setDefaultNumberProperties();
		
		return properties;
	}
	
	private void setDefaultPropertiesForFiles() {
		if(shouldSetDefault(properties.getLearningDataFile()))
			properties.setLearningDataFile(defaults.getLearningDataFile());
		
		if(shouldSetDefault(properties.getTestDataFile()))
			properties.setTestDataFile(defaults.getTestDataFile());
		
		if(shouldSetDefault(properties.getPctFile()))
			properties.setPctFile(defaults.getPctFile());
		
		if(shouldSetDefault(properties.getPctApxFile()))
			properties.setPctApxFile(defaults.getPctApxFile());
		
		if(shouldSetDefault(properties.getPctRulesFile()))
			properties.setPctRulesFile(defaults.getPctRulesFile());
		
		if(shouldSetDefault(properties.getPreferenceGraphFile()))
			properties.setPreferenceGraphFile(defaults.getPreferenceGraphFile());
		
		if(shouldSetDefault(properties.getRankingFile()))
			properties.setRankingFile(defaults.getRankingFile());
	}
	
	private void setDefaultPropertiesForRankingInformation() {
		if(isNullOrEmpty(properties.getReferenceRanking()) && isNullOrEmpty(properties.getPairs()) && isNotNullOrEmpty(defaults.getReferenceRanking()))
			properties.setReferenceRanking(defaults.getReferenceRanking());
		
		if(isNullOrEmpty(properties.getReferenceRanking()) && isNullOrEmpty(properties.getPairs()) && isNotNullOrEmpty(defaults.getPairs()))
			properties.setPairs(defaults.getPairs());
	}
	
	private void setDefaultPropertiesForParameters() {
		if(shouldSetDefault(properties.getTypeOfFamilyOfCriteria()))
			properties.setTypeOfFamilyOfCriteria(defaults.getTypeOfFamilyOfCriteria());
		
		if(shouldSetDefault(properties.getConsistencyMeasure()))
			properties.setConsistencyMeasure(defaults.getConsistencyMeasure());
		
		if(shouldSetDefault(properties.getTypeOfRules()))
			properties.setTypeOfRules(defaults.getTypeOfRules());
		
		if(shouldSetDefault(properties.getConsideredSetOfRules()))
			properties.setConsideredSetOfRules(defaults.getConsideredSetOfRules());
		
		if(shouldSetDefault(properties.getSatisfactionDegreesInPreferenceGraph()))
			properties.setSatisfactionDegreesInPreferenceGraph(defaults.getSatisfactionDegreesInPreferenceGraph());
		
		if(shouldSetDefault(properties.getFuzzySatisfactionDegreeCalculationMethod()))
			properties.setFuzzySatisfactionDegreeCalculationMethod(defaults.getFuzzySatisfactionDegreeCalculationMethod());
		
		if(shouldSetDefault(properties.getRankingProcedure()))
			properties.setRankingProcedure(defaults.getRankingProcedure());
		
		if(shouldSetDefault(properties.getDominance()))
			properties.setDominance(defaults.getDominance());
		
		if(shouldSetDefault(properties.getDominanceForPairsOfOrdinalValues()))
			properties.setDominanceForPairsOfOrdinalValues(defaults.getDominanceForPairsOfOrdinalValues());
		
		if(shouldSetDefault(properties.getNegativeExamplesTreatmentForVCDRSA()))
			properties.setNegativeExamplesTreatmentForVCDRSA(defaults.getNegativeExamplesTreatmentForVCDRSA());
		
		if(shouldSetDefault(properties.getRuleConditionsSelectionMethodInVCDomLEM()))
			properties.setRuleConditionsSelectionMethodInVCDomLEM(defaults.getRuleConditionsSelectionMethodInVCDomLEM());
		
		if(shouldSetDefault(properties.getDominanceForPairsOfOrdinalValues()))
			properties.setDominanceForPairsOfOrdinalValues(defaults.getDominanceForPairsOfOrdinalValues());
		
		if(shouldSetDefault(properties.getOptimizeRuleConsistencyInVCDomLEMWrt()))
			properties.setOptimizeRuleConsistencyInVCDomLEMWrt(defaults.getOptimizeRuleConsistencyInVCDomLEMWrt());
	}
	
	private void setDefaultBooleanProperties() {
		if(isNull(properties.getAllowEmptyRulesInVCDomLEM()))
			properties.setAllowEmptyRulesInVCDomLEM(defaults.getAllowEmptyRulesInVCDomLEM());
		
		if(isNull(properties.getUseEdgeRegionsInVCDomLEM()))
			properties.setUseEdgeRegionsInVCDomLEM(defaults.getUseEdgeRegionsInVCDomLEM());
		
		if(isNull(properties.getWriteDominationInformation()))
			properties.setWriteDominationInformation(defaults.getWriteDominationInformation());
		
		if(isNull(properties.getWriteRulesStatistics()))
			properties.setWriteRulesStatistics(defaults.getWriteRulesStatistics());
		
		if(isNull(properties.getWriteLearningPositiveExamples()))
			properties.setWriteLearningPositiveExamples(defaults.getWriteLearningPositiveExamples());
	}
	
	private void setDefaultNumberProperties() {
		if(isNull(properties.getConsistencyMeasureThreshold()))
			properties.setConsistencyMeasureThreshold(defaults.getConsistencyMeasureThreshold());
		
		if(isNull(properties.getPrecision()))
			properties.setPrecision(defaults.getPrecision());
	}
	
	private boolean shouldSetDefault(String property) {
		return isNullOrEmpty(property);
	}
	
	private boolean shouldSetDefault(JRankParameter parameter) {
		return isNull(parameter) || parametersService.getEmptyParameter().equals(parameter);
	}
	
}
