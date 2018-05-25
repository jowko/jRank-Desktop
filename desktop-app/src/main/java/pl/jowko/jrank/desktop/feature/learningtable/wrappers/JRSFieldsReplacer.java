package pl.jowko.jrank.desktop.feature.learningtable.wrappers;

import pl.jowko.jrank.desktop.feature.learningtable.LearningTable;
import pl.poznan.put.cs.idss.jrs.types.*;

import static pl.jowko.jrank.desktop.feature.learningtable.wrappers.EnumFieldWrapper.UNKNOWN_FIELD_FLAG;
import static pl.jowko.jrank.desktop.utils.BooleanUtils.not;

/**
 * Created by Piotr on 2018-05-13.
 * This class replaces jRS fields using wrapper fields.
 * It also allows to revert this action.
 * Wrappers are needed to fix some issues related with unknown fields.
 */
public class JRSFieldsReplacer {
	
	private JRSFieldsReplacer() {}
	
	/**
	 * This method replaces jRS fields with wrapped ones.
	 * All fields from attributes and examples are replaced.
	 * @param table containing examples and attributes to replace with raw jRS fields
	 */
	public static void replaceJRSEnumsWithTableEnumFields(LearningTable table) {
		replaceJRSFieldsInAttributes(table);
		replaceJRSFieldsInExamples(table);
	}
	
	/**
	 * This method replaces wrapper field with jRS fields.
	 * All fieldd in examples and attributes will be replaced.
	 * @param table containing examples and attributes to replace with wrapped fields
	 */
	public static void replaceWrappersWithJRSEnums(LearningTable table) {
		replaceWrappersInAttributes(table);
		replaceWrappersInExamples(table);
	}
	
	/**
	 * Replace jRS fields in attributes with wrappers.
	 * It will replace all instances of jRS fields to wrapped ones.
	 * @param table containing examples and attributes to replace with raw jRS fields
	 */
	static private void replaceJRSFieldsInAttributes(LearningTable table) {
		for(Attribute attribute : table.getAttributes()) {
			Field initialValue = attribute.getInitialValue();
			
			if(initialValue instanceof EnumField) {
				EnumField field = (EnumField) initialValue;
				attribute.setInitialValue(new EnumFieldWrapper(field));
			}
			
			if(initialValue instanceof IntegerField) {
				IntegerFieldWrapper field = new IntegerFieldWrapper();
				field.set(((IntegerField) initialValue).get());
				attribute.setInitialValue(field);
			}
			
			if(initialValue instanceof FloatField) {
				FloatFieldWrapper field = new FloatFieldWrapper();
				field.set(((FloatField) initialValue).get());
				attribute.setInitialValue(field);
			}
			
			if(initialValue instanceof StringField) {
				StringFieldWrapper field = new StringFieldWrapper();
				field.set(((StringField) initialValue).get());
				attribute.setInitialValue(field);
			}
		}
	}
	
	/**
	 * Replace all fields in examples with wrapped ones.
	 * It will replace all instances of jRS fields to wrapped ones.
	 * @param table containing examples and attributes to replace with raw jRS fields
	 */
	private static void replaceJRSFieldsInExamples(LearningTable table) {
		for(Example example : table.getExamples()) {
			Field[] fields = example.getFields();
			
			for(int i=0; i<fields.length; i++) {
				if(fields[i] instanceof EnumField) {
					EnumField field = (EnumField) fields[i];
					EnumFieldWrapper wrapper;
					
					if(field.isUnknown() == 0) {
						wrapper = new EnumFieldWrapper(0, field.getDomain());
						wrapper.setUnknown();
					} else {
						wrapper = new EnumFieldWrapper(field.getIndex(), field.getDomain());
					}
					
					fields[i] = wrapper;
				}
				
				if(fields[i] instanceof IntegerField) {
					IntegerFieldWrapper wrapper = new IntegerFieldWrapper();
					if(fields[i].isUnknown() == 0)
						wrapper.setUnknown();
					else
						wrapper.set(((IntegerField)fields[i]).get());
					
					fields[i] = wrapper;
				}
				
				if(fields[i] instanceof FloatField) {
					FloatFieldWrapper wrapper = new FloatFieldWrapper();
					if(fields[i].isUnknown() == 0)
						wrapper.setUnknown();
					else
						wrapper.set(((FloatField)fields[i]).get());
					
					fields[i] = wrapper;
				}
				
				if(fields[i] instanceof StringField) {
					StringFieldWrapper wrapper = new StringFieldWrapper();
					if(fields[i].isUnknown() == 0)
						wrapper.setUnknown();
					else
						wrapper.set(((StringField)fields[i]).get());
					
					fields[i] = wrapper;
				}
			}
		}
	}
	
