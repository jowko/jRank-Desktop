package pl.jowko.jrank.desktop.feature.properties;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.jowko.jrank.desktop.MasterTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.jowko.jrank.desktop.feature.properties.TestPropertiesProvider.*;

/**
 * Created by Piotr on 2018-05-06.
 */
class RunnerPropertiesProviderTest extends MasterTest {
	
	private JRankProperties properties;
	private JRankProperties defaults;
	
	@BeforeEach
	void setUpEach() {
		properties = new JRankProperties();
		defaults = getFullProperties();
	}
	
	@Test
	void shouldGetDefaultsForFiles() {
		JRankProperties withDefaults = getPropertiesWithDefaults();
		
		assertEquals(dataFile, withDefaults.getLearningDataFile());
		assertEquals(testDataFile, withDefaults.getTestDataFile());
		assertEquals(pctFile, withDefaults.getPctFile());
		assertEquals(pctApxFile, withDefaults.getPctApxFile());
		assertEquals(pctRulesFile, withDefaults.getPctRulesFile());
		assertEquals(rankingFile, withDefaults.getRankingFile());
		assertEquals(graphFile, withDefaults.getPreferenceGraphFile());
	}
	
	@Test
	void shouldNotGetDefaultsForFiles() {
		properties.setLearningDataFile("dataFile2");
		properties.setTestDataFile("testDataFile2");
		properties.setPctFile("pctFile2");
		properties.setPctApxFile("pctApxFile2");
		properties.setPctRulesFile("pctRulesFile2");
		properties.setRankingFile("rankingFile2");
		properties.setPreferenceGraphFile("graphFile");
		
		JRankProperties withDefaults = getPropertiesWithDefaults();
		
		assertEquals("dataFile2", withDefaults.getLearningDataFile());
		assertEquals("testDataFile2", withDefaults.getTestDataFile());
		assertEquals("pctFile2", withDefaults.getPctFile());
		assertEquals("pctApxFile2", withDefaults.getPctApxFile());
		assertEquals("pctRulesFile2", withDefaults.getPctRulesFile());
		assertEquals("rankingFile2", withDefaults.getRankingFile());
		assertEquals("graphFile", withDefaults.getPreferenceGraphFile());
	}
	
	@Test
	void shouldGetDefaultsForFilesPartially() {
		properties.setLearningDataFile("dataFile2");
		properties.setPctRulesFile("pctRulesFile2");
		
		JRankProperties withDefaults = getPropertiesWithDefaults();
		
		assertEquals("dataFile2", withDefaults.getLearningDataFile());
		assertEquals("pctRulesFile2", withDefaults.getPctRulesFile());
		assertEquals(testDataFile, withDefaults.getTestDataFile());
		assertEquals(pctFile, withDefaults.getPctFile());
		assertEquals(pctApxFile, withDefaults.getPctApxFile());
	}
	
	@Test
	void shouldGetDefaultReferenceRanking() {
		defaults.setPairs(null);
		JRankProperties withDefaults = getPropertiesWithDefaults();
		assertEquals(referenceRanking, withDefaults.getReferenceRanking());
	}
	
	@Test
	void shouldNotGetDefaultReferenceRanking() {
		properties.setReferenceRanking("1,2,3");
		defaults.setPairs(null);
		JRankProperties withDefaults = getPropertiesWithDefaults();
		assertEquals("1,2,3", withDefaults.getReferenceRanking());
	}
	
	@Test
	void shouldGetDefaultPairs() {
		defaults.setReferenceRanking(null);
		JRankProperties withDefaults = getPropertiesWithDefaults();
		assertEquals(pairs, withDefaults.getPairs());
	}
	
	@Test
	void shouldNotGetDefaultPairs() {
		properties.setPairs("{1,2}");
		defaults.setReferenceRanking(null);
		JRankProperties withDefaults = getPropertiesWithDefaults();
		assertEquals("{1,2}", withDefaults.getPairs());
	}
	
