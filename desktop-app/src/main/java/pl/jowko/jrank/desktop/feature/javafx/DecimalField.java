package pl.jowko.jrank.desktop.feature.javafx;

import javafx.scene.control.TextField;

import java.util.regex.Pattern;

import static java.util.Objects.isNull;

/**
 * Created by Piotr on 2018-05-06.
 * This TextField accepts decimal numbers on input.
 */
public class DecimalField extends TextField {
	
	private static Pattern decimalPattern = Pattern.compile("[0-9]*(\\.[0-9]*)?");
	
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
			return decimalPattern.matcher(text + currentText).matches();
		} else {
			return decimalPattern.matcher(currentText + text).matches();
		}
	}
	
}
