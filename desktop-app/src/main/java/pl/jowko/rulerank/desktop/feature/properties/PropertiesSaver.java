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
 * It converts JRankProperties back to Properties objects and replace old properties file with new one.
 * @see Properties
 * @see JRankProperties
 */
public class PropertiesSaver {
	
	private JRankParameter emptyParameter;
	private JRankProperties jRankProperties;
	private Properties properties;
	
	/**
	 * Creates instance of this class and converts JRankProperties to properties.
	 * @param jRankProperties with will be saved.
	 */
	public PropertiesSaver(JRankProperties jRankProperties) {
		emptyParameter = JRankParametersService.getInstance().getEmptyParameter();
		this.jRankProperties = jRankProperties;
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
	 * Converts all JRankProperties to Properties object.
	 */
	private void initializeProperties() {
		setFilePathProperty(LEARNING_DATA_FILE, jRankProperties.getLearningDataFile(), "isf");
		setFilePathProperty(TEST_DATA_FILE, jRankProperties.getTestDataFile(), "isf");
		setFilePathProperty(PCT_FILE, jRankProperties.getPctFile(), "isf");
		setFilePathProperty(PCT_APX_FILE, jRankProperties.getPctApxFile(), "apx");
		setFilePathProperty(PCT_RULES_FILE, jRankProperties.getPctRulesFile(), "rules");
		setFilePathProperty(PREFERENCE_GRAPH_FILE, jRankProperties.getPreferenceGraphFile(), "graph");
		setFilePathProperty(RANKING_FILE, jRankProperties.getRankingFile(), "ranking");
		
		setStringProperty(REFERENCE_RANKING, jRankProperties.getReferenceRanking());
		setStringProperty(PAIRS, jRankProperties.getPairs());
		
		setJRankParameterProperty(TYPE_OF_FAMILY_CRITERIA, jRankProperties.getTypeOfFamilyOfCriteria());
		setJRankParameterProperty(CONSISTENCY_MEASURE, jRankProperties.getConsistencyMeasure());
		setNumberProperty(CONSISTENCY_MEASURE_THREASHOLD, jRankProperties.getConsistencyMeasureThreshold());
		setJRankParameterProperty(TYPE_OF_RULES, jRankProperties.getTypeOfRules());
		setJRankParameterProperty(CONSIDERED_SET_OF_RULES, jRankProperties.getConsideredSetOfRules());
		setJRankParameterProperty(SATISFACTION_DEGREE_IN_GRAPH, jRankProperties.getSatisfactionDegreesInPreferenceGraph());
		setJRankParameterProperty(FUZZY_SAT_DEGREE_CALC_METHOD, jRankProperties.getFuzzySatisfactionDegreeCalculationMethod());
		setJRankParameterProperty(RANKING_PROCEDURE, jRankProperties.getRankingProcedure());
		setJRankParameterProperty(DOMINANCE, jRankProperties.getDominance());
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
