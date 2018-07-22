package pl.jowko.rulerank.desktop.feature.learningtable;

import pl.jowko.rulerank.desktop.feature.learningtable.wrappers.FloatFieldWrapper;
import pl.jowko.rulerank.feature.customfx.DecimalField;
import pl.jowko.rulerank.logger.RuleRankLogger;
import pl.poznan.put.cs.idss.jrs.types.Field;

/**
 * Created by Piotr on 2018-05-11.
 * This class provides special field on edit event for Decimal field type.
 * This field accepts positive and negative double numbers both, in standard and scientific notation.
 * It also allows to handle unknown field type.
 * @see DecimalField
 */
class DecimalFieldTableCell <T> extends AcceptOnExitTableCell<T> {
	
	@Override
	void createTextField() {
		textField = new DecimalField(DecimalField.NEGATIVE_SCIENTIFIC_DECIMAL_PATTERN);
		textField.setCharLimit(500);
		textField.setText(getString());
		
		textField.setOnAction(evt -> {
			if(textField.getText() != null && !textField.getText().isEmpty()){
				try {
					Double value = Double.valueOf(textField.getText());
					if(value.isInfinite() || value.isNaN()) {
						RuleRankLogger.warn("Value: [" + textField.getText() + "] is not valid for decimal field type. Select smaller number.");
						return;
					}
					commitEdit(getField());
				} catch (NumberFormatException e) {
					RuleRankLogger.warn("Value [" + textField.getText() + "] is not valid for decimal field type. Select valid number.");
				}
			}
		});
	}
	
	@Override
	protected Field getField() {
		String text = textField.getText().trim();
		if(text.isEmpty() || "?".equals(text)) {
			Field field = new FloatFieldWrapper();
			field.setUnknown();
			return field;
		}
		
		Double value = Double.valueOf(textField.getText());
		return new FloatFieldWrapper(value);
	}
	
}