	@Test
	void shouldGetDefaultParameters() {
		JRankProperties withDefaults = getPropertiesWithDefaults();
		
		assertEquals(typeOfFamilyOfCriteria, withDefaults.getTypeOfFamilyOfCriteria().getTextValue());
		assertEquals(consistencyMeasure, withDefaults.getConsistencyMeasure().getTextValue());
		assertEquals(consideredSetOfRules, withDefaults.getConsideredSetOfRules().getTextValue());
		assertEquals(satisfactionDegreesInPreferenceGraph, withDefaults.getSatisfactionDegreesInPreferenceGraph().getTextValue());
		assertEquals(fuzzySatisfactionDegreeCalculationMethod, withDefaults.getFuzzySatisfactionDegreeCalculationMethod().getTextValue());
		assertEquals(rankingProcedure, withDefaults.getRankingProcedure().getTextValue());
		assertEquals(dominance, withDefaults.getDominance().getTextValue());
		assertEquals(dominanceForPairsOfOrdinalValues, withDefaults.getDominanceForPairsOfOrdinalValues().getTextValue());
		assertEquals(negativeExamplesTreatmentForVCDRSA, withDefaults.getNegativeExamplesTreatmentForVCDRSA().getTextValue());
		assertEquals(ruleConditionsSelectionMethodInVCDomLEM, withDefaults.getRuleConditionsSelectionMethodInVCDomLEM().getTextValue());
		assertEquals(optimizeRuleConsistencyInVCDomLEMWrt, withDefaults.getOptimizeRuleConsistencyInVCDomLEMWrt().getTextValue());
	}
	
	@Test
	void shouldNotGetDefaultParameters() {
		properties.setTypeOfFamilyOfCriteria(createParameter("typeOfFamilyOfCriteria2"));
		properties.setConsistencyMeasure(createParameter("consistencyMeasure2"));
		properties.setConsideredSetOfRules(createParameter("consideredSetOfRules2"));
		properties.setSatisfactionDegreesInPreferenceGraph(createParameter("satisfactionDegreesInPreferenceGraph2"));
		properties.setFuzzySatisfactionDegreeCalculationMethod(createParameter("fuzzySatisfactionDegreeCalculationMethod2"));
		properties.setRankingProcedure(createParameter("rankingProcedure2"));
		properties.setDominance(createParameter("dominance2"));
		properties.setDominanceForPairsOfOrdinalValues(createParameter("dominanceForPairsOfOrdinalValues2"));
		properties.setNegativeExamplesTreatmentForVCDRSA(createParameter("negativeExamplesTreatmentForVCDRSA2"));
		properties.setRuleConditionsSelectionMethodInVCDomLEM(createParameter("ruleConditionsSelectionMethodInVCDomLEM2"));
		properties.setOptimizeRuleConsistencyInVCDomLEMWrt(createParameter("optimizeRuleConsistencyInVCDomLEMWrt2"));
		
		JRankProperties withDefaults = getPropertiesWithDefaults();
		
		assertEquals("typeOfFamilyOfCriteria2", withDefaults.getTypeOfFamilyOfCriteria().getTextValue());
		assertEquals("consistencyMeasure2", withDefaults.getConsistencyMeasure().getTextValue());
		assertEquals("consideredSetOfRules2", withDefaults.getConsideredSetOfRules().getTextValue());
		assertEquals("satisfactionDegreesInPreferenceGraph2", withDefaults.getSatisfactionDegreesInPreferenceGraph().getTextValue());
		assertEquals("fuzzySatisfactionDegreeCalculationMethod2", withDefaults.getFuzzySatisfactionDegreeCalculationMethod().getTextValue());
		assertEquals("rankingProcedure2", withDefaults.getRankingProcedure().getTextValue());
		assertEquals("dominance2", withDefaults.getDominance().getTextValue());
		assertEquals("dominanceForPairsOfOrdinalValues2", withDefaults.getDominanceForPairsOfOrdinalValues().getTextValue());
		assertEquals("negativeExamplesTreatmentForVCDRSA2", withDefaults.getNegativeExamplesTreatmentForVCDRSA().getTextValue());
		assertEquals("ruleConditionsSelectionMethodInVCDomLEM2", withDefaults.getRuleConditionsSelectionMethodInVCDomLEM().getTextValue());
		assertEquals("optimizeRuleConsistencyInVCDomLEMWrt2", withDefaults.getOptimizeRuleConsistencyInVCDomLEMWrt().getTextValue());
	}
	
