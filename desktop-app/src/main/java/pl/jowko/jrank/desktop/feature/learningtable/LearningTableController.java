package pl.jowko.jrank.desktop.feature.learningtable;

import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import pl.jowko.jrank.desktop.feature.internationalization.Labels;
import pl.jowko.jrank.desktop.feature.internationalization.LanguageService;
import pl.jowko.jrank.desktop.feature.learningtable.dialogs.AttributeItem;
import pl.jowko.jrank.desktop.feature.learningtable.wrappers.CardinalFieldWrapper;
import pl.jowko.jrank.desktop.feature.learningtable.wrappers.JRSFieldsReplacer;
import pl.jowko.jrank.desktop.feature.tabs.JRankTab;
import pl.jowko.jrank.desktop.feature.tabs.upper.UpperTabsController;
import pl.jowko.jrank.desktop.feature.workspace.WorkspaceItem;
import pl.jowko.jrank.desktop.service.DialogsService;
import pl.jowko.jrank.desktop.service.JRSFileMediator;
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
	ComboBox<AttributeItem> selectAttribute;
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
	
	private LearningTable table;
	private JRankTab learningTableTab;
	private WorkspaceItem workspaceItem;
	private LearningTableActions tableActions;
	private LanguageService labels;
	
	public void initializeTable(MemoryContainer container, JRankTab tableTab, WorkspaceItem workspaceItem) {
		table = new LearningTable(container);
		JRSFieldsReplacer.replaceJRSEnumsWithTableEnumFields(table);
		learningTableTab = tableTab;
		this.workspaceItem = workspaceItem;
		labels = LanguageService.getInstance();
		tableActions = new LearningTableActions(learningTable, selectAttribute, learningTableTab);
		new TableContextMenuCreator(learningTable, tableActions).create();
		initializeTable();
		tableActions.setItemsToAttributeComboBox();
		learningTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		initializeSorting();
		initializeTableEditionHandler();
		new LearningTableTranslator(this).translateFields();
	}
	
	/**
	 * Change behavior of sorting in UI table.
	 * This code should remove multi sorting and handle unknown fields on sort action.
	 * It is needed, because jRS fields will throw exception when comparing unknown fields.
	 * @see UnknownFieldSortCallback
	 */
	private void initializeSorting() {
		learningTable.getSortOrder().addListener((ListChangeListener.Change<? extends TableColumn> c) -> {
			while (learningTable.getSortOrder().size() > 1) {
				learningTable.getSortOrder().remove(1);
			}
		});
		
		learningTable.sortPolicyProperty().set(new UnknownFieldSortCallback(learningTable.getItems()));
	}
	
	public void removeAttributeAction() {
		tableActions.removeAttributeAction();
	}
	
	public void removeAllExamplesAction() {
		String header = labels.get(Labels.LEARN_TABLE_REMOVE_ALL_HEADER);
		if(DialogsService.showConfirmationDialog(header))
			learningTable.getItems().clear();
	}
	
	public void saveAction() {
		try {
			LearningTable tableToSave = matchDataFromUIToLearningTable();
			if(not(isTableValid(tableToSave))) {
				return;
			}
			
			JRSFieldsReplacer.replaceWrappersWithJRSEnums(tableToSave);
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
	
	private void initializeTableEditionHandler() {
		learningTable.getItems().addListener((ListChangeListener<? super ObservableList<Field>>) listener ->
				learningTableTab.setTabEdited(true)
		);
		learningTable.getColumns().addListener((ListChangeListener<? super TableColumn<ObservableList<Field>, ?>>) listener ->
				learningTableTab.setTabEdited(true)
		);
		learningTable.editingCellProperty().addListener((ChangeListener<? super TablePosition<ObservableList<Field>, ?>>) (ov, o, n) ->
				learningTableTab.setTabEdited(true)
		);
	}
	
	private void initializeTable() {
		tableActions.createIdColumn();
		for(Attribute attribute : table.getAttributes()) {
			tableActions.createNewColumn(attribute);
		}
		
		for(int i=0; i<table.getExamples().size(); i++) {
			Example example = table.getExamples().get(i);
			learningTable.getItems().add(createItemRow(example, tableActions.getIdColumnValue()));
		}
		initializeCloseEvent();
	}
	
	private ObservableList<Field> createItemRow(Example example, int index) {
		Field[] fields = new Field[example.getFields().length + 1];
		fields[0] = new CardinalFieldWrapper(index);
		
		for(int i=0; i<example.getFields().length; i++) {
			fields[i+1] = example.getFields()[i];
		}
		return observableArrayList(fields);
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
		return learningTableTab.isTabEdited() && not(showConfirmationDialog());
	}
	
	private LearningTable matchDataFromUIToLearningTable() {
		return new LearningTableAssembler(learningTable, table, tableActions.getAttributes()).getLearningTableFromUITable();
	}
	
	private boolean showConfirmationDialog() {
		String header = labels.get(Labels.LEARN_TABLE_ABANDON_CHANGES);
		return DialogsService.showConfirmationDialog(header, "");
	}
	
	private void closeTab() {
		UpperTabsController.getInstance().closeTab(learningTableTab);
	}
	
	private boolean isTableValid(LearningTable tableToSave) {
		LearningTableValidator validator = new LearningTableValidator(tableToSave);
		if(validator.isValid())
			return true;
		
		if(not(validator.isValid())) {
			return DialogsService.showConfirmationDialog(labels.get(Labels.LEARN_TABLE_SAVE_CONFIRM), validator.getErrorMessages());
		}
		
		return false;
	}
	
}
