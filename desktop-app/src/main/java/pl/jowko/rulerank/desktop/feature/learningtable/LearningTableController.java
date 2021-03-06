package pl.jowko.rulerank.desktop.feature.learningtable;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import pl.jowko.rulerank.desktop.feature.internationalization.Labels;
import pl.jowko.rulerank.desktop.feature.internationalization.LanguageService;
import pl.jowko.rulerank.desktop.feature.learningtable.dialogs.AttributeItem;
import pl.jowko.rulerank.desktop.feature.learningtable.wrappers.CardinalFieldWrapper;
import pl.jowko.rulerank.desktop.feature.learningtable.wrappers.JRSFieldsReplacer;
import pl.jowko.rulerank.desktop.feature.tabs.RemovableChangeListener;
import pl.jowko.rulerank.desktop.feature.tabs.RuleRankTab;
import pl.jowko.rulerank.desktop.feature.tabs.upper.UpperTabsController;
import pl.jowko.rulerank.desktop.feature.workspace.WorkspaceItem;
import pl.jowko.rulerank.desktop.service.AbandonableTabForm;
import pl.jowko.rulerank.desktop.service.DialogsService;
import pl.jowko.rulerank.desktop.service.JRSFileMediator;
import pl.jowko.rulerank.logger.RuleRankLogger;
import pl.poznan.put.cs.idss.jrs.core.mem.MemoryContainer;
import pl.poznan.put.cs.idss.jrs.types.Attribute;
import pl.poznan.put.cs.idss.jrs.types.Example;
import pl.poznan.put.cs.idss.jrs.types.Field;

import static java.util.Objects.isNull;
import static javafx.collections.FXCollections.observableArrayList;
import static pl.jowko.rulerank.desktop.utils.BooleanUtils.not;

/**
 * Controller for editable learning data table. <br>
 *  <br>
 * Created by Piotr on 2018-05-08.
 */
public class LearningTableController implements AbandonableTabForm {
	
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
	private RuleRankTab learningTableTab;
	private WorkspaceItem workspaceItem;
	private LearningTableActions tableActions;
	private LanguageService labels;
	
	/**
	 * Initialize table and all needed data. <br>
	 * This class creates LearningTable from provided MemoryContainer. <br>
	 * It will wrap jRS raw fields with custom wrappers. <br>
	 * It also initializes all needed events and ContextMenu.
	 * @param container from with data for table are extracted
	 * @param tableTab in with learning table is set
	 * @param workspaceItem from workspace tree representing initialized file
	 */
	public void initializeTable(MemoryContainer container, RuleRankTab tableTab, WorkspaceItem workspaceItem) {
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
		disableRightClickSelect();
		initializeTableEditionHandler();
		new LearningTableTranslator(this).translateFields();
	}
	
