package pl.jowko.rulerank.desktop.feature.properties.information;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import pl.jowko.rulerank.desktop.feature.internationalization.Labels;
import pl.jowko.rulerank.desktop.service.DialogsService;
import pl.jowko.rulerank.logger.RuleRankLogger;

import java.util.List;

import static java.util.Objects.isNull;
import static pl.jowko.rulerank.desktop.utils.BooleanUtils.not;

/**
 * This controller is used in pairs configuration for properties form. <br>
 *  <br>
 * Created by Piotr on 2018-05-28
 * @see pl.jowko.rulerank.desktop.feature.properties.PropertiesController
 * @see PairsItem
 */
public class PropertiesPairsController extends AbstractInformationController {
	
	@FXML
	Label displayedLabelsLabel;
	@FXML
	ComboBox<AttributeItem> displayedLabels;
	
	@FXML
	ListView<FieldItem> firstListView;
	@FXML
	ListView<FieldItem> secondListView;
	@FXML
	ListView<PairsItem> pairsListView;
	
	@FXML
	Button addSButton;
	@FXML
	Button addScButton;
	@FXML
	Button removeSelectedButton;
	@FXML
	Button saveButton;
	@FXML
	Button cancelButton;
	
	/**
	 * Initialize all UI fields. <br>
	 * It also initializes change labels event to change labels when user select different label from ComboBox. <br>
	 * Also reads values from provided String property
	 */
	@Override
	void initialize() {
		displayedLabels.getItems().addAll(comboItems);
		displayedLabels.getSelectionModel().select(0);
		displayedLabels.valueProperty().addListener((oo, old, newValue) ->
				changeLabelsInListView(newValue.getIndex())
		);
		
		firstListView.getItems().addAll(fieldItems);
		secondListView.getItems().addAll(fieldItems);
		pairsListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		readPreviousPairs();
		initializeContextMenus();
		initializeKeyBoardActions();
		translatePairFields();
	}
	
	/**
	 * Adds selected pair from first and second ListView to pairs ListView with relation S.
	 */
	public void addSAction() {
		addPairItem(labels.get(Labels.PROP_INFO_S), false);
	}
	
	/**
	 * Adds selected pair from first and second ListView to pairs ListView with relation Sc.
	 */
	public void addScAction() {
		addPairItem(labels.get(Labels.PROP_INFO_SC), true);
	}
	
	/**
	 * Removes selected items from pairs ListView.
	 */
	public void removeSelectedAction() {
		var selectedItems = pairsListView.getSelectionModel().getSelectedItems();
		pairsListView.getItems().removeAll(selectedItems);
	}
	
	/**
	 * Saves pairs from pairsListView to pairs field in properties form
	 */
	public void saveAction() {
		result.setValue(convertPairsToText());
		closeDialog();
	}
	
	/**
	 * Closes this dialog.
	 */
	public void cancelAction() {
		if(isUserWishToKeepChanges())
			return;
		closeDialog();
	}
	
	/**
	 * Checks if user wish to keep changes. <br>
	 * If no changes occurred, It is assumed that user doesn't want to keep changes. <br>
	 * If changes are detected, Confirmation dialog will be shown. <br>
	 * It ignores all whitespaces, because it doesn't matter how many whitespaces were typed.
	 * @return true if user wish to keep changes, false otherwise
	 */
	@Override
	protected boolean isUserWishToKeepChanges() {
		String newValue = convertPairsToText().replace(" ", "");
		String oldValue = result.get().replace(" ", "");
		
		if(oldValue.equals(newValue))
			return false;
		
		return not(showConfirmActionDialog());
	}
	
	/**
	 * Initializes keyboard actions for pairs list.
	 */
	private void initializeKeyBoardActions() {
		pairsListView.setOnKeyPressed(event -> {
			if (KeyCode.DELETE == event.getCode()) {
				removeSelectedAction();
			}
		});
		firstListView.setOnKeyPressed(this::handleListViewKeys);
		secondListView.setOnKeyPressed(this::handleListViewKeys);
	}
	
	/**
	 * Handles keys for first and second list view
	 * @param event from list view
	 */
	private void handleListViewKeys(KeyEvent event) {
		if (KeyCode.S == event.getCode()) {
			addSAction();
			return;
		}
		if (KeyCode.C == event.getCode()) {
			addScAction();
		}
	}
	
	/**
	 * Initializes context menus for all list views
	 */
	private void initializeContextMenus() {
		initializeMenuForListView();
		initializeMenuForPairsListView();
	}
	
	/**
	 * Creates context menu for first and second list view. <br>
	 * It will create Add S and Sc MenuItems with work in same way like buttons.
	 */
	private void initializeMenuForListView() {
		MenuItem sItem = new MenuItem(labels.get(Labels.PROP_INFO_ADD_S_MENU));
		sItem.setOnAction(event ->
			addSAction()
		);
		
		MenuItem scItem = new MenuItem(labels.get(Labels.PROP_INFO_ADD_SC_MENU));
		scItem.setOnAction(event ->
				addScAction()
		);
		
		ContextMenu menu = new ContextMenu(sItem, scItem);
		firstListView.setContextMenu(menu);
		secondListView.setContextMenu(menu);
	}
	
