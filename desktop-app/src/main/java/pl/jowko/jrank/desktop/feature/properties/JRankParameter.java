package pl.jowko.jrank.desktop.feature.properties;

/**
 * Created by Piotr on 2018-05-01.
 */
public class JRankParameter {
	
	private String label;
	/**
	 * String value of parameter used in properties files. Exists due to backward compatibility.
	 */
	private String textValue;
	/**
	 * Integer value of parameter for jRank application. Exists due to backward compatibility.
	 */
	private int value;
	
	JRankParameter(String label, String textValue, int value) {
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
	
}
