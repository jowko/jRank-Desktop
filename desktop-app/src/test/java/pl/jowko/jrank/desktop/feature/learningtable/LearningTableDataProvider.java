package pl.jowko.jrank.desktop.feature.learningtable;

import pl.poznan.put.cs.idss.jrs.types.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Piotr on 2018-05-16.
 */
class LearningTableDataProvider {
	
	static LearningTable createLearningTable() {
		LearningTable table = new LearningTable(null, null, null);
		
		table.getAttributes().addAll(createAttributes());
		table.getExamples().addAll(createExamples(table.getAttributes()));
		
		return table;
	}
	
	static List<Attribute> createAttributes() {
		List<Attribute> attributes = new ArrayList<>();
		attributes.add(new Attribute("integer", new IntegerField()));
		attributes.add(new Attribute("string", new StringField()));
		EnumDomain domain = new EnumDomain(new String[]{"1", "2", "3"});
		attributes.add(new Attribute("enum", new EnumField(0, domain)));
		attributes.add(new Attribute("decimal", new FloatField()));
		return attributes;
	}
	
	static List<Example> createExamples(List<Attribute> attributes) {
		List<Example> examples = new ArrayList<>();
		
		examples.add(new Example(attributes.toArray(new Attribute[]{})));
		examples.add(new Example(attributes.toArray(new Attribute[]{})));
		examples.add(new Example(attributes.toArray(new Attribute[]{})));
		
		return examples;
	}
	
}
