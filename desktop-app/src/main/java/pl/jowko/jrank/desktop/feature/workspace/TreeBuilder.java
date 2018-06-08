package pl.jowko.jrank.desktop.feature.workspace;

import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static pl.jowko.jrank.desktop.utils.BooleanUtils.not;

/**
 * Created by Piotr on 2018-04-21.
 * @see FilesFinder
 * @see WorkspaceController
 */
class TreeBuilder {
	
	private TreeView<WorkspaceItem> workspaceTree;
	private FilesFinder filesFinder;
	
	/**
	 * Initialize this object.
	 * @param treeView from workspace fxml file to with workspace tree will be added
	 */
	TreeBuilder(TreeView<WorkspaceItem> treeView) {
		workspaceTree = treeView;
		filesFinder = new FilesFinder();
	}
	
	/**
	 * Build item tree and assign it to workspace tree.
	 */
	void buildTree() {
		initializeCellFactory();
		initializeWorkspaceTree();
	}
	
	/**
	 * Initializes on click event on workspace tree item.
	 * When user double clicks on tree item, related file should be opened in tab.
	 * @see ClickEventHandler
	 */
	private void initializeCellFactory() {
		ClickEventHandler clickEventHandler = new ClickEventHandler();
		workspaceTree.setCellFactory(tree -> {
			TreeCell<WorkspaceItem> cell = new WorkspaceTreeCell();
			cell.setOnMouseClicked(event -> clickEventHandler.onItemClickEvent(event, cell));
			return cell ;
		});
	}
	
	/**
	 * Initializes workspace tree.
	 * It will load files from first and second level from main workspace catalog.
	 */
	private void initializeWorkspaceTree() {
		String workspacePath = WorkspaceService.getInstance().getWorkspacePath();
		List<WorkspaceItem> childrenItems = filesFinder.findFilesInDirectory(workspacePath);
		
		WorkspaceItem root = filesFinder.mapPathToWorkspaceFile(Paths.get(workspacePath));
		TreeItem<WorkspaceItem> rootTreeItem = new TreeItem<> (root);
		rootTreeItem.setExpanded(true);
		
		fillRootTree(rootTreeItem, childrenItems);
		
		workspaceTree.setRoot(rootTreeItem);
	}
	
	/**
	 * Fills main workspace item(root tree item) with file items.
	 * It will convert provided WorkspaceItem list to TreeItem list and add them all as child for root.
	 * @param rootTreeItem with represents root in TreeView
	 * @param childrenItems with represents first level children
	 */
	private void fillRootTree(TreeItem<WorkspaceItem> rootTreeItem, List<WorkspaceItem> childrenItems) {
		childrenItems.forEach(rootItem -> {
			TreeItem<WorkspaceItem> item = new TreeItem<>(rootItem);
			if(FileType.DIRECTORY.equals(rootItem.getFileType())) {
				item.getChildren().addAll(getItemsForDirectory(rootItem));
				initializeExpandEventHandler(item);
			}
			
			rootTreeItem.getChildren().add(item);
		});
	}
	
	/**
	 * Initializes expand event handler.
	 * It will all items for children of expanded node and create TreeItems for them.
	 * This enables to expand children of provided node.
	 *
	 * @param treeItem to with event will be added
	 */
	private void initializeExpandEventHandler(TreeItem<WorkspaceItem> treeItem) {
		treeItem.addEventHandler(TreeItem.branchExpandedEvent(), event -> {
			treeItem.getChildren().forEach(child -> {
				if(FileType.DIRECTORY.equals(child.getValue().getFileType())) {
					if(not(child.getChildren().isEmpty()))
						return;
					
					child.getChildren().clear();
					child.getChildren().addAll(getItemsForDirectory(child.getValue()));
				}
			});
			workspaceTree.refresh();
		});
	}
	
	/**
	 * This method finds files and directories with are directly in directoryItem.
	 * All found files and directories will be added to tree item list.
	 * @param directoryItem for with files will be found
	 * @return list of TreeItems representing files and directories in provided directory
	 */
	private List<TreeItem<WorkspaceItem>> getItemsForDirectory(WorkspaceItem directoryItem) {
		List<WorkspaceItem> items = filesFinder.findFilesInDirectory(directoryItem.getFilePath());
		List<TreeItem<WorkspaceItem>> treeItems = new ArrayList<>();
		
		items.forEach(item -> {
			TreeItem<WorkspaceItem> treeItem = new TreeItem<>(item);
			initializeExpandEventHandler(treeItem);
			treeItems.add(treeItem);
		});
		
		return treeItems;
	}
	
}
