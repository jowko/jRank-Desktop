package pl.jowko.jrank.desktop.feature.learningtable;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import pl.jowko.jrank.feature.customfx.IndexedTableColumn;
import pl.poznan.put.cs.idss.jrs.types.Attribute;
import pl.poznan.put.cs.idss.jrs.types.Field;

/**
 * Created by Piotr on 2018-05-13.
 */
class AttributeTableColumn extends IndexedTableColumn<ObservableList<Field>, Field> {
	
	private Attribute attribute;
	
	AttributeTableColumn(Attribute attribute, int attributeIndex) {
		super(attribute.getName(), attributeIndex);
		this.attribute = attribute;
	}
	
	Attribute getAttribute() {
		return attribute;
	}
	
	void setAttribute(Attribute attribute) {
		this.attribute = attribute;
	}
	
}
