package pl.jowko.jrank.desktop.feature.learningtable;

import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import pl.jowko.jrank.desktop.ResourceLoader;
import pl.jowko.jrank.desktop.feature.learningtable.dialogs.AttributeDialogController;
import pl.jowko.jrank.desktop.utils.Cloner;
import pl.jowko.jrank.logger.JRankLogger;
import pl.poznan.put.cs.idss.jrs.types.Attribute;
import pl.poznan.put.cs.idss.jrs.types.Field;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static javafx.collections.FXCollections.observableArrayList;
import static pl.jowko.jrank.desktop.utils.BooleanUtils.not;

/**
 * Created by Piotr on 2018-05-19.
 * This class contains different actions for Learning data table.
 */
public class LearningTableActions {
	
	private TableView<ObservableList<Field>> learningTable;
	private LearningTableHelper tableHelper;
	private List<Attribute> attributes;
	private ComboBox<String> selectAttribute;
	
	LearningTableActions(TableView<ObservableList<Field>> learningTable, ComboBox<String> selectAttribute) {
		this.learningTable = learningTable;
		this.tableHelper = new LearningTableHelper();
		this.selectAttribute = selectAttribute;
		attributes = new ArrayList<>();
	}
	
	/**
	 * Save edited attributes by replacing them in table.
	 * @param attributesToSave from attributes form
	 */
	public void saveEditedAttributes(List<Attribute> attributesToSave) {
		List<Attribute> oldAttributes = getAttributesFromTable();
		for(int i=0; i<oldAttributes.size(); i++) {
			Attribute attribute = attributesToSave.get(i);
			Attribute currentAttribute = oldAttributes.get(i);
			if(not(attribute.equals(currentAttribute))) {
				editAttribute(currentAttribute, attribute);
			}
		}
	}
	
	/**
	 * Creates new column in learning table and adds new field to all examples.
	 * @param attribute to save
	 */
	public void saveNewAttribute(Attribute attribute) {
		createNewColumn(attribute);
		ObservableList<ObservableList<Field>> list = learningTable.getItems();
		list.forEach(row -> row.add(RuleRankFieldHelper.createNewFieldOfProvidedType(attribute.getInitialValue())));
	}
	
	/**
	 * Creates new column from provided attribute.
	 * @param attribute from with new column will be created
	 */
	void createNewColumn(Attribute attribute) {
		AttributeTableColumn column = new AttributeTableColumn(tableHelper.getColumnText(attribute), attribute);
		int attributeIndex = learningTable.getColumns().size();
		tableHelper.setCellFactories(column, attributeIndex);
		column.setOnEditCommit(col -> col.getOldValue().copy(col.getNewValue()));
		column.setMinWidth(50d);
		if(not(attribute.getActive())) {
			column.getStyleClass().add("not-active");
		}
		
		learningTable.getColumns().add(column);
		attributes.add(attribute);
		setItemsToAttributeComboBox();
	}
	
	/**
	 * Adds new example to table.
	 * This method creates row of fields with default values.
	 */
	void addExampleAction() {
		if(learningTable.getColumns().size() == 0) {
			JRankLogger.info("No attributes in table. Add attributes first.");
		}
		ObservableList<Field> newFields = tableHelper.getEmptyExample(attributes);
		learningTable.getItems().add(newFields);
	}
	
	/**
	 * Removes all selected examples(rows) from learning table.
	 * If no rows were selected, logs information about it and returns.
	 */
	void removeSelectedExamplesAction() {
		ObservableList<ObservableList<Field>> selectedRows = learningTable.getSelectionModel().getSelectedItems();
		if(selectedRows.isEmpty()) {
			JRankLogger.info("No examples were selected. Select examples first.");
			return;
		}
		// we don't want to iterate on same collection on with we remove items
		ArrayList<ObservableList<Field>> rows = new ArrayList<>(selectedRows);
		rows.forEach(row -> learningTable.getItems().remove(row));
	}
	
	/**
	 * Shows customize attributes dialog(AttributeDialogController).
	 * This method loads fxml resource and pass it to the controller.
	 */
	void customizeAttributes() {
		try {
			if(attributes.size() == 0) {
				JRankLogger.warn("No attributes to customize. Add attributes first.");
				return;
			}
			
			ResourceLoader resourceLoader = new ResourceLoader("/fxml/upperTabs/attributeActionDialog.fxml");
			Parent parent = resourceLoader.load();
			AttributeDialogController controller = resourceLoader.getController();
			List<Attribute> attributesToEdit = (List<Attribute>) Cloner.deepClone(getAttributesFromTable());
			controller.initializeEditAction(this, parent, attributesToEdit);
		} catch (IOException e) {
			JRankLogger.error("Error while trying to open customize attributes dialog: " + e.getCause());
		}
	}
	
