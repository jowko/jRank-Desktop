package pl.jowko.rulerank.desktop.feature.workspace;

import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static pl.jowko.rulerank.desktop.utils.BooleanUtils.not;

/**
 * Class used for creating workspace tree from workspace items. <br>
 *  <br>
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
	 * This method refresh WorkspaceTree. <br>
	 * It will recreate tree again. <br>
	 * It uses recursive method to do it. <br>
	 * It will retain information about expanded nodes.
	 */
	void refreshTree() {
		TreeItem<WorkspaceItem> rootTreeItem = createRootNode();
		refresh(workspaceTree.getRoot(), rootTreeItem);
		
		workspaceTree.setRoot(rootTreeItem);
		workspaceTree.refresh();
	}
	
	/**
	 * This method rebuilds workspace tree. <br>
	 * It loads items to tree and checks, if they were previously expanded. <br>
	 * Then it calls itself recursively. <br>
	 * If number of elements in tree will change, information about expanded nodes can be lost. <br>
	 * It was done in such way for simplicity and such solution is less error prone.
	 * @param originalRoot from workspace tree
	 * @param newRoot with will replace root in workspace tree
	 */
	private void refresh(TreeItem<WorkspaceItem> originalRoot, TreeItem<WorkspaceItem> newRoot) {
		if(originalRoot.getChildren().size() != newRoot.getChildren().size())
			return;
		
		int i=0;
		for(TreeItem<WorkspaceItem> item : originalRoot.getChildren()) {
			
			if(item.isExpanded()) {
				var newItem = newRoot.getChildren().get(i);
				newItem.getChildren().clear();
				newItem.getChildren().addAll(getItemsForDirectory(newItem.getValue()));
				newItem.setExpanded(true);
				refresh(item, newItem);
			}
			
			i++;
		}
	}
	
	/**
	 * Initializes on click event on workspace tree item. <br>
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
	 * Initializes workspace tree. <br>
	 * It will load files from first and second level from main workspace catalog.
	 */
	private void initializeWorkspaceTree() {
		workspaceTree.setRoot(createRootNode());
	}
	
	/**
	 * Creates root node with first and second level items loaded into workspace
	 * @return root workspace
	 */
	private TreeItem<WorkspaceItem> createRootNode() {
		String workspacePath = WorkspaceService.getInstance().getWorkspacePath();
		List<WorkspaceItem> childrenItems = filesFinder.findFilesInDirectory(workspacePath);
		
		WorkspaceItem root = filesFinder.mapPathToWorkspaceFile(Paths.get(workspacePath));
		childrenItems.remove(root);
		TreeItem<WorkspaceItem> rootTreeItem = new TreeItem<> (root);
		rootTreeItem.setExpanded(true);
		
		fillRootTree(rootTreeItem, childrenItems);
		
		return rootTreeItem;
	}
	
	/**
	 * Fills main workspace item(root tree item) with file items. <br>
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
	 * Initializes expand event handler. <br>
	 * It will all items for children of expanded node and create TreeItems for them. <br>
	 * This enables to expand children of provided node. <br>
	 * <br>
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
	 * This method finds files and directories with are directly in directoryItem. <br>
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
