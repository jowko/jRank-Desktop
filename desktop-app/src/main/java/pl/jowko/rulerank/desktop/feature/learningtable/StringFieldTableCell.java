package pl.jowko.rulerank.desktop.feature.learningtable;

import pl.jowko.rulerank.desktop.feature.learningtable.wrappers.StringFieldWrapper;
import pl.jowko.rulerank.feature.customfx.StringTextField;
import pl.poznan.put.cs.idss.jrs.types.Field;

import static pl.jowko.rulerank.feature.customfx.StringTextField.ALPHANUMERIC_FIELD_PATTERN;

/**
 * This class provides special field on edit event for String field type. <br>
 * This field accepts only alphanumeric characters. <br>
 * It also allows to handle unknown field type. <br>
 *  <br>
 * Created by Piotr on 2018-05-13.
 * @see StringTextField
 */
class StringFieldTableCell <T> extends AcceptOnExitTableCell<T> {
	
	@Override
	void createTextField() {
		textField = new StringTextField(ALPHANUMERIC_FIELD_PATTERN);
		textField.setText(getString());
		
		textField.setOnAction(evt -> {
			if(textField.getText() != null && !textField.getText().isEmpty()){
				commitEdit(getField());
			}
		});
	}
	
	@Override
	protected Field getField() {
		String text = textField.getText().trim();
		if(text.isEmpty() || "?".equals(text)) {
			Field field = new StringFieldWrapper();
			field.setUnknown();
			return field;
		}
		return new StringFieldWrapper(text);
	}
	
}
