package pl.jowko.jrank.desktop.feature.learningtable;

import pl.jowko.jrank.feature.customfx.DecimalField;
import pl.jowko.jrank.logger.JRankLogger;
import pl.poznan.put.cs.idss.jrs.types.Field;
import pl.poznan.put.cs.idss.jrs.types.FloatField;

/**
 * Created by Piotr on 2018-05-11.
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
						JRankLogger.warn("Value: [" + textField.getText() + "] is not valid for decimal field type. Select smaller number.");
						return;
					}
					commitEdit(getField());
				} catch (NumberFormatException e) {
					JRankLogger.warn("Value [" + textField.getText() + "] is not valid for decimal field type. Select valid number.");
				}
			}
		});
	}
	
	@Override
	protected Field getField() {
		Double value = Double.valueOf(textField.getText());
		return new FloatField(value);
	}
	
}
