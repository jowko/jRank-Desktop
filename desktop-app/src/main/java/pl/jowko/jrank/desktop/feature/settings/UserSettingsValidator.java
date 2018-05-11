package pl.jowko.jrank.desktop.feature.settings;

import pl.jowko.jrank.desktop.feature.workspace.WorkspaceService;
import pl.jowko.jrank.desktop.service.LanguageService;

import java.util.Objects;

import static java.util.Objects.isNull;
import static pl.jowko.jrank.desktop.feature.settings.JRankConst.MSG;
import static pl.jowko.jrank.desktop.feature.settings.Labels.WORKSPACE_ERROR;
import static pl.jowko.jrank.desktop.utils.BooleanUtils.not;

/**
 * Created by Piotr on 2018-04-18.
 */
public class UserSettingsValidator {
	
	private WorkspaceService workspaceService;
	private LanguageService labels;
	
	
	public UserSettingsValidator() {
		workspaceService = WorkspaceService.getInstance();
		labels = LanguageService.getInstance();
	}
	
	public String validateUserSettingsForm(String language, String workspacePath) {
		String errorMsg = "";
		
		if(isNull(workspacePath) || not(workspaceService.isWorkspacePathValid(workspacePath))) {
			String path = getValueOrNullString(workspacePath);
			errorMsg += labels.get(WORKSPACE_ERROR).replace(MSG, path);
		}
		
		if(Objects.isNull(language) || not(labels.getLanguages().containsValue(language))) {
			errorMsg += labels.get(Labels.LANGUAGE_ERROR);
		}
		
		return errorMsg;
	}
	
	public String validateConfiguration() {
		UserSettings settings = UserSettingsService.getInstance().getUserSettings();
		String errorMsg = "";
		
		if(not(labels.getLanguages().containsKey(settings.getLanguage()))) {
			errorMsg += "Language code: " + settings.getLanguage() + " not found.\n";
		}
		
		if(not(workspaceService.isWorkspacePathValid(settings.getWorkspacePath()))) {
			String workspacePath = getValueOrNullString(settings.getWorkspacePath());
			errorMsg += labels.get(WORKSPACE_ERROR).replace(MSG, workspacePath);
		}
		
		return errorMsg;
	}
	
	private String getValueOrNullString(String value) {
		if(isNull(value))
			return "null";
		return value;
	}
	
}
