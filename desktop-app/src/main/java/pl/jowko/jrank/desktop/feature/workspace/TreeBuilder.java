package pl.jowko.jrank.desktop.feature.workspace;

import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.util.ArrayList;
import java.util.List;

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
	 * Builds workspace tree.
	 * At first, it finds all directories in root workspace directory.
	 * Then, for each directory, it finds all files with are contained with it.
	 * TODO: This methods doesn't support nested directories.
	 * TODO: If nested directories exists, they all be displayed on first level of workspace tree
	 * TODO: Also, if directory A contains directories B and C, all files from A, B, C directories will be displayed in A experiment item as there were in single directory
	 * TODO: This issue needs to be fixed by replacing current solution with something else
	 * @see FilesFinder
	 */
	private void initializeWorkspaceTree() {
		List<WorkspaceItem> directories = filesFinder.findAllDirectories();
		
		WorkspaceItem root = findRootInDirectories(directories);
		TreeItem<WorkspaceItem> rootItem = new TreeItem<> (root);
		rootItem.setExpanded(true);
		directories.remove(root);
		
		directories.forEach(directory ->
			rootItem.getChildren().add(createItemForDirectory(directory))
		);
		
		rootItem.getChildren().add(new TreeItem<>(filesFinder.findDefaultProperties()));
		
		workspaceTree.setRoot(rootItem);
	}
	
	/**
	 * This methods find root element(workspace main directory).
	 * This method is used to filter out this directory from later processing.
	 * @param directories from workspace main directory
	 * @return directories without workspace main directory
	 */
	private WorkspaceItem findRootInDirectories(List<WorkspaceItem> directories) {
		return directories.stream()
				.filter(item -> FileType.ROOT.equals(item.getFileType()))
				.findAny()
				.orElse(new WorkspaceItem("ERROR", "", FileType.ROOT));
	}
	
	/**
	 * This method creates TreeItem for directory item.
	 * It finds all files in provided directory and adds them as children to provided directory.
	 * @see FilesFinder
	 * @param directory from with TreeItem will be created
	 * @return TreeItem created for provided directory, it has children with represents files from provided directory
	 */
	private TreeItem<WorkspaceItem> createItemForDirectory(WorkspaceItem directory) {
		TreeItem<WorkspaceItem> directoryItem = new TreeItem<>(directory);
		List<TreeItem<WorkspaceItem>> filesItems = new ArrayList<>();
		List<WorkspaceItem> files = filesFinder.findAllFiles(directory.getFilePath());
		
		files.forEach(item -> {
			TreeItem<WorkspaceItem> fileItem = new TreeItem<>(item);
			filesItems.add(fileItem);
		});
		
		directoryItem.getChildren().addAll(filesItems);
		
		return directoryItem;
	}
	
}
