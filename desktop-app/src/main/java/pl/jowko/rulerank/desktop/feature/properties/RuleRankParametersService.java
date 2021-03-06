package pl.jowko.rulerank.desktop.feature.properties;

import pl.jowko.rulerank.desktop.feature.internationalization.Labels;
import pl.jowko.rulerank.desktop.feature.internationalization.LanguageService;

import java.util.LinkedList;
import java.util.List;

import static pl.poznan.put.cs.idss.jrs.Settings.ANY_FAMILY_OF_CRITERIA;
import static pl.poznan.put.cs.idss.jrs.Settings.CONSISTENT_FAMILY_OF_CRITERIA;
import static pl.poznan.put.cs.idss.jrs.approximations.MonotonicUnion.*;
import static pl.poznan.put.cs.idss.jrs.ranking.PreferenceGraphGenerator.MAX_CREDIBILITY;
import static pl.poznan.put.cs.idss.jrs.ranking.PreferenceGraphGenerator.MAX_PRODUCT_OF_CREDIBILITY_AND_COVERAGE_FACTOR;
import static pl.poznan.put.cs.idss.jrs.ranking.RankerParameters.*;
import static pl.poznan.put.cs.idss.jrs.rules.Rule.CERTAIN;
import static pl.poznan.put.cs.idss.jrs.rules.Rule.POSSIBLE;
import static pl.poznan.put.cs.idss.jrs.rules.RuleConstants.*;
import static pl.poznan.put.cs.idss.jrs.rules.VCDomLem.CHOOSE_CONDITIONS_FROM_ONE_OBJECT;
import static pl.poznan.put.cs.idss.jrs.rules.VCDomLem.MIX_CONDITIONS_FROM_DIFFERENT_OBJECTS;
import static pl.poznan.put.cs.idss.jrs.types.PairField.CLASSIC_ORDINAL_DOMINANCE_CHECK_METHOD;
import static pl.poznan.put.cs.idss.jrs.types.PairField.STRICT_ORDINAL_DOMINANCE_CHECK_METHOD;

/**
 * This class creates all data used in ComboBoxes on properties form. <br>
 * It provides RuleRankParameter lists with are used to pass and display data from "enum" RuleRank options. <br>
 *  <br>
 * Created by Piotr on 2018-05-01.
 * @see RuleRankParameter
 */
class RuleRankParametersService {
	
	private static RuleRankParametersService instance;
	
	private LanguageService labels;
	
	private List<RuleRankParameter> typeOfFamilyOfCriteria;
	private List<RuleRankParameter> consistencyMeasure;
	private List<RuleRankParameter> typeOfRules;
	private List<RuleRankParameter> consideredSetOfRules;
	private List<RuleRankParameter> satisfactionDegreesInPreferenceGraph;
	private List<RuleRankParameter> fuzzySatisfactionDegreeCalculationMethod;
	private List<RuleRankParameter> rankingProcedure;
	private List<RuleRankParameter> dominance;
	private List<RuleRankParameter> dominanceForPairsOfOrdinalValues;
	private List<RuleRankParameter> negativeExamplesTreatmentForVCDRSA;
	private List<RuleRankParameter> ruleConditionsSelectionMethodInVCDomLEM;
	private List<RuleRankParameter> optimizeRuleConsistencyInVCDomLEMWrt;
	
	private List<RuleRankParameter> booleanParameter;
	private List<RuleRankParameter> allowEmptyRules;
	private List<RuleRankParameter> useEdgeRegions;
	private List<RuleRankParameter> writeDominationInfo;
	private List<RuleRankParameter> writeRulesStatistics;
	private List<RuleRankParameter> writePositiveExamples;
	
	private RuleRankParametersService() {
		labels = LanguageService.getInstance();
		initializeParameters();
	}
	
	public static RuleRankParametersService getInstance() {
		if(instance == null) {
			instance = new RuleRankParametersService();
		}
		return instance;
	}
	
	/**
	 * Extract parameter from provided parameters list by textValue field.
	 * @param parameters from with parameter will be extracted
	 * @param textValue from RuleRankParameter to find
	 * @return RuleRankParameter or null
	 */
	public RuleRankParameter findByTextValue(List<RuleRankParameter> parameters, String textValue) {
		return parameters.stream()
				.filter(param -> param.getTextValue().equalsIgnoreCase(textValue))
				.findAny()
				.orElse(null);
	}
	
