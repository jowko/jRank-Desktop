package pl.jowko.jrank.desktop.feature.properties;

import pl.jowko.jrank.desktop.feature.settings.Labels;
import pl.jowko.jrank.desktop.service.LanguageService;
import pl.jowko.jrank.desktop.utils.StringUtils;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static pl.jowko.jrank.desktop.utils.BooleanUtils.not;
import static pl.poznan.put.cs.idss.jrs.ranking.RankerParameters.DRSA;
import static pl.poznan.put.cs.idss.jrs.ranking.RankerParameters.VCDRSA;

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
	
	private void validate() {
		if(StringUtils.isNullOrEmpty(properties.getLearningDataFile()))
			errorMsg += labels.get(Labels.LEARNING_DATA_FILE_EMPTY);
		
		if(validateConsistencyMeasureThreshold())
			errorMsg += labels.get(Labels.CONSISTENCY_MEASURE_LESS_THAN_ZERO);
		
		if(validateFuzzyDegreesForExhaustiveSet())
			errorMsg += labels.get(Labels.FUZZY_EXHAUSTIVE_SET);
		
		if(validateConsistencyMeasure())
			errorMsg += labels.get(Labels.INCORRECT_CONSISTENCY_MEASURE);
		
		if(validateCertainRules())
			errorMsg += labels.get(Labels.INCORRECT_CERTAIN_RULES);
		
		if(validateExhaustiveSetOfCertainRules())
			errorMsg += labels.get(Labels.EXHAUSTIVE_SET_CERTAIN_RULES);
		
		if(validateFuzzyDegreesForExhaustivePossibleRulesWithRoughMembership())
			errorMsg += labels.get(Labels.FUZZY_EXHAUSTIVE_POSSIBLE_ROUGH_MEMBERSHIP);
		
		if(validateOptimizePossibleRules())
			errorMsg += labels.get(Labels.OPTIMIZE_POSSIBLE_RULES);
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
				&& getApproach() == VCDRSA
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
				&& getApproach() == DRSA
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
			return DRSA;
		
		if((properties.getConsistencyMeasure().getTextValue().equalsIgnoreCase("rough-membership") && properties.getConsistencyMeasureThreshold() < 1.0d) ||
				(not(properties.getConsistencyMeasure().getTextValue().equalsIgnoreCase("rough-membership")) && properties.getConsistencyMeasureThreshold() > 0.0d)
				) {
			return VCDRSA;
		} else {
			return DRSA;
 		}
	}
	
}
