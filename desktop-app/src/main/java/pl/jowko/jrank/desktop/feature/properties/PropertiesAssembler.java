package pl.jowko.jrank.desktop.feature.properties;

import pl.jowko.jrank.logger.JRankLogger;

import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static pl.jowko.jrank.desktop.feature.properties.Names.*;
import static pl.jowko.jrank.desktop.utils.BooleanUtils.not;

/**
 * Created by Piotr on 2018-04-29.
 */
public class PropertiesAssembler {
	
	private static Pattern commentsPattern = Pattern.compile("[ \t]*#.*");
	
	private Properties properties;
	private JRankParametersService paramService;
	
	public PropertiesAssembler(Properties properties) {
		this.properties = properties;
		this.paramService = JRankParametersService.getInstance();
	}
	
	public JRankProperties toJrankProperties() {
		JRankProperties prop = new JRankProperties();
		
		prop.setLearningDataFile(getStringFromProperty(LEARNING_DATA_FILE));
		prop.setTestDataFile(getStringFromProperty(TEST_DATA_FILE));
		
		prop.setPctFile(getStringFromProperty(PCT_FILE));
		prop.setPctApxFile(getStringFromProperty(PCT_APX_FILE));
		prop.setPctRulesFile(getStringFromProperty(PCT_RULES_FILE));
		
		prop.setPreferenceGraphFile(getStringFromProperty(PREFERENCE_GRAPH_FILE));
		prop.setRankingFile(getStringFromProperty(RANKING_FILE));
		
		prop.setReferenceRanking(getStringFromProperty(REFERENCE_RANKING));
		prop.setPairs(getStringFromProperty(PAIRS));
		
		prop.setTypeOfFamilyOfCriteria(getParameter(paramService.getTypeOfFamilyOfCriteria(), TYPE_OF_FAMILY_CRITERIA));
		prop.setConsistencyMeasure(getParameter(paramService.getConsistencyMeasure(), CONSISTENCY_MEASURE));
		prop.setConsistencyMeasureThreshold(getDoubleFromProperty(CONSISTENCY_MEASURE_THREASHOLD));
		prop.setTypeOfRules(getParameter(paramService.getTypeOfRules(), TYPE_OF_RULES));
		prop.setConsideredSetOfRules(getParameter(paramService.getConsideredSetOfRules(), CONSIDERED_SET_OF_RULES));
		prop.setSatisfactionDegreesInPreferenceGraph(getParameter(paramService.getSatisfactionDegreesInPreferenceGraph(), SATISFACTION_DEGREE_IN_GRAPH));
		prop.setFuzzySatisfactionDegreeCalculationMethod(getParameter(paramService.getFuzzySatisfactionDegreeCalculationMethod(), FUZZY_SAT_DEGREE_CALC_METHOD));
		prop.setRankingProcedure(getParameter(paramService.getRankingProcedure(), RANKING_PROCEDURE));
		prop.setDominance(getParameter(paramService.getDominance(), DOMINANCE));
		prop.setDominanceForPairsOfOrdinalValues(getParameter(paramService.getDominanceForPairsOfOrdinalValues(), DOMINANCE_FOR_PAIRS));
		prop.setNegativeExamplesTreatmentForVCDRSA(getParameter(paramService.getNegativeExamplesTreatmentForVCDRSA(), NEGATIVE_EXAMPLES_TREATMENT));
		prop.setRuleConditionsSelectionMethodInVCDomLEM(getParameter(paramService.getRuleConditionsSelectionMethodInVCDomLEM(), RULE_CONDITIONS_SELECTION_METHOD));
		prop.setAllowEmptyRulesInVCDomLEM(getParameter(paramService.getBooleanParameter(), ALLOW_EMPTY_RULES));
		prop.setUseEdgeRegionsInVCDomLEM(getParameter(paramService.getBooleanParameter(), USE_EDGE_REGIONS));
		prop.setOptimizeRuleConsistencyInVCDomLEMWrt(getParameter(paramService.getOptimizeRuleConsistencyInVCDomLEMWrt(), OPTIMIZE_RULES_CONSISTENCY));
		
		prop.setWriteDominationInformation(getParameter(paramService.getBooleanParameter(), WRITE_DOMINATION_INFORMATION));
		prop.setWriteRulesStatistics(getParameter(paramService.getBooleanParameter(), WRITE_RULES_STATISTICS));
		prop.setWriteLearningPositiveExamples(getParameter(paramService.getBooleanParameter(), WRITE_LEARNING_POSITIVE_EXAMPLES));
		
		prop.setPrecision(getIntegerFromProperty(PRECISION));
		
		validatePropertiesAndShowWarnings();
		
		return prop;
	}
	
	private String getStringFromProperty(String parameterName) {
		String parameterValue = deleteTrailingWhiteSpacesAndComment(properties.getProperty(parameterName));
		properties.keySet().remove(parameterName);
		if(nonNull(parameterValue) && parameterValue.isEmpty())
			return null;
		return parameterValue;
	}
	
	private Double getDoubleFromProperty(String parameterName) {
		String parameterValue = getStringFromProperty(parameterName);
		
		if(isNull(parameterValue))
			return null;
		
		try{
			return Double.valueOf(parameterValue);
		} catch(NumberFormatException e) {
			logNumberError(parameterName, parameterValue);
		}
		return null;
	}
	
	private Integer getIntegerFromProperty(String parameterName) {
		String parameterValue = getStringFromProperty(parameterName);
		
		if(isNull(parameterValue))
			return null;
		
		try{
			return Integer.valueOf(parameterValue);
		} catch(NumberFormatException e) {
			logNumberError(parameterName, parameterValue);
		}
		return null;
	}
	
	private void logNumberError(String parameterName, String parameterValue) {
		JRankLogger.error("Error when reading parameter: [" + parameterName + "]. Value: [" + parameterValue + "] is not a valid number.");
	}
	
	private JRankParameter getParameter(List<JRankParameter> parameters, String parameterName) {
		String parameterValue = getStringFromProperty(parameterName);
		
		if(isNull(parameterValue))
			return paramService.getEmptyParameter();
		
		JRankParameter parameter = paramService.findByTextValue(parameters, parameterValue);
		if(isNull(parameter)) {
			JRankLogger.warn("Value: [" + parameterValue + "] for property: [" + parameterName + "] is not recognized.");
		}
		
		return parameter;
	}
	
	private String deleteTrailingWhiteSpacesAndComment(String value) {
		if (value == null) {
			return null;
		}
		return commentsPattern.matcher(value).replaceFirst("").trim();
	}
	
	private void validatePropertiesAndShowWarnings() {
		if(not(properties.keySet().isEmpty())) {
			JRankLogger.warn("Some properties were not recognized: " + properties + ". Check spelling of properties names.");
		}
	}
	
}
