package pl.jowko.jrank.desktop.controller;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import pl.jowko.jrank.desktop.Main;
import pl.jowko.jrank.desktop.ResourceLoader;
import pl.jowko.jrank.desktop.service.JRankInfoService;
import pl.jowko.jrank.desktop.settings.JRankInfo;

import java.io.IOException;

/**
 * Created by Piotr on 2018-04-10.
 */
public class AboutController {
	
	@FXML
	Label appInfo;
	@FXML
	Label authorLabel;
	@FXML
	Label versionLabel;
	@FXML
	Label releaseDateLabel;
	@FXML
	Label author;
	@FXML
	Label version;
	@FXML
	Label releaseDate;
	@FXML
	Label bottomText;
	@FXML
	ButtonType cancelButton;
	
	public static void createWindow() throws IOException {
		Dialog dialog = new Dialog<>();
		Parent root = new ResourceLoader().load("/fxml/about.fxml");
		dialog.setDialogPane((DialogPane) root);
		dialog.setTitle("About jRank Desktop Application");
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.setResizable(false);
		dialog.initOwner(Main.getScene().getWindow());
		
		dialog.showAndWait();
	}
	
	@FXML
	private void initialize() {
		JRankInfo info = JRankInfoService.getInstance().getInfo();
		version.setText(info.getVersion());
		releaseDate.setText(info.getReleaseDate());
	}
	
}
