package pl.jowko.rulerank.desktop.feature.properties;

import pl.jowko.rulerank.desktop.feature.internationalization.Labels;
import pl.jowko.rulerank.desktop.service.Validator;

import static java.util.Objects.isNull;

/**
 * This class validates, if properties for experiment have set values for needed fields. <br>
 * This class validates result of merge between defaults and form properties. <br>
 *  <br>
 * Created by Piotr on 2018-06-05
 */
public class PropertiesMandatoryFieldsValidator extends Validator {
	
	private RuleRankProperties properties;
	private RuleRankParameter emptyParameter;
	
	/**
	 * Creates instance of this class and validates properties.
	 * @param properties to validate
	 */
	public PropertiesMandatoryFieldsValidator(RuleRankProperties properties) {
		this.properties = properties;
		emptyParameter = RuleRankParametersService.getInstance().getDefaultParameter();
		validate();
	}
	
	private void validate() {
		validateParameters();
		
		if(isNull(properties.getConsistencyMeasureThreshold()))
			appendError(Labels.PROP_EMPTY_CONSISTENCY_THRESHOLD);
		
		if(isNull(properties.getPrecision()))
			appendError(Labels.PROP_EMPTY_PRECISION);
	}
	
	private void validateParameters() {
		validateParameter(properties.getTypeOfFamilyOfCriteria(), Labels.PROP_EMPTY_TYPE_CRITERIA);
		validateParameter(properties.getConsistencyMeasure(), Labels.PROP_EMPTY_CONSISTENCY);
		validateParameter(properties.getTypeOfRules(), Labels.PROP_EMPTY_TYPE_RULES);
		validateParameter(properties.getConsideredSetOfRules(), Labels.PROP_EMPTY_CONSIDERED_RULES);
		validateParameter(properties.getSatisfactionDegreesInPreferenceGraph(), Labels.PROP_EMPTY_SAT_DEGREES);
		validateParameter(properties.getFuzzySatisfactionDegreeCalculationMethod(), Labels.PROP_EMPTY_FUZZY_METHOD);
		validateParameter(properties.getRankingProcedure(), Labels.PROP_EMPTY_RANKING_PROCEDURE);
		validateParameter(properties.getDominance(), Labels.PROP_EMPTY_DOMINANCE);
		validateParameter(properties.getDominanceForPairsOfOrdinalValues(), Labels.PROP_EMPTY_DOMINANCE_PAIRS);
		validateParameter(properties.getNegativeExamplesTreatmentForVCDRSA(), Labels.PROP_EMPTY_NEGATIVE_EXAMPLES);
		validateParameter(properties.getRuleConditionsSelectionMethodInVCDomLEM(), Labels.PROP_EMPTY_RULE_CONDITIONS);
		validateParameter(properties.getOptimizeRuleConsistencyInVCDomLEMWrt(), Labels.PROP_EMPTY_OPTIMIZE_RULES);
	}
	
	private void validateParameter(RuleRankParameter parameter, String labelCode) {
		if(isNull(parameter) || emptyParameter.equals(parameter))
			appendError(labelCode);
	}
	
}
