package pl.jowko.jrank.desktop.feature.properties;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by Piotr on 2018-04-29.
 * Container for all jRank options from .properties file.
 * @see PropertiesAssembler
 */
public class JRankProperties implements Serializable {
	
	private static final long serialVersionUID = -1902681242571506452L;
	
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
	private JRankParameter allowEmptyRulesInVCDomLEM;
	private JRankParameter useEdgeRegionsInVCDomLEM;
	private JRankParameter optimizeRuleConsistencyInVCDomLEMWrt;
	
	private JRankParameter writeDominationInformation;
	private JRankParameter writeRulesStatistics;
	private JRankParameter writeLearningPositiveExamples;
	
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
	
	public JRankParameter getAllowEmptyRulesInVCDomLEM() {
		return allowEmptyRulesInVCDomLEM;
	}
	
	public void setAllowEmptyRulesInVCDomLEM(JRankParameter allowEmptyRulesInVCDomLEM) {
		this.allowEmptyRulesInVCDomLEM = allowEmptyRulesInVCDomLEM;
	}
	
	public JRankParameter getUseEdgeRegionsInVCDomLEM() {
		return useEdgeRegionsInVCDomLEM;
	}
	
	public void setUseEdgeRegionsInVCDomLEM(JRankParameter useEdgeRegionsInVCDomLEM) {
		this.useEdgeRegionsInVCDomLEM = useEdgeRegionsInVCDomLEM;
	}
	
	public JRankParameter getOptimizeRuleConsistencyInVCDomLEMWrt() {
		return optimizeRuleConsistencyInVCDomLEMWrt;
	}
	
	public void setOptimizeRuleConsistencyInVCDomLEMWrt(JRankParameter optimizeRuleConsistencyInVCDomLEMWrt) {
		this.optimizeRuleConsistencyInVCDomLEMWrt = optimizeRuleConsistencyInVCDomLEMWrt;
	}
	
	public JRankParameter getWriteDominationInformation() {
		return writeDominationInformation;
	}
	
	public void setWriteDominationInformation(JRankParameter writeDominationInformation) {
		this.writeDominationInformation = writeDominationInformation;
	}
	
	public JRankParameter getWriteRulesStatistics() {
		return writeRulesStatistics;
	}
	
	public void setWriteRulesStatistics(JRankParameter writeRulesStatistics) {
		this.writeRulesStatistics = writeRulesStatistics;
	}
	
	public JRankParameter getWriteLearningPositiveExamples() {
		return writeLearningPositiveExamples;
	}
	
	public void setWriteLearningPositiveExamples(JRankParameter writeLearningPositiveExamples) {
		this.writeLearningPositiveExamples = writeLearningPositiveExamples;
	}
	
	public Integer getPrecision() {
		return precision;
	}
	