	/**
	 * @return new empty parameter. This parameter is later changed in properties to should default value label.
	 * If user choose empty parameter from list, it value won't be saved to file.
	 */
	public RuleRankParameter getDefaultParameter() {
		return new RuleRankParameter("", "", Integer.MIN_VALUE);
	}
	
	public List<RuleRankParameter> getTypeOfFamilyOfCriteria() {
		return typeOfFamilyOfCriteria;
	}
	
	public List<RuleRankParameter> getConsistencyMeasure() {
		return consistencyMeasure;
	}
	
	public List<RuleRankParameter> getTypeOfRules() {
		return typeOfRules;
	}
	
	public List<RuleRankParameter> getConsideredSetOfRules() {
		return consideredSetOfRules;
	}
	
	public List<RuleRankParameter> getSatisfactionDegreesInPreferenceGraph() {
		return satisfactionDegreesInPreferenceGraph;
	}
	
	public List<RuleRankParameter> getFuzzySatisfactionDegreeCalculationMethod() {
		return fuzzySatisfactionDegreeCalculationMethod;
	}
	
	public List<RuleRankParameter> getRankingProcedure() {
		return rankingProcedure;
	}
	
	public List<RuleRankParameter> getDominance() {
		return dominance;
	}
	
	public List<RuleRankParameter> getDominanceForPairsOfOrdinalValues() {
		return dominanceForPairsOfOrdinalValues;
	}
	
	public List<RuleRankParameter> getNegativeExamplesTreatmentForVCDRSA() {
		return negativeExamplesTreatmentForVCDRSA;
	}
	
	public List<RuleRankParameter> getRuleConditionsSelectionMethodInVCDomLEM() {
		return ruleConditionsSelectionMethodInVCDomLEM;
	}
	
	public List<RuleRankParameter> getOptimizeRuleConsistencyInVCDomLEMWrt() {
		return optimizeRuleConsistencyInVCDomLEMWrt;
	}
	
	public List<RuleRankParameter> getBooleanParameter() {
		return booleanParameter;
	}
	
	public List<RuleRankParameter> getAllowEmptyRules() {
		return allowEmptyRules;
	}
	
	public List<RuleRankParameter> getUseEdgeRegions() {
		return useEdgeRegions;
	}
	
	public List<RuleRankParameter> getWriteDominationInfo() {
		return writeDominationInfo;
	}
	
	public List<RuleRankParameter> getWriteRulesStatistics() {
		return writeRulesStatistics;
	}
	
	public List<RuleRankParameter> getWritePositiveExamples() {
		return writePositiveExamples;
	}
	
	private void initializeParameters() {
		initTypeOfFamilyOfCriteria();
		initConsistencyMeasure();
		initTypeOfRules();
		initConsideredSetOfRules();
		initSatisfactionDegreesInPreferenceGraph();
		initFuzzySatisfactionDegreeCalculationMethod();
		initRankingProcedure();
		initDominance();
		initDominanceForPairsOfOrdinalValues();
		initNegativeExamplesTreatmentForVCDRSA();
		initRuleConditionsSelectionMethodInVCDomLEM();
		initOptimizeRuleConsistencyInVCDomLEMWrt();
		initBooleanParameter();
	}
	
	private void initTypeOfFamilyOfCriteria() {
		typeOfFamilyOfCriteria = new LinkedList<>();
		typeOfFamilyOfCriteria.add(getDefaultParameter());
		typeOfFamilyOfCriteria.add(new RuleRankParameter(get(Labels.ANY_FAMILY_OF_CRITERIA),"any", ANY_FAMILY_OF_CRITERIA));
		typeOfFamilyOfCriteria.add(new RuleRankParameter(get(Labels.CONSISTENT_FAMILY_OF_CRITERIA),"consistent", CONSISTENT_FAMILY_OF_CRITERIA));
	}
	