	/**
	 * Change behavior of sorting in UI table. <br>
	 * This code should remove multi sorting and handle unknown fields on sort action. <br>
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
	
	/**
	 * Disable right click row select. <br>
	 * When user selects rows and right click on table to execute some action, row below mouse cursor is also selected. <br>
	 * This event will be disabled by this code to avoid accident row selects.
	 */
	private void disableRightClickSelect() {
		learningTable.setRowFactory(tv -> {
			TableRow<ObservableList<Field>> row = new TableRow<>();
			row.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
				if (e.getButton() == MouseButton.SECONDARY) {
					e.consume();
				}
			});
			return row ;
		});
	}
	
	/**
	 * This action will remove selected attribute. <br>
	 * If no attribute was selected, it will inform user about this in dialog.
	 * @see LearningTableActions
	 */
	public void removeAttributeAction() {
		AttributeItem item = selectAttribute.getValue();
		if(isNull(item)) {
			DialogsService.showActionFailedDialog(labels.get(Labels.LEARN_TABLE_REMOVE_ATTRIBUTE_FAIL));
			return;
		}
		
		tableActions.removeAttributeAction(item);
	}
	
	/**
	 * This action will remove all examples(row) from learning table. <br>
	 * It will ask for confirmation.
	 */
	public void removeAllExamplesAction() {
		String header = labels.get(Labels.LEARN_TABLE_REMOVE_ALL_HEADER);
		if(DialogsService.showConfirmationDialog(header))
			learningTable.getItems().clear();
	}
	
	/**
	 * Save edited learning table. <br>
	 * This method will extract data from UI table and put them into new LearningTable object. <br>
	 * Them it will validate, if table is valid. <br>
	 * After this, it will replace wrappers with raw jRS fields. <br>
	 * Then it converts LearningTable object to MemoryContainer and save it to file.
	 * @see LearningTableAssembler
	 * @see JRSFieldsReplacer
	 * @see JRSFileMediator
	 */
	public void saveAction() {
		try {
			LearningTable tableToSave = matchDataFromUIToLearningTable();
			if(not(isTableValid(tableToSave))) {
				return;
			}
			
			JRSFieldsReplacer.replaceWrappersWithJRSEnums(tableToSave);
			MemoryContainer container = MemoryContainerAssembler.assembleContainerFromTable(tableToSave);
			JRSFileMediator.saveMemoryContainer(workspaceItem.getFilePath(), workspaceItem.getFileName(), container);
			closeTab();
		} catch (Exception e) {
			RuleRankLogger.error("Error when saving data table: " + e.getMessage());
		}
	}
	
	/**
	 * Closes tab containing learning data table. <br>
	 * If edition occurred, application ask for confirmation.
	 */
	public void cancelAction() {
		if(isUserWishToKeepChanges()) {
			return;
		}
		closeTab();
	}
	
	@Override
	public RuleRankTab getTab() {
		return learningTableTab;
	}
	
	/**
	 * This will initialize edit event for table. <br>
	 * If any action related with table edition will occur, this code will set learning tab to edit mode. <br>
	 * This solution has one flaw - if user perform change and reverse it, table will be still in edit mode.
	 */
	private void initializeTableEditionHandler() {
		learningTable.getItems().addListener((ListChangeListener<? super ObservableList<Field>>) listener ->
				learningTableTab.setTabEdited(true)
		);
		learningTable.getColumns().addListener((ListChangeListener<? super TableColumn<ObservableList<Field>, ?>>) listener ->
				learningTableTab.setTabEdited(true)
		);
		learningTable.editingCellProperty().addListener(new RemovableChangeListener<>(learningTableTab, learningTable.editingCellProperty()));
	}
	
	/**
	 * Initializes rows and columns for learning table.
	 */
	private void initializeTable() {
		tableActions.createIdColumn();
		for(Attribute attribute : table.getAttributes()) {
			tableActions.createNewColumn(attribute);
		}
		
		for(int i=0; i<table.getExamples().size(); i++) {
			Example example = table.getExamples().get(i);
			learningTable.getItems().add(createItemRow(example));
		}
		initializeCloseEvent();
	}
	
	/**
	 * Create row of data for provided example. <br>
	 * It will create ID cell and cell for each field in example.
	 * @param example for with row of data will be created
	 * @return list of fields with ID cell and cells created from example fields
	 */
	private ObservableList<Field> createItemRow(Example example) {
		Field[] fields = new Field[example.getFields().length + 1];
		fields[0] = new CardinalFieldWrapper(tableActions.getIdColumnValue());
		
		for(int i=0; i<example.getFields().length; i++) {
			fields[i+1] = example.getFields()[i];
		}
		return observableArrayList(fields);
	}
	
	/**
	 * Extract data from UI table and put them in LearningTable
	 * @see LearningTableAssembler
	 * @return learning table extracted from UI table
	 */
	private LearningTable matchDataFromUIToLearningTable() {
		return new LearningTableAssembler(learningTable, table, tableActions.getAttributes()).getLearningTableFromUITable();
	}
	
	private void closeTab() {
		UpperTabsController.getInstance().closeTab(learningTableTab);
	}
	
	/**
	 * Validate table and check if data is valid. <br>
	 * If table is not valid, it will show error messages and ask for confirmation to save.
	 * @param tableToSave with will be validated
	 * @see LearningTableValidator
	 * @return true if table is valid, false otherwise
	 */
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
