package pl.jowko.rulerank.desktop.feature.properties;

import java.io.Serializable;
import java.util.Objects;

/**
 * Container for all RuleRank options from .properties file. <br>
 *  <br>
 * Created by Piotr on 2018-04-29.
 * @see PropertiesAssembler
 */
public class RuleRankProperties implements Serializable {
	
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
	
	private String reportFile;
	
	private RuleRankParameter typeOfFamilyOfCriteria;
	private RuleRankParameter consistencyMeasure;
	private Double consistencyMeasureThreshold;
	private RuleRankParameter typeOfRules;
	private RuleRankParameter consideredSetOfRules;
	private RuleRankParameter satisfactionDegreesInPreferenceGraph;
	private RuleRankParameter fuzzySatisfactionDegreeCalculationMethod;
	private RuleRankParameter rankingProcedure;
	private RuleRankParameter dominance;
	private RuleRankParameter dominanceForPairsOfOrdinalValues;
	private RuleRankParameter negativeExamplesTreatmentForVCDRSA;
	private RuleRankParameter ruleConditionsSelectionMethodInVCDomLEM;
	private RuleRankParameter allowEmptyRulesInVCDomLEM;
	private RuleRankParameter useEdgeRegionsInVCDomLEM;
	private RuleRankParameter optimizeRuleConsistencyInVCDomLEMWrt;
	
	private RuleRankParameter writeDominationInformation;
	private RuleRankParameter writeRulesStatistics;
	private RuleRankParameter writeLearningPositiveExamples;
	
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
	
	public RuleRankParameter getTypeOfFamilyOfCriteria() {
		return typeOfFamilyOfCriteria;
	}
	
	public void setTypeOfFamilyOfCriteria(RuleRankParameter typeOfFamilyOfCriteria) {
		this.typeOfFamilyOfCriteria = typeOfFamilyOfCriteria;
	}
	
	public RuleRankParameter getConsistencyMeasure() {
		return consistencyMeasure;
	}
	
	public void setConsistencyMeasure(RuleRankParameter consistencyMeasure) {
		this.consistencyMeasure = consistencyMeasure;
	}
	
	public Double getConsistencyMeasureThreshold() {
		return consistencyMeasureThreshold;
	}
	
	public void setConsistencyMeasureThreshold(Double consistencyMeasureThreshold) {
		this.consistencyMeasureThreshold = consistencyMeasureThreshold;
	}
	
	public RuleRankParameter getTypeOfRules() {
		return typeOfRules;
	}
	
	public void setTypeOfRules(RuleRankParameter typeOfRules) {
		this.typeOfRules = typeOfRules;
	}
	
	public RuleRankParameter getConsideredSetOfRules() {
		return consideredSetOfRules;
	}
	
	public void setConsideredSetOfRules(RuleRankParameter consideredSetOfRules) {
		this.consideredSetOfRules = consideredSetOfRules;
	}
	
	public RuleRankParameter getSatisfactionDegreesInPreferenceGraph() {
		return satisfactionDegreesInPreferenceGraph;
	}
	
	public void setSatisfactionDegreesInPreferenceGraph(RuleRankParameter satisfactionDegreesInPreferenceGraph) {
		this.satisfactionDegreesInPreferenceGraph = satisfactionDegreesInPreferenceGraph;
	}
	
	public RuleRankParameter getFuzzySatisfactionDegreeCalculationMethod() {
		return fuzzySatisfactionDegreeCalculationMethod;
	}
	
	public void setFuzzySatisfactionDegreeCalculationMethod(RuleRankParameter fuzzySatisfactionDegreeCalculationMethod) {
		this.fuzzySatisfactionDegreeCalculationMethod = fuzzySatisfactionDegreeCalculationMethod;
	}
	
	public RuleRankParameter getRankingProcedure() {
		return rankingProcedure;
	}
	
	public void setRankingProcedure(RuleRankParameter rankingProcedure) {
		this.rankingProcedure = rankingProcedure;
	}
	
	public RuleRankParameter getDominance() {
		return dominance;
	}
	
	public void setDominance(RuleRankParameter dominance) {
		this.dominance = dominance;
	}
	
	public RuleRankParameter getDominanceForPairsOfOrdinalValues() {
		return dominanceForPairsOfOrdinalValues;
	}
	
	public void setDominanceForPairsOfOrdinalValues(RuleRankParameter dominanceForPairsOfOrdinalValues) {
		this.dominanceForPairsOfOrdinalValues = dominanceForPairsOfOrdinalValues;
	}
	
	public RuleRankParameter getNegativeExamplesTreatmentForVCDRSA() {
		return negativeExamplesTreatmentForVCDRSA;
	}
	
	public void setNegativeExamplesTreatmentForVCDRSA(RuleRankParameter negativeExamplesTreatmentForVCDRSA) {
		this.negativeExamplesTreatmentForVCDRSA = negativeExamplesTreatmentForVCDRSA;
	}
	
