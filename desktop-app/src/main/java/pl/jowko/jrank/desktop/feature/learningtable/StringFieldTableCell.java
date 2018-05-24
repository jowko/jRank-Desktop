package pl.jowko.jrank.desktop.feature.learningtable;

import pl.jowko.jrank.feature.customfx.StringTextField;
import pl.poznan.put.cs.idss.jrs.types.Field;

import static pl.jowko.jrank.feature.customfx.StringTextField.ALPHANUMERIC_FIELD_PATTERN;

/**
 * Created by Piotr on 2018-05-13.
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
		return new pl.poznan.put.cs.idss.jrs.types.StringField(textField.getText());
	}
	
}
