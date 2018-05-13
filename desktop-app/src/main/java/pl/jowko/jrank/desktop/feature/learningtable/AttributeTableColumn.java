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
	
	AttributeTableColumn(String header, Attribute attribute) {
		super(header);
		this.attribute = attribute;
	}
	
	Attribute getAttribute() {
		return attribute;
	}
	
}
