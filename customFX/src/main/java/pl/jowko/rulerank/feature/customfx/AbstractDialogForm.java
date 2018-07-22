package pl.jowko.rulerank.feature.customfx;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Created by Piotr on 2018-05-28
 * This class provides functionality common for all Dialog form windows.
 */
public abstract class AbstractDialogForm {
	
	protected Stage stage;
	
	/**
	 * Creates dialog using provided content from fxml file.
	 * @param root with content of dialog
	 * @param scene with is used to set owner
	 * @param title to display on title bar
	 */
	public void createWindow(Parent root, Scene scene, String title) {
		stage = new Stage(StageStyle.DECORATED);
		stage.setScene(new Scene(root));
		stage.setTitle(title);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setResizable(false);
		stage.initOwner(scene.getWindow());
		initializeCloseEvent();
		stage.showAndWait();
	}
	
	/**
	 * Closes current dialog without asking
	 */
	protected void closeDialog() {
		stage.close();
	}
	
	/**
	 * Checks, if user want to keep changes on close.
	 * This should be Overridden by upper class.
	 * @return false because by default this feature is disabled
	 */
	protected boolean isUserWishToKeepChanges() {
		return false;
	}
	
	/**
	 * Initializes event onCloseRequest.
	 * When user tries to close window, it will be asked, if he want to keep changes.
	 * isUserWishToKeepChanges must be overridden by upper class to enable this feature.
	 */
	private void initializeCloseEvent() {
		stage.setOnCloseRequest(event -> {
			if(isUserWishToKeepChanges())
				event.consume();
		});
	}
	
}
