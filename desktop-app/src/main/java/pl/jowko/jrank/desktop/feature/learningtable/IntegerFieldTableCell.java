package pl.jowko.jrank.desktop.feature.learningtable;

import pl.jowko.jrank.feature.customfx.IntegerField;
import pl.jowko.jrank.logger.JRankLogger;

/**
 * Created by Piotr on 2018-05-09.
 */
class IntegerFieldTableCell <T> extends CustomFieldTableCell<T> {
	
	@Override
	void createTextField() {
		textField = new IntegerField(IntegerField.NEGATIVE_INTEGER_PATTERN);
		textField.setCharLimit(10);
		textField.setText(getString());
		textField.setOnAction(evt -> {
			if(textField.getText() != null && !textField.getText().isEmpty()){
				try {
					commitEdit(new pl.poznan.put.cs.idss.jrs.types.IntegerField(Integer.valueOf(textField.getText())));
				} catch (NumberFormatException e) {
					JRankLogger.warn("Value: [" + textField.getText() + "] is not valid for integer type. Valid values are from range: " + Integer.MIN_VALUE + " to " + Integer.MAX_VALUE);
				}
			}
		});
	}
	
}
