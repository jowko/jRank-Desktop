package pl.jowko.jrank.desktop.feature.learningtable;

import pl.jowko.jrank.feature.customfx.StringTextField;

import static pl.jowko.jrank.feature.customfx.StringTextField.STRING_FIELD_PATTERN;

/**
 * Created by Piotr on 2018-05-13.
 */
class StringFieldTableCell <T> extends CustomFieldTableCell<T> {
	
	@Override
	void createTextField() {
		textField = new StringTextField(STRING_FIELD_PATTERN);
		textField.setText(getString());
		
		textField.setOnAction(evt -> {
			if(textField.getText() != null && !textField.getText().isEmpty()){
				commitEdit(new pl.poznan.put.cs.idss.jrs.types.StringField(textField.getText()));
			}
		});
	}
	
}
