package pl.jowko.jrank.desktop.validator;

import pl.jowko.jrank.desktop.feature.workspace.WorkspaceService;
import pl.jowko.jrank.desktop.service.DialogsService;
import pl.jowko.jrank.desktop.service.LanguageService;
import pl.jowko.jrank.desktop.service.UserSettingsService;
import pl.jowko.jrank.desktop.settings.Labels;
import pl.jowko.jrank.desktop.settings.UserSettings;

import java.util.Objects;

import static pl.jowko.jrank.desktop.settings.JRankConst.MSG;
import static pl.jowko.jrank.desktop.utils.BooleanUtils.not;

/**
 * Created by Piotr on 2018-04-18.
 */
public class UserSettingsValidator {
	
	private WorkspaceService workspaceService;
	private LanguageService labels;
	
	private String errorMsg;
	
	public UserSettingsValidator() {
		workspaceService = WorkspaceService.getInstance();
		labels = LanguageService.getInstance();
	}
	
	public boolean isUserSettingsFormValid(String workspacePath, String language) {
		errorMsg = "";
		
		if(not(workspaceService.isWorkspacePathValid(workspacePath))) {
			errorMsg += labels.get(Labels.WORKSPACE_ERROR).replace(MSG, workspacePath);
		}
		
		if(Objects.isNull(language)) {
			errorMsg += labels.get(Labels.LANGUAGE_ERROR);
		}
		
		if(errorMsg.length() == 0) {
			return true;
		}
		
		String errorDialogHeader = labels.get(Labels.VALIDATION_DIALOG_HEADER);
		new DialogsService().showValidationFailedDialog(errorDialogHeader, errorMsg);
		
		return false;
	}
	
	public boolean isConfigurationValid() {
		UserSettings settings = UserSettingsService.getInstance().getUserSettings();
		errorMsg = "";
		
		if(not(labels.getLanguages().containsKey(settings.getLanguage()))) {
			errorMsg += "Language code: " + settings.getLanguage() + " not found.\n";
		}
		
		if(not(workspaceService.isWorkspacePathValid(settings.getWorkspacePath()))) {
			errorMsg += labels.get(Labels.WORKSPACE_ERROR).replace(MSG, settings.getWorkspacePath());
		}
		
		if(errorMsg.length() == 0) {
			return true;
		}
		
		String errorDialogHeader = labels.get(Labels.US_SETTINGS_ERROR);
		new DialogsService().showErrorDialog(errorDialogHeader, errorMsg);
		
		return false;
	}
	
}
