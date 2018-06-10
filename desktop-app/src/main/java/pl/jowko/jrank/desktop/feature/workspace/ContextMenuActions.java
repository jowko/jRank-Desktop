package pl.jowko.jrank.desktop.feature.workspace;

import javafx.scene.control.TreeView;
import javafx.scene.input.Clipboard;
import org.apache.commons.io.FileUtils;
import pl.jowko.jrank.desktop.feature.clipboard.ClipBoardManager;
import pl.jowko.jrank.desktop.feature.internationalization.Labels;
import pl.jowko.jrank.desktop.feature.internationalization.LanguageService;
import pl.jowko.jrank.desktop.service.DialogsService;
import pl.jowko.jrank.logger.JRankLogger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static java.util.Objects.isNull;
import static pl.jowko.jrank.desktop.feature.settings.JRankConst.MSG;

/**
 * Created by Piotr on 2018-06-10
 * This class handles all actions for ContextMenu in workspace.
 * It uses current selected item on all actions.
 * @see ContextMenuCreator
 */
class ContextMenuActions {
	
	private TreeView<WorkspaceItem> treeView;
	private LanguageService labels;
	
	/**
	 * Creates instance of this class.
	 * Also initializes keyboard shortcuts for all workspace tree actions
	 * @param treeView on with actions will be performed
	 * @see KeyBoardActionsHandler
	 */
	ContextMenuActions(TreeView<WorkspaceItem> treeView) {
		this.treeView = treeView;
		
		labels = LanguageService.getInstance();
		new KeyBoardActionsHandler(this).initKeyBoardActions(treeView);
	}
	
	/**
	 * This action deletes selected item.
	 * It does nothing when selected item is root.
	 * It will ask for confirmation when deleting.
	 * When deleting directory, all items in it will be removed.
	 */
	void deleteItemAction() {
		var selected = getSelectedValue();
		if(isNull(selected))
			return;
		
		String filePath = selected.getFilePath();
		
		if(FileType.ROOT.equals(selected.getFileType()))
			return;
		
		boolean isDirectory = FileType.DIRECTORY.equals(selected.getFileType());
		if(confirmDeleteAction(selected.getFileName(), isDirectory)) {
			deleteSelectedItem(filePath, isDirectory);
		}
	}
	
	/**
	 * Copies item to user clipboard.
	 * If item is root or directory, nothing happens.
	 */
	void copyItemAction() {
		var selected = getSelectedValue();
		if(isNull(selected))
			return;
		
		if(FileType.ROOT.equals(selected.getFileType()) || FileType.DIRECTORY.equals(selected.getFileType()))
			return;
		
		ClipBoardManager.putFile(new File(selected.getFilePath()));
	}
	
	/**
	 * Pastes all files from user clipboard to selected experiment directory.
	 */
	void pasteItemAction() {
		var selected = getSelectedValue();
		if(isNull(selected))
			return;
		
		String directory = selected.getExperimentPath();
		Clipboard clipboard = Clipboard.getSystemClipboard();
		List<File> files = clipboard.getFiles();
		
		try {
			for(File file : files) {
				File newFile = new File(directory + file.getName());
				if(newFile.getAbsolutePath().equals(file.getAbsolutePath()))
					continue;
				FileUtils.copyFile(file, newFile, true);
			}
		} catch (IOException e) {
			JRankLogger.error("Error when pasting files: ", e);
		}
		
		WorkspaceController.getInstance().refresh();
	}
	
	private WorkspaceItem getSelectedValue() {
		var selected = treeView.getSelectionModel().getSelectedItem();
		if(isNull(selected))
			return null;
		
		return selected.getValue();
	}
	
	/**
	 * Ask user to confirm action.
	 * @param fileName to display is confirmation message
	 * @param isDirectory manages displayed label in message
	 * @return true of iser confirmed action, false otherwise
	 */
	private boolean confirmDeleteAction(String fileName, boolean isDirectory) {
		String msg;
		
		if(isDirectory)
			msg = labels.get(Labels.WORK_MENU_DEL_CONFIRM_DIR).replace(MSG, fileName);
		else
			msg = labels.get(Labels.WORK_MENU_DEL_CONFIRM_FILE).replace(MSG, fileName);
		
		return DialogsService.showConfirmationDialog("", msg);
	}
	
	/**
	 * Deletes selected item on path.
	 * If path is directory, all files in it will be also deleted.
	 * If any IO exception occur, it will be logged(example: file used by other process)
	 * @param filePath to remove
	 * @param isDirectory to indicate if file is directory
	 */
	private void deleteSelectedItem(String filePath, boolean isDirectory) {
		try {
			if(isDirectory)
				FileUtils.deleteDirectory(new File(filePath));
			else
				Files.delete(Paths.get(filePath));
			
			WorkspaceController.getInstance().refresh();
		} catch (IOException e) {
			JRankLogger.error("Error when deleting item from workspace: ", e);
		}
	}
	
}