	@Test
	void shouldGetDefaultParametersPartially() {
		properties.setTypeOfFamilyOfCriteria(createParameter("typeOfFamilyOfCriteria2"));
		properties.setDominanceForPairsOfOrdinalValues(createParameter("dominanceForPairsOfOrdinalValues2"));
		
		JRankProperties withDefaults = getPropertiesWithDefaults();
		
		assertEquals("typeOfFamilyOfCriteria2", withDefaults.getTypeOfFamilyOfCriteria().getTextValue());
		assertEquals("dominanceForPairsOfOrdinalValues2", withDefaults.getDominanceForPairsOfOrdinalValues().getTextValue());
		assertEquals(satisfactionDegreesInPreferenceGraph, withDefaults.getSatisfactionDegreesInPreferenceGraph().getTextValue());
		assertEquals(fuzzySatisfactionDegreeCalculationMethod, withDefaults.getFuzzySatisfactionDegreeCalculationMethod().getTextValue());
	}
	
	@Test
	void shouldGetDefaultBooleanProperties() {
		JRankProperties withDefaults = getPropertiesWithDefaults();
		
		assertEquals(true, withDefaults.getAllowEmptyRulesInVCDomLEM());
		assertEquals(false, withDefaults.getUseEdgeRegionsInVCDomLEM());
		assertEquals(true, withDefaults.getWriteDominationInformation());
		assertEquals(true, withDefaults.getWriteRulesStatistics());
		assertEquals(false, withDefaults.getWriteLearningPositiveExamples());
	}
	
	@Test
	void shouldNotGetDefaultBooleanProperties() {
		properties.setAllowEmptyRulesInVCDomLEM(false);
		properties.setUseEdgeRegionsInVCDomLEM(false);
		properties.setWriteDominationInformation(true);
		properties.setWriteRulesStatistics(false);
		properties.setWriteLearningPositiveExamples(true);
		
		JRankProperties withDefaults = getPropertiesWithDefaults();
		
		assertEquals(false, withDefaults.getAllowEmptyRulesInVCDomLEM());
		assertEquals(false, withDefaults.getUseEdgeRegionsInVCDomLEM());
		assertEquals(true, withDefaults.getWriteDominationInformation());
		assertEquals(false, withDefaults.getWriteRulesStatistics());
		assertEquals(true, withDefaults.getWriteLearningPositiveExamples());
	}
	
	@Test
	void shouldGetDefaultBooleanPropertiesPartially() {
		properties.setAllowEmptyRulesInVCDomLEM(false);
		JRankProperties withDefaults = getPropertiesWithDefaults();
		
		assertEquals(false, withDefaults.getAllowEmptyRulesInVCDomLEM());
		assertEquals(true, withDefaults.getWriteRulesStatistics());
		assertEquals(false, withDefaults.getWriteLearningPositiveExamples());
	}
	
	@Test
	void shouldGetDefaultNumberProperties() {
		JRankProperties withDefaults = getPropertiesWithDefaults();
		
		assertEquals(consistencyMeasureThreshold, withDefaults.getConsistencyMeasureThreshold());
		assertEquals(precision, withDefaults.getPrecision());
	}
	
	@Test
	void shouldNotGetDefaultNumberProperties() {
		properties.setConsistencyMeasureThreshold(0.3d);
		properties.setPrecision(1);
		JRankProperties withDefaults = getPropertiesWithDefaults();
		
		assertEquals(Double.valueOf(0.3d), withDefaults.getConsistencyMeasureThreshold());
		assertEquals(Integer.valueOf(1), withDefaults.getPrecision());
	}
	
	private JRankProperties getPropertiesWithDefaults() {
		return new RunnerPropertiesProvider(properties, defaults).getPropertiesWithDefaults();
	}
	
}
