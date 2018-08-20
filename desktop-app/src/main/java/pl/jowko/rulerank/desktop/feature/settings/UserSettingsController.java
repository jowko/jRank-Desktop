package pl.jowko.rulerank.desktop.feature.settings;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import pl.jowko.rulerank.desktop.feature.internationalization.Labels;
import pl.jowko.rulerank.desktop.feature.internationalization.LanguageService;
import pl.jowko.rulerank.desktop.service.DialogsService;
import pl.jowko.rulerank.feature.customfx.AbstractDialogForm;
import pl.jowko.rulerank.logger.RuleRankLogger;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static pl.jowko.rulerank.desktop.utils.BooleanUtils.not;

/**
 * Controller for User settings dialog from settings menu. <br>
 * It allows to customize all editable options of application. <br>
 * Settings are saved in userSettings.json file in data directory, where rest of configuration files resides. <br>
 *  <br>
 * Created by Piotr on 2018-03-17.
 * @see UserSettings
 * @see UserSettingsValidator
 */
public class UserSettingsController extends AbstractDialogForm {
	
	@FXML
	Button saveButton;
	@FXML
	Button cancelButton;
	@FXML
	Label languageText;
	@FXML
	ChoiceBox<String> languagesChoice;
	@FXML
	Label workspaceLabel;
	@FXML
	TextField workspaceField;
	@FXML
	Label csvSeparatorLabel;
	@FXML
	TextField csvSeparator;
	@FXML
	CheckBox tooltipsEnabled;
	@FXML
	CheckBox advancedPropertiesEnabled;
	@FXML
	CheckBox manualEditionEnabled;
	@FXML
	CheckBox startMaximized;
	@FXML
	Label infoText;
	
	private static UserSettings newUserSettings;
	private Map<String, String> languages;
	private LanguageService labels;
	private UserSettingsValidator settingsValidator;
	
	/**
	 * Initialize needed objects. <br>
	 * Also sets current value from settings into UI fields.
	 */
	@FXML
	private void initialize() {
		labels = LanguageService.getInstance();
		new UserSettingsTranslator(this).translateFields();
		initializeNewSettings();
		initializeLanguages();
		workspaceField.setText(newUserSettings.getWorkspacePath());
		csvSeparator.setText(newUserSettings.getCsvSeparator());
		tooltipsEnabled.setSelected(newUserSettings.isTooltipsEnabled());
		advancedPropertiesEnabled.setSelected(newUserSettings.isAdvancedPropertiesEnabled());
		manualEditionEnabled.setSelected(newUserSettings.isManualInfoEditionEnabled());
		startMaximized.setSelected(newUserSettings.isStartMaximized());
		settingsValidator = new UserSettingsValidator();
	}
	
	/**
	 * Perform save action. <br>
	 * Settings are validated and if correct, they are saved to files. <br>
	 * User settings are not reloaded in application after change. <br>
	 * Application restart is required to do this.
	 */
	public void onSaveAction() {
		if(not(isSettingsFormValid())) {
			return;
		}
		
		try {
			updateNewSettings();
			UserSettingsService.getInstance().saveUserSettings(newUserSettings);
			onCancelAction();
		} catch (IOException e) {
			RuleRankLogger.error("Error when saving user options: ", e);
			String errorDialogHeader = labels.get(Labels.US_ERROR_DIALOG_HEADER);
			DialogsService.showErrorDialog(errorDialogHeader, e.getMessage());
		}
		
	}
	
	/**
	 * Close user settings dialog without saving.
	 */
	public void onCancelAction() {
		stage.close();
	}
	
	/**
	 * Initialize user settings for form. <br>
	 * Deep copy is performed to avoid changing settings with are already used in application. <br>
	 * Also static reference is remembered, to remember options on form after save and return without application restart.
	 */
	private static void initializeNewSettings() {
		if(Objects.isNull(newUserSettings)) {
			UserSettings settings = UserSettingsService.getInstance().getUserSettings();
			
			newUserSettings = new UserSettingsBuilder()
					.setLanguage(settings.getLanguage())
					.setWorkspacePath(settings.getWorkspacePath())
					.setCsvSeparator(settings.getCsvSeparator())
					.setTooltipsEnabled(settings.isTooltipsEnabled())
					.setAdvancedPropertiesEnabled(settings.isAdvancedPropertiesEnabled())
					.setManualInfoEditionEnabled(settings.isManualInfoEditionEnabled())
					.setStartMaximized(settings.isStartMaximized())
					.createUserSettings();
		}
	}
	
	/**
	 * Update user settings with values extracted from UI form.
	 */
	private void updateNewSettings() {
		newUserSettings.setLanguage(getLangCode());
		newUserSettings.setWorkspacePath(workspaceField.getText());
		newUserSettings.setCsvSeparator(csvSeparator.getText());
		newUserSettings.setTooltipsEnabled(tooltipsEnabled.isSelected());
		newUserSettings.setAdvancedPropertiesEnabled(advancedPropertiesEnabled.isSelected());
		newUserSettings.setManualInfoEditionEnabled(manualEditionEnabled.isSelected());
		newUserSettings.setStartMaximized(startMaximized.isSelected());
	}
	
	/**
	 * Validate user settings if they are correct. <br>
	 * Application checks if workspace path is valid. <br>
	 * Application also checks if provided lang code is provided. <br>
	 * This prevent errors when read in userSettings.json file contains wrong options(like not existing language code)
	 * @see UserSettingsValidator
	 * @return true if settings are valid, false otherwise
	 */
	private boolean isSettingsFormValid() {
		String validationErrors = settingsValidator.validateUserSettingsForm(languagesChoice.getValue(), workspaceField.getText());
		if(not(validationErrors.isEmpty())) {
			String errorDialogHeader = labels.get(Labels.VALIDATION_DIALOG_HEADER);
			DialogsService.showValidationFailedDialog(errorDialogHeader, validationErrors);
			return false;
		}
		return true;
	}
	
	/**
	 * Initialize languages ComboBox. <br>
	 * It is filled with data from languages.json file.
	 */
	private void initializeLanguages() {
		languages = labels.getLanguages();
		languagesChoice.getItems().addAll(languages.values());
		languagesChoice.setValue(languages.get(newUserSettings.getLanguage()));
	}
	
	/**
	 * Get correct language code using chosen value from language ComboBox. <br>
	 * If languages contains: "ENG" : "English", <br>
	 * this method should extract value "ENG" using "English" option from ComboBox.
	 * @return String with proper lang code
	 */
	private String getLangCode() {
		String language = languagesChoice.getValue();
		
		Optional<String> langCode = languages.entrySet()
				.stream()
				.filter(entry -> Objects.equals(entry.getValue(), language))
				.map(Map.Entry::getKey)
				.findFirst();
		
		return langCode.orElse(null);
	}
	
}