	/**
	 * Replace all wrapper fields in attributes with jRS fields.
	 * Unknown fields are not used in attribute initialValue.
	 * @param table containing examples and attributes to replace with wrapped fields
	 */
	private static void replaceWrappersInAttributes(LearningTable table) {
		for(Attribute attribute : table.getAttributes()) {
			if(attribute.getInitialValue() instanceof EnumFieldWrapper) {
				EnumFieldWrapper wrapper = (EnumFieldWrapper) attribute.getInitialValue();
				attribute.setInitialValue(getEnumField(wrapper));
			}
			
			if(attribute.getInitialValue() instanceof IntegerFieldWrapper) {
				IntegerFieldWrapper wrapper = (IntegerFieldWrapper) attribute.getInitialValue();
				attribute.setInitialValue(new IntegerField(wrapper.get()));
			}
			
			if(attribute.getInitialValue() instanceof CardinalFieldWrapper) {
				CardinalFieldWrapper wrapper = (CardinalFieldWrapper) attribute.getInitialValue();
				attribute.setInitialValue(new CardinalField(wrapper.get()));
			}
			
			if(attribute.getInitialValue() instanceof FloatFieldWrapper) {
				FloatFieldWrapper wrapper = (FloatFieldWrapper) attribute.getInitialValue();
				attribute.setInitialValue(new FloatField(wrapper.get()));
			}
			
			if(attribute.getInitialValue() instanceof StringFieldWrapper) {
				StringFieldWrapper wrapper = (StringFieldWrapper) attribute.getInitialValue();
				attribute.setInitialValue( new StringField(wrapper.get()));
			}
		}
	}
	
	/**
	 * Replace wrapped fields with jRS raw fields.
	 * In most cases, this is not needed for any field except enums.
	 * Enums need to be processed, because unknown field value is marked with special flag.
	 * @see EnumFieldWrapper
	 *
	 * Rest of the fields are replaced while extracting fields from UI table to LearningTable object.
	 * @see pl.jowko.jrank.desktop.feature.learningtable.LearningTableAssembler
	 * @param table containing examples and attributes to replace with wrapped fields
	 */
	private static void replaceWrappersInExamples(LearningTable table) {
		for(Example example : table.getExamples()) {
			Field[] fields = example.getFields();
			
			for(int i=0; i<fields.length; i++) {
				if(fields[i] instanceof EnumField) {
					EnumField wrapper = (EnumField)fields[i];
					fields[i] = getEnumField(wrapper);
				}
				
				if(fields[i] instanceof EnumFieldWrapper) {
					EnumFieldWrapper wrapper = ((EnumFieldWrapper) fields[i]);
					fields[i] = getEnumField(wrapper);
				}

				if(fields[i] instanceof IntegerFieldWrapper) {
					IntegerField field = new IntegerField();
					field.copy(fields[i]);
					fields[i] = field;
				}

				if(fields[i] instanceof CardinalFieldWrapper) {
					CardinalField field = new CardinalField();
					field.copy(fields[i]);
					fields[i] = field;
				}

				if(fields[i] instanceof FloatFieldWrapper) {
					FloatField field = new FloatField();
					field.copy(fields[i]);
					fields[i] = field;
				}

				if(fields[i] instanceof StringFieldWrapper) {
					StringFieldWrapper wrapper = ((StringFieldWrapper) fields[i]);
					StringField field = new StringField(wrapper.get());
					if(wrapper.isUnknown() == 0 || wrapper.get().isEmpty()) {
						field.setUnknown();
					}

					fields[i] = field;
				}
				
			}
		}
	}
	
	/**
	 * This method checks, if enum field has set special flag.
	 * After check it returns enum field with is set as unknown or with data extracted from wrapper.
	 * UNKNOWN_FIELD_FLAG indicates, that enum field is unknown.
	 * This flag is needed to differentiate unknown item in ComboBox.
	 * @see EnumFieldWrapper
	 */
	private static EnumField getEnumField(EnumField wrapper) {
		EnumField field;
		
		EnumDomain domain = new EnumDomain();
		for(int index=0; index<wrapper.getDomain().size(); index++) {
			String domainElement = wrapper.getDomain().getName(index);
			if(not(UNKNOWN_FIELD_FLAG.equals(domainElement)))
				domain.addElement(domainElement);
		}
		
		if(wrapper.isUnknown() == 0 || UNKNOWN_FIELD_FLAG.equals(wrapper.getName())) {
			field = new EnumField(0, domain);
			field.setUnknown();
		} else {
			field = new EnumField(wrapper.getName(), domain);
		}
		
		return field;
	}
	
}
