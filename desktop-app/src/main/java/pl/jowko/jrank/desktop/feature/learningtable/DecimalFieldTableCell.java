package pl.jowko.jrank.desktop.feature.learningtable;

import pl.jowko.jrank.feature.customfx.DecimalField;

/**
 * Created by Piotr on 2018-05-11.
 */
class DecimalFieldTableCell <T> extends CustomFieldTableCell<T> {
	
	@Override
	void createTextField() {
		textField = new DecimalField(DecimalField.NEGATIVE_DECIMAL_PATTERN);
		textField.setText(getString());
		
		textField.setOnAction(evt -> {
			if(textField.getText() != null && !textField.getText().isEmpty()){
				commitEdit(new pl.poznan.put.cs.idss.jrs.types.FloatField(Double.valueOf(textField.getText())));
			}
		});
	}
	
}
