package pl.jowko.jrank.desktop.service;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import pl.jowko.jrank.desktop.feature.settings.Labels;

import static pl.jowko.jrank.desktop.feature.settings.Labels.*;

/**
 * Created by Piotr on 2018-04-18.
 */
public class DialogsService {
	
	private DialogsService() {}
	
	public static void showErrorDialog(String header, String msg) {
		LanguageService labels = LanguageService.getInstance();
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle(labels.get(Labels.ERROR_DIALOG_TITLE));
		alert.setHeaderText(header);
		alert.setContentText(msg);
		alert.showAndWait();
	}
	
	public static void showInfoDialog(String title, String msg) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText("");
		alert.setContentText(msg);
		alert.showAndWait();
	}
	
	public static void showValidationFailedDialog(String header, String msg) {
		LanguageService labels = LanguageService.getInstance();
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle(labels.get(Labels.VALIDATION_DIALOG_TITLE));
		alert.setHeaderText(header);
		alert.setContentText(msg);
		
		alert.showAndWait();
	}
	
	public static boolean showConfirmationDialog(String header) {
		return showConfirmationDialog(header, "");
	}
	
	public static boolean showConfirmationDialog(String header, String content) {
		LanguageService labels = LanguageService.getInstance();
		ButtonType yesButtonType = new ButtonType(labels.get(CONFIRM_DIALOG_YES), ButtonBar.ButtonData.YES);
		ButtonType noButtonType = new ButtonType(labels.get(CONFIRM_DIALOG_NO), ButtonBar.ButtonData.NO);
		
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", yesButtonType, noButtonType);
		alert.setTitle(labels.get(CONFIRM_DIALOG_TITLE));
		alert.setHeaderText(header);
		alert.setContentText(content);
		
		// change default button to No
		Button yesButton = (Button) alert.getDialogPane().lookupButton(yesButtonType);
		yesButton.setDefaultButton(false);
		Button noButton = (Button) alert.getDialogPane().lookupButton(noButtonType);
		noButton.setDefaultButton(true);
		
		alert.showAndWait();
		
		return alert.getResult() == yesButtonType;
	}
	
}
