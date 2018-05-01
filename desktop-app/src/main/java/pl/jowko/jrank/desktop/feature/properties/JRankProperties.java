package pl.jowko.jrank.desktop.feature.properties;

/**
 * Created by Piotr on 2018-04-29.
 */
public class JRankProperties {
	
	private String learningDataFile;
	private String testDataFile;
	
	private String pctFile;
	private String pctApxFile;
	private String pctRulesFile;
	
	private String preferenceGraphFile;
	private String rankingFile;
	
	private String referenceRanking;
	private String pairs;
	
	private JRankParameter typeOfFamilyOfCriteria;
	private JRankParameter consistencyMeasure;
	private Double consistencyMeasureThreshold;
	private JRankParameter typeOfRules;
	private JRankParameter consideredSetOfRules;
	private JRankParameter satisfactionDegreesInPreferenceGraph;
	private JRankParameter fuzzySatisfactionDegreeCalculationMethod;
	private JRankParameter rankingProcedure;
	private JRankParameter dominance;
	private JRankParameter dominanceForPairsOfOrdinalValues;
	private JRankParameter negativeExamplesTreatmentForVCDRSA;
	private JRankParameter ruleConditionsSelectionMethodInVCDomLEM;
	private Boolean allowEmptyRulesInVCDomLEM;
	private Boolean useEdgeRegionsInVCDomLEM;
	private JRankParameter optimizeRuleConsistencyInVCDomLEMWrt;
	
	private Boolean writeDominationInformation;
	private Boolean writeRulesStatistics;
	private Boolean writeLearningPositiveExamples;
	
	private Integer precision;
	
	public String getLearningDataFile() {
		return learningDataFile;
	}
	
	public void setLearningDataFile(String learningDataFile) {
		this.learningDataFile = learningDataFile;
	}
	
	public String getTestDataFile() {
		return testDataFile;
	}
	
	public void setTestDataFile(String testDataFile) {
		this.testDataFile = testDataFile;
	}
	
	public String getPctFile() {
		return pctFile;
	}
	
	public void setPctFile(String pctFile) {
		this.pctFile = pctFile;
	}
	
	public String getPctApxFile() {
		return pctApxFile;
	}
	
	public void setPctApxFile(String pctApxFile) {
		this.pctApxFile = pctApxFile;
	}
	
	public String getPctRulesFile() {
		return pctRulesFile;
	}
	
	public void setPctRulesFile(String pctRulesFile) {
		this.pctRulesFile = pctRulesFile;
	}
	
	public String getPreferenceGraphFile() {
		return preferenceGraphFile;
	}
	
	public void setPreferenceGraphFile(String preferenceGraphFile) {
		this.preferenceGraphFile = preferenceGraphFile;
	}
	
	public String getRankingFile() {
		return rankingFile;
	}
	
	public void setRankingFile(String rankingFile) {
		this.rankingFile = rankingFile;
	}
	
	public String getReferenceRanking() {
		return referenceRanking;
	}
	
	public void setReferenceRanking(String referenceRanking) {
		this.referenceRanking = referenceRanking;
	}
	
	public String getPairs() {
		return pairs;
	}
	
	public void setPairs(String pairs) {
		this.pairs = pairs;
	}
	
	public JRankParameter getTypeOfFamilyOfCriteria() {
		return typeOfFamilyOfCriteria;
	}
	
	public void setTypeOfFamilyOfCriteria(JRankParameter typeOfFamilyOfCriteria) {
		this.typeOfFamilyOfCriteria = typeOfFamilyOfCriteria;
	}
	
	public JRankParameter getConsistencyMeasure() {
		return consistencyMeasure;
	}
	
	public void setConsistencyMeasure(JRankParameter consistencyMeasure) {
		this.consistencyMeasure = consistencyMeasure;
	}
	
	public Double getConsistencyMeasureThreshold() {
		return consistencyMeasureThreshold;
	}
	
	public void setConsistencyMeasureThreshold(Double consistencyMeasureThreshold) {
		this.consistencyMeasureThreshold = consistencyMeasureThreshold;
	}
	
	public JRankParameter getTypeOfRules() {
		return typeOfRules;
	}
	
	public void setTypeOfRules(JRankParameter typeOfRules) {
		this.typeOfRules = typeOfRules;
	}
	
	public JRankParameter getConsideredSetOfRules() {
		return consideredSetOfRules;
	}
	
	public void setConsideredSetOfRules(JRankParameter consideredSetOfRules) {
		this.consideredSetOfRules = consideredSetOfRules;
	}
	
