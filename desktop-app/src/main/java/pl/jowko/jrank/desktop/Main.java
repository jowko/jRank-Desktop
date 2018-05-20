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
 */
public class Main extends Application {
	
	private static Scene scene;
	
	private LanguageService labels;
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		Parent root = new ResourceLoader("/fxml/root.fxml").load();
		
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
	
	public static Scene getScene() {
		return scene;
	}
	
}