package pl.jowko.jrank.desktop.feature.learningtable;

import javafx.util.StringConverter;
import pl.poznan.put.cs.idss.jrs.types.EnumDomain;
import pl.poznan.put.cs.idss.jrs.types.Field;

import static java.util.Objects.isNull;

/**
 * Created by Piotr on 2018-05-12.
 */
class EnumFieldConverter extends StringConverter<Field> {
	
	private EnumDomain domain;
	
	EnumFieldConverter(EnumDomain domain) {
		this.domain = domain;
	}
	
	@Override
	public String toString(Field object) {
		if(isNull(object))
			return "";
		return object.toString();
	}
	
	@Override
	public Field fromString(String string) {
		return new TableEnumField(string, domain);
	}
	
}
