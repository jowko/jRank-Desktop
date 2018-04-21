package pl.jowko.jrank.desktop.feature.workspace;

import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

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
		List<WorkspaceItem> directories = filesFinder.findAllDirectories();
		
		WorkspaceItem root = findRootInDirectories(directories);
		TreeItem<WorkspaceItem> rootItem = new TreeItem<> (root);
		rootItem.setExpanded(true);
		directories.remove(root);
		
		directories.forEach(directory -> {
			rootItem.getChildren().add(createItemForDirectory(directory));
		});
		
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
