package pl.jowko.rulerank.desktop.feature.learningtable;

import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import pl.jowko.rulerank.desktop.ResourceLoader;
import pl.jowko.rulerank.desktop.feature.clipboard.ClipBoardManager;
import pl.jowko.rulerank.desktop.feature.clipboard.CsvTable;
import pl.jowko.rulerank.desktop.feature.clipboard.CsvTableCreator;
import pl.jowko.rulerank.desktop.feature.internationalization.Labels;
import pl.jowko.rulerank.desktop.feature.internationalization.LanguageService;
import pl.jowko.rulerank.desktop.feature.learningtable.dialogs.AttributeDialogController;
import pl.jowko.rulerank.desktop.feature.learningtable.dialogs.AttributeItem;
import pl.jowko.rulerank.desktop.feature.learningtable.wrappers.CardinalFieldWrapper;
import pl.jowko.rulerank.desktop.feature.learningtable.wrappers.EnumFieldWrapper;
import pl.jowko.rulerank.desktop.feature.tabs.RuleRankTab;
import pl.jowko.rulerank.desktop.service.DialogsService;
import pl.jowko.rulerank.desktop.utils.Cloner;
import pl.jowko.rulerank.logger.RuleRankLogger;
import pl.poznan.put.cs.idss.jrs.types.Attribute;
import pl.poznan.put.cs.idss.jrs.types.Field;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static javafx.collections.FXCollections.observableArrayList;
import static pl.jowko.rulerank.desktop.utils.BooleanUtils.not;

/**
 * This class contains different actions for Learning data table. <br>
 *  <br>
 * Created by Piotr on 2018-05-19.
 */
public class LearningTableActions {
	
	/**
	 * This constant servers as flag indicating that attribute with such name is ID column with is not editable.
	 */
	public static final String ATTRIBUTE_ID_SECRET_NAME = "SomeVeryLongAttributeNameToServerAsFlagIndicatingThatThisIsIdColumn";
	
	private TableView<ObservableList<Field>> learningTable;
	private LearningTableHelper tableHelper;
	private List<Attribute> attributes;
	private ComboBox<AttributeItem> selectAttribute;
	private RuleRankTab learningTableTab;
	private LanguageService labels;
	
	private int idColumnValue;
	
	LearningTableActions(TableView<ObservableList<Field>> learningTable, ComboBox<AttributeItem> selectAttribute, RuleRankTab learningTableTab) {
		this.learningTable = learningTable;
		this.tableHelper = new LearningTableHelper();
		this.selectAttribute = selectAttribute;
		this.learningTableTab = learningTableTab;
		labels = LanguageService.getInstance();
		attributes = new ArrayList<>();
		idColumnValue = 1;
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
		learningTableTab.setTabEdited(true);
	}
	
	/**
	 * Creates new column in learning table and adds new field to all examples. <br>
	 * All added fields are of type unknown.
	 * @param attribute to save
	 */
	public void saveNewAttribute(Attribute attribute) {
		createNewColumn(attribute);
		ObservableList<ObservableList<Field>> list = learningTable.getItems();
		list.forEach(row -> {
			Field field = RuleRankFieldHelper.createNewFieldOfProvidedType(attribute.getInitialValue());
			field.setUnknown();
			row.add(field);
		});
	}
	
	/**
	 * Creates new column from provided attribute. <br>
	 * It also fixes bug with removing row with is currently in edition by adding null check onEditCommit listener
	 * @param attribute from with new column will be created
	 */
	void createNewColumn(Attribute attribute) {
		int attributeIndex = learningTable.getColumns().size();
		AttributeTableColumn column = new AttributeTableColumn(attribute, attributeIndex);
		column.setGraphic(tableHelper.getColumnLabel(attribute));
		column.setMinWidth(50);
		column.setPrefWidth(tableHelper.getColumnPrefWidth(attribute));
		setCssStyleForColumn(column);
		
		tableHelper.setCellFactories(column, attributeIndex);
		column.setOnEditCommit(col -> {
			if(isNull(col.getOldValue()))
				return;
			col.getOldValue().copy(col.getNewValue());
		});
		
		learningTable.getColumns().add(column);
		attributes.add(attribute);
		setItemsToAttributeComboBox();
	}
	
