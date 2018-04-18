package pl.jowko.jrank.desktop.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pl.jowko.jrank.desktop.service.DialogsService;
import pl.jowko.jrank.desktop.service.LanguageService;
import pl.jowko.jrank.desktop.service.SettingsService;
import pl.jowko.jrank.desktop.settings.Labels;
import pl.jowko.jrank.desktop.settings.UserSettings;
import pl.jowko.jrank.desktop.validator.UserSettingsValidator;
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
	
	private static Stage stage;
	
	@FXML
	Button saveButton;
	@FXML
	Button cancelButton;
	@FXML
	Label languageText;
	@FXML
	ChoiceBox languagesChoice;
	@FXML
	Label workspaceLabel;
	@FXML
	TextField workspaceField;
	@FXML
	Label infoText;
	
	private UserSettings settings;
	private Map<String, String> languages;
	private LanguageService labels;
	private UserSettingsValidator settingsValidator;
	
	@FXML
	private void initialize() {
		labels = LanguageService.getInstance();
		translateLabels();
		settings = SettingsService.getInstance().getUserSettings();
		initializeLanguages();
		workspaceField.setText(settings.getWorkspacePath());
		settingsValidator = new UserSettingsValidator();
	}
	
	public void onSaveAction() {
		if(not(settingsValidator.isUserSettingsFormValid(workspaceField.getText(), (String) languagesChoice.getValue()))) {
			return;
		}
		
		settings.setWorkspacePath(workspaceField.getText());
		settings.setLanguage(getLangCode());
		
		try {
			SettingsService.getInstance().saveUserSettings(settings);
			onCancelAction();
		} catch (IOException e) {
			JRankLogger.error("Error when saving user options: ", e);
			String errorDialogHeader = labels.get(Labels.US_ERROR_DIALOG_HEADER);
			new DialogsService().showErrorDialog(errorDialogHeader, e.getMessage());
		}
		
	}
	
	public void onCancelAction() {
		stage.close();
	}
	
	public static void setStage(Stage stage) {
		UserSettingsController.stage = stage;
	}
	
	private void translateLabels() {
		saveButton.setText(labels.get(Labels.BUTTON_SAVE));
		cancelButton.setText(labels.get(Labels.BUTTON_CANCEL));
		languageText.setText(labels.get(Labels.LANGUAGE));
		workspaceLabel.setText(labels.get(Labels.WORKSPACE));
		infoText.setText(labels.get(Labels.US_INFO));
	}
	
	private void initializeLanguages() {
		languages = labels.getLanguages();
		languagesChoice.getItems().addAll(languages.values());
		languagesChoice.setValue(languages.get(settings.getLanguage()));
	}
	
	private String getLangCode() {
		String language = languagesChoice.getValue().toString();
		
		Optional<String> langCode = languages.entrySet()
				.stream()
				.filter(entry -> Objects.equals(entry.getValue(), language))
				.map(Map.Entry::getKey)
				.findFirst();
		if(langCode.isPresent()) {
			return langCode.get();
		}
		return null;
		
	}
	
}
