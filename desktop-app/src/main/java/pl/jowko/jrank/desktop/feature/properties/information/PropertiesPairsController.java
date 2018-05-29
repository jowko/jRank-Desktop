package pl.jowko.jrank.desktop.feature.properties.information;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import pl.jowko.jrank.desktop.feature.internationalization.Labels;
import pl.jowko.jrank.desktop.service.DialogsService;
import pl.jowko.jrank.desktop.utils.StringUtils;
import pl.jowko.jrank.logger.JRankLogger;

import java.util.Arrays;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static pl.jowko.jrank.desktop.utils.BooleanUtils.not;

/**
 * Created by Piotr on 2018-05-28
 * This controller is used in pairs configuration for properties form.
 * @see pl.jowko.jrank.desktop.feature.properties.PropertiesController
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
	 * Initialize all UI fields.
	 * It also initializes change labels event to change labels when user select different label from ComboBox.
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
		translatePairFields();
	}
	
	/**
	 * Adds selected pair from first and second ListView to pairs ListView with relation S.
	 */
	public void addSAction() {
		addPairItem(labels.get(Labels.PROP_INFO_S));
	}
	
	/**
	 * Adds selected pair from first and second ListView to pairs ListView with relation Sc.
	 */
	public void addScAction() {
		addPairItem(labels.get(Labels.PROP_INFO_SC));
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
	 * Checks if user wish to keep changes.
	 * If no changes occurred, It is assumed that user doesn't want to keep changes.
	 * If changes are detected, Confirmation dialog will be shown.
	 * @return true if user wish to keep changes, false otherwise
	 */
	protected boolean isUserWishToKeepChanges() {
		String newValue = convertPairsToText();
		if(result.get().equals(newValue))
			return false;
		
		return not(showConfirmActionDialog());
	}
	
	/**
	 * Parses previous settings for pairs with are extracted from properties form text.
	 * If any error occur, it will throw exception with message about error.
	 */
	private void readPreviousPairs() {
		try {
			readPreviousPairsFromText();
		} catch (RuntimeException e) {
			throw new TextParseFailException(labels.get(Labels.PROP_INFO_PAIRS_PARSE_EXCEPTION));
		}
	}
	
	/**
	 * Reads previous settings from StringProperty.
	 * If property is empty, it is assumed that no previous settings exists.
	 * It will extract all triples of pairs with relation.
	 * It will ignore any whitespaces.
	 * Valid format of pairs is:
	 * {1,2} S, {5,2} Sc
	 */
	private void readPreviousPairsFromText() {
		String text = result.get();
		if(StringUtils.isNullOrEmpty(text))
			return;
		
		String[] values = text.split("[,{}]");
		String[] pairs = Arrays.stream(values)
				.filter(value -> not(value.isEmpty() || value.trim().isEmpty()))
				.collect(Collectors.toList())
				.toArray(new String[]{});
		
		for(int i=0; i<pairs.length; i+=3) {
			FieldItem leftItem = fieldItems.get(Integer.valueOf(pairs[i])-1);
			FieldItem rightItem = fieldItems.get(Integer.valueOf(pairs[i+1])-1);
			String relation = pairs[i+2].trim();
			pairsListView.getItems().add(new PairsItem(leftItem.getFields(), rightItem.getFields(), relation, 0));
		}
	}
	
	/**
	 * Adds pair item to pairListView.
	 * It will extract selected items from first and second ListView.
	 * Then it will add them with provided relation to pairs set.
	 * If such pair already exists, nothing will be added.
	 * @param relation S or Sc, for added pair
	 */
	private void addPairItem(String relation) {
		FieldItem leftItem = firstListView.getSelectionModel().getSelectedItem();
		FieldItem rightItem = secondListView.getSelectionModel().getSelectedItem();
		
		if(isNull(leftItem) || isNull(rightItem)) {
			JRankLogger.warn("Select items from both sides first.");
			return;
		}
		
		var pair = new PairsItem(leftItem.getFields(), rightItem.getFields(), relation, leftItem.getDisplayedFieldIndex());
		if(pairsListView.getItems().contains(pair)) {
			JRankLogger.warn("Selected items were already added with such relation.");
			return;
		}
		
		pairsListView.getItems().add(pair);
	}
	
	/**
	 * Change displayed label in all ListView to label with provided index.
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
	 * This methods converts pairs from pairs ListView to text with can be passed to ruleRank.
	 * Output text have such format:
	 * {1,2} S, {5,2} Sc
	 *
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
