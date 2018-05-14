package pl.jowko.jrank.desktop.feature.learningtable;

import pl.poznan.put.cs.idss.jrs.types.EnumDomain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Piotr on 2018-05-14.
 */
public class EnumListProvider {
	
	private TableEnumField field;
	
	public EnumListProvider(TableEnumField field) {
		this.field = field;
	}
	
	public List<String> getValues() {
		List<String> values = new ArrayList<>();
		EnumDomain domain = field.getDomain();
		
		for(int i=0; i<domain.size(); i++) {
			values.add(domain.getName(i));
		}
		
		return values;
	}
	
}
