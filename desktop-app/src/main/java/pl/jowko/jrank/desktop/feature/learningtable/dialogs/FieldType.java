package pl.jowko.jrank.desktop.feature.learningtable.dialogs;

/**
 * Created by Piotr on 2018-05-13.
 */
public enum FieldType {
	
	INTEGER_FIELD("Integer"),
	CARDINAL_FIELD("Cardinal"),
	DECIMAL_FIELD("Decimal"),
	STRING_FIELD("String"),
	ENUM_FIELD("Enum");
	
	private String name;
	
	FieldType(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
}
