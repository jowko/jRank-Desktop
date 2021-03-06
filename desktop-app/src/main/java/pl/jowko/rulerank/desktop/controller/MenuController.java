package pl.jowko.rulerank.desktop.controller;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import pl.jowko.rulerank.desktop.Main;
import pl.jowko.rulerank.desktop.ResourceLoader;
import pl.jowko.rulerank.desktop.feature.help.HelpController;
import pl.jowko.rulerank.desktop.feature.internationalization.Labels;
import pl.jowko.rulerank.desktop.feature.internationalization.LanguageService;
import pl.jowko.rulerank.desktop.feature.settings.UserSettingsController;

import java.io.IOException;

import static pl.jowko.rulerank.desktop.feature.settings.RuleRankConst.COMMON_CSS;

/**
 * Controller for main menu bar. It handles actions after menu item click.<br>
 * <br>
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
	
	/**
	 * Closes application in proper way.
	 */
	public void onQuitAction() {
		Main.closeApplication();
	}
	
	/**
	 * Displays user settings modal dialog, on with user can edit own settings. <br>
	 * It loads fxml file and initializes all necessary data
	 * @throws IOException when something goes wrong
	 */
	public void onUserSettingsAction() throws IOException {
		ResourceLoader loader = new ResourceLoader("/fxml/userSettings.fxml");
		Parent root = loader.load();
		UserSettingsController controller = loader.getController();
		controller.createWindow(root, Main.getScene(), labels.get(Labels.US_TITLE));
	}
	
	public void onHelpAction() throws IOException {
		ResourceLoader loader = new ResourceLoader("/fxml/helpInfo.fxml");
		Parent root = loader.load();
		root.getStylesheets().add(COMMON_CSS);
		HelpController controller = loader.getController();
		controller.createWindow(root, Main.getScene(), labels.get(Labels.HELP_TITLE));
	}
	
	/**
	 * Displays user about modal dialog, with displays information about author and version of app. <br>
	 * It loads fxml file and initializes all necessary data
	 * @throws IOException when something goes wrong
	 */
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
