package pl.jowko.rulerank.desktop.feature.learningtable;

import pl.jowko.rulerank.desktop.feature.learningtable.wrappers.IntegerFieldWrapper;
import pl.jowko.rulerank.feature.customfx.IntegerField;
import pl.jowko.rulerank.logger.RuleRankLogger;
import pl.poznan.put.cs.idss.jrs.types.Field;

/**
 * This class provides special field on edit event for Integer field type. <br>
 * This field only accepts integer numbers. <br>
 * It also allows to handle unknown field type. <br>
 *  <br>
 * Created by Piotr on 2018-05-09.
 * @see IntegerField
 */
class IntegerFieldTableCell <T> extends AcceptOnExitTableCell<T> {
	
	@Override
	void createTextField() {
		textField = new IntegerField(IntegerField.NEGATIVE_INTEGER_PATTERN);
		textField.setCharLimit(10);
		textField.setText(getString());
		textField.setOnAction(evt -> {
			if(textField.getText() != null && !textField.getText().isEmpty()){
				try {
					commitEdit(getField());
				} catch (NumberFormatException e) {
					RuleRankLogger.warn("Value: [" + textField.getText() + "] is not valid for integer type. Valid values are from range: " + Integer.MIN_VALUE + " to " + Integer.MAX_VALUE);
				}
			}
		});
	}
	
	@Override
	protected Field getField() {
		String text = textField.getText().trim();
		if(text.isEmpty() || "?".equals(text)) {
			Field field = new IntegerFieldWrapper();
			field.setUnknown();
			return field;
		}
		return new IntegerFieldWrapper(Integer.valueOf(textField.getText()));
	}
	
}