	private void initConsistencyMeasure() {
		consistencyMeasure = new LinkedList<>();
		consistencyMeasure.add(getDefaultParameter());
		consistencyMeasure.add(new RuleRankParameter(get(Labels.EPSILON),"epsilon", EPSILON));
		consistencyMeasure.add(new RuleRankParameter(get(Labels.EPSILON_STAR),"epsilon*", EPSILON_STAR));
		consistencyMeasure.add(new RuleRankParameter(get(Labels.EPSILON_PRIM),"epsilon'", EPSILON_PRIM));
		consistencyMeasure.add(new RuleRankParameter(get(Labels.ROUGH_MEMBERSHIP),"rough-membership", ROUGH_MEMBERSHIP));
	}
	
	private void initTypeOfRules() {
		typeOfRules = new LinkedList<>();
		typeOfRules.add(getDefaultParameter());
		typeOfRules.add(new RuleRankParameter(get(Labels.RULE_CERTAIN),"certain", CERTAIN));
		typeOfRules.add(new RuleRankParameter(get(Labels.RULE_POSSIBLE),"possible", POSSIBLE));
	}
	
	private void initConsideredSetOfRules() {
		consideredSetOfRules = new LinkedList<>();
		consideredSetOfRules.add(getDefaultParameter());
		consideredSetOfRules.add(new RuleRankParameter(get(Labels.MINIMAL_SET_OF_RULES),"minimal", MINIMAL_SET_OF_RULES));
		consideredSetOfRules.add(new RuleRankParameter(get(Labels.EXHAUSTIVE_SET_OF_RULES),"exhaustive", EXHAUSTIVE_SET_OF_RULES));
	}
	
	private void initSatisfactionDegreesInPreferenceGraph() {
		satisfactionDegreesInPreferenceGraph = new LinkedList<>();
		satisfactionDegreesInPreferenceGraph.add(getDefaultParameter());
		satisfactionDegreesInPreferenceGraph.add(new RuleRankParameter(get(Labels.SATISFACTION_FUZZY),"fuzzy", FUZZY));
		satisfactionDegreesInPreferenceGraph.add(new RuleRankParameter(get(Labels.SATISFACTION_CRISP),"crisp", CRISP));
	}
	
	private void initFuzzySatisfactionDegreeCalculationMethod() {
		fuzzySatisfactionDegreeCalculationMethod = new LinkedList<>();
		fuzzySatisfactionDegreeCalculationMethod.add(getDefaultParameter());
		fuzzySatisfactionDegreeCalculationMethod.add(new RuleRankParameter(get(Labels.MAX_CREDIBILITY),"max-credibility", MAX_CREDIBILITY));
		fuzzySatisfactionDegreeCalculationMethod.add(new RuleRankParameter(get(Labels.MAX_CREDIBILITY_X_FACTOR),"max-credibility-x-coverage-factor", MAX_PRODUCT_OF_CREDIBILITY_AND_COVERAGE_FACTOR));
	}
	
	private void initRankingProcedure() {
		rankingProcedure = new LinkedList<>();
		rankingProcedure.add(getDefaultParameter());
		rankingProcedure.add(new RuleRankParameter(get(Labels.NFS),"nfs", NFS));
		rankingProcedure.add(new RuleRankParameter(get(Labels.REPEATED_NFS),"rnfs", REPEATED_NFS));
		rankingProcedure.add(new RuleRankParameter(get(Labels.NFS_CLOSURE),"nfs-*", NFS_CLOSURE));
		rankingProcedure.add(new RuleRankParameter(get(Labels.REPEATED_NFS_CLOSURE),"rnfs-*", REPEATED_NFS_CLOSURE));
		rankingProcedure.add(new RuleRankParameter(get(Labels.NFS_P_CLOSURE),"nfs-p-*", NFS_P_CLOSURE));
		rankingProcedure.add(new RuleRankParameter(get(Labels.REPEATED_NFS_P_CLOSURE),"rnfs-p-*", REPEATED_NFS_P_CLOSURE));
		rankingProcedure.add(new RuleRankParameter(get(Labels.MIF),"mif", MIF));
		rankingProcedure.add(new RuleRankParameter(get(Labels.REPEATED_MIF),"rmif", REPEATED_MIF));
	}
	
	private void initDominance() {
		dominance = new LinkedList<>();
		dominance.add(getDefaultParameter());
		dominance.add(new RuleRankParameter(get(Labels.PARETO),"pareto", PARETO));
		dominance.add(new RuleRankParameter(get(Labels.LORENZ),"lorenz", LORENZ));
	}
	
