package pl.jowko.rulerank.desktop.feature.workspace;

import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TreeView;
import javafx.scene.input.Clipboard;
import org.apache.commons.io.FileExistsException;
import org.apache.commons.io.FileUtils;
import pl.jowko.rulerank.desktop.feature.clipboard.ClipBoardManager;
import pl.jowko.rulerank.desktop.feature.internationalization.Labels;
import pl.jowko.rulerank.desktop.feature.internationalization.LanguageService;
import pl.jowko.rulerank.desktop.service.DialogsService;
import pl.jowko.rulerank.desktop.utils.StringUtils;
import pl.jowko.rulerank.logger.RuleRankLogger;
import pl.poznan.put.cs.idss.jrs.core.ContainerFailureException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.util.Collections.singletonList;
import static java.util.Objects.isNull;
import static pl.jowko.rulerank.desktop.feature.settings.RuleRankConst.MSG;
import static pl.jowko.rulerank.desktop.utils.BooleanUtils.not;
import static pl.jowko.rulerank.desktop.utils.PathUtils.getParentDirectory;

/**
 * This class handles all actions for ContextMenu in workspace. <br>
 * It uses current selected item on all actions. <br>
 *  <br>
 * Created by Piotr on 2018-06-10
 * @see ContextMenuCreator
 */
class ContextMenuActions {
	
	private TreeView<WorkspaceItem> treeView;
	private LanguageService labels;
	
	private AtomicBoolean isCut;
	
	/**
	 * Creates instance of this class. <br>
	 * Also initializes keyboard shortcuts for all workspace tree actions
	 * @param treeView on with actions will be performed
	 * @see KeyBoardActionsHandler
	 */
	ContextMenuActions(TreeView<WorkspaceItem> treeView) {
		this.treeView = treeView;
		
		labels = LanguageService.getInstance();
		new KeyBoardActionsHandler(this).initKeyBoardActions(treeView);
		
		isCut = new AtomicBoolean(false);
	}
	
	/**
	 * This action deletes selected item. <br>
	 * It does nothing when selected item is root. <br>
	 * It will ask for confirmation when deleting. <br>
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
	 * Copies item to user clipboard. <br>
	 * If item is root or directory, nothing happens.
	 */
	void copyItemAction() {
		addSelectedItemToClipBoard();
		isCut.set(false);
	}
	
	/**
	 * Cuts selected item and puts it in user clipboard. <br>
	 * Is user make paste action, this item will be removed.
	 */
	void cutItemAction() {
		addSelectedItemToClipBoard();
		isCut.set(true);
	}
	
	/**
	 * Paste file from user clipboard to selected experiment directory. <br>
	 * If user performed cut action, file will be renamed instead of copied.
	 */
	void pasteItemAction() {
		var selected = getSelectedValue();
		if(isNull(selected))
			return;
		
		String directory = selected.getExperimentPath();
		Clipboard clipboard = Clipboard.getSystemClipboard();
		
		if(not(clipboard.hasFiles()))
			return;
		
		List<File> files = clipboard.getFiles();
		
		for(File file : files) {
			try {
				File newFile = new File(directory + file.getName());
				if(newFile.getAbsolutePath().equals(file.getAbsolutePath()))
					return;
				
				if(isCut.get())
					FileUtils.moveFile(file, newFile);
				else {
					if(newFile.exists()) {
						throw new FileExistsException("Destination '" + newFile.getAbsolutePath() + "' already exists.");
					}
					FileUtils.copyFile(file, newFile, true);
				}
				
				
			} catch (FileExistsException e) {
				RuleRankLogger.warn(e.getMessage() + ". Aborting action.");
			} catch (IOException e) {
				RuleRankLogger.error("Error when pasting files: ", e);
			}
		}
		
		ClipBoardManager.clear();
		refresh();
	}
	
	/**
	 * Adds .properties file to directory of selected item. <br>
	 * User will be asked to provide file name. <br>
	 * If user didn't provided valid file extension, it will be automatically added.
	 */
	void addPropertiesFileAction() {
		var selected = getSelectedValue();
		if(isNull(selected))
			return;
		
		String fileName = promptForFileName(labels.get(Labels.WORK_MENU_ADD_PROPERTIES_PROMPT), "");
		if(StringUtils.isNullOrEmpty(fileName)) {
			return;
		}
		
		try {
			ExperimentFilesCreator.createPropertiesFile(selected.getExperimentPath() + fileName);
		} catch (IOException e) {
			RuleRankLogger.error("Error when creating new properties file: ", e);
		}
		refresh();
	}
	
