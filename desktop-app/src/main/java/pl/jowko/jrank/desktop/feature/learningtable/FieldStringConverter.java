package pl.jowko.jrank.desktop.feature.learningtable;

import javafx.util.StringConverter;
import pl.poznan.put.cs.idss.jrs.types.Field;
import pl.poznan.put.cs.idss.jrs.types.StringField;

/**
 * Created by Piotr on 2018-05-09.
 */
class FieldStringConverter extends StringConverter<Field> {
	
	@Override
	public String toString(Field object) {
		return object.toString();
	}
	
	@Override
	public Field fromString(String string) {
		return new StringField(string);
	}
	
}