	/**
	 * Creates context menu for pairs list view. <br>
	 * It will create delete item action.
	 */
	private void initializeMenuForPairsListView() {
		MenuItem deleteItem = new MenuItem(labels.get(Labels.PROP_INFO_REMOVE_MENU));
		deleteItem.setOnAction(event ->
				removeSelectedAction()
		);
		pairsListView.setContextMenu(new ContextMenu(deleteItem));
	}
	
	/**
	 * Parses previous settings for pairs with are extracted from properties form text. <br>
	 * If any error occur, it will throw exception with message about error.
	 * @throws TextParseFailException when anything goes wrong with pairs text parsing
	 */
	private void readPreviousPairs() {
		try {
			readPreviousPairsFromText();
		} catch (RuntimeException e) {
			throw new TextParseFailException(labels.get(Labels.PROP_INFO_PAIRS_PARSE_EXCEPTION), e);
		}
	}
	
	/**
	 * Reads previous settings from StringProperty. <br>
	 * If property is empty, it is assumed that no previous settings exists. <br>
	 * It will extract all triples of pairs with relation. <br>
	 * It will ignore any whitespaces. <br>
	 * Valid format of pairs is: <br>
	 * {1,2} S, {5,2} Sc
	 */
	private void readPreviousPairsFromText() {
		List<PairOfIndicesWrapper> pairs = InformationExtractor.extractPairs(result.get());
		if(isNull(pairs))
			return;
		
		for(PairOfIndicesWrapper wrapper : pairs) {
			FieldItem leftItem = fieldItems.get(wrapper.getPair().getIndex1());
			FieldItem rightItem = fieldItems.get(wrapper.getPair().getIndex2());
			
			String relation;
			if(wrapper.getRelation() >= 0.0)
				relation = "S";
			else
				relation = "Sc";
			
			pairsListView.getItems().add(new PairsItem(leftItem.getFields(), rightItem.getFields(), relation, 0));
		}
	}
	
	/**
	 * Adds pair item to pairListView. <br>
	 * It will extract selected items from first and second ListView. <br>
	 * Then it will add them with provided relation to pairs set. <br>
	 * If such pair already exists, nothing will be added. <br>
	 * @param relation S or Sc, for added pair
	 * @param isSc with indicates if this item is in Sc relation
	 */
	private void addPairItem(String relation, boolean isSc) {
		FieldItem leftItem = firstListView.getSelectionModel().getSelectedItem();
		FieldItem rightItem = secondListView.getSelectionModel().getSelectedItem();
		
		if(isNull(leftItem) || isNull(rightItem)) {
			RuleRankLogger.warn("Select items from both sides first.");
			return;
		}
		
		if(isSc && leftItem.equals(rightItem)) {
			RuleRankLogger.warn("Object cannot outrank itself. Aborting action.");
			return;
		}
		
		var pair = new PairsItem(leftItem.getFields(), rightItem.getFields(), relation, leftItem.getDisplayedFieldIndex());
		if(pairsListView.getItems().contains(pair)) {
			RuleRankLogger.warn("Selected items were already added with such relation.");
			return;
		}
		
		pairsListView.getItems().add(pair);
	}
	
	/**
	 * Change displayed label in all ListView to label with provided index. <br>
	 * All values are redisplayed immediately.
	 * @param index from AttributeItem
	 * @see AttributeItem
	 */
	private void changeLabelsInListView(int index) {
		firstListView.getItems().forEach(item -> item.setDisplayedFieldIndex(index));
		secondListView.getItems().forEach(item -> item.setDisplayedFieldIndex(index));
		pairsListView.getItems().forEach(item -> item.setDisplayedFieldIndex(index));
		
		firstListView.refresh();
		secondListView.refresh();
		pairsListView.refresh();
	}
	
	/**
	 * This methods converts pairs from pairs ListView to text with can be passed to ruleRank. <br>
	 * Output text have such format: <br>
	 * {1,2} S, {5,2} Sc <br>
	 * <br>
	 * @return text containing pairs with relations in original ruleRank format
	 */
	private String convertPairsToText() {
		ObservableList<PairsItem> pairs = pairsListView.getItems();
		if(pairs.size() < 1)
			return "";
		
		StringBuilder builder = new StringBuilder();
		
		pairs.forEach(pair ->
			builder.append('{')
					.append(pair.getLeftId())
					.append(',')
					.append(pair.getRightId())
					.append("} ")
					.append(pair.getRelation().trim())
					.append(", ")
		);
		// remove last comma with space
		builder.delete(builder.length() - 2, builder.length());
		
		return builder.toString();
	}
	
	private boolean showConfirmActionDialog() {
		String header = labels.get(Labels.PROP_ABANDON_CHANGES);
		return DialogsService.showConfirmationDialog(header);
	}
	
	private void translatePairFields() {
		displayedLabelsLabel.setText(labels.get(Labels.PROP_INFO_LABEL_LABEL));
		addSButton.setText(labels.get(Labels.PROP_INFO_ADD_S_BUTTON));
		addScButton.setText(labels.get(Labels.PROP_INFO_ADD_SC_BUTTON));
		removeSelectedButton.setText(labels.get(Labels.PROP_INFO_REMOVE_BUTTON));
		saveButton.setText(labels.get(Labels.PROP_INFO_SAVE_BUTTON));
		cancelButton.setText(labels.get(Labels.PROP_INFO_CANCEL_BUTTON));
	}
	
}
