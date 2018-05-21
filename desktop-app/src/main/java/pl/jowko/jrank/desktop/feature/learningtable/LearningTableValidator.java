package pl.jowko.jrank.desktop.feature.learningtable;

import pl.jowko.jrank.desktop.feature.internationalization.Labels;
import pl.jowko.jrank.desktop.feature.internationalization.LanguageService;
import pl.poznan.put.cs.idss.jrs.types.Attribute;
import pl.poznan.put.cs.idss.jrs.types.Field;
import pl.poznan.put.cs.idss.jrs.types.StringField;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static pl.jowko.jrank.desktop.utils.StringUtils.isNullOrEmpty;

/**
 * Created by Piotr on 2018-05-16.
 */
class LearningTableValidator {
	
	private LanguageService labels;
	private LearningTable table;
	private StringBuilder errorMsg;
	private StringBuilder decisionMsg;
	
	LearningTableValidator(LearningTable table) {
		this.table = table;
		errorMsg = new StringBuilder();
		decisionMsg = new StringBuilder();
		labels = LanguageService.getInstance();
		validateTable();
	}
	
	boolean isValid() {
		return errorMsg.toString().isEmpty();
	}
	
	String getErrorMsg() {
		return errorMsg.toString();
	}
	
	boolean isDecisionAttributesValid() {
		return decisionMsg.toString().isEmpty();
	}
	
	String getDecisionMsg() {
		return decisionMsg.toString();
	}
	
	private void validateTable() {
		validateDecisionAttributes();
		validateStringFields();
		validateAttributeUniqueness();
	}
	
	private void validateStringFields() {
		for(int index=0; index<table.getExamples().size(); index++) {
			Field[] fields = table.getExamples().get(index).getFields();
			validateStringFields(fields, index);
		}
	}
	
	private void validateStringFields(Field[] fields, int rowIndex) {
		for(int i=0; i<fields.length; i++) {
			if(fields[i] instanceof StringField) {
				String value = ((StringField)fields[i]).get();
				
				if(isNullOrEmpty(value)) {
					errorMsg.append(labels.get(Labels.LEARN_TABLE_STRING_VALIDATION_1))
							.append(" [")
							.append(rowIndex)
							.append("] ")
							.append(labels.get(Labels.LEARN_TABLE_STRING_VALIDATION_2))
							.append(table.getAttributes().get(i).getName())
							.append(labels.get(Labels.LEARN_TABLE_STRING_VALIDATION_3));
				}
			}
		}
	}
	
	private void validateDecisionAttributes() {
		List<Attribute> decisionAttributes = table.getAttributes().stream()
				.filter(attribute -> attribute.getActive() && attribute.getKind() == Attribute.DECISION)
				.collect(Collectors.toList());
		
		if(decisionAttributes.size() > 1) {
			decisionMsg.append(labels.get(Labels.LEARN_TABLE_DECISION_VALIDATION));
			for(Attribute attribute: decisionAttributes) {
				decisionMsg.append('[')
						.append(attribute.getName())
						.append("], ");
			}
			decisionMsg.append('\n');
		}
	}
	
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
		
		errorMsg.append(labels.get(Labels.LEARN_TABLE_ATTRIBUTE_NAMES_NOT_UNIQUE));
		
		notUniqueAttributesNames.forEach(name ->
				errorMsg.append('[')
						.append(name)
						.append("] ")
		);
	}
	
}