	/**
	 * Adds .isf file to directory of selected item. <br>
	 * User will be asked to provide file name. <br>
	 * If user didn't provided valid file extension, it will be automatically added.
	 */
	void addIsfFileAction() {
		var selected = getSelectedValue();
		if(isNull(selected))
			return;
		
		String fileName = promptForFileName(labels.get(Labels.WORK_MENU_ADD_ISF_PROMPT), "");
		if(StringUtils.isNullOrEmpty(fileName)) {
			return;
		}
		
		try {
			ExperimentFilesCreator.createMemoryContainerForWorkspace(selected.getExperimentPath(), fileName);
		} catch (ContainerFailureException e) {
			RuleRankLogger.error("Error when creating new isf file: ", e);
		}
		refresh();
	}
	
	/**
	 * Adds .txt file do directory of selected item. <br>
	 * User will be asked to provide file name. <br>
	 * If user didn't provided valid file extension, it will be automatically added.
	 */
	void addTextFileAction() {
		var selected = getSelectedValue();
		if(isNull(selected))
			return;
		
		String fileName = promptForFileName(labels.get(Labels.WORK_MENU_ADD_TXT_PROMPT), "");
		if(StringUtils.isNullOrEmpty(fileName)) {
			return;
		}
		
		try {
			ExperimentFilesCreator.createTextFileForWorkspace(selected.getExperimentPath(), fileName);
		} catch (IOException e) {
			RuleRankLogger.error("Error when creating new text file: ", e);
		}
		
		refresh();
	}
	
	/**
	 * Renames selected item. <br>
	 * User will be asked to provide file name by entering value in dialog field.
	 */
	void renameItemAction() {
		var selected = getSelectedValue();
		if(isNull(selected) || FileType.ROOT.equals(selected.getFileType()))
			return;
		
		String newFileName = promptForFileName(labels.get(Labels.WORK_MENU_RENAME_PROMPT), selected.getFileName());
		if(StringUtils.isNullOrEmpty(newFileName)) {
			return;
		}
		boolean isDirectory = FileType.DIRECTORY.equals(selected.getFileType());
		
		String filePath;
		if(isDirectory)
			filePath = getParentDirectory(selected.getExperimentPath()) + newFileName;
		else
			filePath = selected.getExperimentPath() + newFileName;
		
		if(selected.getFilePath().equals(filePath))
			return;
		
		try {
			File source = new File(selected.getFilePath());
			File target = new File(filePath);
			
			if(isDirectory)
				FileUtils.moveDirectory(source, target);
			else
				FileUtils.moveFile(source, target);
			
		} catch (FileExistsException e) {
			RuleRankLogger.warn(e.getMessage() + ". Aborting action. Choose another name.");
		} catch (IOException e) {
			RuleRankLogger.error("Error when renaming file: ", e);
		}
		refresh();
	}
	
	private WorkspaceItem getSelectedValue() {
		var selected = treeView.getSelectionModel().getSelectedItem();
		if(isNull(selected))
			return null;
		
		return selected.getValue();
	}
	
	private void refresh() {
		WorkspaceController.getInstance().refresh();
	}
	
	/**
	 * Adds selected item to user ClipBoard. <br>
	 * If selected item is root or directory, nothing happens.
	 */
	private void addSelectedItemToClipBoard() {
		var selected = getSelectedValue();
		if(isNull(selected))
			return;
		
		if(FileType.ROOT.equals(selected.getFileType()) || FileType.DIRECTORY.equals(selected.getFileType()))
			return;
		
		ClipBoardManager.putFile(singletonList(new File(selected.getFilePath())));
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
	 * Deletes selected item on path. <br>
	 * If path is directory, all files in it will be also deleted. <br>
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
			
			refresh();
		} catch (IOException e) {
			RuleRankLogger.error("Error when deleting item from workspace: ", e);
		}
	}
	
	/**
	 * Creates and shows dialog with one TextField. <br>
	 * User is asked to write some value there and it will be returned from this method.
	 * @param msg to display to user
	 * @param defaultValue to initialize TextField value
	 * @return text entered by user
	 */
	private String promptForFileName(String msg, String defaultValue) {
		TextInputDialog dialog = new TextInputDialog(defaultValue);
		dialog.setTitle(labels.get(Labels.WORK_MENU_TEXT_DIALOG_TITLE));
		dialog.setHeaderText(msg);
		dialog.setContentText(labels.get(Labels.WORK_MENU_TEXT_DIALOG_TEXT));
		
		Optional<String> result = dialog.showAndWait();
		return result.orElse(null);
	}
	
}
