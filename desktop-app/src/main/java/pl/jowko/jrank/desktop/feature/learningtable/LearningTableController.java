package pl.jowko.jrank.desktop.feature.learningtable;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import pl.jowko.jrank.desktop.ResourceLoader;
import pl.jowko.jrank.desktop.feature.learningtable.dialogs.ColumnDialogController;
import pl.poznan.put.cs.idss.jrs.core.mem.MemoryContainer;
import pl.poznan.put.cs.idss.jrs.types.*;

import java.io.IOException;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static javafx.collections.FXCollections.observableArrayList;

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
	TableView<ObservableList<Field>> learningTable;
	
	private LearningTable table;
	private LearningTableHelper tableHelper;
	
	public void initializeTable(MemoryContainer container) {
		table = new LearningTable(container);
		tableHelper = new LearningTableHelper();
		new EnumReplacer().replaceJRSEnumsWithTableEnumFields(table);
		initializeTable();
		setItemsToAttributeComboBox();
	}
	
	public void addNewAttributeAction() throws IOException  {
		ResourceLoader resourceLoader = new ResourceLoader("/fxml/upperTabs/columnActionDialog.fxml");
		Parent parent = resourceLoader.load();
		ColumnDialogController controller = resourceLoader.getController();
		controller.initializeDialog(this, parent);
	}
	
	public void createNewColumn(Attribute attribute) {
		AttributeTableColumn column = new AttributeTableColumn(tableHelper.getColumnText(attribute), attribute);
		int attributeIndex = learningTable.getColumns().size();
		tableHelper.setCellFactories(column, attributeIndex);
		column.setOnEditCommit(col -> tableHelper.handleEditCellAction(col));
		column.setMinWidth(50d);
		
		learningTable.getColumns().add(column);
		setItemsToAttributeComboBox();
	}
	
	public void removeAttributeAction() {
		TableColumn tableColumn = getSelectedColumn();
		if(isNull(tableColumn))
			return;
		
		int attributeIndex = learningTable.getColumns().indexOf(tableColumn);
		learningTable.getColumns().remove(tableColumn);
		
		ObservableList<ObservableList<Field>> list = getLearningTable().getItems();
		list.forEach(row -> row.remove(attributeIndex));
		tableHelper.recreateCellValuesFactories(learningTable.getColumns());
		setItemsToAttributeComboBox();
	}
	
	public void editAttributeAction() {
	
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
	}
	
	private TableColumn getSelectedColumn() {
		String attributeName = selectAttribute.getValue();
		
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
	
}
