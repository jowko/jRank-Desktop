package pl.jowko.jrank.desktop.controller;

import javafx.fxml.FXML;
import pl.jowko.jrank.desktop.validator.UserSettingsValidator;

import static pl.jowko.jrank.desktop.utils.BooleanUtils.not;

/**
 * Created by Piotr on 2018-04-09.
 */
public class WorkspaceController {
	
	private UserSettingsValidator settingsValidator;
	private boolean isConfigValid = true;
	
	@FXML
	private void initialize() {
		settingsValidator = new UserSettingsValidator();
		
		if(not(settingsValidator.isConfigurationValid())) {
			isConfigValid = false;
			return;
			//TODO set some info on workspace panel
		}
		
	}
	
	public void onButtonAction() {
		System.out.println("button in workspace");
	}
	
}