	public JRankParameter getSatisfactionDegreesInPreferenceGraph() {
		return satisfactionDegreesInPreferenceGraph;
	}
	
	public void setSatisfactionDegreesInPreferenceGraph(JRankParameter satisfactionDegreesInPreferenceGraph) {
		this.satisfactionDegreesInPreferenceGraph = satisfactionDegreesInPreferenceGraph;
	}
	
	public JRankParameter getFuzzySatisfactionDegreeCalculationMethod() {
		return fuzzySatisfactionDegreeCalculationMethod;
	}
	
	public void setFuzzySatisfactionDegreeCalculationMethod(JRankParameter fuzzySatisfactionDegreeCalculationMethod) {
		this.fuzzySatisfactionDegreeCalculationMethod = fuzzySatisfactionDegreeCalculationMethod;
	}
	
	public JRankParameter getRankingProcedure() {
		return rankingProcedure;
	}
	
	public void setRankingProcedure(JRankParameter rankingProcedure) {
		this.rankingProcedure = rankingProcedure;
	}
	
	public JRankParameter getDominance() {
		return dominance;
	}
	
	public void setDominance(JRankParameter dominance) {
		this.dominance = dominance;
	}
	
	public JRankParameter getDominanceForPairsOfOrdinalValues() {
		return dominanceForPairsOfOrdinalValues;
	}
	
	public void setDominanceForPairsOfOrdinalValues(JRankParameter dominanceForPairsOfOrdinalValues) {
		this.dominanceForPairsOfOrdinalValues = dominanceForPairsOfOrdinalValues;
	}
	
	public JRankParameter getNegativeExamplesTreatmentForVCDRSA() {
		return negativeExamplesTreatmentForVCDRSA;
	}
	
	public void setNegativeExamplesTreatmentForVCDRSA(JRankParameter negativeExamplesTreatmentForVCDRSA) {
		this.negativeExamplesTreatmentForVCDRSA = negativeExamplesTreatmentForVCDRSA;
	}
	
	public JRankParameter getRuleConditionsSelectionMethodInVCDomLEM() {
		return ruleConditionsSelectionMethodInVCDomLEM;
	}
	
	public void setRuleConditionsSelectionMethodInVCDomLEM(JRankParameter ruleConditionsSelectionMethodInVCDomLEM) {
		this.ruleConditionsSelectionMethodInVCDomLEM = ruleConditionsSelectionMethodInVCDomLEM;
	}
	
	public Boolean getAllowEmptyRulesInVCDomLEM() {
		return allowEmptyRulesInVCDomLEM;
	}
	
	public void setAllowEmptyRulesInVCDomLEM(Boolean allowEmptyRulesInVCDomLEM) {
		this.allowEmptyRulesInVCDomLEM = allowEmptyRulesInVCDomLEM;
	}
	
	public Boolean getUseEdgeRegionsInVCDomLEM() {
		return useEdgeRegionsInVCDomLEM;
	}
	
	public void setUseEdgeRegionsInVCDomLEM(Boolean useEdgeRegionsInVCDomLEM) {
		this.useEdgeRegionsInVCDomLEM = useEdgeRegionsInVCDomLEM;
	}
	
	public JRankParameter getOptimizeRuleConsistencyInVCDomLEMWrt() {
		return optimizeRuleConsistencyInVCDomLEMWrt;
	}
	
	public void setOptimizeRuleConsistencyInVCDomLEMWrt(JRankParameter optimizeRuleConsistencyInVCDomLEMWrt) {
		this.optimizeRuleConsistencyInVCDomLEMWrt = optimizeRuleConsistencyInVCDomLEMWrt;
	}
	
	public Boolean getWriteDominationInformation() {
		return writeDominationInformation;
	}
	
	public void setWriteDominationInformation(Boolean writeDominationInformation) {
		this.writeDominationInformation = writeDominationInformation;
	}
	
	public Boolean getWriteRulesStatistics() {
		return writeRulesStatistics;
	}
	
	public void setWriteRulesStatistics(Boolean writeRulesStatistics) {
		this.writeRulesStatistics = writeRulesStatistics;
	}
	
	public Boolean getWriteLearningPositiveExamples() {
		return writeLearningPositiveExamples;
	}
	
	public void setWriteLearningPositiveExamples(Boolean writeLearningPositiveExamples) {
		this.writeLearningPositiveExamples = writeLearningPositiveExamples;
	}
	
	public Integer getPrecision() {
		return precision;
	}
	
	public void setPrecision(Integer precision) {
		this.precision = precision;
	}
	
}
