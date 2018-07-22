package pl.jowko.rulerank.desktop.feature.properties;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.jowko.rulerank.desktop.MasterTest;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;
import static pl.jowko.rulerank.desktop.feature.properties.Names.*;

/**
 * Created by Piotr on 2018-05-02.
 */
class PropertiesAssemblerTest extends MasterTest {
	
	private Properties properties;
	
	@BeforeEach
	void setUpEach() {
		properties = new Properties();
		properties.setProperty(LEARNING_DATA_FILE, "someFile.isf");
		properties.setProperty(REFERENCE_RANKING, "1, 2, 3");
	}
	
	@Test
	void shouldMapSimpleProperties() {
		JRankProperties jRankProperties = toJRankProperties(properties);
		assertEquals("someFile.isf", jRankProperties.getLearningDataFile());
		assertEquals("1, 2, 3", jRankProperties.getReferenceRanking());
	}
	
	@Test
	void shouldMapStrings() {
		properties.setProperty(PCT_FILE, "pctFile");
		properties.setProperty(PCT_APX_FILE, "pctApxFile");
		properties.setProperty(PCT_RULES_FILE, "pctRulesFile");
		properties.setProperty(PREFERENCE_GRAPH_FILE, "preferenceGraphFile");
		properties.setProperty(RANKING_FILE, "rankingFile");
		properties.setProperty(REFERENCE_RANKING, "referenceRanking");
		properties.setProperty(PAIRS, "pairs");
		
		JRankProperties jRankProperties = toJRankProperties(properties);
		
		assertEquals("pctFile", jRankProperties.getPctFile());
		assertEquals("pctApxFile", jRankProperties.getPctApxFile());
		assertEquals("pctRulesFile", jRankProperties.getPctRulesFile());
		assertEquals("preferenceGraphFile", jRankProperties.getPreferenceGraphFile());
		assertEquals("rankingFile", jRankProperties.getRankingFile());
		assertEquals("referenceRanking", jRankProperties.getReferenceRanking());
		assertEquals("pairs", jRankProperties.getPairs());
	}
	
	@Test
	void shouldMapIntegers() {
		properties.setProperty(PRECISION, "-1");
		JRankProperties jRankProperties = toJRankProperties(properties);
		assertEquals(-1, jRankProperties.getPrecision().intValue());
	}
	
	@Test
	void shouldMapBooleans() {
		properties.setProperty(ALLOW_EMPTY_RULES, "true");
		properties.setProperty(USE_EDGE_REGIONS, "TRUE");
		properties.setProperty(WRITE_DOMINATION_INFORMATION, "false");
		properties.setProperty(WRITE_LEARNING_POSITIVE_EXAMPLES, "FALSE");
		properties.setProperty(WRITE_RULES_STATISTICS, "TRASH");
		
		JRankProperties jRankProperties = toJRankProperties(properties);
		
		assertEquals("true", jRankProperties.getAllowEmptyRulesInVCDomLEM().getTextValue());
		assertEquals("true", jRankProperties.getUseEdgeRegionsInVCDomLEM().getTextValue());
		assertEquals("false", jRankProperties.getWriteDominationInformation().getTextValue());
		assertEquals("false", jRankProperties.getWriteLearningPositiveExamples().getTextValue());
		assertNull(jRankProperties.getWriteRulesStatistics());
	}
	
	@Test
	void shouldNotMapInvalidNumbers() {
		properties.setProperty(PRECISION, "1de");
		properties.setProperty(CONSISTENCY_MEASURE_THREASHOLD, "0.a1");
		
		JRankProperties jRankProperties = toJRankProperties(properties);
		
		assertNull(jRankProperties.getPrecision());
		assertNull(jRankProperties.getConsistencyMeasureThreshold());
	}
	
	@Test
	void shouldMapDoubles() {
		properties.setProperty(CONSISTENCY_MEASURE_THREASHOLD, "0.6");
		JRankProperties jRankProperties = toJRankProperties(properties);
		assertEquals(0.6, jRankProperties.getConsistencyMeasureThreshold().doubleValue());
	}
	
	@Test
	void shouldMapJRankParameters() {
		properties.setProperty(TYPE_OF_FAMILY_CRITERIA, "consistent");
		properties.setProperty(CONSISTENCY_MEASURE, "rough-membership");
		properties.setProperty(TYPE_OF_RULES, "certain");
		properties.setProperty(CONSIDERED_SET_OF_RULES, "exhaustive");
		properties.setProperty(SATISFACTION_DEGREE_IN_GRAPH, "crisp");
		properties.setProperty(FUZZY_SAT_DEGREE_CALC_METHOD, "max-credibility-x-coverage-factor");
		properties.setProperty(RANKING_PROCEDURE, "rnfs");
		properties.setProperty(DOMINANCE, "pareto");
		properties.setProperty(DOMINANCE_FOR_PAIRS, "classic");
		properties.setProperty(NEGATIVE_EXAMPLES_TREATMENT, "any");
		properties.setProperty(RULE_CONDITIONS_SELECTION_METHOD, "mix");
		properties.setProperty(OPTIMIZE_RULES_CONSISTENCY, "approximation");
		
		
		JRankProperties jRankProperties = toJRankProperties(properties);
		
		assertEquals("consistent", jRankProperties.getTypeOfFamilyOfCriteria().getTextValue());
		assertEquals("rough-membership", jRankProperties.getConsistencyMeasure().getTextValue());
		assertEquals("certain", jRankProperties.getTypeOfRules().getTextValue());
		assertEquals("exhaustive", jRankProperties.getConsideredSetOfRules().getTextValue());
		assertEquals("crisp", jRankProperties.getSatisfactionDegreesInPreferenceGraph().getTextValue());
		assertEquals("max-credibility-x-coverage-factor", jRankProperties.getFuzzySatisfactionDegreeCalculationMethod().getTextValue());
		assertEquals("rnfs", jRankProperties.getRankingProcedure().getTextValue());
		assertEquals("pareto", jRankProperties.getDominance().getTextValue());
		assertEquals("classic", jRankProperties.getDominanceForPairsOfOrdinalValues().getTextValue());
		assertEquals("any", jRankProperties.getNegativeExamplesTreatmentForVCDRSA().getTextValue());
		assertEquals("mix", jRankProperties.getRuleConditionsSelectionMethodInVCDomLEM().getTextValue());
		assertEquals("approximation", jRankProperties.getOptimizeRuleConsistencyInVCDomLEMWrt().getTextValue());
	}
	
