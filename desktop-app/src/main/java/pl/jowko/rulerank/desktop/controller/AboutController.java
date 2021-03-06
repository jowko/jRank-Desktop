package pl.jowko.rulerank.desktop.controller;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import pl.jowko.rulerank.desktop.Main;
import pl.jowko.rulerank.desktop.feature.internationalization.Labels;
import pl.jowko.rulerank.desktop.feature.internationalization.LanguageService;
import pl.jowko.rulerank.desktop.feature.settings.RuleRankInfo;
import pl.jowko.rulerank.desktop.service.DialogsService;
import pl.jowko.rulerank.desktop.service.RuleRankInfoService;

/**
 * Controller for about dialog with contains author and version information about application.<br>
 * <br>
 * Created by Piotr on 2018-04-10.
 * @see RuleRankInfo
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
	
	private LanguageService labels;
	
	/**
	 * Create modal dialog window displaying about application information.
	 * @param root containing content of dialog
	 */
	void createWindow(Parent root) {
		Dialog dialog = new Dialog<>();
		dialog.setDialogPane((DialogPane) root);
		dialog.setTitle(labels.get(Labels.ABOUT_TITLE));
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.setResizable(false);
		dialog.initOwner(Main.getScene().getWindow());
		DialogsService.resizeDialogPane(dialog.getDialogPane());
		
		dialog.showAndWait();
	}
	
	@FXML
	private void initialize() {
		labels = LanguageService.getInstance();
		translateLabels();
		
		RuleRankInfo info = RuleRankInfoService.getInstance().getInfo();
		version.setText(info.getVersion());
		releaseDate.setText(info.getReleaseDate());
	}
	
	private void translateLabels() {
		appInfo.setText(labels.get(Labels.ABOUT_APP_INFO));
		authorLabel.setText(labels.get(Labels.ABOUT_AUTHOR));
		versionLabel.setText(labels.get(Labels.ABOUT_VERSION));
		releaseDateLabel.setText(labels.get(Labels.ABOUT_RELEASE_DATE));
		bottomText.setText(labels.get(Labels.ABOUT_BOTTOM_INFO));
	}
	
}
