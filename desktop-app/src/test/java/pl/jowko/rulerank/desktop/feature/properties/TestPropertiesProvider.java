package pl.jowko.rulerank.desktop.feature.properties;

/**
 * Created by Piotr on 2018-05-06.
 */
class TestPropertiesProvider {
	
	static String dataFile = "dataFile.isf";
	static String testDataFile = "testDataFile.isf";
	static String pctFile = "pctFile.isf";
	static String pctApxFile = "pctApxFile.apx";
	static String pctRulesFile = "pctRulesFile.rules";
	static String rankingFile = "rankingFile.ranking";
	static String graphFile = "graphFile.graph";
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
	
	static RuleRankProperties getFullProperties() {
		RuleRankProperties ruleRankProperties = new RuleRankProperties();
		
		ruleRankProperties.setLearningDataFile(dataFile);
		ruleRankProperties.setTestDataFile(testDataFile);
		ruleRankProperties.setPctFile(pctFile);
		ruleRankProperties.setPctApxFile(pctApxFile);
		ruleRankProperties.setPctRulesFile(pctRulesFile);
		ruleRankProperties.setRankingFile(rankingFile);
		ruleRankProperties.setPreferenceGraphFile(graphFile);
		ruleRankProperties.setReferenceRanking(referenceRanking);
		ruleRankProperties.setPairs(pairs);
		
		ruleRankProperties.setTypeOfFamilyOfCriteria(createParameter(typeOfFamilyOfCriteria));
		ruleRankProperties.setConsistencyMeasure(createParameter(consistencyMeasure));
		ruleRankProperties.setConsistencyMeasureThreshold(consistencyMeasureThreshold);
		ruleRankProperties.setConsideredSetOfRules(createParameter(consideredSetOfRules));
		ruleRankProperties.setSatisfactionDegreesInPreferenceGraph(createParameter(satisfactionDegreesInPreferenceGraph));
		ruleRankProperties.setFuzzySatisfactionDegreeCalculationMethod(createParameter(fuzzySatisfactionDegreeCalculationMethod));
		ruleRankProperties.setRankingProcedure(createParameter(rankingProcedure));
		ruleRankProperties.setDominance(createParameter(dominance));
		ruleRankProperties.setDominanceForPairsOfOrdinalValues(createParameter(dominanceForPairsOfOrdinalValues));
		ruleRankProperties.setNegativeExamplesTreatmentForVCDRSA(createParameter(negativeExamplesTreatmentForVCDRSA));
		ruleRankProperties.setRuleConditionsSelectionMethodInVCDomLEM(createParameter(ruleConditionsSelectionMethodInVCDomLEM));
		ruleRankProperties.setAllowEmptyRulesInVCDomLEM(createParameter("true"));
		ruleRankProperties.setUseEdgeRegionsInVCDomLEM(createParameter("false"));
		ruleRankProperties.setOptimizeRuleConsistencyInVCDomLEMWrt(createParameter(optimizeRuleConsistencyInVCDomLEMWrt));
		
		ruleRankProperties.setWriteDominationInformation(createParameter("true"));
		ruleRankProperties.setWriteRulesStatistics(createParameter("true"));
		ruleRankProperties.setWriteLearningPositiveExamples(createParameter("false"));
		
		ruleRankProperties.setPrecision(precision);
		
		return ruleRankProperties;
	}
	
	static RuleRankParameter createParameter(String textValue) {
		return new RuleRankParameter("label", textValue, 0);
	}
	
}
