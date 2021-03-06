package pl.jowko.rulerank.desktop.feature.properties.information;

/**
 * This class contains name of attribute and index of it in container. <br>
 * It is used in ComboBoxes to change label display. <br>
 *  <br>
 * Created by Piotr on 2018-05-29
 * @see FieldItem
 */
class AttributeItem {
	
	private String name;
	private int index;
	
	/**
	 * Creates instance of this class.
	 * @param name from attribute
	 * @param index of attribute in container
	 */
	AttributeItem(String name, int index) {
		this.name = name;
		this.index = index;
	}
	
	int getIndex() {
		return index;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
}
