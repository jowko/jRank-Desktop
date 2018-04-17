package pl.jowko.jrank.desktop.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pl.jowko.jrank.desktop.Main;
import pl.jowko.jrank.desktop.ResourceLoader;
import pl.jowko.jrank.desktop.service.LanguageService;
import pl.jowko.jrank.desktop.settings.Labels;

import java.io.IOException;

/**
 * Created by Piotr on 2018-03-17.
 */
public class MenuController {
	
	@FXML
	private Menu fileMenu;
	@FXML
	private MenuItem quitMenuItem;
	@FXML
	private Menu settingsMenu;
	@FXML
	private MenuItem userSettingsItem;
	@FXML
	private MenuItem jRankSettingsItem;
	@FXML
	private Menu helpMenu;
	@FXML
	private MenuItem helpMenuItem;
	@FXML
	private MenuItem aboutMenuItem;
	
	private LanguageService labels;
	
	@FXML
	public void initialize() {
		labels = LanguageService.getInstance();
		translateLabels();
	}
	
	public void onQuitAction() {
		Platform.exit();
	}
	
	public void onUserSettingsAction() throws IOException {
		Parent root = new ResourceLoader().load("/fxml/userSettings.fxml");
		
		Stage stage = new Stage(StageStyle.DECORATED);
		UserSettingsController.setStage(stage);
		
		stage.setScene(new Scene(root));
		stage.setTitle(labels.get(Labels.US_TITLE));
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
		fileMenu.setText(labels.get(Labels.MENU_FILE));
		quitMenuItem.setText(labels.get(Labels.MENU_QUIT));
		settingsMenu.setText(labels.get(Labels.MENU_SETTINGS));
		userSettingsItem.setText(labels.get(Labels.MENU_USER_SETTINGS));
		helpMenu.setText(labels.get(Labels.MENU_HELP));
		helpMenuItem.setText(labels.get(Labels.MENU_ITEM_HELP));
		aboutMenuItem.setText(labels.get(Labels.MENU_ABOUT));
	}
	
}
