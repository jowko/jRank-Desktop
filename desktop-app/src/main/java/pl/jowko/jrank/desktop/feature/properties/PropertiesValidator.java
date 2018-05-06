package pl.jowko.jrank.desktop.feature.properties;

import pl.jowko.jrank.desktop.service.LanguageService;
import pl.jowko.jrank.desktop.utils.StringUtils;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static pl.jowko.jrank.desktop.utils.BooleanUtils.not;

/**
 * Created by Piotr on 2018-05-06.
 */
class PropertiesValidator {
	
	private LanguageService labels;
	private JRankProperties properties;
	private String errorMsg;
	
	PropertiesValidator(JRankProperties properties) {
		this.properties = properties;
		labels = LanguageService.getInstance();
		errorMsg = "";
		validate();
	}
	
	boolean isValid() {
		return errorMsg.isEmpty();
	}
	
	String getErrorMessages() {
		return errorMsg;
	}
	
	private void validate() { //TODO make labels
		if(StringUtils.isNullOrEmpty(properties.getLearningDataFile()))
			errorMsg += "Learning data file is empty.\n";
		
		if(validateConsistencyMeasureThreshold())
			errorMsg += "Consistency measure threshold is smaller than zero.\n";
		
		if(validateFuzzyDegreesForExhaustiveSet())
			errorMsg += "Incorrect fuzzy satisfaction degrees calculation method for exhaustive set of decision rules.\n";
		
		if(validateConsistencyMeasure())
			errorMsg += "Incorrect consistency measure threshold. Value has to be not greater than 1.0.\n";
		
		if(validateCertainRules())
			errorMsg += "In VC-DRSA only certain rules may be induced.\n";
		
		if(validateExhaustiveSetOfCertainRules())
			errorMsg += "In VC-DRSA, exhaustive set of certain rules may be used only for monotonic consistency measures: epsilon, epsilon*, and epsilon', not for rough membership.\n";
		
		if(validateFuzzyDegreesForExhaustivePossibleRulesWithRoughMembership())
			errorMsg += "Fuzzy satisfaction degrees cannot be used in DRSA for exhaustive set of possible rules with rough membership measure. Assuming crisp satisfaction degrees in preference graph.\n";
		
		if(validateOptimizePossibleRules())
			errorMsg += "In VC-DomLEM, consistency of possible rules cannot be optimized w.r.t. set, only w.r.t. upper approximation of that set. Assuming optimization of rule consistency measure w.r.t. upper approximation.\n";
	}
	
	private boolean validateConsistencyMeasureThreshold() {
		return nonNull(properties.getConsistencyMeasureThreshold()) && properties.getConsistencyMeasureThreshold() < 0;
	}
	
	private boolean validateFuzzyDegreesForExhaustiveSet() {
		return nonNull(properties.getSatisfactionDegreesInPreferenceGraph())
				&& nonNull(properties.getFuzzySatisfactionDegreeCalculationMethod())
				&& nonNull(properties.getConsideredSetOfRules())
				&& properties.getSatisfactionDegreesInPreferenceGraph().getTextValue().equalsIgnoreCase("fuzzy")
				&& properties.getFuzzySatisfactionDegreeCalculationMethod().getTextValue().equalsIgnoreCase("max-credibility-x-coverage-factor")
				&& properties.getConsideredSetOfRules().getTextValue().equalsIgnoreCase("exhaustive");
	}
	
	private boolean validateConsistencyMeasure() {
		return nonNull(properties.getConsistencyMeasure())
				&& nonNull(properties.getConsistencyMeasureThreshold())
				&& (properties.getConsistencyMeasure().getTextValue().equalsIgnoreCase("epsilon")
					|| properties.getConsistencyMeasure().getTextValue().equalsIgnoreCase("epsilon*")
					|| properties.getConsistencyMeasure().getTextValue().equalsIgnoreCase("rough-membership"))
				&& properties.getConsistencyMeasureThreshold() > 1.0d;
	}
	
	private boolean validateCertainRules() {
		return nonNull(properties.getTypeOfRules())
				&& getApproach() == 1
				&& properties.getTypeOfRules().getTextValue().equalsIgnoreCase("possible");
	}
	
	private boolean validateExhaustiveSetOfCertainRules() {
		return nonNull(properties.getConsideredSetOfRules())
				&& nonNull(properties.getConsistencyMeasure())
				&& nonNull(properties.getConsistencyMeasureThreshold())
				&& properties.getConsideredSetOfRules().getTextValue().equalsIgnoreCase("exhaustive")
				&& properties.getConsistencyMeasure().getTextValue().equalsIgnoreCase("rough-membership")
				&& properties.getConsistencyMeasureThreshold() < 1.0d;
	}
	
	private boolean validateFuzzyDegreesForExhaustivePossibleRulesWithRoughMembership() {
		return nonNull(properties.getSatisfactionDegreesInPreferenceGraph())
				&& nonNull(properties.getTypeOfRules())
				&& nonNull(properties.getConsideredSetOfRules())
				&& nonNull(properties.getConsistencyMeasure())
				&& properties.getSatisfactionDegreesInPreferenceGraph().getTextValue().equalsIgnoreCase("fuzzy")
				&& getApproach() == 0
				&& properties.getTypeOfRules().getTextValue().equalsIgnoreCase("possible")
				&& properties.getConsideredSetOfRules().getTextValue().equalsIgnoreCase("exhaustive")
				&& properties.getConsistencyMeasure().getTextValue().equalsIgnoreCase("rough-membership");
	}
	
	private boolean validateOptimizePossibleRules() {
		return nonNull(properties.getConsideredSetOfRules())
				&& nonNull(properties.getTypeOfRules())
				&& nonNull(properties.getOptimizeRuleConsistencyInVCDomLEMWrt())
				&& properties.getConsideredSetOfRules().getTextValue().equalsIgnoreCase("minimal")
				&& properties.getTypeOfRules().getTextValue().equalsIgnoreCase("possible")
				&& properties.getOptimizeRuleConsistencyInVCDomLEMWrt().getTextValue().equalsIgnoreCase("set");
	}
	
	private int getApproach() {
		if(isNull(properties.getConsistencyMeasure()) || isNull(properties.getConsistencyMeasureThreshold()))
			return 0;
		
		if((properties.getConsistencyMeasure().getTextValue().equalsIgnoreCase("rough-membership") && properties.getConsistencyMeasureThreshold() < 1.0d) ||
				(not(properties.getConsistencyMeasure().getTextValue().equalsIgnoreCase("rough-membership")) && properties.getConsistencyMeasureThreshold() > 0.0d)
				) {
			return 1;
		} else {
			return 0;
 		}
	}
	
}
