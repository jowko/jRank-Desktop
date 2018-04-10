package pl.jowko.jrank.desktop.controller;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Modality;
import pl.jowko.jrank.desktop.Main;
import pl.jowko.jrank.desktop.ResourceLoader;

import java.io.IOException;

/**
 * Created by Piotr on 2018-04-10.
 */
public class AboutController {
	
	@FXML
	private Label header;
	@FXML
	private Label appInfo;
	@FXML
	private Label authorLabel;
	@FXML
	private Label versionLabel;
	@FXML
	private Label releaseDateLabel;
	@FXML
	private Label author;
	@FXML
	private Label version;
	@FXML
	private Label releaseDate;
	@FXML
	private Label bottomText;
	
	@FXML
	public void initialize() {
	
	}
	
	public static void createWindow() throws IOException {
		Dialog dialog = new Dialog<>();
		Parent root = new ResourceLoader().load("/fxml/about.fxml");
		dialog.setDialogPane((DialogPane) root);
		dialog.setTitle("About jRank Desktop Application");
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.setResizable(false);
		dialog.initOwner(Main.getScene().getWindow());
		dialog.getDialogPane().getButtonTypes().add(new ButtonType("OK", ButtonBar.ButtonData.CANCEL_CLOSE));
		dialog.showAndWait();
	}
	
}
