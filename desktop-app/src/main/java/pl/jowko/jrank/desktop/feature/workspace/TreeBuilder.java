package pl.jowko.jrank.desktop.feature.workspace;

import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * Created by Piotr on 2018-04-21.
 */
class TreeBuilder {
	
	private TreeView<WorkspaceItem> workspaceTree;
	
	TreeBuilder(TreeView<WorkspaceItem> treeView) {
		workspaceTree = treeView;
	}
	
	public void buildTree() {
		initializeCellFactory();
		initializeWorkspaceTree();
	}
	
	private void initializeCellFactory() {
		workspaceTree.setCellFactory(tree -> {
			TreeCell<WorkspaceItem> cell = new WorkspaceTreeCell();
			cell.setOnMouseClicked(event -> onItemClickEvent(event, cell));
			return cell ;
		});
	}
	
	private void onItemClickEvent(MouseEvent event, TreeCell<WorkspaceItem> cell) {
		if(!event.getButton().equals(MouseButton.PRIMARY) || event.getClickCount() != 2 || cell.isEmpty()) {
			return;
		}
		
		TreeItem<WorkspaceItem> treeItem = cell.getTreeItem();
		FileType itemType = treeItem.getValue().getFileType();
		System.out.println(treeItem.getValue()); //TODO remove this later
		
		if(FileType.ROOT.equals(itemType) || FileType.FOLDER.equals(itemType)) {
			// Do nothing
			return;
		}
	}
	
	private void initializeWorkspaceTree() {
		//TODO replace stubs with real data
		TreeItem<WorkspaceItem> rootItem = new TreeItem<> (new WorkspaceItem("root", "asfas", FileType.ROOT));
		rootItem.setExpanded(true);
		for (int i = 1; i < 6; i++) {
			TreeItem<WorkspaceItem> item = new TreeItem<> (new WorkspaceItem(String.valueOf(i), "asfas", FileType.FOLDER));
			rootItem.getChildren().add(item);
		}
		
		workspaceTree.setRoot(rootItem);
	}
	
}
