package pl.jowko.rulerank.desktop.feature.settings;

import pl.jowko.rulerank.desktop.feature.internationalization.Labels;
import pl.jowko.rulerank.desktop.feature.internationalization.LanguageService;
import pl.jowko.rulerank.desktop.feature.workspace.WorkspaceService;

import java.util.Objects;

import static java.util.Objects.isNull;
import static pl.jowko.rulerank.desktop.feature.internationalization.Labels.WORKSPACE_ERROR;
import static pl.jowko.rulerank.desktop.feature.settings.JRankConst.MSG;
import static pl.jowko.rulerank.desktop.utils.BooleanUtils.not;

/**
 * Created by Piotr on 2018-04-18.
 * This class validates configuration from userSettings.json file and user settings dialog form from settings menu.
 * @see UserSettings
 * @see UserSettingsController
 * @see pl.jowko.rulerank.desktop.feature.workspace.WorkspaceController
 */
public class UserSettingsValidator {
	
	private WorkspaceService workspaceService;
	private LanguageService labels;
	
	
	public UserSettingsValidator() {
		workspaceService = WorkspaceService.getInstance();
		labels = LanguageService.getInstance();
	}
	
	/**
	 * Check if user options from user settings form are valid.
	 * @param language value from language ComboBox
	 * @param workspacePath from workspace field
	 * @return String containing errors when they occurred, or empty String.
	 */
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
	
	/**
	 * Validate current configuration to check if its correct.
	 * Methods checks if provided language code is valid(exists in languages.json file).
	 * Also checks if provided workspacePath exists.
	 * Such problem with configuration can occur, when files were edited manually by user.
	 * @see WorkspaceService
	 * @see pl.jowko.rulerank.desktop.feature.workspace.WorkspaceController
	 * @return String containing errors when they occurred, or empty String.
	 */
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
	
	/**
	 * Return provided value of "null" text if value is null.
	 * It is used to display error message.
	 * @param value to transform
	 * @return value or "null" string
	 */
	private String getValueOrNullString(String value) {
		if(isNull(value))
			return "null";
		return value;
	}
	
}