	/**
	 * Creates ID column. <br>
	 * It will add special column with row number with is not editable.
	 */
	void createIdColumn() {
		Attribute idColumn = new Attribute(ATTRIBUTE_ID_SECRET_NAME, new CardinalFieldWrapper());
		AttributeTableColumn column = new AttributeTableColumn(idColumn, 0);
		column.setMinWidth(50);
		column.setEditable(false);
		column.setText(labels.get(Labels.LEARN_TABLE_ID));
		
		tableHelper.setCellFactories(column, 0);
		
		learningTable.getColumns().add(column);
		attributes.add(idColumn);
	}
	
	/**
	 * Adds new example to table. <br>
	 * This method creates row of fields with default values.
	 */
	void addExampleAction() {
		if(learningTable.getColumns().size() == 1) {
			DialogsService.showActionFailedDialog(labels.get(Labels.LEARN_TABLE_ADD_EXAMPLE_FAIL));
			return;
		}
		ObservableList<Field> newFields = tableHelper.getEmptyExample(attributes, idColumnValue++);
		learningTable.getItems().add(newFields);
	}
	
	/**
	 * Removes all selected examples(rows) from learning table. <br>
	 * If no rows were selected, logs information about it and returns.
	 */
	void removeSelectedExamplesAction() {
		ObservableList<ObservableList<Field>> selectedRows = learningTable.getSelectionModel().getSelectedItems();
		if(selectedRows.isEmpty()) {
			DialogsService.showActionFailedDialog(labels.get(Labels.LEARN_TABLE_REMOVE_EXAMPLES_FAIL));
			return;
		}
		// we don't want to iterate on same collection on with we remove items
		ArrayList<ObservableList<Field>> rows = new ArrayList<>(selectedRows);
		rows.forEach(row -> learningTable.getItems().remove(row));
	}
	
	/**
	 * Shows customize attributes dialog(AttributeDialogController). <br>
	 * This method loads fxml resource and pass it to the controller.
	 */
	void customizeAttributes() {
		try {
			if(attributes.size() == 1) {
				DialogsService.showActionFailedDialog(Labels.LEARN_TABLE_CUSTOMIZE_ATTRIBUTES_FAIL);
				return;
			}
			
			ResourceLoader resourceLoader = new ResourceLoader("/fxml/upperTabs/attributeActionDialog.fxml");
			Parent parent = resourceLoader.load();
			AttributeDialogController controller = resourceLoader.getController();
			List<Attribute> attributesToEdit = (List<Attribute>) Cloner.deepClone(getAttributesFromTable());
			controller.initializeEditAction(this, parent, attributesToEdit);
		} catch (IOException e) {
			RuleRankLogger.error("Error while trying to open customize attributes dialog: " + e.getCause());
		}
	}
	
	/**
	 * Show add new attribute dialog. <br>
	 * This method loads fxml resource and pass it to the controller.
	 */
	void addNewAttribute() {
		try {
			ResourceLoader resourceLoader = new ResourceLoader("/fxml/upperTabs/attributeActionDialog.fxml");
			Parent parent = resourceLoader.load();
			AttributeDialogController controller = resourceLoader.getController();
			controller.initializeAddAction(this, parent);
		} catch (IOException e) {
			RuleRankLogger.error("Error while trying to add new attribute: " + e.getCause());
		}
	}
	
	/**
	 * Copies all selected rows(items) to user clipboard. <br>
	 * It will put columns headers on first row and items on next rows. <br>
	 * It needs to know original indexes of columns with is calculated here. <br>
	 * JavaFX in case of reorder, only reorders columns in table. <br>
	 * Item positions are not changing.
	 * @see CsvTableCreator
	 */
	void copySelectedRowsAction() {
		ObservableList<ObservableList<Field>> items = learningTable.getSelectionModel().getSelectedItems();
		if(items.size() == 0) {
			RuleRankLogger.warn("No rows selected. No rows were copied.");
			return;
		}
		
		List<String> columns = CsvTableCreator.extractColumnNames(learningTable);
		List<Integer> indexes = CsvTableCreator.extractIndexes(learningTable);
		
		if(columns.size() <= 1) {
			RuleRankLogger.warn("Could not copy rows because no attributes are available.");
		}
		// replace ID secret flag with ID label
		int idIndex = columns.indexOf(ATTRIBUTE_ID_SECRET_NAME);
		columns.set(idIndex, labels.get(Labels.LEARN_TABLE_ID));
		
		CsvTable table = CsvTableCreator.createTable(columns, items, indexes);
		ClipBoardManager.putCsvTable(table);
	}
	
