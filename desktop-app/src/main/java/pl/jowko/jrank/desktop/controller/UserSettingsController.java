package pl.jowko.jrank.desktop.controller;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Modality;
import pl.jowko.jrank.desktop.Main;
import pl.jowko.jrank.desktop.ResourceLoader;
import pl.jowko.jrank.logger.JRankLogger;

import java.io.IOException;

/**
 * Created by Piotr on 2018-03-17.
 */
public class UserSettingsController  {
	
	@FXML
	ButtonType saveButton;
	@FXML
	ButtonType cancelButton;
	@FXML
	Label languageText;
	@FXML
	ChoiceBox languagesChoice;
	
	public static void createWindow() throws IOException {
		Dialog dialog = new Dialog<>();
		Parent root = new ResourceLoader().load("/fxml/userSettings.fxml");
		dialog.setDialogPane((DialogPane) root);
		dialog.setTitle("User settings");
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.setResizable(false);
		dialog.initOwner(Main.getScene().getWindow());

		dialog.showAndWait().filter(button -> {
			ButtonType type = (ButtonType) button;
			return type.getButtonData().isDefaultButton();
		}).ifPresent(button -> {
			onSaveAction();
		});
	}
	
	@FXML
	public void initialize() {
		translateLabels();
	}
	
	private static void onSaveAction() {
		System.out.println("Saving options");
	}
	
	private void translateLabels() {
		//TODO translate labels here
	}
	
}
