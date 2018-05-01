package pl.jowko.jrank.desktop.feature.workspace;

import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Piotr on 2018-04-21.
 */
class TreeBuilder {
	
	private TreeView<WorkspaceItem> workspaceTree;
	private FilesFinder filesFinder;
	
	TreeBuilder(TreeView<WorkspaceItem> treeView) {
		workspaceTree = treeView;
		filesFinder = new FilesFinder();
	}
	
	public void buildTree() {
		initializeCellFactory();
		initializeWorkspaceTree();
	}
	
	private void initializeCellFactory() {
		ClickEventHandler clickEventHandler = new ClickEventHandler();
		workspaceTree.setCellFactory(tree -> {
			TreeCell<WorkspaceItem> cell = new WorkspaceTreeCell();
			cell.setOnMouseClicked(event -> clickEventHandler.onItemClickEvent(event, cell));
			return cell ;
		});
	}
	
	private void initializeWorkspaceTree() {
		List<WorkspaceItem> directories = filesFinder.findAllDirectories();
		
		WorkspaceItem root = findRootInDirectories(directories);
		TreeItem<WorkspaceItem> rootItem = new TreeItem<> (root);
		rootItem.setExpanded(true);
		directories.remove(root);
		
		directories.forEach(directory ->
			rootItem.getChildren().add(createItemForDirectory(directory))
		);
		
		workspaceTree.setRoot(rootItem);
	}
	
	private WorkspaceItem findRootInDirectories(List<WorkspaceItem> directories) {
		return directories.stream()
				.filter(item -> FileType.ROOT.equals(item.getFileType()))
				.findAny()
				.orElse(new WorkspaceItem("ERROR", "", FileType.ROOT));
	}
	
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
