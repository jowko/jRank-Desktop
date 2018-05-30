package pl.jowko.jrank.desktop.feature.properties.information;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import pl.jowko.jrank.desktop.feature.internationalization.Labels;
import pl.jowko.jrank.desktop.service.DialogsService;

import java.util.ArrayList;

import static java.util.Objects.isNull;
import static pl.jowko.jrank.desktop.utils.BooleanUtils.not;

/**
 * Created by Piotr on 2018-05-28
 * This controller handles action on properties ranking dialog form.
 * This dialog has ListView with contains data from MemoryContainer.
 * TreeView is used to store and configure ranking.
 *
 * In TreeView, 3 types of nodes are used:
 * root - main item in tree, needed by JavaFX
 * rank - serve as container for items from MemoryContainer.
 * position - represents single element from MemoryContainer.
 *
 * Example tree:
 * Ranking
 *   Ranking     // root
 *   	Rank 1   // rank
 *   	  3      // position
 *   	Rank 2   // rank
 *   	  4      // position
 *   	  6	     // position
 */
public class PropertiesRankingController extends AbstractInformationController {
	
	@FXML
	Label displayedLabelsLabel;
	@FXML
	ComboBox<AttributeItem> displayedLabels;
	
	@FXML
	ListView<FieldItem> dataView;
	@FXML
	TreeView<RankingItem> rankingTree;
	
	@FXML
	Button saveButton;
	@FXML
	Button cancelButton;
	
	private int rankingPosition = 1;
	
	/**
	 * Initializes controller.
	 * It fills all Views and ComboBox with data and initializes needed fields.
	 * It also initializes drag and drop action.
	 * Some initialization is also performed in AbstractInformationController
	 * @see DragAndDropInitializer
	 * @see AbstractInformationController
	 */
	@Override
	void initialize() {
		displayedLabels.getItems().addAll(comboItems);
		displayedLabels.getSelectionModel().select(0);
		displayedLabels.valueProperty().addListener((oo, old, newValue) ->
				changeLabelsInViews(newValue.getIndex())
		);
		
		initializeViews();
		translateFields();
		new DragAndDropInitializer(this);
	}
	
