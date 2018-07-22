package pl.jowko.rulerank.desktop.feature.properties.information;

import javafx.beans.property.StringProperty;
import pl.jowko.rulerank.desktop.feature.internationalization.Labels;
import pl.jowko.rulerank.desktop.feature.internationalization.LanguageService;
import pl.jowko.rulerank.feature.customfx.AbstractDialogForm;
import pl.poznan.put.cs.idss.jrs.core.mem.MemoryContainer;
import pl.poznan.put.cs.idss.jrs.types.Attribute;
import pl.poznan.put.cs.idss.jrs.types.CardinalField;
import pl.poznan.put.cs.idss.jrs.types.Example;
import pl.poznan.put.cs.idss.jrs.types.Field;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Piotr on 2018-05-29
 * This class contains base functionality used both in ranking and pairs controllers.
 * Both controllers are used in modal window on with learning information is configured.
 * @see PropertiesPairsController
 * @see PropertiesRankingController
 */
public abstract class AbstractInformationController extends AbstractDialogForm {
	
	protected MemoryContainer container;
	protected StringProperty result;
	
	protected List<FieldItem> fieldItems;
	protected List<AttributeItem> comboItems;
	protected LanguageService labels;
	private int rowId = 1;
	
	/**
	 * Initialize common part of information form.
	 * It will extract data from MemoryContainer and call custom initialize method from upper class.
	 * @param container containing learning data file from edited experiment
	 * @param result containing StringProperty to with result will be saved. This property should have previous value of field from properties form.
	 */
	public void initializeForm(MemoryContainer container, StringProperty result) {
		this.container = container;
		this.result = result;
		labels = LanguageService.getInstance();
		initializeItems();
		initialize();
	}
	
	/**
	 * Abstract initialize method.
	 * All custom initialization should take place in this method
	 */
	abstract void initialize();
	
	/**
	 * Initializes items for ComboBoxes and ListView or TreeView.
	 * It will extract all description attribute names for ComboBox and add ID value.
	 * Items for Views are extracted from examples.
	 */
	private void initializeItems() {
		fieldItems = new ArrayList<>();
		comboItems = new ArrayList<>();
		comboItems.add(new AttributeItem(labels.get(Labels.PROP_INFO_ID), 0));
		
		List<Integer> indexes = new ArrayList<>();
		for(int i=0; i<container.getAttributes().length; i++) {
			Attribute attribute = container.getAttributes()[i];
			if(attribute.getKind() == Attribute.DESCRIPTION) {
				indexes.add(i);
				comboItems.add(new AttributeItem(attribute.getName(), i+1));
			}
			
		}
		
		for(Example example : container.getExamples()) {
			fieldItems.add(createItem(example.getFields(), indexes));
		}
	}
	
	/**
	 * Create item from example fields.
	 * It also creates ID field.
	 * @param fields from example
	 * @param indexes of description fields
	 * @return item containing ID and description fields
	 */
	private FieldItem createItem(Field[] fields, List<Integer> indexes) {
		List<Field> list = new ArrayList<>();
		list.add(new CardinalField(rowId++));
		
		for(int index : indexes) {
			list.add(fields[index]);
		}
		
		return new FieldItem(list);
	}
	
}
