package pl.jowko.jrank.desktop.feature.learningtable;

import pl.jowko.jrank.feature.customfx.IntegerField;

/**
 * Created by Piotr on 2018-05-09.
 */
class IntegerFieldTableCell <T> extends NumberFieldTableCell <T> {
	
	@Override
	void createTextField() {
		textField = new IntegerField(IntegerField.NEGATIVE_INTEGER_PATTERN);
		textField.setText(getString());
		
		textField.setOnAction(evt -> {
			if(textField.getText() != null && !textField.getText().isEmpty()){
				commitEdit(new pl.poznan.put.cs.idss.jrs.types.IntegerField(Integer.valueOf(textField.getText())));
			}
		});
	}
	
}
