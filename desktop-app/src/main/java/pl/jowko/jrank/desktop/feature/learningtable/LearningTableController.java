package pl.jowko.jrank.desktop.feature.learningtable;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import pl.jowko.jrank.desktop.feature.tabs.upper.UpperTabsController;
import pl.jowko.jrank.desktop.feature.workspace.WorkspaceItem;
import pl.jowko.jrank.desktop.service.DialogsService;
import pl.jowko.jrank.desktop.service.JRSFileMediator;
import pl.jowko.jrank.desktop.utils.Cloner;
import pl.jowko.jrank.logger.JRankLogger;
import pl.poznan.put.cs.idss.jrs.core.mem.MemoryContainer;
import pl.poznan.put.cs.idss.jrs.types.Attribute;
import pl.poznan.put.cs.idss.jrs.types.Example;
import pl.poznan.put.cs.idss.jrs.types.Field;

import static javafx.collections.FXCollections.observableArrayList;
import static pl.jowko.jrank.desktop.utils.BooleanUtils.not;

/**
 * Created by Piotr on 2018-05-08.
 */
public class LearningTableController {
	
	@FXML
	Label selectAttributeLabel;
	@FXML
	ComboBox<String> selectAttribute;
	@FXML
	Button removeAttributeButton;
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
	private Tab learningTableTab;
	private WorkspaceItem workspaceItem;
	private LearningTableActions tableActions;
	
	public void initializeTable(MemoryContainer container, Tab tableTab, WorkspaceItem workspaceItem) {
		table = new LearningTable(container);
		EnumReplacer.replaceJRSEnumsWithTableEnumFields(table);
		oldTable = (LearningTable) Cloner.deepClone(table);
		learningTableTab = tableTab;
		this.workspaceItem = workspaceItem;
		tableActions = new LearningTableActions(learningTable, selectAttribute);
		new TableContextMenuCreator(learningTable, tableActions).create();
		initializeTable();
		tableActions.setItemsToAttributeComboBox();
		learningTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	}
	
	public void removeAttributeAction() {
		tableActions.removeAttributeAction();
	}
	
	public void removeAllExamplesAction() {
		String header = "Do you want to remove ALL examples from table?";
		if(DialogsService.showConfirmationDialog(header))
			learningTable.getItems().clear();
	}
	
	public void saveAction() {
		try {
			LearningTable tableToSave = matchDataFromUIToLearningTable();
			EnumReplacer.replaceTableEnumsWithJRSEnums(tableToSave);
			
			if(not(isTableValid(tableToSave))) {
				return;
			}
			
			MemoryContainer container = MemoryContainerAssembler.assembleContainerFromTable(tableToSave);
			JRSFileMediator.saveMemoryContainer(workspaceItem, container);
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
	
	private void initializeTable() {
		for(Attribute attribute : table.getAttributes()) {
			tableActions.createNewColumn(attribute);
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
		return new LearningTableAssembler(learningTable, table, tableActions.getAttributes()).getLearningTableFromUITable();
	}
	
	private boolean showConfirmationDialog() {
		String header = "Do you want to abandon changes in form?";
		return DialogsService.showConfirmationDialog(header, "");
	}
	
	private void closeTab() {
		UpperTabsController.getInstance().closeTab(learningTableTab);
	}
	
	private boolean isTableValid(LearningTable tableToSave) {
		LearningTableValidator validator = new LearningTableValidator(tableToSave);
		if(validator.isValid())
			return true;
		
		DialogsService.showValidationFailedDialog("Table contains errors:", validator.getErrorMsg());
		return false;
	}
	
}