	/**
	 * Show add new attribute dialog.
	 * This method loads fxml resource and pass it to the controller.
	 */
	void addNewAttribute() {
		try {
			ResourceLoader resourceLoader = new ResourceLoader("/fxml/upperTabs/attributeActionDialog.fxml");
			Parent parent = resourceLoader.load();
			AttributeDialogController controller = resourceLoader.getController();
			controller.initializeAddAction(this, parent);
		} catch (IOException e) {
			JRankLogger.error("Error while trying to add new attribute: " + e.getCause());
		}
	}
	
	/**
	 * This method fills selectAttribute ComboBox with current attribute names.
	 */
	void setItemsToAttributeComboBox() {
		selectAttribute.setItems(observableArrayList(
				learningTable.getColumns().stream()
						.map(column -> ((AttributeTableColumn) column).getAttribute().getName())
						.collect(Collectors.toList()))
		);
	}
	
	void removeAttributeAction() {
		AttributeTableColumn tableColumn = (AttributeTableColumn) getColumnByAttributeName(selectAttribute.getValue());
		if(isNull(tableColumn)) {
			JRankLogger.warn("No attribute was selected. Remove action aborted.");
			return;
		}
		
		int attributeIndex = learningTable.getColumns().indexOf(tableColumn);
		learningTable.getColumns().remove(tableColumn);
		attributes.remove(tableColumn.getAttribute());
		
		ObservableList<ObservableList<Field>> list = learningTable.getItems();
		list.forEach(row -> row.remove(attributeIndex));
		tableHelper.recreateCellValuesFactories(learningTable.getColumns());
		setItemsToAttributeComboBox();
	}
	
	public List<Attribute> getAttributes() {
		return attributes;
	}
	
	/**
	 * Replace old attribute with new one.
	 * This method replaces attribute both in model and in UI table.
	 * @param oldAttribute to replace
	 * @param editedAttribute as replacement
	 */
	private void editAttribute(Attribute oldAttribute, Attribute editedAttribute) {
		AttributeTableColumn tableColumn = (AttributeTableColumn) getColumnByAttributeName(oldAttribute.getName());
		tableColumn.setText(tableHelper.getColumnText(editedAttribute));
		tableColumn.setAttribute(editedAttribute);
		int attributeIndex = attributes.indexOf(oldAttribute);
		handleStyleChange(tableColumn);
		
		
		if(editedAttribute.getInitialValue() instanceof TableEnumField) {
			tableHelper.handleEnumEdition(tableColumn.getAttribute(), attributeIndex, learningTable);
			tableHelper.setCellFactories(tableColumn, attributeIndex);
		}
		
		replaceAttributeInLearningTable(oldAttribute, editedAttribute);
		setItemsToAttributeComboBox();
	}
	
	/**
	 * This method marks inactive attribute with style from common.css.
	 * @param tableColumn with was edited
	 */
	private void handleStyleChange(AttributeTableColumn tableColumn) {
		if(not(tableColumn.getAttribute().getActive())) {
			tableColumn.getStyleClass().add("not-active");
		} else {
			tableColumn.getStyleClass().remove("not-active");
		}
	}
	
	/**
	 * Finds column from UI table by attribute name.
	 * @param attributeName to find
	 * @return column with found attribute name or null
	 */
	private TableColumn getColumnByAttributeName(String attributeName) {
		return learningTable.getColumns().stream()
				.filter(column ->
						((AttributeTableColumn) column).getAttribute().getName().equalsIgnoreCase(attributeName)
				)
				.findAny()
				.orElse(null);
	}
	
	private void replaceAttributeInLearningTable(Attribute oldAttribute, Attribute newAttribute) {
		int index = attributes.indexOf(oldAttribute);
		attributes.set(index, newAttribute);
	}
	
	private List<Attribute> getAttributesFromTable() {
		return learningTable.getColumns().stream()
				.map(column -> ((AttributeTableColumn) column).getAttribute())
				.collect(Collectors.toList());
	}
	
}