	/**
	 * Saves ranking from ranking tree to ranking field in properties form
	 */
	public void saveAction() {
		result.setValue(convertRankingToString());
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
	 * Checks, if any changes were made to ranking.
	 * If changes were detected, application will ask if user want to keep changes.
	 * @return true if user wants to keep changes
	 */
	@Override
	protected boolean isUserWishToKeepChanges() {
		String newValue = convertRankingToString();
		if(result.get().equals(newValue))
			return false;
		
		return not(showConfirmActionDialog());
	}
	
	
	/**
	 * Creates node for rank position.
	 * It will display proper label and ranking position.
	 * @return TreeItem of rank position type
	 */
	TreeItem<RankingItem> createRankingNode() {
		var rankingItem = new TreeItem<>(new RankingItem(labels.get(Labels.PROP_INFO_RANK) + rankingPosition++));
		rankingItem.setExpanded(true);
		return rankingItem;
	}
	
	/**
	 * Creates node for field in raking position.
	 * @param item with will be added to rank position
	 * @return TreeItem of field on position type
	 */
	TreeItem<RankingItem> createFieldNode(FieldItem item) {
		return new TreeItem<>(new RankingItem(item));
	}
	
	/**
	 * Removes provided tree item.
	 * It will remove it and also remove its all children(if it is rank node).
	 * If removed item was last in rank container, its parent(rank node) also be removed.
	 * @param selected node with will be removed
	 */
	void removeItemAction(TreeItem<RankingItem> selected) {
		if(isNull(selected))
			return;
		
		var parent = selected.getParent();
		if(selected.getValue().isRootNode())
			return;
		// if it is rank node, it contains children with also need to be removed and restored in ListView
		var children = new ArrayList<>(selected.getChildren());
		children.forEach(child ->
				removeTreeElement(selected, child)
		);
		removeTreeElement(parent, selected);
		
		// if its parent doesn't contain any children, it also be removed.
		if(parent.getChildren().size() == 0 && not(parent.getValue().isRootNode())) {
			removeTreeElement(parent.getParent(), parent);
		}
		recalculateRankingPosition();
	}
	
	private boolean showConfirmActionDialog() {
		String header = labels.get(Labels.PROP_ABANDON_CHANGES);
		return DialogsService.showConfirmationDialog(header);
	}
	
	/**
	 * Initializes ListView and TreeView.
	 * It fills this views with data and also creates ContextMenu for TreeView.
	 * ListView data are extracted from memory container.
	 * TreeView data are extracted from ranking field in properties form.
	 * @see pl.jowko.jrank.desktop.feature.properties.PropertiesController
	 * @see AbstractInformationController
	 */
	private void initializeViews() {
		dataView.getItems().addAll(fieldItems);
		
		var rootItem = new RankingItem(labels.get(Labels.PROP_INFO_RANKING));
		rootItem.setRootNode(true);
		var root = new TreeItem<>(rootItem);
		root.setExpanded(true);
		
		rankingTree.setRoot(root);
		initializeContextMenu();
		readPreviousRanking();
	}
	
	/**
	 * This methods reloads labels in ListView and TreeView.
	 * User can configure displayed labels by selecting field from ComboBox.
	 * This code will redisplay items using index of this label
	 * @param index from ComboBox with is used to determine, with label should be displayed
	 */
	private void changeLabelsInViews(int index) {
		dataView.getItems().forEach(item -> item.setDisplayedFieldIndex(index));
		dataView.refresh();
		
		changeLabelsInTreeView(rankingTree.getRoot(), index);
		rankingTree.refresh();
	}
	
	/**
	 * Changes labels in TreeView.
	 * All nodes are updated, so traversal of all items is performed.
	 * This is done by recurrence method.
	 * @param item to change label
	 * @param index to with change label
	 */
	private void changeLabelsInTreeView(TreeItem<RankingItem> item, int index) {
		if(not(item.getValue().isRankingNode()))
			item.getValue().getItem().setDisplayedFieldIndex(index);
		
		item.getChildren().forEach(child ->
			changeLabelsInTreeView(child, index)
		);
	}
	
	/**
	 * Initializes ContextMenu for ranking tree.
	 * 3 actions are added:
	 * - Remove selected - remove selected item from tree
	 * - Add begin - add new rank node on begin of tree
	 * - Add end - add new rank node at the end of tree
	 */
	private void initializeContextMenu() {
		ContextMenu menu = new ContextMenu();
		menu.getItems().add(createRemoveMenu());
		menu.getItems().add(createAddBeginMenu());
		menu.getItems().add(createAddEndMenu());
		rankingTree.setContextMenu(menu);
	}
	
	/**
	 * Creates remove selected menu item.
	 * This item will remove node with all its children.
	 * @return menu item for remove selected action
	 */
	private MenuItem createRemoveMenu() {
		MenuItem item = new MenuItem(labels.get(Labels.PROP_INFO_REMOVE));
		item.setOnAction(event ->
				removeItemAction(rankingTree.getSelectionModel().getSelectedItem())
		);
		return item;
	}
	
	/**
	 * Creates add rank at the begin action.
	 * It will ad new Rank node as first element in root children and recalculate rank labels
	 * @return menu item for add begin action
	 */
	private MenuItem createAddBeginMenu() {
		MenuItem item = new MenuItem(labels.get(Labels.PROP_INFO_ADD_BEGIN));
		item.setOnAction(event -> {
			var rankingItem = createRankingNode();
			rankingTree.getRoot().getChildren().add(0, rankingItem);
			recalculateRankingPosition();
		});
		return item;
	}
	
	/**
	 * Creates add rank at the end action.
	 * It will add new Rank node as last element in root children.
	 * @return menu item for add end action
	 */
	private MenuItem createAddEndMenu() {
		MenuItem item = new MenuItem(labels.get(Labels.PROP_INFO_ADD_END));
		item.setOnAction(event -> {
			var rankingItem = createRankingNode();
			rankingTree.getRoot().getChildren().add(rankingItem);
		});
		return item;
	}
	
	/**
	 * Removes provided toRemove element from parent element in tree.
	 * It also restores removed field element in daraView.
	 * @param parent from with item will be removed
	 * @param toRemove with will be removed from parent
	 */
	private void removeTreeElement(TreeItem<RankingItem> parent, TreeItem<RankingItem> toRemove) {
		parent.getChildren().remove(toRemove);
		if(not(toRemove.getValue().isRankingNode())) {
			dataView.getItems().add(toRemove.getValue().getItem());
		}
	}
	
	/**
	 * Recalculates labels for rank nodes.
	 * After rank node remove, rank numbers are not in good order.
	 * Example:
	 * Rank 1,2,3 - when we remove rank 2, it will leave such tree: Rank 1,3
	 * This method will replace labels for tree to such format: Rank 1,2
	 */
	private void recalculateRankingPosition() {
		rankingPosition = 1;
		var treeItems = rankingTree.getRoot().getChildren();
		for(var item : treeItems) {
			item.getValue().setLabel(labels.get(Labels.PROP_INFO_RANK) + rankingPosition++);
		}
	}
	
	/**
	 * Reads ranking from properties ranking field and initializes rankingTree with data.
	 * It can throw exception, when any error occurs when parsing.
	 *
	 * Example ranking:
	 * 1, 2 3 6, 4
	 * Such ranking will create 3 positions arrays:
	 * 1  |  2 3 6  | 4
	 *
	 * With lead to such tree:
	 * Rank 1
	 *   1
	 * Rank 2
	 *   2
	 *   3
	 *   6
	 * Rank 3
	 *   4
	 *
	 * @throws TextParseFailException when anything goes wrong with text parsing
	 */
	private void readPreviousRanking() {
		try {
			String[] ranks = result.get().split(",");
			for(String rank : ranks) {
				addReadRankToTree(rank.split(" "));
			}
		} catch (RuntimeException e) {
			throw new TextParseFailException(labels.get(Labels.PROP_INFO_RANKING_PARSE_EXCEPTION));
		}
	}
	
	/**
	 * Adds provided positions to next Rank item.
	 * Positions array represents single set of row number from MemoryContainer with are separated by comma.
	 * @param positions representing row ids for single Rank
	 */
	private void addReadRankToTree(String[] positions) {
		if(positions.length == 0 || (positions.length == 1 && positions[0].isEmpty()))
			return;
		
		var treeItem = createRankingNode();
		
		for(String position : positions) {
			if(position.isEmpty())
				continue;
			var fieldItem = fieldItems.get(Integer.valueOf(position)-1);
			treeItem.getChildren().add(createFieldNode(fieldItem));
			dataView.getItems().remove(fieldItem);
		}
		
		if(treeItem.getChildren().size() > 0)
			rankingTree.getRoot().getChildren().add(treeItem);
	}
	
	/**
	 * Converts ranking from rankingTree to ruleRank string.
	 * It is assumed, that first level from root represents Rank N.
	 * Second level represents rows from MemoryContainer.
	 * Example ranking:
	 * 1, 3 6, 7
	 * Only id values are used in ranking construction.
	 * @return String containing ranking
	 */
	private String convertRankingToString() {
		StringBuilder builder = new StringBuilder();
		var treeItems = rankingTree.getRoot().getChildren();
		
		for(var item : treeItems) {
			if(item.getChildren().size() == 0)
				continue;
			
			item.getChildren().forEach(child ->
				builder.append(' ')
						.append(child.getValue().getItem().getId())
			);
			builder.append(", ");
		}
		if(builder.length() > 2) { // if any items were added
			builder.delete(0, 1); // remove first whitespace
			builder.delete(builder.length() - 2, builder.length()); // remove last comma and whitespace
		}
		
		return builder.toString();
	}
	
	private void translateFields() {
		displayedLabelsLabel.setText(labels.get(Labels.PROP_INFO_DISPLAY_LABEL));
		saveButton.setText(labels.get(Labels.PROP_INFO_R_SAVE_BUTTON));
		cancelButton.setText(labels.get(Labels.PROP_INFO_R_CANCEL_BUTTON));
	}
	
}
