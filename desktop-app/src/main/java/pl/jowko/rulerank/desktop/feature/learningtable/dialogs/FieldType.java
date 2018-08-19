package pl.jowko.rulerank.desktop.feature.learningtable.dialogs;

/**
 * This enum indicates what type is used for jRS field. <br>
 * It is used in customize and add attribute form. <br>
 *  <br>
 * Created by Piotr on 2018-05-13.
 */
public enum FieldType {
	
	INTEGER_FIELD("Integer"),
	CARDINAL_FIELD("Cardinal"),
	DECIMAL_FIELD("Decimal"),
	STRING_FIELD("String"),
	ENUM_FIELD("Enum"),
	PAIR_FIELD("Pair");
	
	private String name;
	
	FieldType(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
}
