package pl.jowko.jrank.desktop.feature.properties;

/**
 * Created by Piotr on 2018-05-06.
 */
class TestPropertiesProvider {
	
	static String dataFile = "dataFile";
	static String testDataFile = "testDataFile";
	static String pctFile = "pctFile";
	static String pctApxFile = "pctApxFile";
	static String pctRulesFile = "pctRulesFile";
	static String rankingFile = "rankingFile";
	static String graphFile = "graphFile";
	static String referenceRanking = "referenceRanking";
	static String pairs = "pairs";
	
	static String typeOfFamilyOfCriteria = "typeOfFamilyOfCriteria";
	static String consistencyMeasure = "consistencyMeasure";
	static Double consistencyMeasureThreshold = 0.5d;
	static String consideredSetOfRules = "consideredSetOfRules";
	static String satisfactionDegreesInPreferenceGraph = "satisfactionDegreesInPreferenceGraph";
	static String fuzzySatisfactionDegreeCalculationMethod = "fuzzySatisfactionDegreeCalculationMethod";
	static String rankingProcedure = "rankingProcedure";
	static String dominance = "dominance";
	static String dominanceForPairsOfOrdinalValues = "dominanceForPairsOfOrdinalValues";
	static String negativeExamplesTreatmentForVCDRSA = "negativeExamplesTreatmentForVCDRSA";
	static String ruleConditionsSelectionMethodInVCDomLEM = "ruleConditionsSelectionMethodInVCDomLEM";
	static String optimizeRuleConsistencyInVCDomLEMWrt = "optimizeRuleConsistencyInVCDomLEMWrt";
	
	static Integer precision = -1;
	
	static JRankProperties getFullProperties() {
		JRankProperties jRankProperties = new JRankProperties();
		
		jRankProperties.setLearningDataFile(dataFile);
		jRankProperties.setTestDataFile(testDataFile);
		jRankProperties.setPctFile(pctFile);
		jRankProperties.setPctApxFile(pctApxFile);
		jRankProperties.setPctRulesFile(pctRulesFile);
		jRankProperties.setRankingFile(rankingFile);
		jRankProperties.setPreferenceGraphFile(graphFile);
		jRankProperties.setReferenceRanking(referenceRanking);
		jRankProperties.setPairs(pairs);
		
		jRankProperties.setTypeOfFamilyOfCriteria(createParameter(typeOfFamilyOfCriteria));
		jRankProperties.setConsistencyMeasure(createParameter(consistencyMeasure));
		jRankProperties.setConsistencyMeasureThreshold(consistencyMeasureThreshold);
		jRankProperties.setConsideredSetOfRules(createParameter(consideredSetOfRules));
		jRankProperties.setSatisfactionDegreesInPreferenceGraph(createParameter(satisfactionDegreesInPreferenceGraph));
		jRankProperties.setFuzzySatisfactionDegreeCalculationMethod(createParameter(fuzzySatisfactionDegreeCalculationMethod));
		jRankProperties.setRankingProcedure(createParameter(rankingProcedure));
		jRankProperties.setDominance(createParameter(dominance));
		jRankProperties.setDominanceForPairsOfOrdinalValues(createParameter(dominanceForPairsOfOrdinalValues));
		jRankProperties.setNegativeExamplesTreatmentForVCDRSA(createParameter(negativeExamplesTreatmentForVCDRSA));
		jRankProperties.setRuleConditionsSelectionMethodInVCDomLEM(createParameter(ruleConditionsSelectionMethodInVCDomLEM));
		jRankProperties.setAllowEmptyRulesInVCDomLEM(true);
		jRankProperties.setUseEdgeRegionsInVCDomLEM(false);
		jRankProperties.setOptimizeRuleConsistencyInVCDomLEMWrt(createParameter(optimizeRuleConsistencyInVCDomLEMWrt));
		
		jRankProperties.setWriteDominationInformation(true);
		jRankProperties.setWriteRulesStatistics(true);
		jRankProperties.setWriteLearningPositiveExamples(false);
		
		jRankProperties.setPrecision(precision);
		
		return jRankProperties;
	}
	
	static JRankParameter createParameter(String textValue) {
		return new JRankParameter("label", textValue, 0);
	}
	
}
