package pl.jowko.jrank.desktop.feature.learningtable;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import pl.jowko.jrank.desktop.ResourceLoader;
import pl.jowko.jrank.desktop.feature.learningtable.dialogs.ColumnDialogController;
import pl.jowko.jrank.desktop.feature.tabs.upper.UpperTabsController;
import pl.jowko.jrank.desktop.feature.workspace.WorkspaceItem;
import pl.jowko.jrank.desktop.service.DialogsService;
import pl.jowko.jrank.desktop.utils.Cloner;
import pl.jowko.jrank.logger.JRankLogger;
import pl.poznan.put.cs.idss.jrs.core.InvalidValueException;
import pl.poznan.put.cs.idss.jrs.core.mem.MemoryContainer;
import pl.poznan.put.cs.idss.jrs.types.Attribute;
import pl.poznan.put.cs.idss.jrs.types.EnumDomain;
import pl.poznan.put.cs.idss.jrs.types.Example;
import pl.poznan.put.cs.idss.jrs.types.Field;
import pl.poznan.put.cs.idss.jrs.utilities.ISFWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static javafx.collections.FXCollections.observableArrayList;
import static pl.jowko.jrank.desktop.utils.BooleanUtils.not;

/**
 * Created by Piotr on 2018-05-08.
 */
public class LearningTableController {
	
	@FXML
	Button addAttributeButton;
	@FXML
	Label selectAttributeLabel;
	@FXML
	ComboBox<String> selectAttribute;
	@FXML
	Button removeAttributeButton;
	@FXML
	Button editAttributeButton;
	@FXML
	Button addExampleButton;
	@FXML
	Button removeSelectedExamplesButton;
	@FXML
	Button removeAllExamplesButton;
	@FXML
	Button saveButton;
	@FXML
	Button cancelButton;
	
	@FXML
	TableView<ObservableList<Field>> learningTable;
	
	private LearningTable oldTable;
	private LearningTable table;
	private List<Attribute> attributes;
	private Tab learningTableTab;
	private WorkspaceItem workspaceItem;
	private LearningTableHelper tableHelper;
	private RuleRankFieldHelper fieldHelper;
	private EnumReplacer enumReplacer;
	
	public void initializeTable(MemoryContainer container, Tab tableTab, WorkspaceItem workspaceItem) {
		table = new LearningTable(container);
		enumReplacer = new EnumReplacer();
		enumReplacer.replaceJRSEnumsWithTableEnumFields(table);
		oldTable = (LearningTable) Cloner.deepClone(table);
		attributes = new ArrayList<>();
		learningTableTab = tableTab;
		this.workspaceItem = workspaceItem;
		tableHelper = new LearningTableHelper();
		fieldHelper = new RuleRankFieldHelper();
		initializeTable();
		setItemsToAttributeComboBox();
		learningTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	}
	
	public void addNewAttributeAction() throws IOException  {
		ResourceLoader resourceLoader = new ResourceLoader("/fxml/upperTabs/columnActionDialog.fxml");
		Parent parent = resourceLoader.load();
		ColumnDialogController controller = resourceLoader.getController();
		controller.initializeAddAction(this, parent);
	}
	
	public void editAttributeAction() throws IOException {
		AttributeTableColumn tableColumn = ((AttributeTableColumn) getColumnByAttributeName(selectAttribute.getValue()));
		if(isNull(tableColumn)) {
			JRankLogger.warn("No attribute was selected. Edit action aborted.");
			return;
		}
		
		ResourceLoader resourceLoader = new ResourceLoader("/fxml/upperTabs/columnActionDialog.fxml");
		Parent parent = resourceLoader.load();
		ColumnDialogController controller = resourceLoader.getController();
		controller.initializeEditAction(this, parent, tableColumn.getAttribute());
	}
	
	public void createNewColumn(Attribute attribute) {
		AttributeTableColumn column = new AttributeTableColumn(tableHelper.getColumnText(attribute), attribute);
		int attributeIndex = learningTable.getColumns().size();
		tableHelper.setCellFactories(column, attributeIndex);
		column.setOnEditCommit(col -> fieldHelper.setFieldValue(col.getOldValue(), col.getNewValue().toString()));
		column.setMinWidth(50d);
		
		learningTable.getColumns().add(column);
		attributes.add(attribute);
		setItemsToAttributeComboBox();
	}
	
