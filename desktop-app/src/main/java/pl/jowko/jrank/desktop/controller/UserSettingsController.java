package pl.jowko.jrank.desktop.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import pl.jowko.jrank.desktop.service.LanguageService;
import pl.jowko.jrank.desktop.service.SettingsService;
import pl.jowko.jrank.desktop.settings.UserSettings;
import pl.jowko.jrank.logger.JRankLogger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

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
	
	private UserSettings settings;
	private Map<String, String> languages;
	
	@FXML
	private void initialize() {
		translateLabels();
		settings = SettingsService.getInstance().getUserSettings();
		initializeLanguages();
		workspaceField.setText(settings.getWorkspacePath());
		
	}
	
	public void onSaveAction() {
		if(!isFormValid()) {
			return;
		}
		
		settings.setWorkspacePath(workspaceField.getText());
		settings.setLanguage(getLangCode());
		
		try {
			SettingsService.getInstance().saveUserSettings(settings);
			onCancelAction();
		} catch (IOException e) {
			JRankLogger.error("Error when saving user options: ", e);
			showErrorDialog(e.getMessage());
		}
		
	}
	
	public void onCancelAction() {
		stage.close();
	}
	
	public static void setStage(Stage stage) {
		UserSettingsController.stage = stage;
	}
	
	private void translateLabels() {
		//TODO translate labels here
	}
	
	private boolean isFormValid() {
		String errorMsg = "";
		String workspacePath = workspaceField.getText();
		if(!Paths.get(workspacePath).isAbsolute()) {
			workspacePath =  new File("").getAbsolutePath() + workspacePath;
		}
		
		File f = new File(workspacePath);
		
		if(!f.exists() || !f.isDirectory()) {
			errorMsg += "Provided path: " + workspacePath + " is not correct\n";
		}
		
		if(Objects.isNull(languagesChoice.getValue())) {
			errorMsg += "Language code is not valid. Please choose language.\n";
		}
		
		if(errorMsg.length() == 0) {
			return true;
		}
		showValidationFailedDialog(errorMsg);
		
		return false;
	}
	
	private void showErrorDialog(String msg) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Unexpected error");
		alert.setHeaderText("Error when saving user options: ");
		alert.setContentText(msg);
		alert.showAndWait();
	}
	
	private void showValidationFailedDialog(String msg) {
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle("Validation Fail");
		alert.setHeaderText("There were some validation errors: ");
		alert.setContentText(msg);
		
		alert.showAndWait();
	}
	
	private void initializeLanguages() {
		languages = LanguageService.getInstance().getLanguages();
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
