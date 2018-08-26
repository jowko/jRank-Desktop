package pl.jowko.rulerank.desktop.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.DirectoryChooser;
import pl.jowko.rulerank.desktop.Main;
import pl.jowko.rulerank.desktop.feature.internationalization.Labels;
import pl.jowko.rulerank.desktop.feature.internationalization.LanguageService;
import pl.jowko.rulerank.desktop.feature.workspace.ExperimentFilesCreator;
import pl.jowko.rulerank.desktop.feature.workspace.FilesFinder;
import pl.jowko.rulerank.desktop.feature.workspace.WorkspaceController;
import pl.jowko.rulerank.desktop.feature.workspace.WorkspaceService;
import pl.jowko.rulerank.desktop.service.DialogsService;
import pl.jowko.rulerank.logger.RuleRankLogger;
import pl.poznan.put.cs.idss.jrs.core.ContainerFailureException;

import java.io.File;
import java.io.IOException;

import static java.util.Objects.isNull;
import static pl.jowko.rulerank.desktop.utils.BooleanUtils.not;
import static pl.jowko.rulerank.desktop.utils.PathUtils.getSubDirectoryPath;

/**
 * Controller for toolbar in main window. It handles toolbar actions.<br>
 * <br>
 * Created by Piotr on 2018-04-09.
 */
public class ToolbarController {
	
	@FXML
	Button refreshButton;
	@FXML
	Button createButton;
	
	private LanguageService labels;
	
	@FXML
	private void initialize() {
		labels = LanguageService.getInstance();
		translateButtons();
	}
	
	/**
	 * This action refresh all workspace tree. <br>
	 * It will display any changes in directories structure.
	 */
	public void refreshAction() {
		if(not(WorkspaceController.getInstance().isConfigValid())) {
			logWorkspaceError("Refresh");
			return;
		}
		
		WorkspaceController.getInstance().refresh();
	}
	
	/**
	 * Creates new experiment. <br>
	 * User chooses new catalog(or creates one) and properties and isf file will be created in this catalog. <br>
	 * Also workspace tree will be refreshed.
	 * @throws IOException when something goes wrong with file reading
	 * @throws ContainerFailureException when something goes wrong with creating example MemoryContainer
	 */
	public void createExperimentAction() throws IOException, ContainerFailureException {
		if(not(WorkspaceController.getInstance().isConfigValid())) {
			logWorkspaceError("Create experiment");
			return;
		}
		
		File selectedDirectory = showSelectDirectoryDialog();
		if(isNull(selectedDirectory))
			return;
		
		FilesFinder filesFinder = new FilesFinder();
		var files = filesFinder.findFilesInDirectory(selectedDirectory.getAbsolutePath());
		if(not(files.isEmpty())) {
			boolean isConfirmed = DialogsService.showConfirmationDialog("", labels.get(Labels.TOOL_CREATE_OVERRIDE_CONFIRM));
			
			if(not(isConfirmed))
				return;
		}
		
		String dirPath = getSubDirectoryPath(selectedDirectory.getAbsolutePath());
		ExperimentFilesCreator.createPropertiesFile(dirPath, selectedDirectory.getName());
		ExperimentFilesCreator.createMemoryContainer(dirPath, selectedDirectory.getName());
		
		refreshAction();
	}
	
	/**
	 * Shows dialog to choose directory from workspace.
	 * @return file representing chosen directory
	 */
	private File showSelectDirectoryDialog() {
		String workspacePath = WorkspaceService.getInstance().getWorkspacePath();
		DirectoryChooser chooser = new DirectoryChooser();
		chooser.setTitle(labels.get(Labels.TOOL_CREATE_DIALOG_TITLE));
		File defaultDirectory = new File(workspacePath);
		chooser.setInitialDirectory(defaultDirectory);
		return chooser.showDialog(Main.getScene().getWindow());
	}
	
	private void translateButtons() {
		refreshButton.setText(labels.get(Labels.TOOL_REFRESH_BUTTON));
		createButton.setText(labels.get(Labels.TOOL_CREATE_BUTTON));
	}
	
	private void logWorkspaceError(String action) {
		RuleRankLogger.warn(action + " action aborted. Workspace is not configured properly. See user settings");
	}
	
}
