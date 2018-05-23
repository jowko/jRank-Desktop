package pl.jowko.jrank.desktop;

import javafx.fxml.FXMLLoader;

import java.io.IOException;

/**
 * Created by Piotr on 2018-03-25.
 * This class serves as mediator between reading JavaFX files and application.
 */
public class ResourceLoader {
	
	private FXMLLoader fxmlLoader;
	
	/**
	 * Initialize instance of this class.
	 * Resource location will be set in this constructor.
	 * @param resourceName to load from resource catalog. Should be fxml file.
	 */
	public ResourceLoader(String resourceName) {
		fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource(resourceName));
	}
	
	/**
	 * Load fxml file from provided location.
	 * It will return Parent object containing content of fxml file.
	 * @see javafx.scene.Parent
	 * @return Parent object with content of fxml file
	 * @throws IOException when something goes wrong while reading fxml file
	 */
	public <T> T load() throws IOException {
		return fxmlLoader.load();
	}
	
	/**
	 * Gets controller for specified resource.
	 * Resource should be loaded first.
	 * If fxml file doesn't have controller, JavaFX will throw exception with error when calling this method.
	 * @return controller from loaded fxml file
	 */
	public <T> T getController() {
		return fxmlLoader.getController();
	}
	
}
