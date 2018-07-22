package pl.jowko.rulerank.desktop.feature.learningtable;

import pl.jowko.rulerank.desktop.feature.internationalization.Labels;
import pl.jowko.rulerank.desktop.service.Validator;
import pl.poznan.put.cs.idss.jrs.types.Attribute;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static pl.jowko.rulerank.desktop.utils.BooleanUtils.not;

/**
 * Created by Piotr on 2018-05-16.
 * This class validates provided Learning Table
 * @see Validator
 * @see LearningTableController
 */
public class LearningTableValidator extends Validator {
	
	private LearningTable table;
	
	/**
	 * Create instance of this class and validate provided table.
	 * @param table to validate
	 */
	public LearningTableValidator(LearningTable table) {
		this.table = table;
		validateTable();
	}
	
	/**
	 * Validate provided table. This will check, if:
	 * - there is only one decision attribute
	 * - attribute names are unique
	 * - non decision fields doesn't contain unknown fields
	 */
	private void validateTable() {
		validateDecisionAttributes();
		validateAttributeUniqueness();
		validateConditionAttributes();
		
		UnknownFieldValidator validator = new UnknownFieldValidator(table);
		if(not(validator.isValid())) {
			appendError(Labels.LEARN_TABLE_UNKNOWN_FIELDS);
		}
	}
	
	/**
	 * This method will check, if there is only one active decision attribute.
	 * If validation fails, it will print all active decision attribute in error message
	 */
	private void validateDecisionAttributes() {
		List<Attribute> decisionAttributes = table.getAttributes().stream()
				.filter(attribute -> attribute.getActive() && attribute.getKind() == Attribute.DECISION)
				.collect(Collectors.toList());
		
		if(decisionAttributes.size() > 1) {
			appendError(Labels.LEARN_TABLE_DECISION_VALIDATION);
			for(Attribute attribute: decisionAttributes) {
				errorMsg.append('[')
						.append(attribute.getName())
						.append("], ");
			}
			errorMsg.append('\n');
		}
	}
	
	/**
	 * This method will check, if attribute names are unique.
	 * If validation fails, it will print error message containing names of this attributes.
	 */
	private void validateAttributeUniqueness() {
		List<String> notUniqueAttributesNames = table.getAttributes().stream()
				.map(Attribute::getName)
				.collect(groupingBy(Function.identity(), counting()))
				.entrySet()
				.stream()
				.filter(entry -> entry.getValue() > 1)
				.map(Map.Entry::getKey)
				.distinct()
				.collect(Collectors.toList());
		
		if(notUniqueAttributesNames.isEmpty())
			return;
		
		appendError(Labels.LEARN_TABLE_ATTRIBUTE_NAMES_NOT_UNIQUE);
		
		notUniqueAttributesNames.forEach(name ->
				errorMsg.append('[')
						.append(name)
						.append("] ")
		);
		errorMsg.append('\n');
	}
	
	private void validateConditionAttributes() {
		for(Attribute attribute : table.getAttributes()) {
			// checks if table contains active condition attributes with gain or cost criterion
			if(attribute.getKind() == Attribute.NONE && attribute.getActive() && attribute.getPreferenceType() != Attribute.NONE) {
				return;
			}
		}
		
		appendError(Labels.LEARN_TABLE_NO_CONDITION_FIELDS);
	}
	
}
