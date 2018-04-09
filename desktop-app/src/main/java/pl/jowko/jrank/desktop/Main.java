package pl.jowko.jrank.desktop;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.jowko.jrank.desktop.settings.SingletonInitializer;
import pl.jowko.jrank.logger.JRankLogger;

import java.io.IOException;

/**
 * Created by Piotr on 2018-03-16.
 */
public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		Parent root = new ResourceLoader().load("/fxml/root.fxml");
		
		Scene scene = new Scene(root, 1366, 768);
		
		primaryStage.setTitle("jRank Desktop Application");
		primaryStage.setScene(scene);
		//primaryStage.setMaximized(true);
		
		primaryStage.show();
		JRankLogger.init("Application started");
	}
	
	public static void main(String[] args) {
		JRankLogger.init("Starting jRank Desktop Application");
		SingletonInitializer.initialize();
		launch(args);
		JRankLogger.info("Application closed");
	}
	
}