package pl.jowko.rulerank.desktop.feature.properties;

import pl.jowko.rulerank.desktop.feature.internationalization.Labels;
import pl.jowko.rulerank.desktop.service.Validator;
import pl.jowko.rulerank.desktop.utils.StringUtils;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static pl.jowko.rulerank.desktop.utils.BooleanUtils.not;
import static pl.jowko.rulerank.desktop.utils.FileExtensionExtractor.getExtension;
import static pl.poznan.put.cs.idss.jrs.ranking.RankerParameters.DRSA;
import static pl.poznan.put.cs.idss.jrs.ranking.RankerParameters.VCDRSA;

/**
 * This class perform validation on properties form before save. <br>
 * Most of validations were extracted from RuleRank console application. <br>
 *  <br>
 * Created by Piotr on 2018-05-06.
 * @see PropertiesController
 */
public class PropertiesValidator extends Validator {
	
	private RuleRankProperties properties;
	private boolean validatingDefaultProperties;
	
	/**
	 * Creates instance of this class and validates provided properties
	 * @param properties to validate
	 */
	public PropertiesValidator(RuleRankProperties properties) {
		this.properties = properties;
		validate();
	}
	
	/**
	 * Creates instance of this class and validates provided properties
	 * @param properties to validate
	 * @param validatingDefaultProperties flag with indicates if default.properties file is validated
	 */
	public PropertiesValidator(RuleRankProperties properties, boolean validatingDefaultProperties) {
		this.properties = properties;
		this.validatingDefaultProperties = validatingDefaultProperties;
		validate();
	}
	
	/**
	 * Perform validation on properties. <br>
	 * Code was modeled by validation in RuleRank console application
	 */
	private void validate() {
		if(not(validatingDefaultProperties) && StringUtils.isNullOrEmpty(properties.getLearningDataFile()))
			appendError(Labels.LEARNING_DATA_FILE_EMPTY);
		
		if(validateConsistencyMeasureThreshold())
			appendError(Labels.CONSISTENCY_MEASURE_LESS_THAN_ZERO);
		
		if(validateFuzzyDegreesForExhaustiveSet())
			appendError(Labels.FUZZY_EXHAUSTIVE_SET);
		
		if(validateConsistencyMeasure())
			appendError(Labels.INCORRECT_CONSISTENCY_MEASURE);
		
		if(validateCertainRules())
			appendError(Labels.INCORRECT_CERTAIN_RULES);
		
		if(validateExhaustiveSetOfCertainRules())
			appendError(Labels.EXHAUSTIVE_SET_CERTAIN_RULES);
		
		if(validateFuzzyDegreesForExhaustivePossibleRulesWithRoughMembership())
			appendError(Labels.FUZZY_EXHAUSTIVE_POSSIBLE_ROUGH_MEMBERSHIP);
		
		if(validateOptimizePossibleRules())
			appendError(Labels.OPTIMIZE_POSSIBLE_RULES);
		
		validateFileExtensions();
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
	
	/**
	 * Check if files have correct extensions. <br>
	 * Files should have correct extensions or no extensions. <br>
	 * If files have no extensions, they will be added on properties save.
	 * @see PropertiesSaver
	 * @see pl.jowko.rulerank.desktop.utils.FileExtensionExtractor
	 */
	private void validateFileExtensions() {
		validateExtension(properties.getLearningDataFile(), "isf", Labels.PROP_EXT_LEARNING);
		validateExtension(properties.getTestDataFile(), "isf", Labels.PROP_EXT_TEST);
		
		validateExtension(properties.getPctFile(), "isf", Labels.PROP_EXT_PCT_ISF);
		validateExtension(properties.getPctApxFile(), "apx", Labels.PROP_EXT_APX);
		validateExtension(properties.getPctRulesFile(), "rules", Labels.PROP_EXT_RULES);
		
		validateExtension(properties.getPreferenceGraphFile(), "graph", Labels.PROP_EXT_GRAPH);
		validateExtension(properties.getRankingFile(), "ranking", Labels.PROP_EXT_RANKING);
	}
	
	private void validateExtension(String property, String extension, String labelCode) {
		String learningExt = getExtension(property);
		if(nonNull(learningExt) && not(extension.equals(learningExt))) {
			appendError(labelCode);
		}
	}
	
	/**
	 * Checks, what approach used chosen: DRSA or VCDRSA.
	 * @return 0 if approach is DRSA and 1 if approach is VCDRSA
	 */
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
