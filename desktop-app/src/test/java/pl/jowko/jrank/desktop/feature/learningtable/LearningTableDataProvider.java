package pl.jowko.jrank.desktop.feature.learningtable;

import pl.jowko.jrank.desktop.feature.learningtable.wrappers.EnumFieldWrapper;
import pl.jowko.jrank.desktop.feature.learningtable.wrappers.FloatFieldWrapper;
import pl.jowko.jrank.desktop.feature.learningtable.wrappers.IntegerFieldWrapper;
import pl.jowko.jrank.desktop.feature.learningtable.wrappers.StringFieldWrapper;
import pl.poznan.put.cs.idss.jrs.types.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Piotr on 2018-05-16.
 * This class provides data for tests related with Learning data table.
 * @see LearningTable
 * @see Attribute
 * @see Example
 * @see Field
 */
public class LearningTableDataProvider {
	
	static final String STRING_COLUMN_NAME = "SomeStringColumn";
	
	/**
	 * Creates basic LearningTable object with attributes and examples.
	 * All fields in examples are empty.
	 * @return LearningTable with attributes and examples with empty fields
	 */
	public static LearningTable createLearningTable() {
		LearningTable table = new LearningTable(null, null, null);
		
		table.getAttributes().addAll(createAttributes());
		table.getExamples().addAll(createExamples(table.getAttributes()));
		
		return table;
	}
	
	/**
	 * Create learning data table with attributes and examples.
	 * Fields in examples have some data in them.
	 * @return LearningTable with attributes and filled examples
	 */
	static LearningTable createFilledLearningTable() {
		LearningTable table = new LearningTable(null, null, null);
		
		table.getAttributes().addAll(createAttributes());
		table.getExamples().addAll(createFilledExamples());
		
		return table;
	}
	
	/**
	 * Create learning data table from provided list of attributes.
	 * Fields in examples are empty.
	 * @param attributes from with fields in examples will be created
	 * @return LearningTable containing provided attributes and created empty fields
	 */
	static LearningTable createLearningTable(List<Attribute> attributes) {
		LearningTable table = new LearningTable(null, null, null);
		
		table.getAttributes().addAll(attributes);
		table.getExamples().addAll(createExamples(attributes));
		
		return table;
	}
	
	/**
	 * Creates basics attributes.
	 * @return list of attributes
	 */
	static List<Attribute> createAttributes() {
		List<Attribute> attributes = new ArrayList<>();
		attributes.add(createAttribute("integer", new IntegerFieldWrapper()));
		attributes.add(createAttribute(STRING_COLUMN_NAME, new StringFieldWrapper()));
		attributes.add(createAttribute("enum", new EnumFieldWrapper(0, createEnumDomain())));
		attributes.add(createAttribute("decimal", new FloatFieldWrapper()));
		return attributes;
	}
	
	/**
	 * Creates decision attribute.
	 * @param name of attribute
	 * @param isActive to indicate if decision atrribute is active
	 * @return decision attribute
	 */
	static Attribute createDecisionAttribute(String name, boolean isActive) {
		Attribute attribute = createAttribute(name);
		attribute.setKind(Attribute.DECISION);
		attribute.setActive(isActive);
		return attribute;
	}
	
	/**
	 * Create attribute from provided name.
	 * @param name of attribute
	 * @return attribute with provided name
	 */
	static Attribute createAttribute(String name) {
		return createAttribute(name, new IntegerFieldWrapper());
	}
	
	/**
	 * Creates attribute with provided name and with type of provided field.
	 * @param name of attribute
	 * @param field with serves as initial value for attribute
	 * @return
	 */
	static Attribute createAttribute(String name, Field field) {
		Attribute attribute = new Attribute(name, field);
		attribute.setKind(Attribute.NONE);
		attribute.setPreferenceType(Attribute.GAIN);
		return attribute;
	}
	
	/**
	 * Creates list of examples with empty fields from provided attributes.
	 * @param attributes list with are used to create fields in examples
	 * @return list of examples with specified attribute fields
	 */
	static List<Example> createExamples(List<Attribute> attributes) {
		List<Example> examples = new ArrayList<>();
		
		examples.add(new Example(attributes.toArray(new Attribute[]{})));
		examples.add(new Example(attributes.toArray(new Attribute[]{})));
		examples.add(new Example(attributes.toArray(new Attribute[]{})));
		
		return examples;
	}
	
	/**
	 * Create list of examples containing filled fields.
	 * @return list of examples with filled fields
	 */
	static List<Example> createFilledExamples() {
		List<Example> examples = new ArrayList<>();
		
		examples.add(new Example(createFields(2, "Test", 0, 33.3d)));
		examples.add(new Example(createFields(43, "Test4", 1, 25)));
		examples.add(new Example(createFields(5323, "TestLong", 1, 4223.436d)));
		
		return examples;
	}
	
	/**
	 * Create array of filled fields.
	 * @param integer for IntegerFieldWrapper
	 * @param text for StringFieldWrapper
	 * @param index for EnumFieldWrapper
	 * @param decimal for FloatFieldWrapper
	 * @return array of fields
	 */
	static Field[] createFields(int integer, String text, int index, double decimal) {
		Field[] fields = new Field[4];
		fields[0] = new IntegerFieldWrapper(integer);
		fields[1] = new StringFieldWrapper(text);
		fields[2] = new EnumFieldWrapper(index, createEnumDomain());
		fields[3] = new FloatFieldWrapper(decimal);
		return fields;
	}
	
	/**
	 * Creates EnumDomain object.
	 * This object is used to create EnumField.
	 * EnumDomain contains allowed values for EnumField.
	 * @see EnumField
	 * @see EnumDomain
	 * @return
	 */
	public static EnumDomain createEnumDomain() {
		return new EnumDomain(new String[]{"1", "2", "3"});
	}
}