	public RuleRankParameter getRuleConditionsSelectionMethodInVCDomLEM() {
		return ruleConditionsSelectionMethodInVCDomLEM;
	}
	
	public void setRuleConditionsSelectionMethodInVCDomLEM(RuleRankParameter ruleConditionsSelectionMethodInVCDomLEM) {
		this.ruleConditionsSelectionMethodInVCDomLEM = ruleConditionsSelectionMethodInVCDomLEM;
	}
	
	public RuleRankParameter getAllowEmptyRulesInVCDomLEM() {
		return allowEmptyRulesInVCDomLEM;
	}
	
	public void setAllowEmptyRulesInVCDomLEM(RuleRankParameter allowEmptyRulesInVCDomLEM) {
		this.allowEmptyRulesInVCDomLEM = allowEmptyRulesInVCDomLEM;
	}
	
	public RuleRankParameter getUseEdgeRegionsInVCDomLEM() {
		return useEdgeRegionsInVCDomLEM;
	}
	
	public void setUseEdgeRegionsInVCDomLEM(RuleRankParameter useEdgeRegionsInVCDomLEM) {
		this.useEdgeRegionsInVCDomLEM = useEdgeRegionsInVCDomLEM;
	}
	
	public RuleRankParameter getOptimizeRuleConsistencyInVCDomLEMWrt() {
		return optimizeRuleConsistencyInVCDomLEMWrt;
	}
	
	public void setOptimizeRuleConsistencyInVCDomLEMWrt(RuleRankParameter optimizeRuleConsistencyInVCDomLEMWrt) {
		this.optimizeRuleConsistencyInVCDomLEMWrt = optimizeRuleConsistencyInVCDomLEMWrt;
	}
	
	public RuleRankParameter getWriteDominationInformation() {
		return writeDominationInformation;
	}
	
	public void setWriteDominationInformation(RuleRankParameter writeDominationInformation) {
		this.writeDominationInformation = writeDominationInformation;
	}
	
	public RuleRankParameter getWriteRulesStatistics() {
		return writeRulesStatistics;
	}
	
	public void setWriteRulesStatistics(RuleRankParameter writeRulesStatistics) {
		this.writeRulesStatistics = writeRulesStatistics;
	}
	
	public RuleRankParameter getWriteLearningPositiveExamples() {
		return writeLearningPositiveExamples;
	}
	
	public void setWriteLearningPositiveExamples(RuleRankParameter writeLearningPositiveExamples) {
		this.writeLearningPositiveExamples = writeLearningPositiveExamples;
	}
	
	public Integer getPrecision() {
		return precision;
	}
	
	public void setPrecision(Integer precision) {
		this.precision = precision;
	}
	
	public String getReportFile() {
		return reportFile;
	}
	
	public void setReportFile(String reportFile) {
		this.reportFile = reportFile;
	}
	
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("RuleRankProperties{\n");
		sb.append("learningDataFile='").append(learningDataFile).append("\'\n");
		sb.append(", testDataFile='").append(testDataFile).append("\'\n");
		sb.append(", pctFile='").append(pctFile).append("\'\n");
		sb.append(", pctApxFile='").append(pctApxFile).append("\'\n");
		sb.append(", pctRulesFile='").append(pctRulesFile).append("\'\n");
		sb.append(", preferenceGraphFile='").append(preferenceGraphFile).append("\'\n");
		sb.append(", rankingFile='").append(rankingFile).append("\'\n");
		sb.append(", reportFile=").append(reportFile).append('\n');
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
		RuleRankProperties that = (RuleRankProperties) o;
		return Objects.equals(learningDataFile, that.learningDataFile) &&
				Objects.equals(testDataFile, that.testDataFile) &&
				Objects.equals(pctFile, that.pctFile) &&
				Objects.equals(pctApxFile, that.pctApxFile) &&
				Objects.equals(pctRulesFile, that.pctRulesFile) &&
				Objects.equals(preferenceGraphFile, that.preferenceGraphFile) &&
				Objects.equals(rankingFile, that.rankingFile) &&
				Objects.equals(reportFile, that.reportFile) &&
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
				preferenceGraphFile, rankingFile, reportFile, referenceRanking, pairs, typeOfFamilyOfCriteria,
				consistencyMeasure, consistencyMeasureThreshold, typeOfRules, consideredSetOfRules,
				satisfactionDegreesInPreferenceGraph, fuzzySatisfactionDegreeCalculationMethod,
				rankingProcedure, dominance, dominanceForPairsOfOrdinalValues, negativeExamplesTreatmentForVCDRSA,
				ruleConditionsSelectionMethodInVCDomLEM, allowEmptyRulesInVCDomLEM, useEdgeRegionsInVCDomLEM,
				optimizeRuleConsistencyInVCDomLEMWrt, writeDominationInformation, writeRulesStatistics,
				writeLearningPositiveExamples, precision);
	}
}
