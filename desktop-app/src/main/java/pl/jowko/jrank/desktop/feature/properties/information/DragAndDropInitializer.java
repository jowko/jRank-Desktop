package pl.jowko.jrank.desktop.feature.properties.information;

import javafx.scene.control.ListCell;
import javafx.scene.control.TreeCell;
import javafx.scene.input.*;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static pl.jowko.jrank.desktop.utils.BooleanUtils.not;

/**
 * Created by Piotr on 2018-05-30
 * This class initializes drag and drop action for ranking configuration in properties form.
 * @see PropertiesRankingController
 */
class DragAndDropInitializer {
	
	private PropertiesRankingController controller;
	private DataFormat fieldFormat;
	private DataFormat rankingItemFormat;
	
	/**
	 * Create instance of this class.
	 * It will initialize all events for drag and drop action for ranking dialog.
	 * @param controller with is used to perform some actions and access Views variables
	 */
	DragAndDropInitializer(PropertiesRankingController controller) {
		this.controller = controller;
		initDataFormat();
		initDragAndDrop();
	}
	
	/**
	 * Init data format with is used in Clipboard.
	 * This field serves as key for fields on drag and drop event.
	 * Each created field must have unique ID and this field is not removed after closing this dialog.
	 * So check is performed to find out, if such format already exists.
	 * Such situation happens, when user opens dialog more than one in application run time.
	 * rankingItemFormat is used in case of drag and drop between ranking tree items
	 */
	private void initDataFormat() {
		fieldFormat = DataFormat.lookupMimeType("FIELD_ID");
		if(isNull(fieldFormat))
			fieldFormat = new DataFormat("FIELD_ID");
		
		rankingItemFormat = DataFormat.lookupMimeType("RANKING_ITEM");
		if(isNull(rankingItemFormat))
			rankingItemFormat = new DataFormat("RANKING_ITEM");
	}
	
	/**
	 * Initializes drag and drop event between dataView and rankingTree.
	 * Items from dataView can be dragged and dropped to rankingTree.
	 * After such operation, item from ListView will be removed and added to ranking tree.
	 */
	private void initDragAndDrop() {
		controller.dataView.setCellFactory(param -> {
			ListCell<FieldItem> listCell = new ListCell<>() {
				@Override
				protected void updateItem(FieldItem item, boolean empty) {
					super.updateItem(item, empty);
					if(empty || isNull(item))
						setText(null);
					else
						setText(item.toString());
				}
			};
			
			listCell.setOnDragDetected((MouseEvent event) -> {
				if(nonNull(listCell.getItem())) {
					Dragboard db = listCell.startDragAndDrop(TransferMode.COPY);
					ClipboardContent content = new ClipboardContent();
					content.put(fieldFormat, listCell.getItem());
					db.setContent(content);
				}
				
				event.consume();
			});
			
			return listCell;
		});
		
		controller.rankingTree.setEditable(true);
		
		controller.rankingTree.setCellFactory(param -> {
			TreeCell<RankingItem> treeCell = new TreeCell<>() {
				@Override
				protected void updateItem(RankingItem item, boolean empty) {
					super.updateItem(item, empty);
					if(empty || isNull(item))
						setText(null);
					else
						setText(item.toString());
				}
			};
			
			// enable dragging of items in tree ranking view
			treeCell.setOnDragDetected((MouseEvent event) -> {
				RankingItem item = treeCell.getItem();
				if(nonNull(item) && not(item.isRankingNode())) {
					Dragboard db = treeCell.startDragAndDrop(TransferMode.COPY);
					ClipboardContent content = new ClipboardContent();
					content.put(fieldFormat, item.getItem());
					content.put(rankingItemFormat, item);
					db.setContent(content);
				}
				event.consume();
			});
			
			treeCell.setOnDragEntered((DragEvent event) ->
					treeCell.setStyle("-fx-background-color: aqua;")
			);
			
			treeCell.setOnDragExited((DragEvent event) ->
					treeCell.setStyle("")
			);
			
			treeCell.setOnDragOver((DragEvent event) -> {
				Dragboard db = event.getDragboard();
				if(db.hasContent(fieldFormat)) {
					event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
				}
				event.consume();
			});
			
			treeCell.setOnDragDropped((DragEvent event) ->
					handleOnDragDropped(event, treeCell)
			);
			
			return treeCell;
		});
	}
	
	/**
	 * Handle drop event on ranking tree.
	 * It will create position node for dragged field.
	 * It also creates new Rank node, when item was dragged to root node or empty cell.
	 * @param event with was fired
	 * @param treeCell on with this event was fired
	 */
	private void handleOnDragDropped(DragEvent event, TreeCell<RankingItem> treeCell) {
		Dragboard db = event.getDragboard();
		boolean success = false;
		
		// remove item from RankingTree in case of drag from tree
		if(db.hasContent(rankingItemFormat)) {
			var item = (RankingItem) db.getContent(rankingItemFormat);
			controller.removeItemAction(item);
		}
		
		if (db.hasContent(fieldFormat)) {
			FieldItem item = (FieldItem) db.getContent(fieldFormat);
			controller.dataView.getItems().remove(item);
			var treeItem = treeCell.getTreeItem();
			// is item was dragged to empty area, add item to root node
			if(isNull(treeItem))
				treeItem = controller.rankingTree.getRoot();
			// if item was dragged to field item, add it to its parent
			if(not(treeItem.getValue().isRankingNode()))
				treeItem = treeItem.getParent();
			
			if(treeItem.getValue().isRootNode()) {
				var rankingItem = controller.createRankingNode();
				rankingItem.getChildren().add(controller.createFieldNode(item));
				treeItem.getChildren().add(rankingItem);
			} else {
				treeItem.getChildren().add(controller.createFieldNode(item));
			}
			
			success = true;
		}
		
		db.clear();
		event.setDropCompleted(success);
		event.consume();
	}
	
}
