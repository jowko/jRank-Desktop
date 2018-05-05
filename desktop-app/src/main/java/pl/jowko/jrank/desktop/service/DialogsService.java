package pl.jowko.jrank.desktop.service;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import pl.jowko.jrank.desktop.settings.Labels;

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
	
	public void showValidationFailedDialog(String header, String msg) {
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle(labels.get(Labels.VALIDATION_DIALOG_TITLE));
		alert.setHeaderText(header);
		alert.setContentText(msg);
		
		alert.showAndWait();
	}
	
	public boolean showConfirmationDialog(String header) {
		ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES); //TODO make labels
		ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
		
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", yesButton, noButton);
		alert.setTitle("Confirm action");
		alert.setHeaderText(header);
		alert.showAndWait();
		
		return alert.getResult() == yesButton;
	}
	
}
