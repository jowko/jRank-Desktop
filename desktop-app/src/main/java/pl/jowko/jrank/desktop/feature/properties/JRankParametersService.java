package pl.jowko.jrank.desktop.feature.properties;

import pl.jowko.jrank.desktop.feature.internationalization.LanguageService;

import java.util.ArrayList;
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
 * Created by Piotr on 2018-05-01.
 */
class JRankParametersService {
	
	private static JRankParametersService instance;
	
	private LanguageService labels; //TODO make labels
	
	private JRankParameter emptyParameter;
	private List<JRankParameter> typeOfFamilyOfCriteria;
	private List<JRankParameter> consistencyMeasure;
	private List<JRankParameter> typeOfRules;
	private List<JRankParameter> consideredSetOfRules;
	private List<JRankParameter> satisfactionDegreesInPreferenceGraph;
	private List<JRankParameter> fuzzySatisfactionDegreeCalculationMethod;
	private List<JRankParameter> rankingProcedure;
	private List<JRankParameter> dominance;
	private List<JRankParameter> dominanceForPairsOfOrdinalValues;
	private List<JRankParameter> negativeExamplesTreatmentForVCDRSA;
	private List<JRankParameter> ruleConditionsSelectionMethodInVCDomLEM;
	private List<JRankParameter> optimizeRuleConsistencyInVCDomLEMWrt;
	private List<JRankParameter> booleanParameter;
	
	private JRankParametersService() {
		labels = LanguageService.getInstance();
		initializeParameters();
	}
	
	public static JRankParametersService getInstance() {
		if(instance == null) {
			instance = new JRankParametersService();
		}
		return instance;
	}
	
	public JRankParameter findByTextValue(List<JRankParameter> parameters, String textValue) {
		return parameters.stream()
				.filter(param -> param.getTextValue().equalsIgnoreCase(textValue))
				.findAny()
				.orElse(null);
	}
	
	public JRankParameter getEmptyParameter() {
		return emptyParameter;
	}
	
	public List<JRankParameter> getTypeOfFamilyOfCriteria() {
		return typeOfFamilyOfCriteria;
	}
	
	public List<JRankParameter> getConsistencyMeasure() {
		return consistencyMeasure;
	}
	
	public List<JRankParameter> getTypeOfRules() {
		return typeOfRules;
	}
	
	public List<JRankParameter> getConsideredSetOfRules() {
		return consideredSetOfRules;
	}
	
	public List<JRankParameter> getSatisfactionDegreesInPreferenceGraph() {
		return satisfactionDegreesInPreferenceGraph;
	}
	
	public List<JRankParameter> getFuzzySatisfactionDegreeCalculationMethod() {
		return fuzzySatisfactionDegreeCalculationMethod;
	}
	
	public List<JRankParameter> getRankingProcedure() {
		return rankingProcedure;
	}
	
	public List<JRankParameter> getDominance() {
		return dominance;
	}
	
	public List<JRankParameter> getDominanceForPairsOfOrdinalValues() {
		return dominanceForPairsOfOrdinalValues;
	}
	
	public List<JRankParameter> getNegativeExamplesTreatmentForVCDRSA() {
		return negativeExamplesTreatmentForVCDRSA;
	}
	
	public List<JRankParameter> getRuleConditionsSelectionMethodInVCDomLEM() {
		return ruleConditionsSelectionMethodInVCDomLEM;
	}
	
	public List<JRankParameter> getOptimizeRuleConsistencyInVCDomLEMWrt() {
		return optimizeRuleConsistencyInVCDomLEMWrt;
	}
	
	public List<JRankParameter> getBooleanParameter() {
		return booleanParameter;
	}
	
	private void initializeParameters() {
		emptyParameter = new JRankParameter("", "", Integer.MIN_VALUE);
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
		typeOfFamilyOfCriteria = new ArrayList<>();
		typeOfFamilyOfCriteria.add(getEmptyParameter());
		typeOfFamilyOfCriteria.add(new JRankParameter("Any","any", ANY_FAMILY_OF_CRITERIA));
		typeOfFamilyOfCriteria.add(new JRankParameter("Consistent","consistent", CONSISTENT_FAMILY_OF_CRITERIA));
	}
	
	private void initConsistencyMeasure() {
		consistencyMeasure = new ArrayList<>();
		consistencyMeasure.add(getEmptyParameter());
		consistencyMeasure.add(new JRankParameter("Epsilon","epsilon", EPSILON));
		consistencyMeasure.add(new JRankParameter("Epsilon*","epsilon*", EPSILON_STAR));
		consistencyMeasure.add(new JRankParameter("Epsilon'","epsilon'", EPSILON_PRIM));
		consistencyMeasure.add(new JRankParameter("Rough Membership","rough-membership", ROUGH_MEMBERSHIP));
	}
	
	private void initTypeOfRules() {
		typeOfRules = new ArrayList<>();
		typeOfRules.add(getEmptyParameter());
		typeOfRules.add(new JRankParameter("Certain","certain", CERTAIN));
		typeOfRules.add(new JRankParameter("Possible","possible", POSSIBLE));
	}
	
	private void initConsideredSetOfRules() {
		consideredSetOfRules = new ArrayList<>();
		consideredSetOfRules.add(getEmptyParameter());
		consideredSetOfRules.add(new JRankParameter("Minimal","minimal", MINIMAL_SET_OF_RULES));
		consideredSetOfRules.add(new JRankParameter("Exhaustive","exhaustive", EXHAUSTIVE_SET_OF_RULES));
	}
	