	@Test
	void shouldMapWithComments() {
		properties.setProperty(RANKING_FILE, " rankingFile # some test comment");
		properties.setProperty(CONSISTENCY_MEASURE_THREASHOLD, " 0.8 # comment \n");
		properties.setProperty(TYPE_OF_RULES, " certain # or possible");
		properties.setProperty(ALLOW_EMPTY_RULES, " true #true|false; allow VC-DomLEM algorithm to induce rules with empty condition part if their consistency is good enough?");
		properties.setProperty(PRECISION, " -1 #integer value; denotes precision of floating-point numbers; set -1 in order to print floating-point numbers as they are, without rounding");
		
		JRankProperties jRankProperties = toJRankProperties(properties);
		
		assertEquals("rankingFile", jRankProperties.getRankingFile());
		assertEquals(0.8, jRankProperties.getConsistencyMeasureThreshold().doubleValue());
		assertEquals("certain", jRankProperties.getTypeOfRules().getTextValue());
		assertEquals("true", jRankProperties.getAllowEmptyRulesInVCDomLEM().getTextValue());
		assertEquals(-1, jRankProperties.getPrecision().intValue());
	}
	
	@Test
	void shouldMapWithSpaces() {
		properties.setProperty(RANKING_FILE, " rankingFile ");
		properties.setProperty(CONSISTENCY_MEASURE_THREASHOLD, " 0.8 \n");
		properties.setProperty(TYPE_OF_RULES, " certain ");
		properties.setProperty(ALLOW_EMPTY_RULES, " true ");
		properties.setProperty(PRECISION, " -1 ");
		
		JRankProperties jRankProperties = toJRankProperties(properties);
		
		assertEquals("rankingFile", jRankProperties.getRankingFile());
		assertEquals(0.8, jRankProperties.getConsistencyMeasureThreshold().doubleValue());
		assertEquals("certain", jRankProperties.getTypeOfRules().getTextValue());
		assertEquals("true", jRankProperties.getAllowEmptyRulesInVCDomLEM().getTextValue());
		assertEquals(-1, jRankProperties.getPrecision().intValue());
	}
	
	@Test
	void shouldMapToEmptyParameters() {
		JRankProperties jRankProperties = toJRankProperties(properties);
		
		assertEquals("", jRankProperties.getTypeOfFamilyOfCriteria().getTextValue());
		assertEquals("", jRankProperties.getConsistencyMeasure().getTextValue());
		assertEquals("", jRankProperties.getTypeOfRules().getTextValue());
		assertEquals("", jRankProperties.getConsideredSetOfRules().getTextValue());
		assertEquals("", jRankProperties.getSatisfactionDegreesInPreferenceGraph().getTextValue());
		assertEquals("", jRankProperties.getFuzzySatisfactionDegreeCalculationMethod().getTextValue());
		assertEquals("", jRankProperties.getRankingProcedure().getTextValue());
		assertEquals("", jRankProperties.getDominance().getTextValue());
		assertEquals("", jRankProperties.getDominanceForPairsOfOrdinalValues().getTextValue());
		assertEquals("", jRankProperties.getNegativeExamplesTreatmentForVCDRSA().getTextValue());
		assertEquals("", jRankProperties.getRuleConditionsSelectionMethodInVCDomLEM().getTextValue());
		assertEquals("", jRankProperties.getOptimizeRuleConsistencyInVCDomLEMWrt().getTextValue());
	}
	
	@Test
	void shouldMapToNulls() {
		JRankProperties jRankProperties = toJRankProperties(properties);
		
		assertNull(jRankProperties.getRankingFile());
		assertNull(jRankProperties.getConsistencyMeasureThreshold());
		assertNull(jRankProperties.getPrecision());
	}
	
	@Test
	void shouldMapToNullsWhenWrongParameters() {
		properties.setProperty(TYPE_OF_FAMILY_CRITERIA, "some key");
		properties.setProperty(CONSISTENCY_MEASURE, "other key");
		properties.setProperty(TYPE_OF_RULES, "not a key");
		properties.setProperty(CONSIDERED_SET_OF_RULES, "SuperKey");
		
		JRankProperties jRankProperties = toJRankProperties(properties);
		
		assertNull(jRankProperties.getTypeOfFamilyOfCriteria());
		assertNull(jRankProperties.getConsistencyMeasure());
		assertNull(jRankProperties.getTypeOfRules());
		assertNull(jRankProperties.getConsideredSetOfRules());
	}
	
	@Test
	void shouldWriteLogWhenPropertyIsNotRecognized() {
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		
		properties.setProperty("SomeUnknownProperty", "Key");
		toJRankProperties(properties);
		
		assertTrue(outContent.toString().contains("WARN") && outContent.toString().contains("SomeUnknownProperty"));
		
		System.setOut(System.out);
	}
	
	private JRankProperties toJRankProperties(Properties properties) {
		PropertiesAssembler assembler = new PropertiesAssembler(properties);
		return assembler.toJrankProperties();
	}
	
}