	public void removeAttributeAction() {
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
	
	public void editAttribute(Attribute oldAttribute, Attribute editedAttribute) {
		AttributeTableColumn tableColumn = (AttributeTableColumn) getColumnByAttributeName(oldAttribute.getName());
		tableColumn.setText(tableHelper.getColumnText(editedAttribute));
		tableColumn.setAttribute(editedAttribute);
		
		if(editedAttribute.getInitialValue() instanceof TableEnumField) {
			handleEnumEdition(tableColumn);
		}
		
		replaceAttributeInLearningTable(oldAttribute, editedAttribute);
		int attributeIndex = learningTable.getColumns().indexOf(tableColumn);
		tableHelper.setCellFactories(tableColumn, attributeIndex);
		setItemsToAttributeComboBox();
	}
	
	public void addExampleAction() {
		if(learningTable.getColumns().size() == 0) {
			JRankLogger.info("No attributes in table. Add attributes first.");
		}
		ObservableList<Field> newFields = tableHelper.getEmptyExample(attributes);
		learningTable.getItems().add(newFields);
	}
	
	public void removeSelectedExamplesAction() {
		ObservableList<ObservableList<Field>> selectedRows = learningTable.getSelectionModel().getSelectedItems();
		if(selectedRows.isEmpty()) {
			JRankLogger.info("No examples were selected. Select examples first.");
			return;
		}
		// we don't want to iterate on same collection on with we remove items
		ArrayList<ObservableList<Field>> rows = new ArrayList<>(selectedRows);
		rows.forEach(row -> learningTable.getItems().remove(row));
	}
	
	public void removeAllExamplesAction() {
		String header = "Do you want to remove ALL examples from table?";
		if(new DialogsService().showConfirmationDialog(header))
			learningTable.getItems().clear();
	}
	
	public void saveAction() {
		try {
			LearningTable tableToSave = matchDataFromUIToLearningTable();
			enumReplacer.replaceTableEnumsWithJRSEnums(tableToSave);
			MemoryContainer container = MemoryContainerAssembler.assembleContainerFromTable(tableToSave);
			ISFWriter.saveMemoryContainerIntoISF(workspaceItem.getFilePath(), container);
			closeTab();
		} catch (Exception e) {
			JRankLogger.error("Error when saving data table: " + e.getMessage());
		}
	}
	
	public void cancelAction() {
		if(isUserWishToKeepChanges()) {
			return;
		}
		closeTab();
	}
	
	public TableView<ObservableList<Field>> getLearningTable() {
		return learningTable;
	}
	
	private void initializeTable() {
		for(Attribute attribute : table.getAttributes()) {
			createNewColumn(attribute);
		}
		
		for(Example example : table.getExamples()) {
			learningTable.getItems().add(observableArrayList(example.getFields()));
		}
		initializeCloseEvent();
	}
	
	private void initializeCloseEvent() {
		learningTableTab.setOnCloseRequest(event -> {
			if(isUserWishToKeepChanges()) {
				event.consume();
			}
		});
	}
	
	private boolean isUserWishToKeepChanges() {
		if(learningTable.getColumns().isEmpty()) {
			return not(showConfirmationDialog());
		}
		
		LearningTable actualTable = matchDataFromUIToLearningTable();
		return not(oldTable.equals(actualTable)) && not(showConfirmationDialog());
	}
	
	private LearningTable matchDataFromUIToLearningTable() {
		return new LearningTableAssembler(learningTable, table, attributes).getLearningTableFromUITable();
	}
	
	private boolean showConfirmationDialog() {
		String header = "Do you want to abandon changes in form?";
		return new DialogsService().showConfirmationDialog(header, "");
	}
	
	private void closeTab() {
		UpperTabsController.getInstance().closeTab(learningTableTab);
	}
	
	private TableColumn getColumnByAttributeName(String attributeName) {
		return learningTable.getColumns().stream()
				.filter(column ->
					((AttributeTableColumn) column).getAttribute().getName().equalsIgnoreCase(attributeName)
				)
				.findAny()
				.orElse(null);
	}
	
	private void setItemsToAttributeComboBox() {
		selectAttribute.setItems(observableArrayList(
				learningTable.getColumns().stream()
						.map(column -> ((AttributeTableColumn) column).getAttribute().getName())
						.collect(Collectors.toList()))
		);
	}
	
	private void handleEnumEdition(AttributeTableColumn tableColumn) {
		TableEnumField enumField = (TableEnumField) tableColumn.getAttribute().getInitialValue();
		int attributeIndex = learningTable.getColumns().indexOf(tableColumn);
		EnumDomain domain = enumField.getDomain();
		
		ObservableList<ObservableList<Field>> list = learningTable.getItems();
		list.forEach(row -> {
			TableEnumField field = (TableEnumField) row.get(attributeIndex);
			String previousValue = field.getName();
			try {
				int previousIndex = domain.getIndex(previousValue);
				row.set(attributeIndex, new TableEnumField(domain.getName(previousIndex), domain));
			} catch (InvalidValueException e) {
				JRankLogger.warn("Value: " + previousValue + " is not available now for field [" + tableColumn.getAttribute().getName() + "]. Setting first element into field.");
				row.set(attributeIndex, new TableEnumField(domain.getName(0), domain));
			}
			
		});
	}
	
	private void replaceAttributeInLearningTable(Attribute oldAttribute, Attribute newAttribute) {
		int index = attributes.indexOf(oldAttribute);
		attributes.set(index, newAttribute);
	}
	
}
