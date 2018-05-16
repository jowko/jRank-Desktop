package pl.jowko.jrank.desktop.feature.learningtable;

import pl.poznan.put.cs.idss.jrs.types.Field;
import pl.poznan.put.cs.idss.jrs.types.StringField;

import static pl.jowko.jrank.desktop.utils.StringUtils.isNullOrEmpty;

/**
 * Created by Piotr on 2018-05-16.
 */
class LearningTableValidator {
	
	private LearningTable table;
	private StringBuilder errorMsg;
	
	LearningTableValidator(LearningTable table) {
		this.table = table;
		errorMsg = new StringBuilder();
		validateTable();
	}
	
	boolean isValid() {
		return errorMsg.toString().isEmpty();
	}
	
	String getErrorMsg() {
		return errorMsg.toString();
	}
	
	private void validateTable() {
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
	
}
