package pl.jowko.rulerank.desktop;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import pl.jowko.rulerank.desktop.exception.ConfigurationException;
import pl.jowko.rulerank.desktop.feature.internationalization.Labels;
import pl.jowko.rulerank.desktop.feature.internationalization.LanguageService;
import pl.jowko.rulerank.desktop.feature.settings.ConfigurationInitializer;
import pl.jowko.rulerank.desktop.feature.settings.UserSettingsService;
import pl.jowko.rulerank.desktop.feature.tabs.TabEditionChecker;
import pl.jowko.rulerank.desktop.feature.tabs.upper.UpperTabsController;
import pl.jowko.rulerank.logger.RuleRankLogger;

import java.io.IOException;
import java.util.List;

import static pl.jowko.rulerank.desktop.feature.settings.RuleRankConst.COMMON_CSS;
import static pl.jowko.rulerank.desktop.feature.settings.RuleRankConst.MIN_HEIGHT;
import static pl.jowko.rulerank.desktop.feature.settings.RuleRankConst.MIN_WIDTH;
import static pl.jowko.rulerank.desktop.utils.BooleanUtils.not;

/**
 * Main class with contains static main method and extends JavaFX Application class.<br>
 * It reads all configuration files before UI start.<br>
 * If some files from configuration are not available or corrupted, UI application doesn't start and application closes with error.<br>
 * <br>
 * Created by Piotr on 2018-03-16.
 * @see Application
 * @see ConfigurationInitializer
 * @see ConfigurationException
 */
public class Main extends Application {
	
	private static Scene scene;
	private static Stage stage;
	private static HostServices services;
	
	private LanguageService labels;
	
	/**
	 * This method is started by JavaFX thread to build UI application. <br>
	 * Methods is called after successful validation of configuration files. <br>
	 * It creates root element and add css file to it. <br>
	 * It also configures main window and show it.
	 * @param primaryStage on with UI will be build, provided by JavaFX
	 * @throws IOException when something goes wrong with reading fxml files.
	 */
	@Override
	public void start(Stage primaryStage) throws IOException {
		Parent root = new ResourceLoader("/fxml/root.fxml").load();
		root.getStylesheets().add(COMMON_CSS);
		
		scene = new Scene(root, MIN_WIDTH, MIN_HEIGHT);
		stage = primaryStage;
		services = getHostServices();
		labels = LanguageService.getInstance();
		
		primaryStage.setTitle(labels.get(Labels.APP_TITLE));
		primaryStage.setScene(scene);
		primaryStage.setMinWidth(MIN_WIDTH);
		primaryStage.setMinHeight(MIN_HEIGHT);
		setOnCloseEvent(primaryStage);
		
		boolean isMaximized = UserSettingsService.getInstance().getUserSettings().isStartMaximized();
		primaryStage.setMaximized(isMaximized);
		
		primaryStage.show();
		RuleRankLogger.init("Application started");
	}
	
	/**
	 * Checks, if config files are valid. <br>
	 * If they are valid, applications start UI thread. <br>
	 * If not, application closes displaying errors.
	 * @see ConfigurationInitializer
	 * @see ConfigurationException
	 * @param args are not used in this application
	 */
	public static void main(String[] args) {
		RuleRankLogger.init("Starting RuleRank Desktop Application");
		
		try {
			new ConfigurationInitializer().initialize();
		} catch (ConfigurationException e) {
			RuleRankLogger.error(e.getMessage());
			Platform.exit();
			return;
		}
		
		launch(args);
		RuleRankLogger.info("Application closed");
	}
	
	/**
	 * Gets scene associated with root window. <br>
	 * Some say, that it is a good practice to attach DialogPane to some window. <br>
	 * This methods provides Scene object for such case.
	 * @return Scene from main window
	 */
	public static Scene getScene() {
		return scene;
	}
	
	/**
	 * @return services with can be used to open browser
	 */
	public static HostServices getServices() {
		return services;
	}
	
	/**
	 * Close application properly. <br>
	 * It will fire onCloseRequest with will close application.
	 */
	public static void closeApplication() {
		stage.fireEvent(
				new WindowEvent(
						stage,
						WindowEvent.WINDOW_CLOSE_REQUEST
				)
		);
	}
	
	/**
	 * Initialize handler for onCloseRequest event. <br>
	 * It will check if any tabs were edited. <br>
	 * If at least one tab was edited, applications ask for confirmation to close app. <br>
	 * If user confirms close action or no tabs were edited, application will be closed. <br>
	 * If user didn't confirmed close action, nothing happens.
	 * @param primaryStage on with UI will be build, provided by JavaFX
	 * @see TabEditionChecker
	 */
	private void setOnCloseEvent(Stage primaryStage) {
		TabEditionChecker editionChecker = new TabEditionChecker();
		primaryStage.setOnCloseRequest(we -> {
			List<Tab> tabs = UpperTabsController.getInstance().getUpperTabs().getTabs();
			boolean areTabsEdited = editionChecker.areTabsInEditMode(tabs);
			if(not(editionChecker.shouldPerformAction(areTabsEdited, Labels.TABS_CLOSE_ALL_CONFIRM))) {
				we.consume();
			}
		});
	}
	
}