package pl.jowko.jrank.desktop.feature.learningtable;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import pl.poznan.put.cs.idss.jrs.types.Attribute;
import pl.poznan.put.cs.idss.jrs.types.Field;

/**
 * Created by Piotr on 2018-05-13.
 */
class AttributeTableColumn extends TableColumn<ObservableList<Field>, Field> {
	
	private Attribute attribute;
	
	private int attributeIndex;
	
	AttributeTableColumn(Attribute attribute, int attributeIndex) {
		this.attribute = attribute;
		this.attributeIndex = attributeIndex;
	}
	
	Attribute getAttribute() {
		return attribute;
	}
	
	void setAttribute(Attribute attribute) {
		this.attribute = attribute;
	}
	
	int getAttributeIndex() {
		return attributeIndex;
	}
	
	void setAttributeIndex(int attributeIndex) {
		this.attributeIndex = attributeIndex;
	}
	
}
