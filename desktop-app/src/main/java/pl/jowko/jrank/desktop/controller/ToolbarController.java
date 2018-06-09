package pl.jowko.jrank.desktop.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import pl.jowko.jrank.desktop.feature.internationalization.Labels;
import pl.jowko.jrank.desktop.feature.internationalization.LanguageService;
import pl.jowko.jrank.desktop.feature.workspace.WorkspaceController;

/**
 * Created by Piotr on 2018-04-09.
 * Controller for toolbar in main window. It handles toolbar actions.
 */
public class ToolbarController {
	
	@FXML
	Button refreshButton;
	
	private LanguageService labels;
	
	@FXML
	private void initialize() {
		labels = LanguageService.getInstance();
		translateButtons();
	}
	
	public void refreshAction() {
		WorkspaceController.getInstance().refresh();
	}
	
	private void translateButtons() {
		refreshButton.setText(labels.get(Labels.TOOL_REFRESH_BUTTON));
	}
	
}