	private void initSatisfactionDegreesInPreferenceGraph() {
		satisfactionDegreesInPreferenceGraph = new ArrayList<>();
		satisfactionDegreesInPreferenceGraph.add(getEmptyParameter());
		satisfactionDegreesInPreferenceGraph.add(new JRankParameter("Fuzzy","fuzzy", FUZZY));
		satisfactionDegreesInPreferenceGraph.add(new JRankParameter("Crisp","crisp", CRISP));
	}
	
	private void initFuzzySatisfactionDegreeCalculationMethod() {
		fuzzySatisfactionDegreeCalculationMethod = new ArrayList<>();
		fuzzySatisfactionDegreeCalculationMethod.add(getEmptyParameter());
		fuzzySatisfactionDegreeCalculationMethod.add(new JRankParameter("Max credibility","max-credibility", MAX_CREDIBILITY));
		fuzzySatisfactionDegreeCalculationMethod.add(new JRankParameter("Max credibility X coverage factor","max-credibility-x-coverage-factor", MAX_PRODUCT_OF_CREDIBILITY_AND_COVERAGE_FACTOR));
	}
	
	private void initRankingProcedure() {
		rankingProcedure = new ArrayList<>();
		rankingProcedure.add(emptyParameter);
		rankingProcedure.add(new JRankParameter("NFS","nfs", NFS));
		rankingProcedure.add(new JRankParameter("RNFS","rnfs", REPEATED_NFS));
		rankingProcedure.add(new JRankParameter("NFS-*","nfs-*", NFS_CLOSURE));
		rankingProcedure.add(new JRankParameter("RNFS-*","rnfs-*", REPEATED_NFS_CLOSURE));
		rankingProcedure.add(new JRankParameter("NFS-P-*","nfs-p-*", NFS_P_CLOSURE));
		rankingProcedure.add(new JRankParameter("RNFS-P-*","rnfs-p-*", REPEATED_NFS_P_CLOSURE));
	}
	
	private void initDominance() {
		dominance = new ArrayList<>();
		dominance.add(getEmptyParameter());
		dominance.add(new JRankParameter("Pareto","pareto", PARETO));
		dominance.add(new JRankParameter("Lorenz","lorenz", LORENZ));
	}
	
	private void initDominanceForPairsOfOrdinalValues() {
		dominanceForPairsOfOrdinalValues = new ArrayList<>();
		dominanceForPairsOfOrdinalValues.add(getEmptyParameter());
		dominanceForPairsOfOrdinalValues.add(new JRankParameter("Strict","strict", STRICT_ORDINAL_DOMINANCE_CHECK_METHOD));
		dominanceForPairsOfOrdinalValues.add(new JRankParameter("Classic","classic", CLASSIC_ORDINAL_DOMINANCE_CHECK_METHOD));
	}
	
	private void initNegativeExamplesTreatmentForVCDRSA() {
		negativeExamplesTreatmentForVCDRSA = new ArrayList<>();
		negativeExamplesTreatmentForVCDRSA.add(getEmptyParameter());
		negativeExamplesTreatmentForVCDRSA.add(new JRankParameter("Only inconsistent","only-inconsistent", COVER_ONLY_INCONSISTENT_NEGATIVE_EXAMPLES));
		negativeExamplesTreatmentForVCDRSA.add(new JRankParameter("Only inconsistent and boundary","only-inconsistent-and-boundary", COVER_ONLY_INCONSISTENT_AND_BOUNDARY_NEGATIVE_EXAMPLES));
		negativeExamplesTreatmentForVCDRSA.add(new JRankParameter("Any","any", COVER_ANY_NEGATIVE_EXAMPLES));
	}
	
	private void initRuleConditionsSelectionMethodInVCDomLEM() {
		ruleConditionsSelectionMethodInVCDomLEM = new ArrayList<>();
		ruleConditionsSelectionMethodInVCDomLEM.add(getEmptyParameter());
		ruleConditionsSelectionMethodInVCDomLEM.add(new JRankParameter("Base","base", CHOOSE_CONDITIONS_FROM_ONE_OBJECT));
		ruleConditionsSelectionMethodInVCDomLEM.add(new JRankParameter("Mix","mix", MIX_CONDITIONS_FROM_DIFFERENT_OBJECTS));
	}
	
	private void initOptimizeRuleConsistencyInVCDomLEMWrt() {
		optimizeRuleConsistencyInVCDomLEMWrt = new ArrayList<>();
		optimizeRuleConsistencyInVCDomLEMWrt.add(getEmptyParameter());
		optimizeRuleConsistencyInVCDomLEMWrt.add(new JRankParameter("Approximation","approximation", APPROXIMATION));
		optimizeRuleConsistencyInVCDomLEMWrt.add(new JRankParameter("Set","set", SET));
	}
	
	private void initBooleanParameter() {
		booleanParameter = new ArrayList<>();
		booleanParameter.add(getEmptyParameter());
		booleanParameter.add(new JRankParameter("Yes","true", Integer.MAX_VALUE));
		booleanParameter.add(new JRankParameter("No","false", Integer.MAX_VALUE));
	}
	
}
