package pl.jowko.jrank.desktop.feature.learningtable;

import pl.poznan.put.cs.idss.jrs.types.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Piotr on 2018-05-16.
 */
public class LearningTableDataProvider {
	
	static final String STRING_COLUMN_NAME = "SomeStringColumn";
	
	static LearningTable createLearningTable() {
		LearningTable table = new LearningTable(null, null, null);
		
		table.getAttributes().addAll(createAttributes());
		table.getExamples().addAll(createExamples(table.getAttributes()));
		
		return table;
	}
	
	static LearningTable createFilledLearningTable() {
		LearningTable table = new LearningTable(null, null, null);
		
		table.getAttributes().addAll(createAttributes());
		table.getExamples().addAll(createFilledExamples());
		
		return table;
	}
	
	static LearningTable createLearningTable(List<Attribute> attributes) {
		LearningTable table = new LearningTable(null, null, null);
		
		table.getAttributes().addAll(attributes);
		table.getExamples().addAll(createExamples(attributes));
		
		return table;
	}
	
	static List<Attribute> createAttributes() {
		List<Attribute> attributes = new ArrayList<>();
		attributes.add(createAttribute("integer", new IntegerField()));
		attributes.add(createAttribute(STRING_COLUMN_NAME, new StringField()));
		attributes.add(createAttribute("enum", new EnumField(0, createEnumDomain())));
		attributes.add(createAttribute("decimal", new FloatField()));
		return attributes;
	}
	
	static  Attribute createDecisionAttribute(String name, boolean isActive) {
		Attribute attribute = createAttribute(name);
		attribute.setKind(Attribute.DECISION);
		attribute.setActive(isActive);
		return attribute;
	}
	
	static Attribute createAttribute(String name) {
		return createAttribute(name, new IntegerField());
	}
	
	static Attribute createAttribute(String name, Field field) {
		return new Attribute(name, field);
	}
	
	static List<Example> createExamples(List<Attribute> attributes) {
		List<Example> examples = new ArrayList<>();
		
		examples.add(new Example(attributes.toArray(new Attribute[]{})));
		examples.add(new Example(attributes.toArray(new Attribute[]{})));
		examples.add(new Example(attributes.toArray(new Attribute[]{})));
		
		return examples;
	}
	
	static List<Example> createFilledExamples() {
		List<Example> examples = new ArrayList<>();
		
		examples.add(new Example(createFields(2, "Test", 0, 33.3d)));
		examples.add(new Example(createFields(43, "Test4", 1, 25)));
		examples.add(new Example(createFields(5323, "TestLong", 1, 4223.436d)));
		
		return examples;
	}
	
	static Field[] createFields(int integer, String text, int index, double decimal) {
		Field[] fields = new Field[4];
		fields[0] = new IntegerField(integer);
		fields[1] = new StringField(text);
		fields[2] = new EnumField(index, createEnumDomain());
		fields[3] = new FloatField(decimal);
		return fields;
	}
	
	public static EnumDomain createEnumDomain() {
		return new EnumDomain(new String[]{"1", "2", "3"});
	}
}
