package pl.jowko.jrank.desktop;

import javafx.fxml.FXMLLoader;

import java.io.IOException;

/**
 * Created by Piotr on 2018-03-25.
 */
public class ResourceLoader {
	
	private FXMLLoader fxmlLoader;
	
	public ResourceLoader(String resourceName) {
		fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource(resourceName));
	}
	
	public <T> T load() throws IOException {
		return fxmlLoader.load();
	}
	
	public <T> T getController() {
		return fxmlLoader.getController();
	}
	
}
