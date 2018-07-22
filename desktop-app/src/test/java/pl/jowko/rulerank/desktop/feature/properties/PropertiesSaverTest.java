package pl.jowko.rulerank.desktop.feature.properties;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.jowko.rulerank.desktop.MasterTest;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static pl.jowko.rulerank.desktop.feature.properties.Names.*;
import static pl.jowko.rulerank.desktop.feature.properties.TestPropertiesProvider.*;

/**
 * Created by Piotr on 2018-05-05.
 */
class PropertiesSaverTest extends MasterTest {
	
	private RuleRankProperties ruleRankProperties;
	
	@BeforeEach
	void setUpEach() {
		ruleRankProperties = new RuleRankProperties();
	}
	
	@Test
	void shouldMapStringProperty() {
		ruleRankProperties.setLearningDataFile(dataFile);
		ruleRankProperties.setPreferenceGraphFile(graphFile);
		Properties properties = getProperties();
		
		assertEquals(dataFile, properties.getProperty(LEARNING_DATA_FILE));
		assertEquals(graphFile, properties.getProperty(PREFERENCE_GRAPH_FILE));
	}
	
	@Test
	void shouldMapNullStringProperty() {
		ruleRankProperties.setRankingFile(null);
		Properties properties = getProperties();
		assertNull(properties.getProperty(RANKING_FILE));
	}
	
	@Test
	void shouldMapEmptyStringProperty() {
		ruleRankProperties.setRankingFile("");
		Properties properties = getProperties();
		assertNull(properties.getProperty(RANKING_FILE));
	}
	
	@Test
	void shouldMapDoubleProperty() {
		ruleRankProperties.setConsistencyMeasureThreshold(0.5d);
		Properties properties = getProperties();
		assertEquals(Double.valueOf(0.5d), Double.valueOf(properties.getProperty(CONSISTENCY_MEASURE_THREASHOLD)));
	}
	
	@Test
	void shouldNotMapNullDoubleProperty() {
		ruleRankProperties.setConsistencyMeasureThreshold(null);
		Properties properties = getProperties();
		assertNull(properties.getProperty(CONSISTENCY_MEASURE_THREASHOLD));
	}
	
	@Test
	void shouldMapIntegerProperty() {
		ruleRankProperties.setPrecision(-1);
		Properties properties = getProperties();
		assertEquals(Integer.valueOf(-1), Integer.valueOf(properties.getProperty(PRECISION)));
	}
	
	@Test
	void shouldNotMapNullIntegerProperty() {
		ruleRankProperties.setPrecision(null);
		Properties properties = getProperties();
		assertNull(properties.getProperty(PRECISION));
	}
	
	@Test
	void shouldMapBooleanProperty() {
		ruleRankProperties.setWriteLearningPositiveExamples(createParameter("true"));
		Properties properties = getProperties();
		assertEquals("true", properties.getProperty(WRITE_LEARNING_POSITIVE_EXAMPLES));
	}
	
	@Test
	void shouldNotMapNullBooleanProperty() {
		ruleRankProperties.setWriteLearningPositiveExamples(createParameter("false"));
		ruleRankProperties.setWriteRulesStatistics(null);
		Properties properties = getProperties();
		
		assertEquals("false", properties.getProperty(WRITE_LEARNING_POSITIVE_EXAMPLES));
		assertNull(properties.getProperty(WRITE_RULES_STATISTICS));
	}
	
	@Test
	void shouldMapParameter() {
		ruleRankProperties.setConsistencyMeasure(createParameter(consistencyMeasure));
		Properties properties = getProperties();
		assertEquals(consistencyMeasure, properties.getProperty(CONSISTENCY_MEASURE));
	}
	
	@Test
	void shouldNotMapNullParameter() {
		ruleRankProperties.setConsistencyMeasure(null);
		Properties properties = getProperties();
		assertNull(properties.getProperty(CONSISTENCY_MEASURE));
	}
	
	@Test
	void shouldNotMapEmptyParameter() {
		ruleRankProperties.setConsistencyMeasure(RuleRankParametersService.getInstance().getEmptyParameter());
		Properties properties = getProperties();
		assertNull(properties.getProperty(CONSISTENCY_MEASURE));
	}
	
