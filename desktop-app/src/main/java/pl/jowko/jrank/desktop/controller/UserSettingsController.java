package pl.jowko.jrank.desktop.controller;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pl.jowko.jrank.desktop.ResourceLoader;
import pl.jowko.jrank.logger.JRankLogger;

import java.io.IOException;

/**
 * Created by Piotr on 2018-03-17.
 */
public class UserSettingsController  {
	
	private static Stage stage;
	
	@FXML
	Label languageText;
	@FXML
	ChoiceBox languagesChoice;
	
	@FXML
	Button saveButton;
	@FXML
	Button cancelButton;
	
	public static void createWindow() throws IOException {
		stage = new Stage();
		Parent root = new ResourceLoader().load("fxml/userSettings.fxml");
		
		stage.setScene(new Scene(root));
		stage.setTitle("My modal window");
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setResizable(false);
		stage.setOnCloseRequest(event -> JRankLogger.closed(UserSettingsController.class.getSimpleName()));
		stage.show();
	}
	
	@FXML
	public void initialize() {
		JRankLogger.initialized(getClass().getSimpleName());
		translateLabels();
	}
	
	public void onSaveAction() {
		onCancelAction();
	}
	
	public void onCancelAction() {
		stage.close();
		JRankLogger.closed(getClass().getSimpleName());
	}
	
	private void translateLabels() {
		//TODO translate labels here
	}
	
}
