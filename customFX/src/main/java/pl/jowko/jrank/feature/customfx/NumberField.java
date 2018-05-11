package pl.jowko.jrank.feature.customfx;

import javafx.scene.control.TextField;

import java.util.regex.Pattern;

import static java.util.Objects.isNull;

/**
 * Created by Piotr on 2018-05-09.
 */
class NumberField extends TextField {
	
	private Pattern numberPattern;
	
	NumberField(String pattern) {
		numberPattern = Pattern.compile(pattern);
	}
	
	@Override
	public void replaceText(int start, int end, String text) {
		if (validate(start, text)) {
			super.replaceText(start, end, text);
		}
	}
	
	@Override
	public void replaceSelection(String text) {
		if (validate(Integer.MAX_VALUE, text)) {
			super.replaceSelection(text);
		}
	}
	
	private boolean validate(int start, String text) {
		String currentText = isNull(getText()) ? "" : getText();
		if(start == 0) {
			return numberPattern.matcher(text + currentText).matches();
		} else {
			return numberPattern.matcher(currentText + text).matches();
		}
	}
	
}