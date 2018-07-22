package pl.jowko.rulerank.desktop.feature.properties;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by Piotr on 2018-05-01.
 * This class is used in properties form as data storage for ComboBox values.
 * It contains label and also text and int value from jRank application.
 * @see RuleRankParametersService
 */
public class RuleRankParameter implements Serializable {
	
	private static final long serialVersionUID = -9123653248987654173L;
	
	private String label;
	/**
	 * String value of parameter used in properties files. Exists due to backward compatibility.
	 */
	private String textValue;
	/**
	 * Integer value of parameter for jRank application. Exists due to backward compatibility.
	 */
	private int value;
	
	/**
	 * @param label to display for user
	 * @param textValue from jRank application
	 * @param value from jRank application
	 */
	RuleRankParameter(String label, String textValue, int value) {
		this.label = label;
		this.textValue = textValue;
		this.value = value;
	}
	
	public String getLabel() {
		return label;
	}
	
	public String getTextValue() {
		return textValue;
	}
	
	public int getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return label;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		RuleRankParameter that = (RuleRankParameter) o;
		return value == that.value &&
				Objects.equals(textValue, that.textValue);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(textValue, value);
	}
}
