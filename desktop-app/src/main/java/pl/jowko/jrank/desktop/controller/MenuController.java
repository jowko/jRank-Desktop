package pl.jowko.jrank.desktop.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pl.jowko.jrank.desktop.Main;
import pl.jowko.jrank.desktop.ResourceLoader;

import java.io.IOException;

/**
 * Created by Piotr on 2018-03-17.
 */
public class MenuController {
	
	@FXML
	private Button button;
	
	@FXML
	private MenuItem quitMenu;
	@FXML
	private MenuItem userSettings;
	@FXML
	private MenuItem jRankSettings;
	@FXML
	private MenuItem helpMenu;
	@FXML
	private MenuItem aboutMenu;
	
	@FXML
	public void initialize() {
		System.out.println("second");
		translateLabels();
	}
	
	public void onButtonAction() {
		System.out.println("button");
		button.setText("Przycisk");
	}
	
	public void onQuitAction() {
		Platform.exit();
	}
	
	public void onUserSettingsAction() throws IOException {
		Parent root = new ResourceLoader().load("/fxml/userSettings.fxml");
		
		Stage stage = new Stage(StageStyle.DECORATED);
		UserSettingsController.setStage(stage);
		
		stage.setScene(new Scene(root));
		stage.setTitle("User settings");
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setResizable(false);
		stage.initOwner(Main.getScene().getWindow());
		stage.showAndWait();
	}
	
	public void onJRankSettingsAction() {
		System.out.println("Jrank opcje");
	}
	
	public void onHelpAction() {
		System.out.println("Help");
	}
	
	public void onAboutAction() throws IOException {
		AboutController.createWindow();
	}
	
	private void translateLabels() {
		//TODO translate labels here
	}
	
}
