package pl.jowko.jrank.desktop.feature.properties;

import pl.jowko.jrank.desktop.service.LanguageService;

import java.util.ArrayList;
import java.util.List;

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
	}
	
	private void initTypeOfFamilyOfCriteria() {
		typeOfFamilyOfCriteria = new ArrayList<>();
		typeOfFamilyOfCriteria.add(getEmptyParameter());
		typeOfFamilyOfCriteria.add(new JRankParameter("Any","any", 0));
		typeOfFamilyOfCriteria.add(new JRankParameter("Consistent","consistent", 1));
	}
	
	private void initConsistencyMeasure() {
		consistencyMeasure = new ArrayList<>();
		consistencyMeasure.add(getEmptyParameter());
		consistencyMeasure.add(new JRankParameter("Epsilon","epsilon", 0));
		consistencyMeasure.add(new JRankParameter("Epsilon*","epsilon*", 1));
		consistencyMeasure.add(new JRankParameter("Epsilon'","epsilon'", 2));
		consistencyMeasure.add(new JRankParameter("Rough Membership","rough-membership", -1));
	}
	
	private void initTypeOfRules() {
		typeOfRules = new ArrayList<>();
		typeOfRules.add(getEmptyParameter());
		typeOfRules.add(new JRankParameter("Certain","certain", 0));
		typeOfRules.add(new JRankParameter("Possible","possible", 1));
	}
	
	private void initConsideredSetOfRules() {
		consideredSetOfRules = new ArrayList<>();
		consideredSetOfRules.add(getEmptyParameter());
		consideredSetOfRules.add(new JRankParameter("Minimal","minimal", 0));
		consideredSetOfRules.add(new JRankParameter("Exhaustive","exhaustive", 1));
	}
	
	private void initSatisfactionDegreesInPreferenceGraph() {
		satisfactionDegreesInPreferenceGraph = new ArrayList<>();
		satisfactionDegreesInPreferenceGraph.add(getEmptyParameter());
		satisfactionDegreesInPreferenceGraph.add(new JRankParameter("Fuzzy","fuzzy", 0));
		satisfactionDegreesInPreferenceGraph.add(new JRankParameter("Crisp","crisp", 1));
	}
	
	private void initFuzzySatisfactionDegreeCalculationMethod() {
		fuzzySatisfactionDegreeCalculationMethod = new ArrayList<>();
		fuzzySatisfactionDegreeCalculationMethod.add(getEmptyParameter());
		fuzzySatisfactionDegreeCalculationMethod.add(new JRankParameter("Max credibility","max-credibility", 0));
		fuzzySatisfactionDegreeCalculationMethod.add(new JRankParameter("Max credibility X coverage factor","max-credibility-x-coverage-factor", 1));
	}
	
	private void initRankingProcedure() {
		rankingProcedure = new ArrayList<>();
		rankingProcedure.add(emptyParameter);
		rankingProcedure.add(new JRankParameter("NFS","nfs", 0));
		rankingProcedure.add(new JRankParameter("RNFS","rnfs", 1));
		rankingProcedure.add(new JRankParameter("NFS-*","nfs-*", 2));
		rankingProcedure.add(new JRankParameter("RNFS-*","rnfs-*", 3));
		rankingProcedure.add(new JRankParameter("NFS-P-*","nfs-p-*", 4));
		rankingProcedure.add(new JRankParameter("RNFS-P-*","rnfs-p-*", 5));
	}
	
	private void initDominance() {
		dominance = new ArrayList<>();
		dominance.add(getEmptyParameter());
		dominance.add(new JRankParameter("Pareto","pareto", 0));
		dominance.add(new JRankParameter("Lorenz","lorenz", 1));
	}
	
	private void initDominanceForPairsOfOrdinalValues() {
		dominanceForPairsOfOrdinalValues = new ArrayList<>();
		dominanceForPairsOfOrdinalValues.add(getEmptyParameter());
		dominanceForPairsOfOrdinalValues.add(new JRankParameter("Strict","strict", 4));
		dominanceForPairsOfOrdinalValues.add(new JRankParameter("Classic","classic", 5));
	}
	
	private void initNegativeExamplesTreatmentForVCDRSA() {
		negativeExamplesTreatmentForVCDRSA = new ArrayList<>();
		negativeExamplesTreatmentForVCDRSA.add(getEmptyParameter());
		negativeExamplesTreatmentForVCDRSA.add(new JRankParameter("Only inconsistent","only-inconsistent", 1));
		negativeExamplesTreatmentForVCDRSA.add(new JRankParameter("Only inconsistent and boundary","only-inconsistent-and-boundary", 2));
		negativeExamplesTreatmentForVCDRSA.add(new JRankParameter("Any","any", 3));
	}
	
	private void initRuleConditionsSelectionMethodInVCDomLEM() {
		ruleConditionsSelectionMethodInVCDomLEM = new ArrayList<>();
		ruleConditionsSelectionMethodInVCDomLEM.add(getEmptyParameter());
		ruleConditionsSelectionMethodInVCDomLEM.add(new JRankParameter("Base","base", 0));
		ruleConditionsSelectionMethodInVCDomLEM.add(new JRankParameter("Mix","mix", 1));
	}
	
	private void initOptimizeRuleConsistencyInVCDomLEMWrt() {
		optimizeRuleConsistencyInVCDomLEMWrt = new ArrayList<>();
		optimizeRuleConsistencyInVCDomLEMWrt.add(getEmptyParameter());
		optimizeRuleConsistencyInVCDomLEMWrt.add(new JRankParameter("Approximation","approximation", 0));
		optimizeRuleConsistencyInVCDomLEMWrt.add(new JRankParameter("Set","set", 1));
	}
	
}
