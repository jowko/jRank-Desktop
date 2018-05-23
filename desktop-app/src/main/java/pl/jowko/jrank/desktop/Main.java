package pl.jowko.jrank.desktop;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.jowko.jrank.desktop.exception.ConfigurationException;
import pl.jowko.jrank.desktop.feature.internationalization.Labels;
import pl.jowko.jrank.desktop.feature.internationalization.LanguageService;
import pl.jowko.jrank.desktop.feature.settings.ConfigurationInitializer;
import pl.jowko.jrank.logger.JRankLogger;

import java.io.IOException;

import static pl.jowko.jrank.desktop.feature.settings.JRankConst.MIN_HEIGHT;
import static pl.jowko.jrank.desktop.feature.settings.JRankConst.MIN_WIDTH;

/**
 * Created by Piotr on 2018-03-16.
 * Main class with contains static main method and extends JavaFX Application class.
 * It reads all configuration files before UI start.
 * If some files from configuration are not available or corrupted, UI application doesn't start and application closes with error.
 * @see Application
 * @see ConfigurationInitializer
 * @see ConfigurationException
 */
public class Main extends Application {
	
	private static Scene scene;
	
	private LanguageService labels;
	
	/**
	 * This method is started by JavaFX thread to build UI application.
	 * Methods is called after successful validation of configuration files.
	 * It creates root element and add common.css to it.
	 * It also configures main window and show it.
	 * @param primaryStage on with UI will be build, provided by JavaFX
	 * @throws IOException when something goes wrong with reading fxml files.
	 */
	@Override
	public void start(Stage primaryStage) throws IOException {
		Parent root = new ResourceLoader("/fxml/root.fxml").load();
		root.getStylesheets().add("common.css");
		
		scene = new Scene(root, MIN_WIDTH, MIN_HEIGHT);
		labels = LanguageService.getInstance();
		
		primaryStage.setTitle(labels.get(Labels.APP_TITLE));
		primaryStage.setScene(scene);
		primaryStage.setMinWidth(MIN_WIDTH);
		primaryStage.setMinHeight(MIN_HEIGHT);
		//primaryStage.setMaximized(true);
		
		primaryStage.show();
		JRankLogger.init("Application started");
	}
	
	/**
	 * Checks, if config files are valid.
	 * If they are valid, applications start UI thread.
	 * If not, application closes displaying errors.
	 * @see ConfigurationInitializer
	 * @see ConfigurationException
	 * @param args are not used in this application
	 */
	public static void main(String[] args) {
		JRankLogger.init("Starting jRank Desktop Application");
		
		try {
			new ConfigurationInitializer().initialize();
		} catch (ConfigurationException e) {
			JRankLogger.error(e.getMessage());
			Platform.exit();
			return;
		}
		
		launch(args);
		JRankLogger.info("Application closed");
	}
	
	/**
	 * Gets scene associated with root window.
	 * Some say, that it is a good practice to attach DialogPane to some window.
	 * This methods provides Scene object for such case.
	 * @return Scene from main window
	 */
	public static Scene getScene() {
		return scene;
	}
	
}