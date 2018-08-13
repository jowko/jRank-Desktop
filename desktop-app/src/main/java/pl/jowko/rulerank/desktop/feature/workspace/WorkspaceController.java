package pl.jowko.rulerank.desktop.feature.workspace;

import javafx.fxml.FXML;
import javafx.scene.control.TreeView;
import pl.jowko.rulerank.desktop.feature.internationalization.Labels;
import pl.jowko.rulerank.desktop.feature.internationalization.LanguageService;
import pl.jowko.rulerank.desktop.feature.settings.UserSettingsValidator;
import pl.jowko.rulerank.desktop.service.DialogsService;

import static pl.jowko.rulerank.desktop.utils.BooleanUtils.not;

/**
 * Created by Piotr on 2018-04-09.
 * This controller manages TreeView containing all files and directories from workspace directory.
 * It builds nested tree containing all components.
 * @see UserSettingsValidator
 */
public class WorkspaceController {
	
	private static WorkspaceController instance;
	
	@FXML
	private TreeView<WorkspaceItem> workspaceTree;
	
	private UserSettingsValidator settingsValidator;
	private LanguageService labels;
	private TreeBuilder treeBuilder;
	private boolean configValid = true;
	
	public static WorkspaceController getInstance() {
		return instance;
	}
	
	public void refresh() {
		treeBuilder.refreshTree();
	}
	
	/**
	 * @return true if workspace is properly configured, false otherwise
	 */
	public boolean isConfigValid() {
		return configValid;
	}
	
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
		instance = this;
		settingsValidator = new UserSettingsValidator();
		labels = LanguageService.getInstance();
		
		validateConfiguration();
		if(not(configValid)) {
			return;
		}
		
		treeBuilder = new TreeBuilder(workspaceTree);
		treeBuilder.buildTree();
		new ContextMenuCreator().createContextMenu(workspaceTree);
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
			configValid = false;
		}
	}
	
}
