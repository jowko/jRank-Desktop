package pl.jowko.jrank.desktop.feature.properties;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import static java.util.Objects.nonNull;
import static pl.jowko.jrank.desktop.feature.properties.Names.*;
import static pl.jowko.jrank.desktop.utils.BooleanUtils.not;
import static pl.jowko.jrank.desktop.utils.StringUtils.isNotNullOrEmpty;

/**
 * Created by Piotr on 2018-05-04.
 */
class PropertiesSaver {
	
	private JRankParameter emptyParameter;
	private JRankProperties jRankProperties;
	private Properties properties;
	
	PropertiesSaver(JRankProperties jRankProperties) {
		emptyParameter = JRankParametersService.getInstance().getEmptyParameter();
		this.jRankProperties = jRankProperties;
		properties = new Properties();
		initializeProperties();
	}
	
	void save(String filePath) throws IOException {
		properties.store(new FileOutputStream(filePath), null);
	}
	
	Properties getProperties() {
		return properties;
	}
	
	private void initializeProperties() {
		setStringProperty(LEARNING_DATA_FILE, jRankProperties.getLearningDataFile());
		setStringProperty(TEST_DATA_FILE, jRankProperties.getTestDataFile());
		setStringProperty(PCT_FILE, jRankProperties.getPctFile());
		setStringProperty(PCT_APX_FILE, jRankProperties.getPctApxFile());
		setStringProperty(PCT_RULES_FILE, jRankProperties.getPctRulesFile());
		setStringProperty(PREFERENCE_GRAPH_FILE, jRankProperties.getPreferenceGraphFile());
		setStringProperty(RANKING_FILE, jRankProperties.getRankingFile());
		
		setStringProperty(REFERENCE_RANKING, jRankProperties.getReferenceRanking());
		setStringProperty(PAIRS, jRankProperties.getPairs());
		
		setJRankParameterProperty(TYPE_OF_FAMILY_CRITERIA, jRankProperties.getTypeOfFamilyOfCriteria());
		setJRankParameterProperty(CONSISTENCY_MEASURE, jRankProperties.getConsistencyMeasure());
		setNumberProperty(CONSISTENCY_MEASURE_THREASHOLD, jRankProperties.getConsistencyMeasureThreshold());
		setJRankParameterProperty(TYPE_OF_RULES, jRankProperties.getTypeOfRules());
		setJRankParameterProperty(CONSIDERED_SET_OF_RULES, jRankProperties.getConsideredSetOfRules());
		setJRankParameterProperty(SATISTACTION_DEGREE_IN_GRAPH, jRankProperties.getSatisfactionDegreesInPreferenceGraph());
		setJRankParameterProperty(FUZZY_SAT_DEGREE_CALC_METHOD, jRankProperties.getFuzzySatisfactionDegreeCalculationMethod());
		setJRankParameterProperty(RANKING_PROCEDURE, jRankProperties.getRankingProcedure());
		setJRankParameterProperty(DOMINACE, jRankProperties.getDominance());
		setJRankParameterProperty(DOMINANCE_FOR_PAIRS, jRankProperties.getDominanceForPairsOfOrdinalValues());
		setJRankParameterProperty(NEGATIVE_EXAMPLES_TREATMENT, jRankProperties.getNegativeExamplesTreatmentForVCDRSA());
		setJRankParameterProperty(RULE_CONDITIONS_SELECTION_METHOD, jRankProperties.getRuleConditionsSelectionMethodInVCDomLEM());
		setJRankParameterProperty(ALLOW_EMPTY_RULES, jRankProperties.getAllowEmptyRulesInVCDomLEM());
		setJRankParameterProperty(USE_EDGE_REGIONS, jRankProperties.getUseEdgeRegionsInVCDomLEM());
		setJRankParameterProperty(OPTIMIZE_RULES_CONSISTENCY, jRankProperties.getOptimizeRuleConsistencyInVCDomLEMWrt());
		
		setJRankParameterProperty(WRITE_DOMINATION_INFORMATION, jRankProperties.getWriteDominationInformation());
		setJRankParameterProperty(WRITE_RULES_STATISTICS, jRankProperties.getWriteRulesStatistics());
		setJRankParameterProperty(WRITE_LEARNING_POSITIVE_EXAMPLES, jRankProperties.getWriteLearningPositiveExamples());
		
		setNumberProperty(PRECISION, jRankProperties.getPrecision());
	}
	
	private void setStringProperty(String propertyName, String value) {
		if(isNotNullOrEmpty(value)) {
			properties.setProperty(propertyName, value);
		}
	}
	
	private void setJRankParameterProperty(String propertyName, JRankParameter value) {
		if(isParameterFilled(value)) {
			properties.setProperty(propertyName, value.getTextValue());
		}
	}
	
	private void setNumberProperty(String propertyName, Object value) {
		if(nonNull(value)) {
			properties.setProperty(propertyName, value.toString());
		}
	}
	
	private boolean isParameterFilled(JRankParameter parameter) {
		return nonNull(parameter) && not(emptyParameter.equals(parameter));
	}
	
}
