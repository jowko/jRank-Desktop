package pl.jowko.jrank.desktop.feature.learningtable;

import pl.jowko.jrank.desktop.feature.learningtable.wrappers.EnumFieldWrapper;
import pl.poznan.put.cs.idss.jrs.types.EnumDomain;

import java.util.ArrayList;
import java.util.List;

import static pl.jowko.jrank.desktop.feature.learningtable.wrappers.EnumFieldWrapper.UNKNOWN_FIELD_FLAG;

/**
 * Created by Piotr on 2018-05-14.
 * This class extract values from enum field domains and returns list of string with domain element names.
 */
public class EnumListProvider {
	
	private EnumFieldWrapper field;
	
	/**
	 * Creates instance of this class.
	 * @param field from with domain element names will be extracted
	 */
	public EnumListProvider(EnumFieldWrapper field) {
		this.field = field;
	}
	
	/**
	 * Gets list of String containing domain element names.
	 * Flag indicating unknown field will be removed from this list.
	 * @see EnumFieldWrapper
	 * @return list of domain element names
	 */
	public List<String> getValues() {
		List<String> values = new ArrayList<>();
		EnumDomain domain = field.getDomain();
		
		for(int i=0; i<domain.size(); i++) {
			values.add(domain.getName(i));
		}
		values.remove(UNKNOWN_FIELD_FLAG);
		
		return values;
	}
	
}
