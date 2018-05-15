package pl.jowko.jrank.desktop.feature.learningtable;

import pl.jowko.jrank.feature.customfx.DecimalField;
import pl.jowko.jrank.logger.JRankLogger;

/**
 * Created by Piotr on 2018-05-11.
 */
class DecimalFieldTableCell <T> extends CustomFieldTableCell<T> {
	
	@Override
	void createTextField() {
		textField = new DecimalField(DecimalField.NEGATIVE_DECIMAL_PATTERN);
		textField.setCharLimit(500);
		textField.setText(getString());
		
		textField.setOnAction(evt -> {
			if(textField.getText() != null && !textField.getText().isEmpty()){
				Double value = Double.valueOf(textField.getText());
				if(value.isInfinite() || value.isNaN()) {
					JRankLogger.warn("Value: [" + textField.getText() + "] is not valid for decimal field type. Select smaller number.");
					return;
				}
				commitEdit(new pl.poznan.put.cs.idss.jrs.types.FloatField(value));
			}
		});
	}
	
}