	/**
	 * This method fills selectAttribute ComboBox with current attributes.
	 * @see AttributeItem
	 */
	void setItemsToAttributeComboBox() {
		selectAttribute.setItems(observableArrayList(
				learningTable.getColumns().stream()
						.map(column -> new AttributeItem(((AttributeTableColumn) column).getAttribute()))
						.filter(col -> not(ATTRIBUTE_ID_SECRET_NAME.equals(col.getAttribute().getName())))
						.collect(Collectors.toList()))
		);
	}
	
	void removeAttributeAction(AttributeItem item) {
		AttributeTableColumn tableColumn = (AttributeTableColumn) getColumnByAttribute(item.getAttribute());
		
		int attributeIndex = tableColumn.getIndex();
		learningTable.getColumns().remove(tableColumn);
		attributes.remove(tableColumn.getAttribute());
		
		ObservableList<ObservableList<Field>> list = learningTable.getItems();
		list.forEach(row -> row.remove(attributeIndex));
		tableHelper.recreateCellValuesFactories(learningTable.getColumns(), attributeIndex);
		setItemsToAttributeComboBox();
	}
	
	public List<Attribute> getAttributes() {
		return attributes;
	}
	
	int getIdColumnValue() {
		return idColumnValue++;
	}
	
	/**
	 * Replace old attribute with new one. <br>
	 * This method replaces attribute both in model and in UI table.
	 * @param oldAttribute to replace
	 * @param editedAttribute as replacement
	 */
	private void editAttribute(Attribute oldAttribute, Attribute editedAttribute) {
		AttributeTableColumn tableColumn = (AttributeTableColumn) getColumnByAttribute(oldAttribute);
		tableColumn.setGraphic(tableHelper.getColumnLabel(editedAttribute));
		tableColumn.setAttribute(editedAttribute);
		tableColumn.setMinWidth(50);
		tableColumn.setPrefWidth(tableHelper.getColumnPrefWidth(editedAttribute));
		int attributeIndex = attributes.indexOf(oldAttribute);
		setCssStyleForColumn(tableColumn);
		
		if(editedAttribute.getInitialValue() instanceof EnumFieldWrapper) {
			tableHelper.handleEnumEdition(tableColumn, attributeIndex, learningTable);
		}
		
		replaceAttributeInLearningTable(oldAttribute, editedAttribute);
		setItemsToAttributeComboBox();
	}
	
	/**
	 * Sets css styles for column headers. <br>
	 * If field is not active, all css styles from column are removed to avoid overwriting not active style properties
	 * @param tableColumn for with css style will be set
	 */
	private void setCssStyleForColumn(AttributeTableColumn tableColumn) {
		Attribute attribute = tableColumn.getAttribute();
		if(attribute.getKind() == Attribute.DECISION) {
			tableColumn.getStyleClass().remove("description");
			tableColumn.getStyleClass().add("decision");
		} else if(attribute.getKind() == Attribute.DESCRIPTION) {
			tableColumn.getStyleClass().remove("decision");
			tableColumn.getStyleClass().add("description");
		}
		
		if(not(tableColumn.getAttribute().getActive())) {
			tableColumn.getStyleClass().clear();
			tableColumn.getStyleClass().add("not-active");
		} else {
			tableColumn.getStyleClass().remove("not-active");
			tableColumn.getStyleClass().add("active");
		}
	}
	
	/**
	 * Finds column from UI table by attribute.
	 * @param attribute to find
	 * @return column with found attribute or null
	 */
	private TableColumn getColumnByAttribute(Attribute attribute) {
		return learningTable.getColumns().stream()
				.filter(column ->
						((AttributeTableColumn) column).getAttribute().equals(attribute)
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
				.filter(att -> not(ATTRIBUTE_ID_SECRET_NAME.equals(att.getName())))
				.collect(Collectors.toList());
	}
	
}