	@Test
	void shouldMapAllProperties() {
		ruleRankProperties = getFullProperties();
		Properties properties = getProperties();
		
		assertEquals(dataFile, properties.getProperty(LEARNING_DATA_FILE));
		assertEquals(testDataFile, properties.getProperty(TEST_DATA_FILE));
		assertEquals(pctFile, properties.getProperty(PCT_FILE));
		assertEquals(pctApxFile, properties.getProperty(PCT_APX_FILE));
		assertEquals(pctRulesFile, properties.getProperty(PCT_RULES_FILE));
		assertEquals(rankingFile, properties.getProperty(RANKING_FILE));
		assertEquals(graphFile, properties.getProperty(PREFERENCE_GRAPH_FILE));
		assertEquals(referenceRanking, properties.getProperty(REFERENCE_RANKING));
		assertEquals(pairs, properties.getProperty(PAIRS));
		
		assertEquals(typeOfFamilyOfCriteria, properties.getProperty(TYPE_OF_FAMILY_CRITERIA));
		assertEquals(consistencyMeasure, properties.getProperty(CONSISTENCY_MEASURE));
		assertEquals(consistencyMeasureThreshold.toString(), properties.getProperty(CONSISTENCY_MEASURE_THREASHOLD));
		assertEquals(consideredSetOfRules, properties.getProperty(CONSIDERED_SET_OF_RULES));
		assertEquals(satisfactionDegreesInPreferenceGraph, properties.getProperty(SATISFACTION_DEGREE_IN_GRAPH));
		assertEquals(fuzzySatisfactionDegreeCalculationMethod, properties.getProperty(FUZZY_SAT_DEGREE_CALC_METHOD));
		assertEquals(rankingProcedure, properties.getProperty(RANKING_PROCEDURE));
		assertEquals(dominance, properties.getProperty(DOMINANCE));
		assertEquals(dominanceForPairsOfOrdinalValues, properties.getProperty(DOMINANCE_FOR_PAIRS));
		assertEquals(negativeExamplesTreatmentForVCDRSA, properties.getProperty(NEGATIVE_EXAMPLES_TREATMENT));
		assertEquals(ruleConditionsSelectionMethodInVCDomLEM, properties.getProperty(RULE_CONDITIONS_SELECTION_METHOD));
		assertEquals("true", properties.getProperty(ALLOW_EMPTY_RULES));
		assertEquals("false", properties.getProperty(USE_EDGE_REGIONS));
		assertEquals(optimizeRuleConsistencyInVCDomLEMWrt, properties.getProperty(OPTIMIZE_RULES_CONSISTENCY));
		
		assertEquals("true", properties.getProperty(WRITE_DOMINATION_INFORMATION));
		assertEquals("true", properties.getProperty(WRITE_RULES_STATISTICS));
		assertEquals("false", properties.getProperty(WRITE_LEARNING_POSITIVE_EXAMPLES));
		
		assertEquals(precision.toString(), properties.getProperty(PRECISION));
	}
	
	@Test
	void shouldAddExtensionsToFiles() {
		ruleRankProperties = getFullProperties();
		ruleRankProperties.setLearningDataFile("data");
		ruleRankProperties.setTestDataFile("test");
		ruleRankProperties.setPctFile("pct");
		ruleRankProperties.setPctApxFile("pct");
		ruleRankProperties.setPctRulesFile("pct");
		ruleRankProperties.setRankingFile("test");
		ruleRankProperties.setPreferenceGraphFile("test");
		
		Properties properties = getProperties();
		
		assertEquals("data.isf", properties.getProperty(LEARNING_DATA_FILE));
		assertEquals("test.isf", properties.getProperty(TEST_DATA_FILE));
		assertEquals("pct.isf", properties.getProperty(PCT_FILE));
		assertEquals("pct.apx", properties.getProperty(PCT_APX_FILE));
		assertEquals("pct.rules", properties.getProperty(PCT_RULES_FILE));
		assertEquals("test.ranking", properties.getProperty(RANKING_FILE));
		assertEquals("test.graph", properties.getProperty(PREFERENCE_GRAPH_FILE));
	}
	
	@Test
	void shouldNotAddExtensionsToFiles() {
		ruleRankProperties = getFullProperties();
		ruleRankProperties.setLearningDataFile("data.isf.");
		ruleRankProperties.setTestDataFile("test.isf.");
		ruleRankProperties.setPctFile("pct.isf.");
		ruleRankProperties.setPctApxFile("pct.apx.");
		ruleRankProperties.setPctRulesFile("pct.rules.");
		ruleRankProperties.setRankingFile("test.ranking.");
		ruleRankProperties.setPreferenceGraphFile("test.graph.");
		
		Properties properties = getProperties();
		
		assertEquals("data.isf.", properties.getProperty(LEARNING_DATA_FILE));
		assertEquals("test.isf.", properties.getProperty(TEST_DATA_FILE));
		assertEquals("pct.isf.", properties.getProperty(PCT_FILE));
		assertEquals("pct.apx.", properties.getProperty(PCT_APX_FILE));
		assertEquals("pct.rules.", properties.getProperty(PCT_RULES_FILE));
		assertEquals("test.ranking.", properties.getProperty(RANKING_FILE));
		assertEquals("test.graph.", properties.getProperty(PREFERENCE_GRAPH_FILE));
	}
	
	private Properties getProperties() {
		PropertiesSaver saver = new PropertiesSaver(ruleRankProperties);
		return saver.getProperties();
	}
	
}
