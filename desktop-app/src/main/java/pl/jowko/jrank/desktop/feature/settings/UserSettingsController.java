package pl.jowko.jrank.desktop.feature.settings;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pl.jowko.jrank.desktop.Main;
import pl.jowko.jrank.desktop.feature.internationalization.Labels;
import pl.jowko.jrank.desktop.feature.internationalization.LanguageService;
import pl.jowko.jrank.desktop.service.DialogsService;
import pl.jowko.jrank.logger.JRankLogger;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static pl.jowko.jrank.desktop.utils.BooleanUtils.not;

/**
 * Created by Piotr on 2018-03-17.
 * Controller for User settings dialog from settings menu.
 * It allows to customize all editable options of application.
 * Settings are saved in userSettings.json file in data directory, where rest of configuration files resides.
 * @see UserSettings
 * @see UserSettingsValidator
 */
public class UserSettingsController  {
	
	private Stage stage;
	
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
	CheckBox tooltipsEnabled;
	@FXML
	CheckBox advancedPropertiesEnabled;
	@FXML
	Label infoText;
	
	private static UserSettings newUserSettings;
	private Map<String, String> languages;
	private LanguageService labels;
	private UserSettingsValidator settingsValidator;
	
	/**
	 * Initialize needed objects.
	 * Also sets current value from settings into UI fields.
	 */
	@FXML
	private void initialize() {
		labels = LanguageService.getInstance();
		new UserSettingsTranslator(this).translateFields();
		initializeNewSettings();
		initializeLanguages();
		workspaceField.setText(newUserSettings.getWorkspacePath());
		tooltipsEnabled.setSelected(newUserSettings.isTooltipsEnabled());
		advancedPropertiesEnabled.setSelected(newUserSettings.isAdvancedPropertiesEnabled());
		settingsValidator = new UserSettingsValidator();
	}
	
	/**
	 * Creates dialog for user settings form.
	 * User settings content from fxml file should be passed to this method.
	 * @param root with content of dialog
	 */
	public void createWindow(Parent root) {
		stage = new Stage(StageStyle.DECORATED);
		stage.setScene(new Scene(root));
		stage.setTitle(labels.get(Labels.US_TITLE));
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setResizable(false);
		stage.initOwner(Main.getScene().getWindow());
		stage.showAndWait();
	}
	
	/**
	 * Perform save action.
	 * Settings are validated and if correct, they are saved to files.
	 * User settings are not reloaded in application after change.
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
			JRankLogger.error("Error when saving user options: ", e);
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
	 * Initialize user settings for form.
	 * Deep copy is performed to avoid changing settings with are already used in application.
	 * Also static reference is remembered, to remember options on form after save and return without application restart.
	 */
	private static void initializeNewSettings() {
		if(Objects.isNull(newUserSettings)) {
			UserSettings settings = UserSettingsService.getInstance().getUserSettings();
			
			newUserSettings = new UserSettingsBuilder()
					.setLanguage(settings.getLanguage())
					.setWorkspacePath(settings.getWorkspacePath())
					.setTooltipsEnabled(settings.isTooltipsEnabled())
					.setAdvancedPropertiesEnabled(settings.isAdvancedPropertiesEnabled())
					.createUserSettings();
		}
	}
	
	/**
	 * Update user settings with values extracted from UI form.
	 */
	private void updateNewSettings() {
		newUserSettings.setWorkspacePath(workspaceField.getText());
		newUserSettings.setLanguage(getLangCode());
		newUserSettings.setTooltipsEnabled(tooltipsEnabled.isSelected());
		newUserSettings.setAdvancedPropertiesEnabled(advancedPropertiesEnabled.isSelected());
	}
	
	/**
	 * Validate user settings if they are correct.
	 * Application checks if workspace path is valid.
	 * Application also checks if provided lang code is provided.
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
	 * Initialize languages ComboBox.
	 * It is filled with data from languages.json file.
	 */
	private void initializeLanguages() {
		languages = labels.getLanguages();
		languagesChoice.getItems().addAll(languages.values());
		languagesChoice.setValue(languages.get(newUserSettings.getLanguage()));
	}
	
	/**
	 * Get correct language code using chosen value from language ComboBox.
	 * If languages contains: "ENG" : "English",
	 * this method should extract value "ENG" using "English" option from ComboBox.
	 * @return
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
