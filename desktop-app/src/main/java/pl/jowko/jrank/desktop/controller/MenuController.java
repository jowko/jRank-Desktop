package pl.jowko.jrank.desktop.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;

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
	
	public void onUserSettingsAction(ActionEvent event) throws IOException {
		UserSettingsController.createWindow();
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
