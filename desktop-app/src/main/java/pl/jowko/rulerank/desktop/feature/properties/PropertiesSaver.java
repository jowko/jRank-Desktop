package pl.jowko.rulerank.desktop.feature.properties;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static pl.jowko.rulerank.desktop.feature.properties.Names.*;
import static pl.jowko.rulerank.desktop.utils.BooleanUtils.not;
import static pl.jowko.rulerank.desktop.utils.FileExtensionExtractor.getExtension;
import static pl.jowko.rulerank.desktop.utils.StringUtils.isNotNullOrEmpty;

/**
 * Created by Piotr on 2018-05-04.
 * This class is used to save properties in .properties file.
 * It converts RuleRankProperties back to Properties objects and replace old properties file with new one.
 * @see Properties
 * @see RuleRankProperties
 */
public class PropertiesSaver {
	
	private RuleRankParameter emptyParameter;
	private RuleRankProperties ruleRankProperties;
	private Properties properties;
	
	/**
	 * Creates instance of this class and converts RuleRankProperties to properties.
	 * @param ruleRankProperties with will be saved.
	 */
	public PropertiesSaver(RuleRankProperties ruleRankProperties) {
		emptyParameter = RuleRankParametersService.getInstance().getEmptyParameter();
		this.ruleRankProperties = ruleRankProperties;
		properties = new Properties();
		initializeProperties();
	}
	
	/**
	 * Save properties to file on provided file path.
	 * @param filePath where file will be saved
	 * @throws IOException when something goes wrong with saving file
	 */
	public void save(String filePath) throws IOException {
		properties.store(new FileOutputStream(filePath), null);
	}
	
	Properties getProperties() {
		return properties;
	}
	
	/**
	 * Converts all RuleRankProperties to Properties object.
	 */
	private void initializeProperties() {
		setFilePathProperty(LEARNING_DATA_FILE, ruleRankProperties.getLearningDataFile(), "isf");
		setFilePathProperty(TEST_DATA_FILE, ruleRankProperties.getTestDataFile(), "isf");
		setFilePathProperty(PCT_FILE, ruleRankProperties.getPctFile(), "isf");
		setFilePathProperty(PCT_APX_FILE, ruleRankProperties.getPctApxFile(), "apx");
		setFilePathProperty(PCT_RULES_FILE, ruleRankProperties.getPctRulesFile(), "rules");
		setFilePathProperty(PREFERENCE_GRAPH_FILE, ruleRankProperties.getPreferenceGraphFile(), "graph");
		setFilePathProperty(RANKING_FILE, ruleRankProperties.getRankingFile(), "ranking");
		
		setStringProperty(REFERENCE_RANKING, ruleRankProperties.getReferenceRanking());
		setStringProperty(PAIRS, ruleRankProperties.getPairs());
		
		setParameterProperty(TYPE_OF_FAMILY_CRITERIA, ruleRankProperties.getTypeOfFamilyOfCriteria());
		setParameterProperty(CONSISTENCY_MEASURE, ruleRankProperties.getConsistencyMeasure());
		setNumberProperty(CONSISTENCY_MEASURE_THREASHOLD, ruleRankProperties.getConsistencyMeasureThreshold());
		setParameterProperty(TYPE_OF_RULES, ruleRankProperties.getTypeOfRules());
		setParameterProperty(CONSIDERED_SET_OF_RULES, ruleRankProperties.getConsideredSetOfRules());
		setParameterProperty(SATISFACTION_DEGREE_IN_GRAPH, ruleRankProperties.getSatisfactionDegreesInPreferenceGraph());
		setParameterProperty(FUZZY_SAT_DEGREE_CALC_METHOD, ruleRankProperties.getFuzzySatisfactionDegreeCalculationMethod());
		setParameterProperty(RANKING_PROCEDURE, ruleRankProperties.getRankingProcedure());
		setParameterProperty(DOMINANCE, ruleRankProperties.getDominance());
		setParameterProperty(DOMINANCE_FOR_PAIRS, ruleRankProperties.getDominanceForPairsOfOrdinalValues());
		setParameterProperty(NEGATIVE_EXAMPLES_TREATMENT, ruleRankProperties.getNegativeExamplesTreatmentForVCDRSA());
		setParameterProperty(RULE_CONDITIONS_SELECTION_METHOD, ruleRankProperties.getRuleConditionsSelectionMethodInVCDomLEM());
		setParameterProperty(ALLOW_EMPTY_RULES, ruleRankProperties.getAllowEmptyRulesInVCDomLEM());
		setParameterProperty(USE_EDGE_REGIONS, ruleRankProperties.getUseEdgeRegionsInVCDomLEM());
		setParameterProperty(OPTIMIZE_RULES_CONSISTENCY, ruleRankProperties.getOptimizeRuleConsistencyInVCDomLEMWrt());
		
		setParameterProperty(WRITE_DOMINATION_INFORMATION, ruleRankProperties.getWriteDominationInformation());
		setParameterProperty(WRITE_RULES_STATISTICS, ruleRankProperties.getWriteRulesStatistics());
		setParameterProperty(WRITE_LEARNING_POSITIVE_EXAMPLES, ruleRankProperties.getWriteLearningPositiveExamples());
		
		setNumberProperty(PRECISION, ruleRankProperties.getPrecision());
	}
	
	private void setStringProperty(String propertyName, String value) {
		if(isNotNullOrEmpty(value)) {
			properties.setProperty(propertyName, value);
		}
	}
	
	/**
	 * Sets filePath property.
	 * If user typed property with no file extension, it will be added to property value.
	 * @see PropertiesValidator
	 * @see pl.jowko.rulerank.desktop.utils.FileExtensionExtractor
	 */
	private void setFilePathProperty(String propertyName, String value, String extension) {
		if(isNotNullOrEmpty(value)) {
			String ext = getExtension(value);
			if(not(value.contains('.' + extension)) && (isNull(ext) || ext.isEmpty() || not(ext.equals(extension))))
				value += '.' + extension;
			
			properties.setProperty(propertyName, value);
		}
	}
	
	private void setParameterProperty(String propertyName, RuleRankParameter value) {
		if(isParameterFilled(value)) {
			properties.setProperty(propertyName, value.getTextValue());
		}
	}
	
	private void setNumberProperty(String propertyName, Object value) {
		if(nonNull(value)) {
			properties.setProperty(propertyName, value.toString());
		}
	}
	
	private boolean isParameterFilled(RuleRankParameter parameter) {
		return nonNull(parameter) && not(emptyParameter.equals(parameter));
	}
	
}
