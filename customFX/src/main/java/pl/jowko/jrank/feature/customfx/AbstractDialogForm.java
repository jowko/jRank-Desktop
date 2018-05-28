package pl.jowko.jrank.feature.customfx;

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
		stage.showAndWait();
	}
	
}
