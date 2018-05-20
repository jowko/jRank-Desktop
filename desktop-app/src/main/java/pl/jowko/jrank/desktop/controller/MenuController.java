package pl.jowko.jrank.desktop.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import pl.jowko.jrank.desktop.ResourceLoader;
import pl.jowko.jrank.desktop.feature.internationalization.Labels;
import pl.jowko.jrank.desktop.feature.internationalization.LanguageService;
import pl.jowko.jrank.desktop.feature.settings.UserSettingsController;
import pl.jowko.jrank.logger.JRankLogger;

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
		ResourceLoader loader = new ResourceLoader("/fxml/userSettings.fxml");
		Parent root = loader.load();
		UserSettingsController controller = loader.getController();
		controller.createWindow(root);
	}
	
	public void onHelpAction() {
		JRankLogger.info("Help window is not implemented yet.");
	}
	
	public void onAboutAction() throws IOException {
		ResourceLoader loader = new ResourceLoader("/fxml/about.fxml");
		Parent root = loader.load();
		AboutController controller = loader.getController();
		controller.createWindow(root);
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
