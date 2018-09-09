package pl.jowko.rulerank.desktop.feature.properties;

import pl.jowko.rulerank.desktop.utils.Cloner;

import static java.util.Objects.isNull;
import static pl.jowko.rulerank.desktop.utils.StringUtils.isNotNullOrEmpty;
import static pl.jowko.rulerank.desktop.utils.StringUtils.isNullOrEmpty;

/**
 * This class merges properties from experiment/form with default properties. <br>
 * Result is used for running RuleRank algorithm and in form validation. <br>
 *  <br>
 * Created by Piotr on 2018-05-06.
 */
public class RunnerPropertiesProvider {
	
	private RuleRankParametersService parametersService;
	private RuleRankProperties properties;
	private RuleRankProperties defaults;
	
	/**
	 * Initialize instance of this class.
	 * @param experimentProperties from experiment or form
	 * @param defaults properties from default.properties file
	 */
	public RunnerPropertiesProvider(RuleRankProperties experimentProperties, RuleRankProperties defaults) {
		parametersService = RuleRankParametersService.getInstance();
		this.properties = Cloner.deepClone(experimentProperties);
		this.defaults = defaults;
	}
	
	/**
	 * Merge provided properties with defaults and return result. <br>
	 * If property from provided properties is null or empty, it will be replaced with default value. <br>
	 * If both provided properties and defaults have filled property A, property A is taken from provided properties. <br>
	 * All properties are checked.
	 * @return merged provided properties and default properties
	 */
	public RuleRankProperties getPropertiesWithDefaults() {
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
		if(shouldSetDefault(properties.getAllowEmptyRulesInVCDomLEM()))
			properties.setAllowEmptyRulesInVCDomLEM(defaults.getAllowEmptyRulesInVCDomLEM());
		
		if(shouldSetDefault(properties.getUseEdgeRegionsInVCDomLEM()))
			properties.setUseEdgeRegionsInVCDomLEM(defaults.getUseEdgeRegionsInVCDomLEM());
		
		if(shouldSetDefault(properties.getWriteDominationInformation()))
			properties.setWriteDominationInformation(defaults.getWriteDominationInformation());
		
		if(shouldSetDefault(properties.getWriteRulesStatistics()))
			properties.setWriteRulesStatistics(defaults.getWriteRulesStatistics());
		
		if(shouldSetDefault(properties.getWriteLearningPositiveExamples()))
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
	
	private boolean shouldSetDefault(RuleRankParameter parameter) {
		return isNull(parameter) || parametersService.getDefaultParameter().equals(parameter);
	}
	
}
