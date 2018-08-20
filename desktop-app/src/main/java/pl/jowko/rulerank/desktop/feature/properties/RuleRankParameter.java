package pl.jowko.rulerank.desktop.feature.properties;

import java.io.Serializable;
import java.util.Objects;

/**
 * This class is used in properties form as data storage for ComboBox values. <br>
 * It contains label and also text and int value from RuleRank application. <br>
 *  <br>
 * Created by Piotr on 2018-05-01.
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
	 * Integer value of parameter for RuleRank application. Exists due to backward compatibility.
	 */
	private int value;
	
	/**
	 * @param label to display for user
	 * @param textValue from RuleRank application
	 * @param value from RuleRank application
	 */
	RuleRankParameter(String label, String textValue, int value) {
		this.label = label;
		this.textValue = textValue;
		this.value = value;
	}
	
	public String getLabel() {
		return label;
	}
	
	public void setLabel(String label) {
		this.label = label;
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
	
	/**
	 * This method and hashCode intentionally skip label field.
	 * @param o to be compared to
	 * @return true if objects are same
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		RuleRankParameter that = (RuleRankParameter) o;
		return value == that.value &&
				Objects.equals(textValue, that.textValue);
	}
	
	/**
	 * This method and equals intentionally skip label field.
	 * @return hashCode
	 */
	@Override
	public int hashCode() {
		return Objects.hash(textValue, value);
	}
}
