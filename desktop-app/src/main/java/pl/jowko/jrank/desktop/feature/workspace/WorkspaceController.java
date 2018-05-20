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
 */
public class WorkspaceController {
	
	@FXML
	private TreeView<WorkspaceItem> workspaceTree;
	
	private UserSettingsValidator settingsValidator;
	private LanguageService labels;
	private TreeBuilder treeBuilder;
	private boolean isConfigValid = true;
	
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
