package pl.jowko.rulerank.desktop.feature.learningtable;

import pl.jowko.rulerank.desktop.feature.learningtable.wrappers.CardinalFieldWrapper;
import pl.jowko.rulerank.feature.customfx.IntegerField;
import pl.jowko.rulerank.logger.RuleRankLogger;
import pl.poznan.put.cs.idss.jrs.types.Field;

/**
 * This class provides special field on edit event for Cardinal field type. <br>
 * This field only accepts positive integer numbers. <br>
 * It also allows to handle unknown field type. <br>
 *  <br>
 * Created by Piotr on 2018-05-16.
 * @see IntegerField
 */
class CardinalFieldTableCell <T> extends AcceptOnExitTableCell<T> {
	
	@Override
	void createTextField() {
		textField = new IntegerField(IntegerField.POSITIVE_INTEGER_PATTERN);
		textField.setCharLimit(9);
		textField.setText(getString());
		textField.setOnAction(evt -> {
			if(textField.getText() != null && !textField.getText().isEmpty()){
				try {
					commitEdit(getField());
				} catch (NumberFormatException e) {
					RuleRankLogger.warn("Value: [" + textField.getText() + "] is to large. Maximum value for cardinal type is: " + Integer.MAX_VALUE);
				}
			}
		});
	}
	
	@Override
	protected Field getField() {
		String text = textField.getText().trim();
		if(text.isEmpty() || "?".equals(text)) {
			Field field = new CardinalFieldWrapper();
			field.setUnknown();
			return field;
		}
		return new CardinalFieldWrapper(Integer.valueOf(textField.getText()));
	}
	
}
