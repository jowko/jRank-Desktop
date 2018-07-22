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
		RuleRankProperties ruleRankProperties = toRuleRankProperties(properties);
		assertEquals("someFile.isf", ruleRankProperties.getLearningDataFile());
		assertEquals("1, 2, 3", ruleRankProperties.getReferenceRanking());
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
		
		RuleRankProperties ruleRankProperties = toRuleRankProperties(properties);
		
		assertEquals("pctFile", ruleRankProperties.getPctFile());
		assertEquals("pctApxFile", ruleRankProperties.getPctApxFile());
		assertEquals("pctRulesFile", ruleRankProperties.getPctRulesFile());
		assertEquals("preferenceGraphFile", ruleRankProperties.getPreferenceGraphFile());
		assertEquals("rankingFile", ruleRankProperties.getRankingFile());
		assertEquals("referenceRanking", ruleRankProperties.getReferenceRanking());
		assertEquals("pairs", ruleRankProperties.getPairs());
	}
	
	@Test
	void shouldMapIntegers() {
		properties.setProperty(PRECISION, "-1");
		RuleRankProperties ruleRankProperties = toRuleRankProperties(properties);
		assertEquals(-1, ruleRankProperties.getPrecision().intValue());
	}
	
	@Test
	void shouldMapBooleans() {
		properties.setProperty(ALLOW_EMPTY_RULES, "true");
		properties.setProperty(USE_EDGE_REGIONS, "TRUE");
		properties.setProperty(WRITE_DOMINATION_INFORMATION, "false");
		properties.setProperty(WRITE_LEARNING_POSITIVE_EXAMPLES, "FALSE");
		properties.setProperty(WRITE_RULES_STATISTICS, "TRASH");
		
		RuleRankProperties ruleRankProperties = toRuleRankProperties(properties);
		
		assertEquals("true", ruleRankProperties.getAllowEmptyRulesInVCDomLEM().getTextValue());
		assertEquals("true", ruleRankProperties.getUseEdgeRegionsInVCDomLEM().getTextValue());
		assertEquals("false", ruleRankProperties.getWriteDominationInformation().getTextValue());
		assertEquals("false", ruleRankProperties.getWriteLearningPositiveExamples().getTextValue());
		assertNull(ruleRankProperties.getWriteRulesStatistics());
	}
	
	@Test
	void shouldNotMapInvalidNumbers() {
		properties.setProperty(PRECISION, "1de");
		properties.setProperty(CONSISTENCY_MEASURE_THREASHOLD, "0.a1");
		
		RuleRankProperties ruleRankProperties = toRuleRankProperties(properties);
		
		assertNull(ruleRankProperties.getPrecision());
		assertNull(ruleRankProperties.getConsistencyMeasureThreshold());
	}
	
	@Test
	void shouldMapDoubles() {
		properties.setProperty(CONSISTENCY_MEASURE_THREASHOLD, "0.6");
		RuleRankProperties ruleRankProperties = toRuleRankProperties(properties);
		assertEquals(0.6, ruleRankProperties.getConsistencyMeasureThreshold().doubleValue());
	}
	
	@Test
	void shouldMapParameters() {
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
		
		
		RuleRankProperties ruleRankProperties = toRuleRankProperties(properties);
		
		assertEquals("consistent", ruleRankProperties.getTypeOfFamilyOfCriteria().getTextValue());
		assertEquals("rough-membership", ruleRankProperties.getConsistencyMeasure().getTextValue());
		assertEquals("certain", ruleRankProperties.getTypeOfRules().getTextValue());
		assertEquals("exhaustive", ruleRankProperties.getConsideredSetOfRules().getTextValue());
		assertEquals("crisp", ruleRankProperties.getSatisfactionDegreesInPreferenceGraph().getTextValue());
		assertEquals("max-credibility-x-coverage-factor", ruleRankProperties.getFuzzySatisfactionDegreeCalculationMethod().getTextValue());
		assertEquals("rnfs", ruleRankProperties.getRankingProcedure().getTextValue());
		assertEquals("pareto", ruleRankProperties.getDominance().getTextValue());
		assertEquals("classic", ruleRankProperties.getDominanceForPairsOfOrdinalValues().getTextValue());
		assertEquals("any", ruleRankProperties.getNegativeExamplesTreatmentForVCDRSA().getTextValue());
		assertEquals("mix", ruleRankProperties.getRuleConditionsSelectionMethodInVCDomLEM().getTextValue());
		assertEquals("approximation", ruleRankProperties.getOptimizeRuleConsistencyInVCDomLEMWrt().getTextValue());
	}
	
	@Test
	void shouldMapWithComments() {
		properties.setProperty(RANKING_FILE, " rankingFile # some test comment");
		properties.setProperty(CONSISTENCY_MEASURE_THREASHOLD, " 0.8 # comment \n");
		properties.setProperty(TYPE_OF_RULES, " certain # or possible");
		properties.setProperty(ALLOW_EMPTY_RULES, " true #true|false; allow VC-DomLEM algorithm to induce rules with empty condition part if their consistency is good enough?");
		properties.setProperty(PRECISION, " -1 #integer value; denotes precision of floating-point numbers; set -1 in order to print floating-point numbers as they are, without rounding");
		
		RuleRankProperties ruleRankProperties = toRuleRankProperties(properties);
		
		assertEquals("rankingFile", ruleRankProperties.getRankingFile());
		assertEquals(0.8, ruleRankProperties.getConsistencyMeasureThreshold().doubleValue());
		assertEquals("certain", ruleRankProperties.getTypeOfRules().getTextValue());
		assertEquals("true", ruleRankProperties.getAllowEmptyRulesInVCDomLEM().getTextValue());
		assertEquals(-1, ruleRankProperties.getPrecision().intValue());
	}
	
	@Test
	void shouldMapWithSpaces() {
		properties.setProperty(RANKING_FILE, " rankingFile ");
		properties.setProperty(CONSISTENCY_MEASURE_THREASHOLD, " 0.8 \n");
		properties.setProperty(TYPE_OF_RULES, " certain ");
		properties.setProperty(ALLOW_EMPTY_RULES, " true ");
		properties.setProperty(PRECISION, " -1 ");
		
		RuleRankProperties ruleRankProperties = toRuleRankProperties(properties);
		
		assertEquals("rankingFile", ruleRankProperties.getRankingFile());
		assertEquals(0.8, ruleRankProperties.getConsistencyMeasureThreshold().doubleValue());
		assertEquals("certain", ruleRankProperties.getTypeOfRules().getTextValue());
		assertEquals("true", ruleRankProperties.getAllowEmptyRulesInVCDomLEM().getTextValue());
		assertEquals(-1, ruleRankProperties.getPrecision().intValue());
	}
	
	@Test
	void shouldMapToEmptyParameters() {
		RuleRankProperties ruleRankProperties = toRuleRankProperties(properties);
		
		assertEquals("", ruleRankProperties.getTypeOfFamilyOfCriteria().getTextValue());
		assertEquals("", ruleRankProperties.getConsistencyMeasure().getTextValue());
		assertEquals("", ruleRankProperties.getTypeOfRules().getTextValue());
		assertEquals("", ruleRankProperties.getConsideredSetOfRules().getTextValue());
		assertEquals("", ruleRankProperties.getSatisfactionDegreesInPreferenceGraph().getTextValue());
		assertEquals("", ruleRankProperties.getFuzzySatisfactionDegreeCalculationMethod().getTextValue());
		assertEquals("", ruleRankProperties.getRankingProcedure().getTextValue());
		assertEquals("", ruleRankProperties.getDominance().getTextValue());
		assertEquals("", ruleRankProperties.getDominanceForPairsOfOrdinalValues().getTextValue());
		assertEquals("", ruleRankProperties.getNegativeExamplesTreatmentForVCDRSA().getTextValue());
		assertEquals("", ruleRankProperties.getRuleConditionsSelectionMethodInVCDomLEM().getTextValue());
		assertEquals("", ruleRankProperties.getOptimizeRuleConsistencyInVCDomLEMWrt().getTextValue());
	}
	
	@Test
	void shouldMapToNulls() {
		RuleRankProperties ruleRankProperties = toRuleRankProperties(properties);
		
		assertNull(ruleRankProperties.getRankingFile());
		assertNull(ruleRankProperties.getConsistencyMeasureThreshold());
		assertNull(ruleRankProperties.getPrecision());
	}
	
	@Test
	void shouldMapToNullsWhenWrongParameters() {
		properties.setProperty(TYPE_OF_FAMILY_CRITERIA, "some key");
		properties.setProperty(CONSISTENCY_MEASURE, "other key");
		properties.setProperty(TYPE_OF_RULES, "not a key");
		properties.setProperty(CONSIDERED_SET_OF_RULES, "SuperKey");
		
		RuleRankProperties ruleRankProperties = toRuleRankProperties(properties);
		
		assertNull(ruleRankProperties.getTypeOfFamilyOfCriteria());
		assertNull(ruleRankProperties.getConsistencyMeasure());
		assertNull(ruleRankProperties.getTypeOfRules());
		assertNull(ruleRankProperties.getConsideredSetOfRules());
	}
	
	@Test
	void shouldWriteLogWhenPropertyIsNotRecognized() {
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		
		properties.setProperty("SomeUnknownProperty", "Key");
		toRuleRankProperties(properties);
		
		assertTrue(outContent.toString().contains("WARN") && outContent.toString().contains("SomeUnknownProperty"));
		
		System.setOut(System.out);
	}
	
	private RuleRankProperties toRuleRankProperties(Properties properties) {
		PropertiesAssembler assembler = new PropertiesAssembler(properties);
		return assembler.toRuleRankProperties();
	}
	
}
