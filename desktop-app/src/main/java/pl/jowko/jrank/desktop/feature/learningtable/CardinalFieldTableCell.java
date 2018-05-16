package pl.jowko.jrank.desktop.feature.learningtable;

import pl.jowko.jrank.feature.customfx.IntegerField;
import pl.jowko.jrank.logger.JRankLogger;
import pl.poznan.put.cs.idss.jrs.types.CardinalField;

/**
 * Created by Piotr on 2018-05-16.
 */
class CardinalFieldTableCell <T> extends CustomFieldTableCell<T> {
	
	@Override
	void createTextField() {
		textField = new IntegerField(IntegerField.POSITIVE_INTEGER_PATTERN);
		textField.setCharLimit(9);
		textField.setText(getString());
		textField.setOnAction(evt -> {
			if(textField.getText() != null && !textField.getText().isEmpty()){
				try {
					commitEdit(new CardinalField(Integer.valueOf(textField.getText())));
				} catch (NumberFormatException e) {
					JRankLogger.warn("Value: [" + textField.getText() + "] is to large. Maximum value for cardinal type is: " + Integer.MAX_VALUE);
				}
			}
		});
	}
	
}