	private void initDominanceForPairsOfOrdinalValues() {
		dominanceForPairsOfOrdinalValues = new LinkedList<>();
		dominanceForPairsOfOrdinalValues.add(getDefaultParameter());
		dominanceForPairsOfOrdinalValues.add(new RuleRankParameter(get(Labels.STRICT_ORDINAL_DOMINANCE_CHECK_METHOD),"strict", STRICT_ORDINAL_DOMINANCE_CHECK_METHOD));
		dominanceForPairsOfOrdinalValues.add(new RuleRankParameter(get(Labels.CLASSIC_ORDINAL_DOMINANCE_CHECK_METHOD),"classic", CLASSIC_ORDINAL_DOMINANCE_CHECK_METHOD));
	}
	
	private void initNegativeExamplesTreatmentForVCDRSA() {
		negativeExamplesTreatmentForVCDRSA = new LinkedList<>();
		negativeExamplesTreatmentForVCDRSA.add(getDefaultParameter());
		negativeExamplesTreatmentForVCDRSA.add(new RuleRankParameter(get(Labels.ONLY_INCONSISTENT_NEGATIVE_EXAMPLES),"only-inconsistent", COVER_ONLY_INCONSISTENT_NEGATIVE_EXAMPLES));
		negativeExamplesTreatmentForVCDRSA.add(new RuleRankParameter(get(Labels.ONLY_INCONSISTENT_AND_BOUNDARY_NEGATIVE_EXAMPLES),"only-inconsistent-and-boundary", COVER_ONLY_INCONSISTENT_AND_BOUNDARY_NEGATIVE_EXAMPLES));
		negativeExamplesTreatmentForVCDRSA.add(new RuleRankParameter(get(Labels.ANY_NEGATIVE_EXAMPLES),"any", COVER_ANY_NEGATIVE_EXAMPLES));
	}
	
	private void initRuleConditionsSelectionMethodInVCDomLEM() {
		ruleConditionsSelectionMethodInVCDomLEM = new LinkedList<>();
		ruleConditionsSelectionMethodInVCDomLEM.add(getDefaultParameter());
		ruleConditionsSelectionMethodInVCDomLEM.add(new RuleRankParameter(get(Labels.CHOOSE_CONDITIONS_FROM_ONE_OBJECT),"base", CHOOSE_CONDITIONS_FROM_ONE_OBJECT));
		ruleConditionsSelectionMethodInVCDomLEM.add(new RuleRankParameter(get(Labels.MIX_CONDITIONS_FROM_DIFFERENT_OBJECTS),"mix", MIX_CONDITIONS_FROM_DIFFERENT_OBJECTS));
	}
	
	private void initOptimizeRuleConsistencyInVCDomLEMWrt() {
		optimizeRuleConsistencyInVCDomLEMWrt = new LinkedList<>();
		optimizeRuleConsistencyInVCDomLEMWrt.add(getDefaultParameter());
		optimizeRuleConsistencyInVCDomLEMWrt.add(new RuleRankParameter(get(Labels.CONSISTENCY_APPROXIMATION),"approximation", APPROXIMATION));
		optimizeRuleConsistencyInVCDomLEMWrt.add(new RuleRankParameter(get(Labels.CONSISTENCY_SET),"set", SET));
	}
	
	private void initBooleanParameter() {
		booleanParameter = createBooleanValues();
		allowEmptyRules = createBooleanValues();
		useEdgeRegions = createBooleanValues();
		writeDominationInfo = createBooleanValues();
		writeRulesStatistics = createBooleanValues();
		writePositiveExamples = createBooleanValues();
	}
	
	private List<RuleRankParameter> createBooleanValues() {
		List<RuleRankParameter> list = new LinkedList<>();
		list.add(getDefaultParameter());
		list.add(new RuleRankParameter(get(Labels.BOOLEAN_YES),"true", Integer.MAX_VALUE));
		list.add(new RuleRankParameter(get(Labels.BOOLEAN_NO),"false", Integer.MAX_VALUE));
		return list;
	}
	
	private String get(String labelCode) {
		return labels.get(labelCode);
	}
	
}
