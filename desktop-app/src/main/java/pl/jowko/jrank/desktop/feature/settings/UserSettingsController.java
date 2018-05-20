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
	
	@FXML
	private void initialize() {
		labels = LanguageService.getInstance();
		translateLabels();
		initializeNewSettings();
		initializeLanguages();
		workspaceField.setText(newUserSettings.getWorkspacePath());
		tooltipsEnabled.setSelected(newUserSettings.isTooltipsEnabled());
		advancedPropertiesEnabled.setSelected(newUserSettings.isAdvancedPropertiesEnabled());
		settingsValidator = new UserSettingsValidator();
	}
	
	public void createWindow(Parent root) {
		stage = new Stage(StageStyle.DECORATED);
		stage.setScene(new Scene(root));
		stage.setTitle(labels.get(Labels.US_TITLE));
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setResizable(false);
		stage.initOwner(Main.getScene().getWindow());
		stage.showAndWait();
	}
	
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
	
	public void onCancelAction() {
		stage.close();
	}
	
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
	
	private void updateNewSettings() {
		newUserSettings.setWorkspacePath(workspaceField.getText());
		newUserSettings.setLanguage(getLangCode());
		newUserSettings.setTooltipsEnabled(tooltipsEnabled.isSelected());
		newUserSettings.setAdvancedPropertiesEnabled(advancedPropertiesEnabled.isSelected());
	}
	
	private boolean isSettingsFormValid() {
		String validationErrors = settingsValidator.validateUserSettingsForm(languagesChoice.getValue(), workspaceField.getText());
		if(not(validationErrors.isEmpty())) {
			String errorDialogHeader = labels.get(Labels.VALIDATION_DIALOG_HEADER);
			DialogsService.showValidationFailedDialog(errorDialogHeader, validationErrors);
			return false;
		}
		return true;
	}
	
	private void translateLabels() {
		saveButton.setText(labels.get(Labels.BUTTON_SAVE));
		cancelButton.setText(labels.get(Labels.BUTTON_CANCEL));
		languageText.setText(labels.get(Labels.LANGUAGE));
		workspaceLabel.setText(labels.get(Labels.WORKSPACE));
		tooltipsEnabled.setText(labels.get(Labels.TOOLTIPS_ENABLED));
		advancedPropertiesEnabled.setText(labels.get(Labels.ADVANCED_PROPERTIES_ENABLED));
		advancedPropertiesEnabled.setTooltip(new Tooltip(labels.get(Labels.ADVANCED_PROPERTIES_ENABLED_TOOLTIP)));
		infoText.setText(labels.get(Labels.US_INFO));
	}
	
	private void initializeLanguages() {
		languages = labels.getLanguages();
		languagesChoice.getItems().addAll(languages.values());
		languagesChoice.setValue(languages.get(newUserSettings.getLanguage()));
	}
	
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
