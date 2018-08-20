package pl.jowko.rulerank.desktop.feature.learningtable.dialogs;

/**
 * This class contains jRS value and label for ComboBox fields. <br>
 *  <br>
 * Created by Piotr on 2018-05-13.
 * @see AttributeParamService
 */
class AttributeParam {
	
	private String label;
	private int value;
	
	AttributeParam(String label, int value) {
		this.label = label;
		this.value = value;
	}
	
	public String getLabel() {
		return label;
	}
	
	public int getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return label;
	}
	
}
