package pl.jowko.jrank.desktop.service;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import pl.jowko.jrank.desktop.settings.Labels;

import static pl.jowko.jrank.desktop.settings.Labels.CONFIRM_DIALOG_NO;
import static pl.jowko.jrank.desktop.settings.Labels.CONFIRM_DIALOG_TITLE;
import static pl.jowko.jrank.desktop.settings.Labels.CONFIRM_DIALOG_YES;

/**
 * Created by Piotr on 2018-04-18.
 */
public class DialogsService {
	
	private LanguageService labels;
	
	public DialogsService() {
		labels = LanguageService.getInstance();
	}
	
	public void showErrorDialog(String header, String msg) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle(labels.get(Labels.ERROR_DIALOG_TITLE));
		alert.setHeaderText(header);
		alert.setContentText(msg);
		alert.showAndWait();
	}
	
	public void showInfoDialog(String title, String msg) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText("");
		alert.setContentText(msg);
		alert.showAndWait();
	}
	
	public void showValidationFailedDialog(String header, String msg) {
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle(labels.get(Labels.VALIDATION_DIALOG_TITLE));
		alert.setHeaderText(header);
		alert.setContentText(msg);
		
		alert.showAndWait();
	}
	
	public boolean showConfirmationDialog(String header) {
		return showConfirmationDialog(header, "");
	}
	
	public boolean showConfirmationDialog(String header, String content) {
		ButtonType yesButton = new ButtonType(labels.get(CONFIRM_DIALOG_YES), ButtonBar.ButtonData.YES);
		ButtonType noButton = new ButtonType(labels.get(CONFIRM_DIALOG_NO), ButtonBar.ButtonData.NO);
		
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", yesButton, noButton);
		alert.setTitle(labels.get(CONFIRM_DIALOG_TITLE));
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
		
		return alert.getResult() == yesButton;
	}
	
}