	public void setPrecision(Integer precision) {
		this.precision = precision;
	}
	
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("JRankProperties{\n");
		sb.append("learningDataFile='").append(learningDataFile).append("\'\n");
		sb.append(", testDataFile='").append(testDataFile).append("\'\n");
		sb.append(", pctFile='").append(pctFile).append("\'\n");
		sb.append(", pctApxFile='").append(pctApxFile).append("\'\n");
		sb.append(", pctRulesFile='").append(pctRulesFile).append("\'\n");
		sb.append(", preferenceGraphFile='").append(preferenceGraphFile).append("\'\n");
		sb.append(", rankingFile='").append(rankingFile).append("\'\n");
		sb.append(", referenceRanking='").append(referenceRanking).append("\'\n");
		sb.append(", pairs='").append(pairs).append("\'\n");
		sb.append(", typeOfFamilyOfCriteria=").append(typeOfFamilyOfCriteria).append('\n');
		sb.append(", consistencyMeasure=").append(consistencyMeasure).append('\n');
		sb.append(", consistencyMeasureThreshold=").append(consistencyMeasureThreshold).append('\n');
		sb.append(", typeOfRules=").append(typeOfRules).append('\n');
		sb.append(", consideredSetOfRules=").append(consideredSetOfRules).append('\n');
		sb.append(", satisfactionDegreesInPreferenceGraph=").append(satisfactionDegreesInPreferenceGraph).append('\n');
		sb.append(", fuzzySatisfactionDegreeCalculationMethod=").append(fuzzySatisfactionDegreeCalculationMethod).append('\n');
		sb.append(", rankingProcedure=").append(rankingProcedure).append('\n');
		sb.append(", dominance=").append(dominance).append('\n');
		sb.append(", dominanceForPairsOfOrdinalValues=").append(dominanceForPairsOfOrdinalValues).append('\n');
		sb.append(", negativeExamplesTreatmentForVCDRSA=").append(negativeExamplesTreatmentForVCDRSA).append('\n');
		sb.append(", ruleConditionsSelectionMethodInVCDomLEM=").append(ruleConditionsSelectionMethodInVCDomLEM).append('\n');
		sb.append(", allowEmptyRulesInVCDomLEM=").append(allowEmptyRulesInVCDomLEM).append('\n');
		sb.append(", useEdgeRegionsInVCDomLEM=").append(useEdgeRegionsInVCDomLEM).append('\n');
		sb.append(", optimizeRuleConsistencyInVCDomLEMWrt=").append(optimizeRuleConsistencyInVCDomLEMWrt).append('\n');
		sb.append(", writeDominationInformation=").append(writeDominationInformation).append('\n');
		sb.append(", writeRulesStatistics=").append(writeRulesStatistics).append('\n');
		sb.append(", writeLearningPositiveExamples=").append(writeLearningPositiveExamples).append('\n');
		sb.append(", precision=").append(precision).append('\n');
		sb.append('}');
		return sb.toString();
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		JRankProperties that = (JRankProperties) o;
		return Objects.equals(learningDataFile, that.learningDataFile) &&
				Objects.equals(testDataFile, that.testDataFile) &&
				Objects.equals(pctFile, that.pctFile) &&
				Objects.equals(pctApxFile, that.pctApxFile) &&
				Objects.equals(pctRulesFile, that.pctRulesFile) &&
				Objects.equals(preferenceGraphFile, that.preferenceGraphFile) &&
				Objects.equals(rankingFile, that.rankingFile) &&
				Objects.equals(referenceRanking, that.referenceRanking) &&
				Objects.equals(pairs, that.pairs) &&
				Objects.equals(typeOfFamilyOfCriteria, that.typeOfFamilyOfCriteria) &&
				Objects.equals(consistencyMeasure, that.consistencyMeasure) &&
				Objects.equals(consistencyMeasureThreshold, that.consistencyMeasureThreshold) &&
				Objects.equals(typeOfRules, that.typeOfRules) &&
				Objects.equals(consideredSetOfRules, that.consideredSetOfRules) &&
				Objects.equals(satisfactionDegreesInPreferenceGraph, that.satisfactionDegreesInPreferenceGraph) &&
				Objects.equals(fuzzySatisfactionDegreeCalculationMethod, that.fuzzySatisfactionDegreeCalculationMethod) &&
				Objects.equals(rankingProcedure, that.rankingProcedure) &&
				Objects.equals(dominance, that.dominance) &&
				Objects.equals(dominanceForPairsOfOrdinalValues, that.dominanceForPairsOfOrdinalValues) &&
				Objects.equals(negativeExamplesTreatmentForVCDRSA, that.negativeExamplesTreatmentForVCDRSA) &&
				Objects.equals(ruleConditionsSelectionMethodInVCDomLEM, that.ruleConditionsSelectionMethodInVCDomLEM) &&
				Objects.equals(allowEmptyRulesInVCDomLEM, that.allowEmptyRulesInVCDomLEM) &&
				Objects.equals(useEdgeRegionsInVCDomLEM, that.useEdgeRegionsInVCDomLEM) &&
				Objects.equals(optimizeRuleConsistencyInVCDomLEMWrt, that.optimizeRuleConsistencyInVCDomLEMWrt) &&
				Objects.equals(writeDominationInformation, that.writeDominationInformation) &&
				Objects.equals(writeRulesStatistics, that.writeRulesStatistics) &&
				Objects.equals(writeLearningPositiveExamples, that.writeLearningPositiveExamples) &&
				Objects.equals(precision, that.precision);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(learningDataFile, testDataFile, pctFile, pctApxFile, pctRulesFile,
				preferenceGraphFile, rankingFile, referenceRanking, pairs, typeOfFamilyOfCriteria,
				consistencyMeasure, consistencyMeasureThreshold, typeOfRules, consideredSetOfRules,
				satisfactionDegreesInPreferenceGraph, fuzzySatisfactionDegreeCalculationMethod,
				rankingProcedure, dominance, dominanceForPairsOfOrdinalValues, negativeExamplesTreatmentForVCDRSA,
				ruleConditionsSelectionMethodInVCDomLEM, allowEmptyRulesInVCDomLEM, useEdgeRegionsInVCDomLEM,
				optimizeRuleConsistencyInVCDomLEMWrt, writeDominationInformation, writeRulesStatistics,
				writeLearningPositiveExamples, precision);
	}
}
