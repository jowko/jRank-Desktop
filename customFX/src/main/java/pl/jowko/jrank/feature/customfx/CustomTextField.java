package pl.jowko.jrank.feature.customfx;

import javafx.scene.control.TextField;

import java.util.regex.Pattern;

import static java.util.Objects.isNull;

/**
 * Created by Piotr on 2018-05-09.
 */
public class CustomTextField extends TextField {
	
	private Pattern pattern;
	private int charLimit;
	
	CustomTextField(String pattern) {
		this.pattern = Pattern.compile(pattern);
		charLimit = 10_000;
	}
	
	CustomTextField(String pattern, int charLimit) {
		this.pattern = Pattern.compile(pattern);
		this.charLimit = charLimit;
	}
	
	public void setPattern(String pattern) {
		this.pattern = Pattern.compile(pattern);
	}
	
	public void setCharLimit(int charLimit) {
		this.charLimit = charLimit;
	}
	
	@Override
	public void replaceText(int start, int end, String text) {
		if(getCurrentText().length() > charLimit && !"".equals(text))
			return;
		
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
		String currentText = getCurrentText();
		
		if(start == 0) {
			return pattern.matcher(text + currentText).matches();
		} else {
			return pattern.matcher(currentText + text).matches();
		}
	}
	
	private String getCurrentText() {
		return isNull(getText()) ? "" : getText();
	}
	
}
