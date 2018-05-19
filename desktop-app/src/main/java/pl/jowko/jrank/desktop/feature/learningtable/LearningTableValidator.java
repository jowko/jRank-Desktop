package pl.jowko.jrank.desktop.feature.learningtable;

import pl.poznan.put.cs.idss.jrs.types.Attribute;
import pl.poznan.put.cs.idss.jrs.types.Field;
import pl.poznan.put.cs.idss.jrs.types.StringField;

import java.util.List;
import java.util.stream.Collectors;

import static pl.jowko.jrank.desktop.utils.StringUtils.isNullOrEmpty;

/**
 * Created by Piotr on 2018-05-16.
 */
class LearningTableValidator {
	
	private LearningTable table;
	private StringBuilder errorMsg;
	private StringBuilder decisionMsg;
	
	LearningTableValidator(LearningTable table) {
		this.table = table;
		errorMsg = new StringBuilder();
		decisionMsg = new StringBuilder();
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
					errorMsg.append("Field in row: [")
							.append(rowIndex)
							.append("] and column: ")
							.append(table.getAttributes().get(i).getName())
							.append(" is empty.\n");
				}
			}
		}
	}
	
	private void validateDecisionAttributes() {
		List<Attribute> decisionAttributes = table.getAttributes().stream()
				.filter(attribute -> attribute.getActive() && attribute.getKind() == Attribute.DECISION)
				.collect(Collectors.toList());
		
		if(decisionAttributes.size() > 1) {
			decisionMsg.append("Table can only have one active decision attribute. Current decision attributes: ");
			for(Attribute attribute: decisionAttributes) {
				decisionMsg.append('[')
						.append(attribute.getName())
						.append("], ");
			}
			decisionMsg.append('\n');
		}
	}
	
}
