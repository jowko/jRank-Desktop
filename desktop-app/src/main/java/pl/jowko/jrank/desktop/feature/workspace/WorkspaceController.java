package pl.jowko.jrank.desktop.feature.workspace;

import javafx.fxml.FXML;
import javafx.scene.control.TreeView;
import pl.jowko.jrank.desktop.validator.UserSettingsValidator;

import static pl.jowko.jrank.desktop.utils.BooleanUtils.not;

/**
 * Created by Piotr on 2018-04-09.
 */
public class WorkspaceController {
	
	@FXML
	private TreeView<WorkspaceItem> workspaceTree;
	
	private UserSettingsValidator settingsValidator;
	private TreeBuilder treeBuilder;
	private boolean isConfigValid = true;
	
	@FXML
	private void initialize() {
		settingsValidator = new UserSettingsValidator();
		
		if(not(settingsValidator.isConfigurationValid())) {
			isConfigValid = false;
			return;
			//TODO set some info on workspace panel
		}
		treeBuilder = new TreeBuilder(workspaceTree);
		treeBuilder.buildTree();
	}
	
}
