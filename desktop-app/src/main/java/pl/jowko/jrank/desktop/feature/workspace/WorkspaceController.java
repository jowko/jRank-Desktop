package pl.jowko.jrank.desktop.feature.workspace;

import javafx.fxml.FXML;
import javafx.scene.control.TreeView;
import pl.jowko.jrank.desktop.feature.internationalization.Labels;
import pl.jowko.jrank.desktop.feature.internationalization.LanguageService;
import pl.jowko.jrank.desktop.feature.settings.UserSettingsValidator;
import pl.jowko.jrank.desktop.service.DialogsService;

import static pl.jowko.jrank.desktop.utils.BooleanUtils.not;

/**
 * Created by Piotr on 2018-04-09.
 * This controller manages TreeView containing all files and directories from workspace directory.
 * It builds nested tree containing all components.
 * @see UserSettingsValidator
 */
public class WorkspaceController {
	
	@FXML
	private TreeView<WorkspaceItem> workspaceTree;
	
	private UserSettingsValidator settingsValidator;
	private LanguageService labels;
	private TreeBuilder treeBuilder;
	private boolean isConfigValid = true;
	
	/**
	 * Initialize workspace tree.
	 * This method perform configuration validation before creating workspace tree.
	 * Such problems can occur when user edits configuration files manually and make mistakes.
	 * If configuration is valid, its creates workspace tree from files and directories in workspace directory.
	 * @see UserSettingsValidator
	 * @see TreeBuilder
	 */
	@FXML
	private void initialize() {
		settingsValidator = new UserSettingsValidator();
		labels = LanguageService.getInstance();
		
		validateConfiguration();
		if(not(isConfigValid)) {
			return;
		}
		
		treeBuilder = new TreeBuilder(workspaceTree);
		treeBuilder.buildTree();
	}
	
	/**
	 * Perform validation of configuration files.
	 * If user configuration is not valid, this controller doesn't create workspace tree.
	 * It checks if workspacePath and user language is valid.
	 */
	private void validateConfiguration() {
		String validationErrors = settingsValidator.validateConfiguration();
		if(not(validationErrors.isEmpty())) {
			String errorDialogHeader = labels.get(Labels.US_SETTINGS_ERROR);
			DialogsService.showErrorDialog(errorDialogHeader, validationErrors);
			isConfigValid = false;
			//TODO set some info on workspace panel
		}
	}
	
}
