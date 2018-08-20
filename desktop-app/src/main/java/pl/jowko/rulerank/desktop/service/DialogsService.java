package pl.jowko.rulerank.desktop.service;

import javafx.scene.control.*;
import javafx.scene.layout.Region;
import pl.jowko.rulerank.desktop.feature.internationalization.Labels;
import pl.jowko.rulerank.desktop.feature.internationalization.LanguageService;

import static pl.jowko.rulerank.desktop.feature.internationalization.Labels.*;

/**
 * This class provides api for creating alert dialogs.<br>
 * <br>
 * Created by Piotr on 2018-04-18.
 */
public class DialogsService {
	
	private static LanguageService labels;
	
	static {
		labels = LanguageService.getInstance();
	}
	
	private DialogsService() {}
	
	/**
	 * Show default error dialog.
	 * @param header to display in header field
	 * @param msg to display as alert content
	 */
	public static void showErrorDialog(String header, String msg) {
		LanguageService labels = LanguageService.getInstance();
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle(labels.get(Labels.ERROR_DIALOG_TITLE));
		alert.setHeaderText(header);
		alert.setContentText(msg);
		resizeDialogPane(alert.getDialogPane());
		alert.showAndWait();
	}
	
	/**
	 * Show default information dialog.
	 * @param title to display in title field
	 * @param msg to display as alert content text
	 */
	public static void showInfoDialog(String title, String msg) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText("");
		alert.setContentText(msg);
		resizeDialogPane(alert.getDialogPane());
		alert.showAndWait();
	}
	
	/**
	 * Show dialog with information, that application failed to perform requested action.
	 * @param msg to display to user
	 */
	public static void showActionFailedDialog(String msg) {
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle(labels.get(Labels.DIALOG_ACTION_FAIL));
		alert.setHeaderText("");
		alert.setContentText(msg);
		resizeDialogPane(alert.getDialogPane());
		alert.showAndWait();
	}
	
	/**
	 * Show warning dialog for cases where validation errors are displayed.
	 * @param header to display in header field
	 * @param msg to display as alert content text
	 */
	public static void showValidationFailedDialog(String header, String msg) {
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle(labels.get(Labels.VALIDATION_DIALOG_TITLE));
		alert.setHeaderText(header);
		alert.setContentText(msg);
		resizeDialogPane(alert.getDialogPane());
		
		alert.showAndWait();
	}
	
	/**
	 * Show confirmation dialog with empty content.
	 * @param header with be displayed in header field
	 * @return true if user clicks Yes button, false otherwise
	 */
	public static boolean showConfirmationDialog(String header) {
		return showConfirmationDialog(header, "");
	}
	
	/**
	 * Show default confirmation dialog. <br>
	 * No button is focused as default.
	 * @param header to display on header field
	 * @param content to display as text content
	 * @return true if user clicks Yes button, false otherwise
	 */
	public static boolean showConfirmationDialog(String header, String content) {
		LanguageService labels = LanguageService.getInstance();
		ButtonType yesButtonType = new ButtonType(labels.get(CONFIRM_DIALOG_YES), ButtonBar.ButtonData.YES);
		ButtonType noButtonType = new ButtonType(labels.get(CONFIRM_DIALOG_NO), ButtonBar.ButtonData.NO);
		
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", yesButtonType, noButtonType);
		alert.setTitle(labels.get(CONFIRM_DIALOG_TITLE));
		alert.setHeaderText(header);
		alert.setContentText(content);
		resizeDialogPane(alert.getDialogPane());
		
		// change default button to No
		Button yesButton = (Button) alert.getDialogPane().lookupButton(yesButtonType);
		yesButton.setDefaultButton(false);
		Button noButton = (Button) alert.getDialogPane().lookupButton(noButtonType);
		noButton.setDefaultButton(true);
		
		alert.showAndWait();
		
		return alert.getResult() == yesButtonType;
	}
	
	/**
	 * This methods fixes bug with size of alert on some computers and Operating Systems. <br>
	 * See: https://bugs.openjdk.java.net/browse/JDK-8087981
	 * @param dialogPane to resize
	 */
	public static void resizeDialogPane(DialogPane dialogPane) {
		dialogPane.setMinHeight(Region.USE_PREF_SIZE);
	}
	
}
